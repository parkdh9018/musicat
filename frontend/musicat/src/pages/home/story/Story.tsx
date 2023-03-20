import style from "./Story.module.css";
import { Input } from "@/components/common/input/Input";
import { Button } from "@/components/common/button/Button";
import { ContentBox } from "./contentBox/ContentBox";
import { ContentPlus } from "./contentPlus/ContentPlus";
import { useState } from "react";
import { v4 as uuidv4 } from "uuid";

import { storyContentState, storyTitleState } from "@/atoms/story.atoms";
import { useRecoilState, useRecoilValue } from "recoil";

export const Story = () => {

  const myInputStyle = {width : '80%', marginLeft: '2%'}

  const [title, setTitle] = useRecoilState(storyTitleState);
  const content = useRecoilValue(storyContentState);
  // const [title, setTitle] = useState("");

  return (
    <>
      <div className={style.story}>
        <div className={style.group}>
          제목 <Input style={myInputStyle} input={title} setInput={setTitle}/>
        </div>
        <div className={style.group}>
          내용 
          <div className={style.content} style={{marginLeft: '2%'}}>
            {content.map((v,i) => 
              <ContentBox key={uuidv4()} {...v} index={i}/>
            )}
            <ContentPlus/>
          </div>
        </div>
        <div>
          {/* 신청곡 <Input style={myInputStyle}/> */}
        </div>
        <div>
          <Button content="등록하기" onClick={()=>{}}/>
        </div>
      </div>
    </>
  )
};
