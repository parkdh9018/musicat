import { useState, useEffect } from "react";
import style from "./SongRequest.module.css";
import { SongList } from "./songList/SongList";
import { postSongRequest } from "@/connect/axios/queryHooks/music";
import { SongSearch } from "@/components/common/songSearch/SongSearch";
import { Song } from "@/types/home";
import { useRecoilValue } from "recoil";
import { userInfoState } from "@/atoms/user.atom";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { Button } from "@/components/common/button/Button";

export const SongRequest = () => {
  const [requestSong, setRequestSong] = useState<Song>();
  const userInfo = useRecoilValue(userInfoState);

  const onClickReq = async () => {
    if (userInfo.userRole === "") {
      useCustomToast("warning", "로그인후 신청 가능합니다.");
    } else {
      if (requestSong) {
        const req = { ...requestSong, userSeq: userInfo.userSeq };
        const result = await postSongRequest(req);

        // 신청한 곡이 아직 플레이 리스트에 남아있는 경우
        if (result.data.status === 1) {
          useCustomToast(
            "warning",
            "내가 신청한 곡이 재생된 이후에 다시 신청할 수 있습니다!"
          );
        }

        // 다른 사람이 이미 신청한 곡인 경우
        if (result.data.status === 2) {
          useCustomToast(
            "warning",
            `다른 사용자가 해당 곡을 이미 신청했습니다. 해당 곡은 ${result.data.playOrder}번째로 재생될 예정입니다.`
          );
        }
      }
    }
  };

  return (
    <>
      <div className={style.songRequest}>
        <div className={style.songSearch}>
          <SongSearch setRequestSong={setRequestSong} width={90} />
          <Button content="20Chur" onClick={onClickReq} />
        </div>
        <hr />
        <div className={style.songRequestTxt}>신청곡 목록</div>
        <SongList />
      </div>
    </>
  );
};
