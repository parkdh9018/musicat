import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";
import style from "./SongList.module.css";
import { Modal } from "@/components/common/modal/Modal";
import { SongDetailModal } from "./songDetailModal/SongDetailModal";
import { getSongList } from "@/connect/axios/queryHooks/music";
import { Song } from "@/types/home";
import { useRecoilValue } from "recoil";
import { userInfoState } from "@/atoms/user.atom";
import { musicState } from "@/atoms/song.atom";

export const SongList = () => {
  const { data: songs } = getSongList();
  const [playingMuisicId, setPlayingMusicId] = useState(""); // 소켓 연결 하고 값 다시 설정하기
  const [selectedSong, setSelectedSong] = useState<Song | null>(null);
  const userInfo = useRecoilValue(userInfoState);
  const nowPlaying = useRecoilValue(musicState);
  const [isSongDetailModalOpen, setIsSongDetailModalOpen] = useState(false);

  useEffect(() => {
    if (nowPlaying.type === "youtube") {
      const nowId: string = nowPlaying.path.split("embed/")[1];
      console.log(nowId);
      setPlayingMusicId(nowId);
    } else {
      setPlayingMusicId("");
    }
  }, [nowPlaying]);

  console.log(songs);

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
      {song.musicYoutubeId === playingMuisicId ? (
        <span className={style.playingSongSpan}>
          <img src={song.musicImage} alt="사진" className={style.songImg} />
          {song.musicTitle} - {song.musicArtist}
        </span>
      ) : song.userSeq == userInfo.userSeq ? (
        <span className={style.userSongSpan}>
          <img src={song.musicImage} alt="사진" className={style.songImg} />
          {song.musicTitle} - {song.musicArtist}
        </span>
      ) : (
        <span className={style.songSpan}>
          <img src={song.musicImage} alt="사진" className={style.songImg} />
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

  return (
    <div className={style.songListBox}>
      <div>{songList}</div>
      {isSongDetailModalOpen && selectedSong != undefined && (
        <Modal setModalOpen={setIsSongDetailModalOpen}>
          <SongDetailModal musicSeq={selectedSong.musicSeq} />
        </Modal>
      )}
    </div>
  );
};
