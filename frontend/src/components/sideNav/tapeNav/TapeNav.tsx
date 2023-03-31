import { TapeButtons } from "./tapeButtons/TapeButtons";
import { Tape } from "@/components/sideNav/tapeNav/Tape/Tape";
import { VolumeBar } from "./volumeBar/VolumeBar";
import style from "./TapeNav.module.css"

export const TapeNav = () => {
  return (
    <div className={style.tapeNav}>
      <TapeButtons />
      <VolumeBar />
      <Tape />
    </div>
  );
};
