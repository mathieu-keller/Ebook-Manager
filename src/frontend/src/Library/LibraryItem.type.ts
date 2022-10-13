export type LibraryItemType = {
  readonly id: number;
  readonly cover?: string;
  readonly title: string;
  readonly itemType: 'book' | 'collection';
  readonly bookCount: number;
}
