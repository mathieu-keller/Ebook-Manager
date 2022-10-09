import type { Component } from 'solid-js';

import { Route, Routes } from 'solid-app-router';
import Header from './Header/Header';
import { lazy } from 'solid-js';

const Book = lazy(() => import('./Book/Book'));
const Collection = lazy(() => import('./Collection/Collection'));
const Library = lazy(() => import('./Library/Library'));
const Search = lazy(() => import('./Search/Search'));

const App: Component = () => {
  return (
    <>
      <Header/>
      <Routes>
        <Route path="/" element={<Library/>}/>
        <Route path="/collection/:collection" element={<Collection/>}/>
        <Route path="/book/:book" element={<Book/>}/>
        <Route path="/search" element={<Search/>}/>
      </Routes>
    </>
  );
};

export default App;
