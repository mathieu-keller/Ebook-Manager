const API_PREFIX = '/api';
export const DOWNLOAD_API = (id: number) => `${API_PREFIX}/book/${id}/download`;
export const SEARCH_API = (search: string, page: number) => {
  const params = new URLSearchParams({page: page.toString()});
  params.append('q', search);
  return `${API_PREFIX}/library?${params.toString()}`;
};
export const BOOK_API = (bookId: number) => `${API_PREFIX}/book/${bookId}`;
export const LIBRARY_API = (page: number) => `${API_PREFIX}/library?page=${page}`;
export const COLLECTION_API = (collectionId: number) => `${API_PREFIX}/collection/${collectionId}`;
export const UPLOAD_API = `${API_PREFIX}/book/upload`;
