import { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import style from "./SongList.module.css";
import { Modal } from "@/components/common/modal/Modal";
import { SongDetailModal } from "./SongDetailModal";
import { getSongList } from "@/connect/axios/queryHooks/music";

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
  //  const songs: Song[] = "api 요청(/music) 리액트 쿼리로 받아오기"

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
  // 404 웅렬님께서 수정중이심...
  // const data = getSongList();
  // console.log(data);

  const [isSongDetailModalOpen, setIsSongDetailModalOpen] = useState(false);

  const onSongDetail = () => {
    setIsSongDetailModalOpen(true);
  };

  const songList: JSX.Element[] = songs.map((song) => (
    <div className={style.songList} key={uuidv4()}>
      <span className={style.songSpan}>
        {song.musicName} - {song.musicArtist}
      </span>
      <button className={style.songBtn} onClick={() => onSongDetail()}>
        ...
      </button>
    </div>
  ));

  return (
    <>
      <div>{songList}</div>
      {isSongDetailModalOpen && (
        <Modal
          setModalOpen={setIsSongDetailModalOpen}
          children={<SongDetailModal />}
        />
      )}
    </>
  );
};
