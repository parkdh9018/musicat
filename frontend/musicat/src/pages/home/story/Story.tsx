import style from "./Story.module.css";
import { Input } from "@/components/common/input/Input";
import { Button } from "@/components/common/button/Button";
import { ContentBox } from "./contentBox/ContentBox";
import { ContentPlus } from "./contentPlus/ContentPlus";
import { useState } from "react";
import { v4 as uuidv4 } from "uuid";
import { useClickOutside } from "@mantine/hooks";
export const Story = () => {

  const myInputStyle = {width : '80%', marginLeft: '2%'}
  const [contentBoxList, setContentBoxList] = useState([{index : 1}])

  return (
    <>
      <div className={style.story}>
        <div className={style.group}>
          제목 <Input style={myInputStyle}/>
        </div>
        <div className={style.group}>
          내용 
          <div className={style.content} style={{marginLeft: '2%'}}>
            {contentBoxList.map((v,i) => 
              <ContentBox key={uuidv4()} index={i+1}/>
            )}
            <ContentPlus/>
          </div>
        </div>
        <div>
          신청곡 <Input style={myInputStyle}/>
        </div>
        <div>
          <Button content="등록하기" onClick={()=>{}}/>
        </div>
      </div>
    </>
  )
};
