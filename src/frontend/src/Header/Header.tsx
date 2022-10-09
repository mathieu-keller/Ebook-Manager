import { Component, createSignal, onMount, Show } from 'solid-js';
import Upload from '../Upload/Upload';
import { Button, NavLinkButton, PrimaryButton } from '../UI/Button';
import uploadIcon from '../assets/upload.svg';
import { useNavigate, useSearchParams } from 'solid-app-router';
import { headerStore } from '../Store/HeaderStore';
import menuIcon from '../assets/menu.svg';

const Header: Component = () => {
  const navigate = useNavigate();
  const [isDarkMode, setDarkMode] = createSignal<boolean>(window.matchMedia('(prefers-color-scheme: dark)').matches);
  const [showUploadModal, setShowUploadModal] = createSignal<boolean>(false);
  const [showOptions, setShowOptions] = createSignal<boolean>(false);
  const [searchParams] = useSearchParams<{ readonly q?: string }>();

  const setDarkClass = () => {
    if (isDarkMode()) {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  };

  const setDark = (): void => {
    setDarkMode(old => !old);
    setDarkClass();
  };

  onMount(() => {
    setDarkClass();
  });

  return (
    <>
      <Show when={showUploadModal()}>
        <Upload onClose={() => setShowUploadModal(false)}/>
      </Show>
      <div class="flex flex-row justify-between border-b-2" onMouseLeave={() => setShowOptions(false)}>
        <div>
          <Button className="h-[100%]" onClick={() => navigate('/')}>
            Home
          </Button>
        </div>
        <h1 class="text-5xl m-2 font-bold break-all max-w-[90%]">{headerStore.title}</h1>
        <div class="relative">
          <Button className="h-[100%]" onClick={() => setShowOptions(!showOptions())}>
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
              <PrimaryButton
                className="w-[100%]"
                onClick={() => setShowUploadModal(true)}
              >
                <img
                  class="dark:invert invert-0 h-8 mr-1"
                  src={uploadIcon}
                  alt="upload"
                /> Upload!
              </PrimaryButton>
              <NavLinkButton
                className="w-[100%]"
                href={`/search${searchParams.q !== undefined ? '?' + new URLSearchParams({ q: searchParams.q }).toString() : ''}`}
              >
                Search
              </NavLinkButton>
              <Button className="w-[100%]" onClick={setDark}>
                {isDarkMode() ? 'Light mode' : 'Dark mode'}
              </Button>
            </div>
          </Show>
        </div>
      </div>
    </>
  );
};

export default Header;
