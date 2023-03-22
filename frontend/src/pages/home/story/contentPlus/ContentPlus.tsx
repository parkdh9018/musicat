import style from "./ContentPlus.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons";
import { Button } from "@/components/common/button/Button";
import { MouseEventHandler, useState } from "react";
import { useClickOutside } from "@mantine/hooks";
import { addStoryContent, storyContentState } from "@/atoms/story.atoms";

export const ContentPlus = () => {


  const [popover, setPopover] = useState(false)
  const ref = useClickOutside(() => setPopover(false));

  const add = addStoryContent();

  const clickNormal:MouseEventHandler<HTMLDivElement> = (e) => {
    e.stopPropagation();
    add({type : "normal", speaker: "narr", value : ""})
    setPopover(false)
  };

  const clickNarration:MouseEventHandler<HTMLDivElement> = (e) => {    
    e.stopPropagation();
    add({type : "narr", speaker : "male", value : ""})
    setPopover(false)
  };

  const plusEvent:MouseEventHandler<HTMLDivElement> = () => {
    setPopover(true)
  }

  return (
    <div className={style.contentPlus}>
      <span onClick={plusEvent} className={style.plusButton}>
        문단 추가하기 <FontAwesomeIcon icon={faCirclePlus} />
        {popover && <div ref={ref} className={style.popover}>
          <Button
            content="일반"
            onClick={clickNormal}
            style={{ border: "1.5px solid black",color : "black" }}
          />
          <Button
            content="나레이션"
            onClick={clickNarration}
            style={{ border: "1.5px solid black", color : "black" }}
          />
        </div>}
      </span>
    </div>
  );
};
