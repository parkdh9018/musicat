import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { v4 as uuidv4 } from "uuid";

import style from "./SelectedUsers.module.css";

interface userType {
  userNickname : string;
}


export const SelectedUsers = () => {

  const [userList, setUserList] = useState<userType[]>([]);

  useEffect(() => {
    setUserList(prev => [...prev, {userNickname : "라면부엉"}])
    setUserList(prev => [...prev, {userNickname : "라면부엉"}])
    setUserList(prev => [...prev, {userNickname : "안녕하세요"}])
    setUserList(prev => [...prev, {userNickname : "라면부엉"}])
    setUserList(prev => [...prev, {userNickname : "안녕"}])
    setUserList(prev => [...prev, {userNickname : "라면부엉"}])

  }, [])


  const userClick = (index:number) => {
    setUserList(prev => prev.filter((_, i) => i != index))
  }

  return (
    <div className={style.selectedUsers}>
      <div className={style.container}>
        {userList.map((user, i) => (
          <div key={uuidv4()} className={style.userComponent} onClick={() => userClick(i)}>
            <span>{user.userNickname}</span>
            <FontAwesomeIcon icon={faXmark} className={style.close} />
          </div>
        ))}
      </div>
    </div>
  );
};
