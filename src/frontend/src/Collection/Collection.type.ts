import type { BookType } from '../Book/Book.type';

export type CollectionType = {
  readonly id: number;
  readonly title: string;
  readonly books: BookType[];
}
