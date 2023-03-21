import { Button } from "@/components/common/button/Button";
import { v4 as uuidv4 } from "uuid";
import style from "./SongList.module.css";

interface Song {
  title: string;
  artist: string;
}

export const SongList = () => {
  const songs: Song[] = [
    { title: "ditto", artist: "newjeans" },
    { title: "파이팅해야지", artist: "부석순" },
    { title: "죽겠다", artist: "ikon" },
  ];

  const onDetail = () => {
    console.log("detail~~~~~~~~");
  };

  const songList: JSX.Element[] = songs.map((song) => (
    <div className={style.songList} key={uuidv4()}>
      <span className={style.songSpan}>
        {song.title} - {song.artist}
      </span>
      <button className={style.songBtn} onClick={onDetail}>
        ...
      </button>
    </div>
  ));
  return <div>{songList}</div>;
};
