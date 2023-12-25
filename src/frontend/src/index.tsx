/* @refresh reload */
import {render} from 'solid-js/web';

import './index.css';
import {Route, Router} from '@solidjs/router';
import {type Component, lazy} from 'solid-js';
import Header from './Header/Header';
import type {RouteSectionProps} from '@solidjs/router/dist/types';

const Book = lazy(() => import('./Book/Book'));
const Collection = lazy(() => import('./Collection/Collection'));
const Library = lazy(() => import('./Library/Library'));

const Search = lazy(() => import('./Search/Search'));
const App: Component<RouteSectionProps> = (props: RouteSectionProps) => (
    <>
        <Header/>
        {props.children}
    </>
);
render(() => <Router root={App}>
    <Route path="/collection/:collectionId/:collection" component={Collection}/>
    <Route path="/book/:bookId/:book" component={Book}/>
    <Route path="/search" component={Search}/>
    <Route path="/" component={Library}/>
</Router>, document.getElementById('root') as HTMLElement);
