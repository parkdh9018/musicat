import { nowSideNavState } from "@/atoms/common.atom";
import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { getAllUsers } from "@/connect/axios/queryHooks/admin";
import { useEffect, useState } from "react";
import { useSetRecoilState } from "recoil";
import { SelectedUsers } from "./SelectedUsers/SelectedUsers";

// import { User } from "@/types/mypage";

import style from "./UserManage.module.css";

interface selectedUser {
  userNickname: string;
  userSeq: number;
}

export const UserManage = () => {
  const setNowSideNav = useSetRecoilState(nowSideNavState);

  const { setUserList, userList, isLoading } = getAllUsers(0);
  const [ selectedUserList, setSelectedUserList] = useState<selectedUser[]>([]);

  useEffect(() => {
    setNowSideNav("유저관리");
  }, []);

  const boardColumnClick = (seq: number, nickname: string) => {
    if(!userList?.every(v => v.userSeq != seq)) return;
    setSelectedUserList((prev) => [...prev, { userNickname: nickname, userSeq: seq }]);
  };



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
  const userList_grid = "8% 15% 32% 25% 10% 10%";
  const userList_headRow = [
    "번호",
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
        <SelectedUsers selectedUserList={selectedUserList} setSelectedUserList={setSelectedUserList} />
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
          data={userList}
          grid={userList_grid}
          headRow={userList_headRow}
          type={"userManage"}
          boardColumnClick={boardColumnClick}
        />
      </div>
    </div>
  );
};
