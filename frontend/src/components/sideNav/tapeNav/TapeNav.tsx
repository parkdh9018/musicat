import React from "react";
import { TapeButtons } from "./tapeButtons/TapeButtons";
import { Tape } from "@/components/sideNav/tapeNav/Tape/Tape";
import { VolumeBar } from "./volumeBar/VolumeBar";

export const TapeNav = () => {
  return (
    <>
      <TapeButtons />
      <VolumeBar />
      <Tape />
    </>
  );
};
