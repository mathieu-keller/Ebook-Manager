import type {Author} from '../Author/Author.type';
import type {Subject} from '../Subject/Subject.type';

export type BookType = {
  readonly id: number;
  readonly title: string;
  readonly published: string;
  readonly language: string;
  readonly subjects: Subject[];
  readonly publisher: string;
  readonly authors: Author[];
  readonly collection: {
    readonly id: number;
    readonly title: string;
  };
  readonly collectionIndex: number;
}
