import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons";
import { Button } from "@/components/common/button/Button";

import style from "./ContentPlus.module.css";
import { useState } from "react";
interface ContentPlusProps {}

export const ContentPlus = () => {

  // TODO : 이것도 리코일로 빼야할듯?
  const [popoverState, setPopoverState] = useState<boolean>(false)

  const clickEvent = () => {
    // console.log("EE");
  };

  const plusEvent = () => {
    setPopoverState(true)
  }

  return (
    <div onClick={clickEvent}>
      <span onClick={plusEvent} className={style.plusButton}>
        문단 추가하기 <FontAwesomeIcon icon={faCirclePlus} />
        {popoverState && <div className={style.popover}>
          <Button
            content="일반"
            onClick={() => {}}
            style={{ border: "1.5px solid black",color : "black" }}
          />
          <Button
            content="나레이션"
            onClick={() => {}}
            style={{ border: "1.5px solid black", color : "black" }}
          />
        </div>}
      </span>
    </div>
  );
};
