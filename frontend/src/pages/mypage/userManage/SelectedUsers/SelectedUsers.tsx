import { faXmark } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { v4 as uuidv4 } from "uuid";

import style from "./SelectedUsers.module.css";
interface userType {
  userNickname : string;
  userSeq: number;
}

interface props {
  userClick: (seq:number) => void;
  selectedUserList : userType[];
}

export const SelectedUsers = ({userClick, selectedUserList}:props) => {


  return (
    <div className={style.selectedUsers}>
      <div className={style.container}>
        {selectedUserList.map((user) => (
          <div key={uuidv4()} className={style.userComponent} onClick={() => userClick(user.userSeq)}>
            <span>{user.userSeq}-{user.userNickname}</span>
            <FontAwesomeIcon icon={faXmark} className={style.close} />
          </div>
        ))}
      </div>
    </div>
  );
};
