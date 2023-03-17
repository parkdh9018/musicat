import { HTMLAttributes } from "react";

interface ButtonProps extends HTMLAttributes<HTMLDivElement> {
  content: string;
  onClick: () => void;
}

export const TapeButton = ({ content, onClick, ...props }: ButtonProps) => {
  return <div>TapeButton</div>;
};
