import style from "./SongRequest.module.css";
import { SongList } from "./songList/SongList";
import { SongSearch } from "@/components/common/songSearch/SongSearch";

export const SongRequest = () => {
  return (
    <>
      <div className={style.songRequest}>
        <SongSearch />
        <hr />
        <div className={style.songRequestList}>신청곡 목록</div>
        <SongList />
      </div>
    </>
  );
};
