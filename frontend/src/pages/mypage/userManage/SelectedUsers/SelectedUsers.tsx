import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { v4 as uuidv4 } from "uuid";

import style from "./SelectedUsers.module.css";

export const SelectedUsers = () => {
  const userList = [
    { userNickname: "라면부엉" },
    { userNickname: "안녕" },
    { userNickname: "ㄹㄹㄹㄹㄹㄹㄹㄹㄹ" },
    { userNickname: "라면부엉" },
    { userNickname: "라면부엉" },
    { userNickname: "라면부엉" },
    { userNickname: "라면부엉" },
    { userNickname: "라면부엉" },
  ];

  return (
    <div className={style.selectedUsers}>
      <div className={style.container}>
        {userList.map((user) => (
          <div key={uuidv4()} className={style.userComponent}>
            <span>{user.userNickname}</span>
            <FontAwesomeIcon icon={faXmark} className={style.close} />
          </div>
        ))}
      </div>
    </div>
  );
};
