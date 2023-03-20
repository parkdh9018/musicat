import { nowSideNav } from "@/atoms/common.atom";
import { memberInfo } from "@/atoms/user.atom";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { useEffect, useState } from "react";
import { useRecoilState, useSetRecoilState } from "recoil";
import style from "./Myinfo.module.css";

export const Myinfo = () => {
  const setNowSideNav = useSetRecoilState(nowSideNav);
  const [userInfo, setUserInfo] = useRecoilState(memberInfo);
  const [input, setInput] = useState("");

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("나의 정보 관리");
  }, []);

  return (
    <div className={style.myinfo}>
      <div className={style.leftbox}>
        <img src={userInfo.memberProfile} alt="프로필 이미지" />
        <h3 style={{ textAlign: "center" }}>{userInfo.memberNick} 님</h3>
        <p className={style.ffc} style={{ marginBottom: "40px" }}>
          환영합니다
        </p>
        <p className={style.ffc}>다크 모드</p>
        <div className={style.toolbar}>
          <div className={style.circle}></div>
        </div>
        <p className={style.ffc}>가입 날짜</p>
        <p className={style.fc} style={{ marginBottom: "40px" }}>
          2022년 10월 12일
        </p>
        <p className={style.ffc}>이메일</p>
        <p className={style.fc}>ramenbuang@gmail.com</p>
      </div>
      <hr className={style.middle_hr} />
      <div className={style.rightbox}>
        <h3>닉네임</h3>
        <Input input={input} setInput={setInput} />
        <Button
          content="중복확인"
          onClick={() => {}}
          style={{ margin: "0 5px" }}
        ></Button>
        <Button content="변 경" onClick={() => {}}></Button>
        <h3>나의 츄르 : 456p</h3>
      </div>
    </div>
  );
};
