import { nowSideNavState } from "@/atoms/common.atom";
import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { getAllUsers } from "@/connect/axios/queryHooks/admin";
import { useEffect } from "react";
import { useSetRecoilState } from "recoil";
import { SelectedUsers } from "./SelectedUsers/SelectedUsers";

import style from "./UserManage.module.css";

export const UserManage = () => {
  const setNowSideNav = useSetRecoilState(nowSideNavState);

  useEffect(() => {
    setNowSideNav("유저관리");
  }, []);

  const {data, isLoading} = getAllUsers(0);

  const searchOptions = [
    { value: "all", name: "모두" },
    { value: "banChat", name: "채팅여부" },
    { value: "ban", name: "정지여부" },
  ];
  const useStateChangeOptions = [
    { value: "ban", name: "권한정지" },
    { value: "not_ban", name: "정지해제" },
    { value: "banChat", name: "채팅금지" },
    { value: "not_ban_chat", name: "채팅허용" },
  ];
  const userList_grid = "20% 25% 25% 15% 15%";
  const userList_headRow = [
    "닉네임",
    "이메일",
    "가입일",
    "채팅여부",
    "정지여부",
  ];


  return (
    <div className={style.userManage}>
      <div className={style.searchBar}>
        <span>유저검색 : </span>
        <SelectBox
          options={searchOptions}
          setValue={() => {
            console.log("뭘봐");
          }}
          style={{ width: "13%" }}
        />
        <Input
          input={""}
          setInput={() => {
            return;
          }}
        />
        <Button
          content="검색"
          onClick={() => {
            // 
          }}
        />
      </div>
      <div className={style.seleceted_userList}>
        <SelectedUsers />
      </div>
      <div className={style.userStateChange}>
        <span>변동사항 : </span>
        <SelectBox
          options={useStateChangeOptions}
          setValue={() => {
            return;
          }}
          style={{ width: "13%" }}
        />
        <Button
          content="적용"
          onClick={() => {
            return;
          }}
        />
      </div>
      <div className={style.userList}>
        <Board
          data={data?.content}
          grid={userList_grid}
          headRow={userList_headRow}
          type={"userManage"}
        />
      </div>
    </div>
  );
};