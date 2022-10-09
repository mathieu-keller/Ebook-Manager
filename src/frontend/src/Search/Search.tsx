import { createSignal, onCleanup, onMount, Show } from 'solid-js';
import { setHeaderTitle } from '../Store/HeaderStore';
import Rest from '../Rest';
import { SEARCH_API } from '../Api/Api';
import ItemGrid from '../UI/ItemGrid';
import { searchStore, setSearch, setSearchStore } from '../Store/SearchStore';
import { BookType } from '../Book/Book.type';
import { useSearchParams } from 'solid-app-router';

const Search = () => {
  const [loading, setLoading] = createSignal<boolean>(false);
  const [searchValue, setSearchValue] = createSignal<string>('');
  const [getSearchParams, setSearchParams] = useSearchParams<{ readonly q?: string }>();
  onMount(() => {
    const searchParam = getSearchParams.q === undefined ? searchStore.search : getSearchParams.q;
    setSearchValue(searchParam);
    setHeaderTitle(`Search: ${searchParam}`);
    if (getSearchParams.q !== searchStore.search) {
      if (getSearchParams.q === undefined) {
        setSearchParams({ q: searchParam });
      } else {
        setSearch(searchParam);
      }
      setTimeout(() => search(), 0);
    }
    window.addEventListener('scroll', shouldLoadNextPage);
  });

  const [searchRequestTimer, setSearchRequestTimer] = createSignal<number | null>(null);

  const search = () => {
    if (!searchStore.allLoaded && !loading()) {
      setLoading(true);
      getBooks(searchStore.page).then(r => {
        if (r.length > 0) {
          setSearchStore({
            page: searchStore.page + 1,
            books: [...searchStore.books, ...r]
          });
          window.setTimeout(() => shouldLoadNextPage(), 50);
        } else if (r.length === 0 || r.length > 32) {
          setSearchStore({ allLoaded: true });
        }
        setLoading(false);
      });
    } else {
      if (searchRequestTimer() == null) {
        setSearchRequestTimer(setTimeout(() => {
          search();
          setSearchRequestTimer(null);
        }, 200));
      }
    }
  };

  const getBooks = async (page: number): Promise<BookType[]> => {
    const response = await Rest.get<BookType[]>(SEARCH_API(searchStore.search, page));
    return response.data;
  };

  const shouldLoadNextPage = (): void => {
    const element = document.querySelector('#loading-trigger');
    const position = element?.getBoundingClientRect();
    if (position !== undefined && position.top >= 0 && position.bottom <= window.innerHeight) {
      search();
    }
  };

  const [searchInputTimer, setSearchInputTimer] = createSignal<number | null>(null);
  const changeSearchValue = (inputValue: string) => {
    setSearchValue(inputValue);
    setHeaderTitle(`Search: ${inputValue}`);
    if (searchInputTimer() == null) {
      setSearchInputTimer(setTimeout(() => {
        setSearch(searchValue());
        setSearchParams({ q: searchValue() });
        setSearchInputTimer(null);
        search();
      }, 1000));
    }
  };

  onCleanup(() => {
    window.removeEventListener('scroll', shouldLoadNextPage);
    const searchInputTimeout = searchInputTimer();
    if (searchInputTimeout != null) {
      clearTimeout(searchInputTimeout);
    }
    const searchRequestTimeout = searchRequestTimer();
    if (searchRequestTimeout != null) {
      clearTimeout(searchRequestTimeout);
    }
  });

  return (
    <>
      <input
        class="w-[100%] text-5xl bg-slate-300 dark:bg-slate-700"
        placeholder="Search Books, Authors and Subjects"
        value={searchValue()}
        onInput={e => changeSearchValue(e.currentTarget.value)}
      />
      <ItemGrid
        items={searchStore.books.map(book => ({
          id: book.id,
          title: book.title,
          cover: book.cover,
          itemType: 'book',
          bookCount: 1
        }))}
      />
      <Show when={!searchStore.allLoaded}>
        <div
          id="loading-trigger"
          onClick={() => search()}
          class="m-5 border cursor-pointer text-center text-5xl"
        >
          Load More
        </div>
      </Show>
    </>
  );
};

export default Search;
