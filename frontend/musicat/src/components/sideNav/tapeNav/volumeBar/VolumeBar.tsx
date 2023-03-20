import { useState } from "react";
import style from "./VolumeBar.module.css";

export const VolumeBar = () => {
  return (
    <input
      type="range"
      className={style.rangeInput}
      min="0"
      max="100"
      defaultValue={35}
      step="1"
    />
  );
};
