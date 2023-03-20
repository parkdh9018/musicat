import React from "react";
import { TapeButtons } from "./tapeButtons/TapeButtons";
import { Tape } from "@/components/sideNav/tapeNav/Tape/Tape";

export const TapeNav = () => {
  return (
    <>
      <TapeButtons />
      <div>TapeNav</div>;
      <Tape />
    </>
  );
};
