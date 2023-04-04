import { nowSideNavState } from "@/atoms/common.atom";
import { userInfoState } from "@/atoms/user.atom";
import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Modal } from "@/components/common/modal/Modal";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import {
  getUserConfig,
  getUserDetailInfo,
  getUserMoney,
  getUserMoneyList,
  loginUser,
  putDarkMode,
} from "@/connect/axios/queryHooks/user";
import { $ } from "@/connect/axios/setting";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import style from "./Myinfo.module.css";
import { MyinfoModal } from "./myinfoModal/MyinfoModal";
import Swal from "sweetalert2";

export const Myinfo = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const page = searchParams.get("page");

  const queryClient = useQueryClient();
  const { data: moneyList } = getUserMoneyList(Number(page));
  const { data: userDetailInfo } = getUserDetailInfo();
  const { data: userMoney } = getUserMoney();
  const setNowSideNav = useSetRecoilState(nowSideNavState);

  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const [input, setInput] = useState("");
  const [moneySeq, setMoneySeq] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { data } = getUserConfig();

  const { mutate } = useMutation(
    async () => {
      const { data } = await $.put(`/user/nickname`, input);
      return data;
    },
    {
      onSuccess: (data) => {
        sessionStorage.setItem("token", data.token);
        loginUser(setUserInfo);
        setInput("");
        useCustomToast("success", "닉네임 변경 성공!");
      },
      onError: () => {
        Swal.fire({
          icon: "error",
          title: "",
          text: "네트워크 오류 / 다시 시도해 주세요.",
          confirmButtonText: "닫기",
        });
      },
    }
  );

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("나의 정보 관리");
  }, []);

  useEffect(() => {
    if (input.length > 15) {
      useCustomToast("warning", "닉네임은 15자를 넘을 수 없습니다!");
      setInput(input.slice(0, 14));
    }
  }, [input]);

  return (
    <div className={style.myinfo}>
      <div className={style.leftbox}>
        <img src={userInfo.userProfile} alt="프로필 이미지" />
        <h3 style={{ textAlign: "center" }}>{userInfo.userNick} 님</h3>
        <p className={style.ffc} style={{ marginBottom: "40px" }}>
          환영합니다
        </p>
        <p className={style.ffc}>다크 모드</p>
        <div className={style.toolbar}>
          <div
            className={
              data?.data.userIsDarkmode
                ? style.circle
                : style.circle + " " + style.circleLeft
            }
            onClick={() => {
              putDarkMode()
                .then(() => {
                  queryClient.invalidateQueries(["getUserConfig"]);
                })
                .catch(() => {
                  Swal.fire({
                    icon: "error",
                    title: "",
                    text: "네트워크 오류 / 다시 시도해 주세요",
                    confirmButtonText: "닫기",
                  });
                });
              return;
            }}
          />
        </div>
        <p className={style.ffc}>가입 날짜</p>
        <p className={style.fc} style={{ marginBottom: "40px" }}>
          {userDetailInfo?.data.userCreatedAt}
        </p>
        <p className={style.ffc}>이메일</p>
        <p className={style.fc}>{userDetailInfo?.data.userEmail}</p>
      </div>
      <hr className={style.middle_hr} />
      <div className={style.rightbox}>
        <h3>닉네임</h3>
        <Input input={input} setInput={setInput} />
        <Button
          content="변 경"
          style={{ marginLeft: "5px" }}
          onClick={() => {
            mutate();
          }}
        />
        <h3 style={{ margin: "40px 0" }}>
          나의 츄르 : {userMoney?.data.userMoney}p
        </h3>
        <Board
          data={moneyList?.data.content}
          grid={"40% 30% 30%"}
          headRow={["날짜", "변동내역", "상새내역"]}
          setIsModalOpen={setIsModalOpen}
          type={"userMoney"}
          setMoneySeq={setMoneySeq}
        />
        <Pagenation
          number={moneyList?.data.number}
          first={moneyList?.data.first}
          last={moneyList?.data.last}
          totalPages={moneyList?.data.totalPages}
          url={`?page=`}
        />
      </div>
      {isModalOpen && (
        <Modal setModalOpen={setIsModalOpen}>
          <MyinfoModal dataSeq={moneySeq} />
        </Modal>
      )}
    </div>
  );
};
