import { HTMLAttributes, useState } from "react";
import style from "@/components/common/input/Input.module.css";

interface InputProps extends HTMLAttributes<HTMLInputElement> {
  type?: string;
}

export const Input = ({ type, placeholder, ...props }: InputProps) => {
  const [input, setInput] = useState("");

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
