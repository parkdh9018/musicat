import style from "./SongDetailModal.module.css";
import { useEffect, useState } from "react";

import { Song } from "@/types/home";
import { $ } from "@/connect/axios/setting";

interface SongDetailModalProps {
  musicSeq: number;
}

export const SongDetailModal = ({ musicSeq }: SongDetailModalProps) => {
  const [song, setSong] = useState<Song>();

  useEffect(() => {
    async function fetchData() {
      const detailFromAPI = await $.get(`/music/${musicSeq}`);
      setSong(detailFromAPI.data);
    }
    fetchData();
  }, []);

  console.log(song);

  if (song != undefined)
    return (
      <div className={style.songModalContainer}>
        <img
          className={style.songModalImg}
          src={`${song.musicImage}`}
          alt="곡 사진"
        />{" "}
        <span className={style.songModalContentBox}>
          <div className={style.songTitle}>{song.musicTitle}</div>
          <div className={style.songDetail}>{song.musicArtist}</div>
          <div className={style.songDetail}>앨범: {song.musicAlbum}</div>
          <div className={style.songDetail}>
            발매일: {song.musicReleaseDate}
          </div>
        </span>
      </div>
    );
  else return <div></div>;
};
