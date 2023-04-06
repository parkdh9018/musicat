import { HTMLAttributes, useState } from "react";
import style from "./Input.module.css";

interface InputProps extends HTMLAttributes<HTMLInputElement> {
  type?: string;
  input: string;
  onKeyDown?: any;
  disabled? : boolean;
  setInput: React.Dispatch<React.SetStateAction<string>>;
}

export const Input = ({
  type,
  placeholder,
  input,
  onKeyDown,
  setInput,
  disabled,
  ...props
}: InputProps) => {
  return (
    <input
      className={style.input_text}
      onChange={(e) => {
        setInput(e.target.value);
      }}
      type={type}
      placeholder={placeholder}
      value={input}
      onKeyDown={(event) => {
        if (event.key === "Enter") onKeyDown();
      }}
      disabled = {disabled === undefined ? false : disabled}
      {...props}
    />
  );
};
