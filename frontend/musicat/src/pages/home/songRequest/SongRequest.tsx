import style from "./SongRequest.module.css";
import { Input } from "@/components/common/input/Input";
import { Button } from "@/components/common/button/Button";
import { ContentBox } from "./contentBox/ContentBox";
import { ContentPlus } from "./contentPlus/ContentPlus";

export const SongRequest = () => {

  const myInputStyle = {width : '80%', marginLeft: '2%'}

  return (
    <>
      <div className={style.songRequest}>
        <div className={style.group}>
          제목 <Input style={myInputStyle}/>
        </div>
        <div className={style.group}>
          내용 
          <div className={style.content} style={{marginLeft: '2%'}}>
            <ContentBox index={1}/>
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
