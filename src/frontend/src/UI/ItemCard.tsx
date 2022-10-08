import { Component, createSignal, Show } from 'solid-js';
import { Button, LinkButton } from './Button';
import { DOWNLOAD_API, DOWNLOAD_ORIGINAL_API } from '../Api/Api';
import defaultCover from '../assets/cover.jpg';
import menuIcon from '../assets/menu.svg';
import EditBook from '../Book/EditBook';

type ItemCardProps = {
  readonly id: number;
  readonly onClick: () => void;
  readonly name: string;
  readonly cover: string | null;
  readonly itemType: 'book' | 'collection';
  readonly itemCount?: number;
}

const ItemCard: Component<ItemCardProps> = (props) => {
  const [showOptions, setShowOptions] = createSignal<boolean>(false);
  const [showEditSubjects, setShowEditSubjects] = createSignal<boolean>(false);

  return (
    <>
      <Show when={showEditSubjects()}>
        <EditBook onClose={() => setShowEditSubjects(false)} title={props.name}/>
      </Show>
      <div class="m-3 p-2 flex h-max w-80 flex-col" onMouseLeave={() => setShowOptions(false)}>
        <div onClick={props.onClick} class="flex justify-center hover:pb-3 cursor-pointer hover:mt-0 hover:mb-3 p-0 my-3 relative">
          <Show when={props.itemCount !== undefined}>
            <div class="absolute p-3 left-5 top-0 text-5xl bg-red-700 text-white rounded-b-full">
              {props.itemCount}
            </div>
          </Show>

          <img
            src={props.cover === null ? defaultCover : props.cover}
            alt={`cover picture of ${props.name}`}
            width="270"
            height="470"
          />
        </div>
        <div>
          <h1
            onClick={props.onClick}
            class={'cursor-pointer text-center break-words text-2xl font-bold ' + (props.itemType === 'book' ? 'float-left w-10/12' : 'w-12/12')}
          >
            {props.name}
          </h1>
          <Show when={props.itemType === 'book'}>
            <div class="w-2/12 relative float-right">
              <Button
                onClick={() => setShowOptions(!showOptions())}
              >
                <img
                  src={menuIcon}
                  alt="menu"
                  width="30"
                  height="30"
                  class="dark:invert invert-0 h-8"
                />
              </Button>
              <Show when={showOptions()}>
                <div class="absolute right-0 w-max border-2 border-white dark:bg-slate-900 dark:text-slate-300 bg-slate-50 text-slate-800 z-10">
                  <LinkButton
                    download={true}
                    href={DOWNLOAD_API(props.id)}
                    className="p-2 w-[100%]"
                  >
                    Download Book
                  </LinkButton>
                  <LinkButton
                    download={true}
                    href={DOWNLOAD_ORIGINAL_API(props.id)}
                    className="p-2 w-[100%]"
                  >
                    Download Original Book
                  </LinkButton>
                  <Button className="p-2 w-[100%]" onClick={() => setShowEditSubjects(true)}>
                    Edit
                  </Button>
                </div>
              </Show>
            </div>
          </Show>
        </div>
      </div>
    </>
  );
};

export default ItemCard;
