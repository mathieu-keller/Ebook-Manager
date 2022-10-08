import type { BookType } from '../Book/Book.type';

export type Author = {
  readonly id: number;
  readonly name: string;
  readonly books: BookType[];
}
