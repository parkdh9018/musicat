import { nowSideNavState } from "@/atoms/common.atom";
import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { getAllUsers } from "@/connect/axios/queryHooks/admin";
import { MouseEventHandler, useEffect, useMemo, useState } from "react";
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

  useEffect(() => {
    setNowSideNav("유저관리");
  }, []);

  const { userList, isLoading } = getAllUsers(0);

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

  const [selectedUserList, setSelectedUserList] = useState<selectedUser[]>([]);
  const [searchInput, setSearchInput] = useState<string>("");
  const [searchSelectValue, setSearchSelectValue] = useState<string>("all");
  const [changeSelectValue, setChangeSelectValue] = useState<string>("ban");

  const filtered_userList = useMemo(() => {
    const set = new Set(selectedUserList.map((v) => v.userSeq));
    return userList?.filter((v) => !set.has(v.userSeq));
  }, [userList, selectedUserList]);


  // click eventListener
  const boardColumnClick = (seq: number, nickname: string) => {
    if (!selectedUserList?.every((v) => v.userSeq != seq)) return;
    setSelectedUserList((prev) => [
      ...prev,
      { userNickname: nickname, userSeq: seq },
    ]);
  };

  const userClick = (seq: number) => {
    setSelectedUserList((prev) => prev.filter((v) => v.userSeq != seq));
  };

  const searchClick:MouseEventHandler = () => {
    // //
    // getAllBanUsers()
  }

  const changeClick:MouseEventHandler = () => {
    //
  }



  return (
    <div className={style.userManage}>
      <div className={style.searchBar}>
        <span>유저검색 : </span>
        <SelectBox
          options={searchOptions}
          setValue={setSearchSelectValue}
          style={{ width: "13%" }}
        />
        <Input
          input={searchInput}
          setInput={setSearchInput}
        />
        <Button
          content="검색"
          onClick={searchClick}
        />
      </div>
      <div className={style.seleceted_userList}>
        <SelectedUsers
          selectedUserList={selectedUserList}
          userClick={userClick}
        />
      </div>
      <div className={style.userStateChange}>
        <span>변동사항 : </span>
        <SelectBox
          options={useStateChangeOptions}
          setValue={setChangeSelectValue}
          style={{ width: "13%" }}
        />
        <Button
          content="적용"
          onClick={changeClick}
        />
      </div>
      <div className={style.userList}>
        <Board
          data={filtered_userList}
          grid={userList_grid}
          headRow={userList_headRow}
          type={"userManage"}
          boardColumnClick={boardColumnClick}
        />
      </div>
    </div>
  );
};
