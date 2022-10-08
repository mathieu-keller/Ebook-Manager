import Modal from '../UI/Modal';
import { Component, createSignal, For, onMount, Show } from 'solid-js';
import { BookType } from './Book.type';
import Rest from '../Rest';
import { BOOK_API } from '../Api/Api';
import Badge from '../UI/Badge';
import { Subject } from '../Subject/Subject.type';
import MultiSelect from '../UI/MultiSelect';
import { Button, PrimaryButton } from '../UI/Button';
import { AxiosResponse } from 'axios';

type EditSubjectsProps = {
  readonly onClose: () => void;
  readonly title: string;
};

const EditBook: Component<EditSubjectsProps> = (props) => {
  const [book, setBook] = createSignal<BookType | null>(null);
  const [allSubjects, setAllSubjects] = createSignal<Subject[]>([]);
  onMount(() => {
    Rest.get<BookType>(BOOK_API(props.title))
      .then(r => setBook(r.data));
    Rest.get<Subject[]>('/api/subjects').then(r => setAllSubjects(r.data));
  });

  const submit = () => {
    Rest.put<Subject[], AxiosResponse<Subject[]>, BookType>('/api/book/', book()!)
      .then(() => props.onClose());
  };

  return (
    <Show when={book() !== null}>
      <Modal
        onClose={props.onClose}
        title={props.title}
      >
        <div class="p-5">
          <h1 class="font-bold text-2xl">Subjects:</h1>
          <MultiSelect<Subject, 'name'>
            data={allSubjects()}
            selected={book()!.subjects}
            showValue="name"
            onCreateNew={(name) => {
              const newSubject = { name: name.charAt(0).toUpperCase() + name.slice(1) };
              setAllSubjects([...allSubjects(), newSubject]);
              setBook({
                ...book()!,
                subjects: [...book()!.subjects, newSubject]
              });
            }}
            onChange={data => setBook({
              ...book()!,
              subjects: data
            })}/>
          <div class="flex flex-row flex-wrap pt-1">
            <For each={book()!.subjects}>
              {(subject) => <Badge text={subject.name} onRemove={() => {
                setBook({
                  ...book()!,
                  subjects: book()!.subjects.filter(sub => sub.name !== subject.name)
                });
              }}/>}
            </For>
          </div>
        </div>
        <footer class="border-t-2 w-full pt-5 flex justify-center">
          <div class="flex justify-around w-full">
            <PrimaryButton onClick={submit} className="w-1/2">
              Submit
            </PrimaryButton>
            <Button button-type="default" onClick={props.onClose} className="w-1/2">
              Close
            </Button>
          </div>
        </footer>
      </Modal>
    </Show>
  );
};

export default EditBook;
