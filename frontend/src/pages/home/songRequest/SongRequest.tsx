import { useState } from "react";
import style from "./SongRequest.module.css";
import { SongList } from "./songList/SongList";
import { postSongRequest } from "@/connect/axios/queryHooks/music";
import { SongSearch } from "@/components/common/songSearch/SongSearch";
import { Song } from "@/types/home";
export const SongRequest = () => {
  const [isFocused, setIsFocused] = useState(false);
  const [requestSong, setRequestSong] = useState<Song>();

  const onFocus = () => {
    setIsFocused(true);
  };

  const onBlur = () => {
    // 여기서 검색 결과나 상태에 맞춰 블러 처리 할지 말지 로직 넣기
    setIsFocused(false);
  };
  const onClickReq = async () => {
    console.log("CLicked");
    if (requestSong) {
      // console.log("음악 신청 api 호출!!");
      const req = { ...requestSong, userSeq: 4 }; // 리코일 유저 만들어지면 유저 시퀀스 가져와서 적용하기
      // console.log(req);
      const result = await postSongRequest(req);
      // setSearch("");

      // 신청한 곡이 아직 플레이 리스트에 남아있는 경우
      if (result.data.status === 1) {
        alert("내가 신청한 곡이 재생된 이후에 다시 신청할 수 있습니다!");
      }
      // 다른 사람이 이미 신청한 곡인 경우
      if (result.data.status === 2) {
        alert(
          `다른 사용자가 해당 곡을 이미 신청했습니다. 해당 곡은 ${result.data.playOrder}번째로 재생될 예정입니다.`
        );
      }
      // 유튜브 검색 결과가 없어 재생할 수 없는 경우
      if (result.data.status === 3) {
        alert("죄송합니다 재생할 수 없는 곡입니다. 다른곡을 신청해주세요");
      }
      // console.log(result);
    }
  };

  console.log(requestSong);

  return (
    <>
      <div className={style.songRequest} onFocus={onFocus}>
        <div className={style.songSearch}>
          <SongSearch setRequestSong={setRequestSong} width={90} />
          <button className={style.requestBtn} onClick={onClickReq}>
            20Chur
          </button>
        </div>
        <hr />
        <div className={style.songRequestList}>신청곡 목록</div>
        <SongList />
      </div>
    </>
  );
};
