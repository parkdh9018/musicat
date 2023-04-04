import { nowSideNavState } from "@/atoms/common.atom";
import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { getUsers } from "@/connect/axios/queryHooks/admin";
import { MouseEventHandler, useEffect, useMemo, useState } from "react";
import { useSetRecoilState } from "recoil";
import { SelectedUsers } from "./SelectedUsers/SelectedUsers";

import style from "./UserManage.module.css";
import Swal from "sweetalert2";
import { useNavigate } from "react-router-dom";

export const UserManage = () => {
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const navigate = useNavigate();

  useEffect(() => {
    setNowSideNav("유저관리");
  }, []);

  const {
    searchInput,
    isFetchingNextPage,
    selectedUserList,
    changeSelectValue,
    filtered_userList,
    isFetching,
    isRefetching,
    fetchNextPage,
    refetch,
    setSearchInput,
    setSearchSelectValue,
    chattingBanMutate,
    chattingNotBanMutate,
    BanMutate,
    NotBanMutate,
    setSelectedUserList,
    setChangeSelectValue,
  } = getUsers();

  const searchOptions = [
    { value: "all", name: "모두" },
    { value: "banChat", name: "채팅정지" },
    { value: "ban", name: "권한정지" },
    { value: "notBanChat", name: "채팅허용" },
    { value: "notBan", name: "권한허용" },
  ];
  const useStateChangeOptions = [
    { value: "ban", name: "권한정지" },
    { value: "not_ban", name: "정지해제" },
    { value: "banChat", name: "채팅금지" },
    { value: "not_banChat", name: "채팅허용" },
  ];
  const userList_grid = "9% 15% 31% 25% 10% 10%";
  const userList_headRow = [
    "일련번호",
    "닉네임",
    "이메일",
    "가입일",
    "채팅여부",
    "정지여부",
  ];

  const isMobile = () => {
    return /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
      navigator.userAgent
    );
  };

  if (isMobile()) {
    Swal.fire({
      icon: "info",
      title: "",
      text: "관리 기능은 PC/테블릿만 지원합니다",
      confirmButtonText: "닫기",
    }).then(() => {
      navigate(-1);
    });
  }

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

  const searchClick: MouseEventHandler = () => {
    refetch();
  };

  const changeClick: MouseEventHandler = () => {
    switch (changeSelectValue) {
      case "ban":
        BanMutate();
        break;
      case "not_ban":
        NotBanMutate();
        break;
      case "banChat":
        chattingBanMutate();
        break;
      case "not_banChat":
        chattingNotBanMutate();
        break;
      default:
        break;
    }
  };

  const handleScroll = () => {
    if (
      window.innerHeight + document.documentElement.scrollTop >=
        document.documentElement.offsetHeight &&
      !isFetchingNextPage &&
      !isFetching &&
      !isRefetching
    ) {
      fetchNextPage();
    }
  };

  window.addEventListener("scroll", handleScroll);

  return (
    <div className={style.userManage}>
      <div className={style.searchBar}>
        <span>유저검색 : </span>
        <SelectBox
          options={searchOptions}
          setValue={setSearchSelectValue}
          style={{ width: "13%" }}
        />
        <Input input={searchInput} setInput={setSearchInput} />
        <Button content="검색" onClick={searchClick} />
      </div>
      <div className={style.horizontal_line}></div>
      <div className={style.seleceted_userList}>
        <div>선택된 유저들</div>
        <SelectedUsers
          selectedUserList={selectedUserList}
          userClick={userClick}
        />
      </div>
      <div className={style.userStateChange}>
        <span>유저 상태변경 : </span>
        <SelectBox
          options={useStateChangeOptions}
          setValue={setChangeSelectValue}
          style={{ width: "110px" }}
        />
        <Button content="적용" onClick={changeClick} />
      </div>
      <div className={style.horizontal_line}></div>
      <div className={style.userList}>
        <div>유저 리스트</div>
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
