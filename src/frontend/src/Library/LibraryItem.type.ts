export type LibraryItemType = {
  readonly id: number;
  readonly title: string;
  readonly itemType: 'book' | 'collection';
  readonly bookCount: number;
}
