import { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import style from "./SongList.module.css";
import { Modal } from "@/components/common/modal/Modal";
import { SongDetailModal } from "./songDetailModal/SongDetailModal";
import { getSongList } from "@/connect/axios/queryHooks/music";
import { Song } from "@/types/home";
import { useRecoilValue } from "recoil";
import { userInfoState } from "@/atoms/user.atom";

export const SongList = () => {
  const { data: songs } = getSongList();
  const playingMuisicSeq = 1; // 소켓 연결 하고 값 다시 설정하기
  const [selectedSong, setSelectedSong] = useState<Song | null>(null);
  const userInfo = useRecoilValue(userInfoState);

  console.log(songs);
  console.log(userInfo);

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
      {song.musicSeq === playingMuisicSeq ? (
        <span className={style.playingSongSpan}>
          {song.musicTitle} - {song.musicArtist}
        </span>
      ) : song.userSeq === userInfo.userSeq ? (
        <span className={style.userSongSpan}>
          {song.musicTitle} - {song.musicArtist}
        </span>
      ) : (
        <span className={style.songSpan}>
          {song.musicTitle} - {song.musicArtist}
        </span>
      )}

      <button
        className={style.songBtn}
        value={JSON.stringify(song)}
        onClick={onSongDetail}
      >
        ...
      </button>
    </div>
  ));

  const songDetailModal = selectedSong ? (
    <SongDetailModal song={selectedSong} />
  ) : null;

  return (
    <>
      <div>{songList}</div>
      {isSongDetailModalOpen && (
        <Modal setModalOpen={setIsSongDetailModalOpen}>
          <div>{songDetailModal}</div>
        </Modal>
      )}
    </>
  );
};
