import { nowSideNavState } from "@/atoms/common.atom";
import { userInfoState } from "@/atoms/user.atom";
import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Modal } from "@/components/common/modal/Modal";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import {
  getUserDetailInfo,
  getUserMoneyList,
} from "@/connect/axios/queryHooks/user";
import { useQueryClient } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import style from "./Myinfo.module.css";
import { MyinfoModal } from "./myinfoModal/MyinfoModal";

export const Myinfo = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const page = searchParams.get("page");

  const queryClient = useQueryClient();
  const { data: moneyList } = getUserMoneyList(Number(page));
  const { data: userDetailInfo } = getUserDetailInfo();
  const userConfig = queryClient.getQueryData<any>(["getUserConfig"]);
  const userMoney = queryClient.getQueryData<any>(["getUserMoney"]);
  const setNowSideNav = useSetRecoilState(nowSideNavState);

  const [userInfo, setUserInfo] = useRecoilState(userInfoState);
  const [input, setInput] = useState("");
  const [moneySeq, setMoneySeq] = useState(0);
  const [isModalOpen, setIsModalOpen] = useState(false);

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("나의 정보 관리");
  }, []);

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
              userConfig?.data.userIsDarkmode
                ? style.circle
                : style.circle + " " + style.circleLeft
            }
            onClick={() => {
              queryClient.invalidateQueries(["getUserConfig"]);
              console.log("dd");
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
            return;
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
