import style from "./SongDetailModal.module.css";
import { Song } from "@/connect/axios/queryHooks/music";
import { $ } from "@/connect/axios/setting";

interface SongDetailModalProps {
  song: Song;
}

export const SongDetailModal = ({ song }: SongDetailModalProps) => {
  if (song === null) {
    return null;
  }

  const getSongDetail = async () => {
    console.log(song);
    const detailFromAPI = await $.get(`/music/${song.musicSeq}`);
    console.log(detailFromAPI);
  };

  getSongDetail();

  console.log(song.musicImage);
  return (
    <div className={style.songModalContainer}>
      <img
        className={style.songModalImg}
        src={`${song.musicImage}`}
        alt="곡 사진"
      />
      <span className={style.songModalContentBox}>
        <div>{song.musicTitle}</div>
        <div>{song.musicArtist}</div>
        {/* <div>{song.musicCreatedAt}</div>
        <div>{song.album}</div> */}
      </span>
    </div>
  );
};
