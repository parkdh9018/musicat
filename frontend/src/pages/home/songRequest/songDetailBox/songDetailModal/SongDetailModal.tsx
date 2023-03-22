import style from "../SongDetailNav.module.css";

export interface Song {
  title: string;
  artist: string;
  releaseDate: string;
  genre: string;
  album: string;
  coverImg: string;
}

export const SongDetailModal = () => {
  const song: Song = {
    title: "ditto",
    artist: "newjeans",
    releaseDate: "2023-01-02",
    genre: "k-pop",
    album: "ditto",
    coverImg: "dummy src",
  };

  return (
    <div className={style.songModal}>
      <img className={style.songModalImg} src={song.coverImg} alt="앨범 사진" />
      <span>
        <div>{song.title}</div>
        <div>{song.artist}</div>
        <div>{song.releaseDate}</div>
        <div>{song.genre}</div>
        <div>{song.album}</div>
      </span>
    </div>
  );
};
