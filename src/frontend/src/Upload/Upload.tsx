import Modal from '../UI/Modal';
import { Button, PrimaryButton } from '../UI/Button';
import { Component, createSignal, Show } from 'solid-js';
import uploadIcon from '../assets/upload.svg';
import { UPLOAD_API } from '../Api/Api';
import Rest from '../Rest';
import { AxiosProgressEvent } from 'axios';

type UploadProps = {
  readonly onClose: () => void;
}

const Upload: Component<UploadProps> = (props) => {
  const [maxSize, setMaxSize] = createSignal<number | null>(null);
  const [current, setCurrent] = createSignal<number | null>(null);

  const uploadBooks = async (data: FormData): Promise<void> => {
    const response = await Rest.post(UPLOAD_API, data, {
      onUploadProgress: (e: AxiosProgressEvent): void => {
        setMaxSize(e.total || null);
        setCurrent(e.loaded);
      }
    });
    if (response.status === 200) {
      location.reload();
    }
  };

  const onSubmit = (e: any): void => {
    e.preventDefault();
    const form = new FormData(e.currentTarget);
    uploadBooks(form)
      .then((): void => props.onClose())
      .catch((e: string): void => window.alert(e));
  };

  return (
    <Modal
      onClose={props.onClose}
      title="Upload E-Book">
      <div class="p-5">
        <form
          id="upload-epub"
          onSubmit={onSubmit}
        >
          <input type="file" accept="application/epub+zip" name="myFiles" multiple/>
        </form>
        <Show when={current() !== null && maxSize() !== null} keyed>
          <progress value={current()!} max={maxSize()!}/>
          {(Math.round((current()! / maxSize()!) * 10000)) / 100}% <br/>
          ({current()!} / {maxSize()!})
        </Show>
      </div>
      <footer class="border-t-2 w-full pt-5 flex justify-center">
        <div class="flex justify-around w-full">
          <PrimaryButton type="submit" form="upload-epub">
            <img
              class="dark:invert invert-0 h-8 mr-1"
              src={uploadIcon}
              alt="upload"
            /> Upload
          </PrimaryButton>
          <Button button-type="default" onClick={props.onClose}>
            Close
          </Button>
        </div>
      </footer>
    </Modal>
  );
};

export default Upload;
