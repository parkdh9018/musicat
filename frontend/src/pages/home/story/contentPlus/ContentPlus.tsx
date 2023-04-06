import style from "./ContentPlus.module.css";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCirclePlus } from "@fortawesome/free-solid-svg-icons";
// import { Button } from "@/components/common/button/Button";
import { MouseEventHandler, useState } from "react";
import { addStoryContent } from "@/atoms/story.atoms";
import { useTokenData } from "@/customHooks/useTokenData";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { TTS_OPTION } from "../contentBox/speakerConfig";

export const ContentPlus = () => {

  const userInfo = useTokenData();

  // const [popover, setPopover] = useState(false)
  // const ref = useClickOutside(() => setPopover(false));

  const add = addStoryContent();

  const clickNormal:MouseEventHandler<HTMLDivElement> = (e) => {
    e.stopPropagation();

    if(!userInfo?.userNick) {
      useCustomToast("error", "로그인이 필요합니다.")
      return;
    }
    
    add({speaker: TTS_OPTION[0].value, content: ""})
    // setPopover(false)
  };

  // const clickNarration:MouseEventHandler<HTMLDivElement> = (e) => {    
  //   e.stopPropagation();
  //   add({speaker : "narr", content : ""})
  //   setPopover(false)
  // };

  // const plusEvent:MouseEventHandler<HTMLDivElement> = () => {

  //   if(!userInfo?.userNick) {
  //     useCustomToast("error", "로그인이 필요합니다.")
  //     return;
  //   }

  //   setPopover(true)
  // }

  return (
    <div className={style.contentPlus}>
      <span onClick={clickNormal} className={style.plusButton}>
        문단 추가하기 <FontAwesomeIcon icon={faCirclePlus} />
        {/* {popover && <div ref={ref} className={style.popover}>
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
        </div>} */}
      </span>
    </div>
  );
};
