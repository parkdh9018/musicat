import type { Song } from "../songDetailModal/SongDetailModal";
import style from "../SongDetailNav.module.css";

interface Artist {
  artistNmae: string;
  genres: Array<string>;
  famusSongs: Array<Song>;
  artistImg?: string;
}

export const ArtistDetailModal = () => {
  const song: Song = {
    title: "ditto",
    artist: "newjeans",
    releaseDate: "2023-01-02",
    genre: "k-pop",
    album: "ditto",
    coverImg: "",
  };

  const artist: Artist = {
    artistNmae: "뉴진스",
    genres: ["댄스팝", "일렉트로팝", "뭄바톤", "힙합"],
    famusSongs: [song, song, song],
    artistImg: "sdddd",
  };

  const genreList: JSX.Element[] = artist.genres.map((genre) => (
    <span>{genre} </span>
  ));

  const songList: JSX.Element[] = artist.famusSongs.map((song) => (
    <div>
      {song.title} - {song.artist}
    </div>
  ));

  return (
    <div className={style.songModal}>
      <img
        className={style.songModalImg}
        src={artist.artistImg}
        alt="아티스트 이미지"
      />
      <span>
        <div>{artist.artistNmae}</div>
        <div>{genreList}</div>
        <div>{songList}</div>
      </span>
    </div>
  );
};
