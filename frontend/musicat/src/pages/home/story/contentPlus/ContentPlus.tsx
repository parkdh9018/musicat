import style from "./ContentPlus.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons";
import { Button } from "@/components/common/button/Button";
import { useState } from "react";
import { useClickOutside } from "@mantine/hooks";

export const ContentPlus = () => {


  const [popover, setPopover] = useState(false)
  const ref = useClickOutside(() => setPopover(false));

  const clickEvent = () => {
    // console.log("EE");
  };

  const plusEvent = () => {
    setPopover(true)
  }

  return (
    <div onClick={clickEvent}>
      <span onClick={plusEvent} className={style.plusButton}>
        문단 추가하기 <FontAwesomeIcon icon={faCirclePlus} />
        {popover && <div ref={ref} className={style.popover}>
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
