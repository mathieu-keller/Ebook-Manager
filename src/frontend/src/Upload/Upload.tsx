import Modal from '../UI/Modal';
import {Button, PrimaryButton} from '../UI/Button';
import {Component, createSignal, Show} from 'solid-js';
import uploadIcon from '../assets/upload.svg';
import {UPLOAD_API} from '../Api/Api';
import Rest from '../Rest';
import {AxiosProgressEvent} from 'axios';

type UploadProps = {
  readonly onClose: () => void;
}

const Upload: Component<UploadProps> = (props) => {
  const [maxSize, setMaxSize] = createSignal<number | null>(null);
  const [current, setCurrent] = createSignal<number | null>(null);
  const [allFilesCount, setAllFilesCount] = createSignal<number | null>(null);
  const [currentFile, setCurrentFile] = createSignal<number | null>(null);
  const [errors, setErrors] = createSignal<string[]>([]);
  const uploadBooks = async (data: FormData): Promise<void> => {
    const allFiles = data.getAll('myFiles');
    setAllFilesCount(allFiles.length);
    setCurrentFile(0);
    for (const file of allFiles) {
      await Rest.post(UPLOAD_API, file, {
        headers: {'Content-Type': 'application/octet-stream'},
        onUploadProgress: (e: AxiosProgressEvent): void => {
          setMaxSize(e.total || null);
          setCurrent(e.loaded);
        }
      }).catch(e => setErrors([...errors(), e]))
        .finally(() => {
          setCurrentFile(allFiles.indexOf(file) + 1);
          setMaxSize(null);
          setCurrent(null);
        });
    }
    setAllFilesCount(null);
    setCurrentFile(null);
    if (errors().length !== 0) {
      // eslint-disable-next-line no-console
      console.error(errors().join('\n'));
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
        <Show when={allFilesCount() !== null} keyed>
          <h1>Files: </h1>
          <progress value={currentFile()!} max={allFilesCount()!}/>
          {(Math.round((currentFile()! / allFilesCount()!) * 10000)) / 100}% <br/>
          ({currentFile()!} / {allFilesCount()!})
        </Show>
        <Show when={current() !== null && maxSize() !== null} keyed>
          <h1>Upload: </h1>
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
