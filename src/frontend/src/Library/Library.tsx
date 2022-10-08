import { Component, createSignal, onCleanup, onMount, Show } from 'solid-js';
import ItemGrid from '../UI/ItemGrid';
import { LibraryItemType } from './LibraryItem.type';
import { LIBRARY_API } from '../Api/Api';
import Rest from '../Rest';
import { libraryStore, setLibraryStore } from '../Store/LibraryStore';
import { resetHeaderTitle } from '../Store/HeaderStore';

const Library: Component = () => {
  const [loading, setLoading] = createSignal<boolean>(false);

  onMount(() => {
    resetHeaderTitle();
    window.addEventListener('scroll', shouldLoadNextPage);
    loadLibraryItems();
  });

  const loadLibraryItems = () => {
    if (!libraryStore.allLoaded && !loading()) {
      setLoading(true);
      getLibraryItems(libraryStore.page).then(r => {
        if (r.length > 0) {
          setLoading(false);
          setLibraryStore({
            page: libraryStore.page + 1,
            libraryItems: [...libraryStore.libraryItems, ...r]
          });
          window.setTimeout(() => shouldLoadNextPage(), 50);
        } else if (r.length === 0 || r.length > 32) {
          setLibraryStore({ allLoaded: true });
        }
      });
    }
  };

  const getLibraryItems = async (page: number): Promise<LibraryItemType[]> => {
    const response = await Rest.get<LibraryItemType[]>(LIBRARY_API(page));
    return response.data;
  };

  const shouldLoadNextPage = (): void => {
    const element = document.querySelector('#loading-trigger');
    const position = element?.getBoundingClientRect();
    if (position !== undefined && position.top >= 0 && position.bottom <= window.innerHeight) {
      loadLibraryItems();
    }
  };

  onCleanup(() => {
    window.removeEventListener('scroll', shouldLoadNextPage);
  });

  return (
    <>
      <ItemGrid
        items={libraryStore.libraryItems}
      />
      <Show when={!libraryStore.allLoaded}>
        <div id="loading-trigger" onClick={loadLibraryItems} class="m-5 border cursor-pointer text-center text-5xl">Load More</div>
      </Show>
    </>
  );
};

export default Library;
