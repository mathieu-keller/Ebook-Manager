import { createStore } from 'solid-js/store';
import { CollectionType } from '../Collection/Collection.type';

const initialState: CollectionType[] = [];

export const [collectionStore, setCollectionStore] = createStore<CollectionType[]>(initialState);
