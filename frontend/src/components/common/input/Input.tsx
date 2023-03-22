import { HTMLAttributes, useState } from "react";
import style from "./Input.module.css";

interface InputProps extends HTMLAttributes<HTMLInputElement> {
  type?: string;
  input: string;
  setInput: React.Dispatch<React.SetStateAction<string>>;
}

export const Input = ({
  type,
  placeholder,
  input,
  setInput,
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
      {...props}
    />
  );
};
