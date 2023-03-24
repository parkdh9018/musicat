import { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import style from "./SongList.module.css";
import { Modal } from "@/components/common/modal/Modal";
import { SongDetailModal } from "./SongDetailModal";
import { getSongList, Song } from "@/connect/axios/queryHooks/music";

export const SongList = () => {
  const { data: songs } = getSongList();
  const [selectedSong, setSelectedSong] = useState<Song | null>(null);

  console.log(songs);

  const [isSongDetailModalOpen, setIsSongDetailModalOpen] = useState(false);

  const onSongDetail = (e: React.MouseEvent<HTMLButtonElement>) => {
    const value = e.currentTarget.value;
    const song = JSON.parse(value);
    setSelectedSong(song);
    setIsSongDetailModalOpen(true);
  };

  if (!songs) {
    return <div>로딩 중...</div>;
  }

  const songList: JSX.Element[] = songs.map((song) => (
    <div className={style.songList} key={uuidv4()}>
      <span className={style.songSpan}>
        {song.musicTitle} - {song.musicArtist}
      </span>
      <button
        className={style.songBtn}
        value={JSON.stringify(song)}
        onClick={onSongDetail}
      >
        ...
      </button>
    </div>
  ));

  // const songDetailModal = selectedSong ? (
  //   <SongDetailModal song={selectedSong} />
  // ) : null;

  return (
    <>
      {/* <div>{songList}</div>
      {isSongDetailModalOpen && (
        <Modal
          setModalOpen={setIsSongDetailModalOpen}
          children={songDetailModal}
        ></Modal>
      )} */}
    </>
  );
};
