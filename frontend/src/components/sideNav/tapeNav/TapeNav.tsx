import { TapeButtons } from "./tapeButtons/TapeButtons";
import { Tape } from "@/components/sideNav/tapeNav/Tape/Tape";
import { VolumeBar } from "./volumeBar/VolumeBar";
import style from "./TapeNav.module.css";
import { getUserConfig } from "@/connect/axios/queryHooks/user";
import { CDplayer } from "./CDplayer/CDplayer";

export const TapeNav = () => {
  const { data: theme } = getUserConfig();
  return (
    <div className={style.tapeNav}>
      <TapeButtons />
      <VolumeBar />
      {theme?.data.themeSeq !== 2 ? <Tape /> : <CDplayer />}
    </div>
  );
};
