import { createEffect, createSignal, For, on, onMount, Show } from 'solid-js';

// eslint-disable-next-line no-unused-vars, no-use-before-define
type MultiSelectProps<T extends { [A in R]: string }, R extends keyof T> = {
  readonly data: readonly T[];
  readonly showValue: R;
  readonly onChange: (data: T[]) => void;
  readonly selected: readonly T[];
  readonly onCreateNew: (name: string) => void;
}

// eslint-disable-next-line no-unused-vars, no-use-before-define
function MultiSelect<T extends { [A in R]: string }, R extends keyof T> (props: MultiSelectProps<T, R>) {
  const [value, setValue] = createSignal<string>('');
  const [showDataSet, setShowDataSet] = createSignal<boolean>(false);
  const [focus, setFocus] = createSignal<boolean>(false);
  const [height, setHeight] = createSignal<number>(0);

  const selectData = (data: T) => {
    props.onChange([...props.selected, data]);
  };

  const onEnter = () => {
    const filteredData = props.data
      .filter(d => props.selected.find((select) => select[props.showValue] === d[props.showValue]) === undefined)
      .filter(d => d[props.showValue].toLowerCase() === value().toLowerCase());
    if (filteredData.length === 1) {
      selectData(filteredData[0]);
      setValue('');
    } else if (props.data.find(sub => sub[props.showValue].trim().toLowerCase() === value().trim().toLowerCase()) === undefined) {
      props.onCreateNew(value());
      setValue('');
    }
  };

  const setOptionMaxHeight = () => {
    const div = document.getElementById('options');
    if (div !== null) {
      div.style.maxHeight = `${window.innerHeight - div.offsetTop}}px`;
      setHeight(window.innerHeight - div.offsetTop);
    }
  };

  onMount(() => window.addEventListener('resize', setOptionMaxHeight));

  createEffect(on(showDataSet, setOptionMaxHeight));

  return (
    <div class="relative w-[100%]" onMouseLeave={() => !focus() && setShowDataSet(false)}>
      <input
        value={value() || ''}
        onFocusIn={() => {
          setShowDataSet(true);
          setFocus(true);
        }}
        onFocusOut={() => setFocus(false)}
        onInput={e => setValue(e.currentTarget.value)}
        class="w-[100%] text-xl bg-slate-300 dark:bg-slate-700"
        onKeyUp={(e) => {
          e.preventDefault();
          if (e.keyCode === 13) {
            onEnter();
          }
        }}
      />
      <Show when={showDataSet()}>
        <div id="options" style={`max-height: ${height()}px`}
             class="fixed overflow-y-auto border-2 border-white dark:bg-slate-900 dark:text-slate-300 bg-slate-50 text-slate-800 z-10">
          <Show
            when={value().trim() !== '' && props.data.find(sub => sub[props.showValue].trim().toLowerCase() === value().trim().toLowerCase()) === undefined}>
            <p
              class="p-1 hover:text-white hover:bg-slate-400 hover:border-transparent dark:hover:bg-slate-500 cursor-pointer"
              onClick={() => props.onCreateNew(value())}
            >
              Create new entry: {value()}
            </p>
          </Show>
          <For each={props.data
            .filter(d => props.selected.find((select) => select[props.showValue] === d[props.showValue]) === undefined)
            .filter(d => d[props.showValue].toLowerCase().startsWith(value()?.toLowerCase() || ''))}>
            {(d) => <p
              class="p-1 hover:text-white hover:bg-slate-400 hover:border-transparent dark:hover:bg-slate-500 cursor-pointer"
              onClick={() => selectData(d)}
            >
              {d[props.showValue]}
            </p>}
          </For>
        </div>
      </Show>
    </div>
  );
}

export default MultiSelect;
