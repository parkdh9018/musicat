import { useState, useEffect } from "react";
import style from "./SongRequest.module.css";
import { SongList } from "./songList/SongList";
import {
  getAlredayRegistedSong,
  postSongRequest,
} from "@/connect/axios/queryHooks/music";
import { SongSearch } from "@/components/common/songSearch/SongSearch";
import { Song } from "@/types/home";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { userInfoState } from "@/atoms/user.atom";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { Button } from "@/components/common/button/Button";
import Swal from "sweetalert2";
import { nowYoutubeSearchState, searchState } from "@/atoms/song.atom";
import { getUserMoney } from "@/connect/axios/queryHooks/user";
import { useQueryClient } from "@tanstack/react-query";

export const SongRequest = () => {
  const [requestSong, setRequestSong] = useState<Song | undefined>(undefined);
  const setSearch = useSetRecoilState(searchState);
  const userInfo = useRecoilValue(userInfoState);
  const queryClient = useQueryClient();
  const youtubeSearch = useRecoilValue(nowYoutubeSearchState);
  const { data: userMoney } = getUserMoney();
  const { data: alreadyRegisted } = getAlredayRegistedSong();

  // 모바일 기기인지 확인해 주는 함수
  const isMobile = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent
    );
  };

  const onClickReq = () => {
    if (userInfo.userRole === "") {
      useCustomToast("warning", "로그인후 신청 가능합니다.");
    } else {
      if (requestSong) {
        if (userMoney?.data.userMoney < 20) {
          Swal.fire({
            icon: "warning",
            title: "",
            text: "츄르가 부족합니다!",
            confirmButtonText: "닫기",
          }).then(() => {
            return;
          });
        } else {
          Swal.fire({
            title: `${requestSong.musicTitle}을 신청하시는게 맞습니까?`,
            text: "츄르 20개가 소모됩니다!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "신청",
            cancelButtonText: "취소",
          }).then(async (result) => {
            if (result.isConfirmed) {
              const req = { ...requestSong, userSeq: userInfo.userSeq };
              const result = await postSongRequest(req).catch(() => {
                Swal.fire({
                  icon: "error",
                  title: "",
                  text: "네트워크 오류 / 다시 시도해 주세요",
                  confirmButtonText: "닫기",
                });
                return;
              });

              // 다른 사람이 이미 신청한 곡인 경우
              if (result?.data.status === 2) {
                useCustomToast(
                  "warning",
                  `다른 사용자가 해당 곡을 이미 신청했습니다. 해당 곡은 ${result.data.playOrder}번째로 재생될 예정입니다.`
                );
              } else {
                Swal.fire(
                  "신청되었습니다!",
                  "다음 곡은 해당 곡이 재생된 이후 다시 신청 가능합니다",
                  "success"
                ).then(() => {
                  setSearch("");
                  setRequestSong(undefined);
                  queryClient.invalidateQueries(["getUserMoney"]);
                  queryClient.invalidateQueries(["SongRequset"]);
                  queryClient.invalidateQueries(["AlredayRegistedSong"]);
                });
              }
            }
          });
        }
      }
    }
  };

  return (
    <div
      className={style.songRequest}
      style={{ animation: "0.7s ease-in-out loadEffect3" }}
    >
      <div className={style.songSearch}>
        <SongSearch
          setRequestSong={setRequestSong}
          width={isMobile() ? 65 : 80}
          placeholder={
            alreadyRegisted === 409
              ? "이미 신청한 노래가 있습니다!"
              : " 가수 이름 / 노래 제목"
          }
          status={alreadyRegisted}
        />
        <Button
          style={
            !requestSong || alreadyRegisted === 409
              ? { opacity: "0.5", pointerEvents: "none" }
              : undefined
          }
          content={youtubeSearch ? "검색중..." : "노래 신청"}
          onClick={onClickReq}
        />
      </div>
      <hr className={style.hr} />
      <div className={style.songRequestTxt}>신청곡 목록</div>
      <SongList />
    </div>
  );
};
