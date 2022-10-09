import { FlowComponent } from 'solid-js';
import { Link } from 'solid-app-router';

type ButtonProps = {
  readonly onClick?: () => void;
  readonly form?: string;
  readonly type?: 'submit' | 'reset' | 'button';
  readonly className?: string;
}

export const Button: FlowComponent<ButtonProps> = (props) => {
  const className = 'bg-transparent flex justify-center items-center dark:border-slate-200 dark:hover:bg-slate-500 dark:text-slate-50 border-slate-500 ' +
    'hover:bg-slate-400 text-slate-800 font-semibold hover:text-white py-1 px-2 border hover:border-transparent rounded ';
  return (
    <button
      onClick={props.onClick}
      type={props.type}
      form={props.form}
      class={className + (props.className || '')}
    >
      {props.children}
    </button>
  );
};

export const PrimaryButton: FlowComponent<ButtonProps> = (props) => {
  const className = 'dark:bg-red-900 flex justify-center items-center dark:hover:bg-red-800 bg-red-500 hover:bg-red-400 text-white font-bold py-1 px-2 rounded ';
  return (
    <button
      onClick={props.onClick}
      type={props.type}
      form={props.form}
      class={className + (props.className || '')}
    >
      {props.children}
    </button>
  );
};

type LinkButtonProps = {
  readonly download?: boolean;
  readonly href: string;
  readonly className?: string;
};

export const LinkButton: FlowComponent<LinkButtonProps> = (props) => {
  const className = 'w-[100%] text-center flex justify-center items-center cursor-pointer bg-transparent dark:border-slate-200 dark:hover:bg-slate-500 ' +
    'dark:text-slate-50 border-slate-500 hover:bg-slate-400 text-slate-800 font-semibold hover:text-white py-1 px-2 border hover:border-transparent rounded ';
  return (
    <a
      download={props.download}
      href={props.href}
      class={className + (props.className || '')}
    >
      {props.children}
    </a>
  );
};

export const NavLinkButton: FlowComponent<LinkButtonProps> = (props) => {
  const className = 'w-[100%] text-center flex justify-center items-center cursor-pointer bg-transparent dark:border-slate-200 dark:hover:bg-slate-500 ' +
    'dark:text-slate-50 border-slate-500 hover:bg-slate-400 text-slate-800 font-semibold hover:text-white py-1 px-2 border hover:border-transparent rounded ';
  return (
    <Link
      href={props.href}
      class={className + (props.className || '')}
    >
      {props.children}
    </Link>
  );
};
