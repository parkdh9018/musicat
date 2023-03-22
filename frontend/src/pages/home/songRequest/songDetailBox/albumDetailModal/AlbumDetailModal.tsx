import type { Song } from "../songDetailModal/SongDetailModal";
import style from "../SongDetailNav.module.css";

interface Album {
  albumTitle: string;
  artist: string;
  releaseDate: string;
  includedSongs: Array<Song>;
  coverImg?: string;
}

export const AlbumDetailModal = () => {
  const song: Song = {
    title: "ditto",
    artist: "newjeans",
    releaseDate: "2023-01-02",
    genre: "k-pop",
    album: "ditto",
    coverImg: "",
  };

  const album: Album = {
    albumTitle: "dummy title",
    artist: "dummy artist",
    releaseDate: "22-01-01",
    includedSongs: [song, song],
    coverImg: "ddd",
  };

  const songList: JSX.Element[] = album.includedSongs.map((song) => (
    <div>
      {song.title} - {song.artist}
    </div>
  ));

  return (
    <div className={style.songModal}>
      <img
        className={style.songModalImg}
        src={album.coverImg}
        alt="앨범 커버 이미지"
      />
      <span>
        <div>{album.albumTitle}</div>
        <div>{album.artist}</div>
        <div>{album.releaseDate}</div>
        <div>{songList}</div>
      </span>
    </div>
  );
};
