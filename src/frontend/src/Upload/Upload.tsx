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
  const uploadBooks = async (data: FormData): Promise<void> => {
    const allFiles: File[] = data.getAll('myFiles') as File[];
    const errors: string[] = [];
    setAllFilesCount(allFiles.length);
    setCurrentFile(0);
    for (const file of allFiles) {
      const form = new FormData();
      form.set('file', file);
      await Rest({showErrors: false}).post(UPLOAD_API, form, {
        onUploadProgress: (e: AxiosProgressEvent): void => {
          setMaxSize(e.total || 0);
          setCurrent(e.loaded);
        }
      })
        .then(r => {
          if (typeof r === 'string') {
            errors.push(file.name + ': ' + r);
          }
        })
        .catch(e => errors.push(file.name + ': ' + e))
        .finally(() => {
          setCurrentFile(allFiles.indexOf(file) + 1);
          setMaxSize(0);
          setCurrent(0);
        });
    }
    setMaxSize(null);
    setCurrent(null);
    setAllFilesCount(null);
    setCurrentFile(null);
    if (errors.length !== 0) {
      return Promise.reject(errors.join('\n'));
    }
    window.location.reload();
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
          {current() !== 0 && maxSize() !== 0 ? (Math.round((current()! / maxSize()!) * 10000)) / 100 : 0}% <br/>
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
