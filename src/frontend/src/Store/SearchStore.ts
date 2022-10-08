import { createStore } from 'solid-js/store';
import { BookType } from '../Book/Book.type';

type SearchStoreType = {
  readonly search: string;
  readonly books: BookType[];
  readonly page: number;
  readonly allLoaded: boolean;
}

const initValues: SearchStoreType = {
  search: '',
  books: [],
  page: 1,
  allLoaded: false
};

export const [searchStore, setSearchStore] = createStore<SearchStoreType>({ ...initValues });

export const setSearch = (search: string) => {
  setSearchStore({
    ...initValues,
    search
  });
};
