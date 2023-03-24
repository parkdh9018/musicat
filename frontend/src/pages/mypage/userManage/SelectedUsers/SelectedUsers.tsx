import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";

import style from "./SelectedUsers.module.css";
interface userType {
  userNickname : string;
  userSeq: number;
}

interface props {
  setUserList: React.Dispatch<React.SetStateAction<userType[]>>;
  userList : userType[];
}

export const SelectedUsers = ({setUserList, userList}:props) => {

  const userClick = (index:number) => {
    setUserList(prev => prev.filter((_, i) => i != index))
  }

  return (
    <div className={style.selectedUsers}>
      <div className={style.container}>
        {userList.map((user, i) => (
          <div key={uuidv4()} className={style.userComponent} onClick={() => userClick(i)}>
            <span>{user.userSeq}-{user.userNickname}</span>
            <FontAwesomeIcon icon={faXmark} className={style.close} />
          </div>
        ))}
      </div>
    </div>
  );
};
