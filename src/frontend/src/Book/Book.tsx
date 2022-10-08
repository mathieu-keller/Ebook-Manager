import { BookType } from './Book.type';
import { createSignal, For, onMount, Show } from 'solid-js';
import Rest from '../Rest';
import { BOOK_API, DOWNLOAD_API, DOWNLOAD_ORIGINAL_API } from '../Api/Api';
import defaultCover from '../assets/cover.jpg';
import downloadIcon from '../assets/download.svg';
import Badge from '../UI/Badge';
import { LinkButton } from '../UI/Button';
import { useNavigate, useParams } from 'solid-app-router';
import { setHeaderTitle } from '../Store/HeaderStore';
import { setSearch } from '../Store/SearchStore';

const Book = () => {
  const [book, setBook] = createSignal<BookType | null>(null);
  const path = useParams<{ readonly book: string }>();
  const getBook = async (): Promise<BookType> => {
    const response = await Rest.get<BookType>(BOOK_API(path.book));
    return response.data;
  };

  onMount(() => {
    setHeaderTitle(decodeURIComponent(path.book));
    getBook()
      .then(book => setBook(book));
  });

  const navigator = useNavigate();

  const search = (searchValue: string) => {
    setSearch(searchValue);
    navigator('/search');
  };

  return (
    <Show when={book() !== null} fallback={<h1>Loading...</h1>}>
      <div class="mt-10 flex justify-center">
        <div class="grid max-w-[100%] sm:max-w-[90%] md:max-w-[70%] xl:max-w-[50%]">
          <img
            alt={`cover picture of ${book()!.title}`}
            src={book()!.cover !== null ? book()!.cover : defaultCover}
            width="270"
            height="470"
          />
          <div class="grid-cols-1 grid h-max">
            <div class="m-5">
              <h1>Authors:</h1>
              <For each={book()!.authors}>
                {(author) => (
                  <Badge
                    onClick={() => search(author.name)}
                    text={author.name}
                  />)}
              </For>
            </div>
            <div class="m-5">
              <h1>Subjects:</h1>
              <For each={book()!.subjects}>
                {(subject) => (
                  <Badge
                    onClick={() => search(subject.name)}
                    text={subject.name}
                  />)}
              </For>
            </div>
          </div>
          <div class="col-start-1 col-end-3 mt-5 flex justify-self-stretch">
            <LinkButton
              href={DOWNLOAD_API(book()!.id)}
              download={true}
              className="w-6/12"
            >
              <img
                class="dark:invert invert-0 h-8 mr-1"
                src={downloadIcon}
                alt="download"
              /> Download
            </LinkButton>
            <LinkButton
              href={DOWNLOAD_ORIGINAL_API(book()!.id)}
              download={true}
              className="w-6/12"
            >
              <img
                class="dark:invert invert-0 h-8 mr-1"
                src={downloadIcon}
                alt="download"
              /> Download Original
            </LinkButton>
          </div>
        </div>
      </div>
    </Show>
  );
};

export default Book;
