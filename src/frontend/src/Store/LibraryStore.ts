import { createStore } from 'solid-js/store';
import { LibraryItemType } from '../Library/LibraryItem.type';

type LibraryStoreType = {
  readonly libraryItems: readonly LibraryItemType[];
  readonly page: number;
  readonly allLoaded: boolean;
}

const initialState: LibraryStoreType = {
  libraryItems: [],
  page: 1,
  allLoaded: false
};

export const [libraryStore, setLibraryStore] = createStore<LibraryStoreType>(initialState);
