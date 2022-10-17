import {BookType} from './Book.type';
import {createSignal, For, onMount, Show} from 'solid-js';
import Rest from '../Rest';
import {BOOK_API, DOWNLOAD_API} from '../Api/Api';
import defaultCover from '../assets/cover.jpg';
import downloadIcon from '../assets/download.svg';
import Badge from '../UI/Badge';
import {LinkButton} from '../UI/Button';
import {useNavigate, useParams} from 'solid-app-router';
import {setHeaderTitle} from '../Store/HeaderStore';
import {setSearch} from '../Store/SearchStore';

const Book = () => {
  const [book, setBook] = createSignal<BookType | null>(null);
  const path = useParams<{ readonly book: string; readonly bookId: string }>();
  const getBook = async (): Promise<BookType> => {
    const bookId = Number(path.bookId);
    if (!isNaN(bookId)) {
      const response = await Rest.get<BookType>(BOOK_API(bookId));
      return response.data;
    }
    return Promise.reject(new Error(`book id ${path.bookId} is not a number!`));
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
            src={book()!.cover !== undefined ? book()!.cover : defaultCover}
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
          <LinkButton
            href={DOWNLOAD_API(book()!.id)}
            download={true}
            className="col-start-1 col-end-3 mt-5 flex justify-self-stretch"
          >
            <img
              class="dark:invert invert-0 h-8 mr-1"
              src={downloadIcon}
              alt="download"
            /> Download
          </LinkButton>
        </div>
      </div>
    </Show>
  );
};

export default Book;
