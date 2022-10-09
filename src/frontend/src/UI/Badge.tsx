import { Component, Show } from 'solid-js';

type BadgeProps = {
  readonly onClick?: () => void;
  readonly text: string;
  readonly onRemove?: () => void;
}

const Badge: Component<BadgeProps> = (props) => {
  const hoverClasses = props.onClick ? 'hover:text-white hover:bg-slate-400 hover:border-transparent dark:hover:bg-slate-500 cursor-pointer' : '';
  return (
    <div
      onClick={props.onClick}
      class={'float-left border-2 w-max m-2 rounded-2xl bg-transparent dark:border-slate-200 dark:text-slate-50 border-slate-500 text-slate-800 font-semibold ' + hoverClasses}
    >
      <div class="float-left p-2">
        {props.text}
      </div>
      <Show when={props.onRemove !== undefined}>
        <div class="hover:bg-red-500 float-right p-2 cursor-pointer rounded-r-2xl" onClick={props.onRemove}> X</div>
      </Show>
    </div>
  );
};

export default Badge;
