import style from "./SongList.module.css";
import { Song } from "@/connect/axios/queryHooks/music";

interface SongDetailModalProps {
  song: Song;
}

export const SongDetailModal = (song: Song) => {
  // const song: Song = {
  //   title: "ditto",
  //   artist: "newjeans",
  //   releaseDate: "2023-01-02",
  //   genre: "k-pop",
  //   album: "ditto",
  //   coverImg: "dummy src",
  // };

  return (
    <div className={style.songModalContainer}>
      <img className={style.songModalImg} src={song.musicCover} alt="곡 사진" />
      <span>
        <div>{song.musicTitle}</div>
        <div>{song.musicArtist}</div>
        {/* <div>{song.musicCreatedAt}</div>
        <div>{song.album}</div> */}
      </span>
    </div>
  );
};

// memberSeq: number;
// musicTitle: string;
// musicArtist: string;
// musicLength: TimeRanges | null;
// musicCover: ImageData | null;
// //
// musicSeq: number;
// musicCreatedAt: Date | null;
