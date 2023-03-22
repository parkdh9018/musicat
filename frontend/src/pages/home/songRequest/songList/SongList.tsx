import { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import style from "./SongList.module.css";
import { SongDetailNav } from "../songDetailBox/SongDetailNav";

interface Song {
  musicSeq: number;
  memberSeq: number;
  musicCreatedAt: Date | null;
  musicName: string;
  musicArtist: string;
  musicLength: TimeRanges | null;
  musicCover: ImageData | null;
}

export const SongList = () => {
  //  const songs: Song[] = "api 요청 리액트 쿼리로 받아오기"

  // 더미데이터
  const songs: Song[] = [
    {
      musicSeq: 1,
      memberSeq: 1,
      musicCreatedAt: null,
      musicName: "ditto",
      musicArtist: "newjeans",
      musicLength: null,
      musicCover: null,
    },
    {
      musicSeq: 2,
      memberSeq: 1,
      musicCreatedAt: null,
      musicName: "파이팅해야지",
      musicArtist: "부석순",
      musicLength: null,
      musicCover: null,
    },
    {
      musicSeq: 3,
      memberSeq: 1,
      musicCreatedAt: null,
      musicName: "죽겠다",
      musicArtist: "ikon",
      musicLength: null,
      musicCover: null,
    },
  ];

  const [isDetailOpened, setIsDetailOpened] = useState<{
    [id: number]: boolean;
  }>({});

  const toggleSongDetail = (id: number) => {
    // 다른 부분 클릭하면 기존 창 닫는 로직 추가 필요
    setIsDetailOpened((prevStatus) => ({
      ...prevStatus,
      [id]: !prevStatus[id],
    }));
  };

  const songList: JSX.Element[] = songs.map((song) => (
    <div className={style.songList} key={uuidv4()}>
      <span className={style.songSpan}>
        {song.musicName} - {song.musicArtist}
      </span>
      {isDetailOpened[song.musicSeq] ? (
        //위치 변경 필요 (버튼에 옆에 떠있는 포지션으로)
        <SongDetailNav />
      ) : null}
      <button
        className={style.songBtn}
        onClick={() => toggleSongDetail(song.musicSeq)}
      >
        ...
      </button>
    </div>
  ));

  return (
    <>
      <div>{songList}</div>
      <div></div>
    </>
  );
};
