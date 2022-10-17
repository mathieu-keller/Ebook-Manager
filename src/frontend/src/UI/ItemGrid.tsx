import {Component, For} from 'solid-js';
import ItemCard from './ItemCard';
import {LibraryItemType} from '../Library/LibraryItem.type';
import {useNavigate} from '@solidjs/router';

type ItemGridProps = {
  readonly items: readonly LibraryItemType[];
}

const ItemGrid: Component<ItemGridProps> = (props) => {
  const navigate = useNavigate();
  const openItem = (item: LibraryItemType) => {
    if (item.itemType === 'collection') {
      navigate(`/collection/${item.id}/${encodeURIComponent(item.title)}`);
    } else {
      navigate(`/book/${item.id}/${encodeURIComponent(item.title)}`);
    }
  };

  return (
    <div class="flex flex-wrap flex-row justify-center">
      <For each={props.items}>
        {(item) => (
          <ItemCard
            name={item.title}
            itemCount={item.itemType === 'collection' ? item.bookCount : undefined}
            id={item.id}
            itemType={item.itemType}
            onClick={() => openItem(item)}
          />)}
      </For>
    </div>
  );
};

export default ItemGrid;
