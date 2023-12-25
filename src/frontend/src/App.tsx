import type {Component} from 'solid-js';
import {lazy} from 'solid-js';

import {Route, Router} from '@solidjs/router';
import Header from './Header/Header';

const Book = lazy(() => import('./Book/Book'));
const Collection = lazy(() => import('./Collection/Collection'));
const Library = lazy(() => import('./Library/Library'));
const Search = lazy(() => import('./Search/Search'));

const App: Component = () => {
  return (
    <>
      <Header/>
      <Router>
        <Route path="/" component={Library}/>
        <Route path="/collection/:collectionId/:collection" component={Collection}/>
        <Route path="/book/:bookId/:book" component={Book}/>
        <Route path="/search" component={Search}/>
      </Router>
    </>
  );
};

export default App;
