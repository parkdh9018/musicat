import style from "./Story.module.css";
import { Input } from "@/components/common/input/Input";
import { Button } from "@/components/common/button/Button";
import { ContentBox } from "./contentBox/ContentBox";
import { ContentPlus } from "./contentPlus/ContentPlus";
import { v4 as uuidv4 } from "uuid";
import {
  allStorySelector,
  storySongState,
  storyContentState,
  storyTitleState,
} from "@/atoms/story.atoms";
import { useRecoilState, useRecoilValue } from "recoil";
import { SongSearch } from "@/components/common/songSearch/SongSearch";
import { userInfoState } from "@/atoms/user.atom";
import { useEffect, useState } from "react";
import { storyHook } from "@/connect/axios/queryHooks/story";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { LoadingSpinner } from "@/pages/common/loadingSpinner/LoadingSpinner";
import { useTokenData } from "@/customHooks/useTokenData";
import { useResetRecoilState } from "recoil";

export const Story = () => {
  const disable: React.CSSProperties = {
    opacity: "0.5",
    pointerEvents: "none",
  };
  const LOGIN_REQUEST_STRING = "로그인이 필요합니다."

  // const userInfo = useRecoilValue(userInfoState);

  const userInfo = useTokenData();
  
  const { data, mutate, storyReqeustData, isLoading } = storyHook(userInfo?.userSeq ? userInfo.userSeq : -1);

  const [title, setTitle] = useRecoilState(storyTitleState);
  const [song, setSong] = useRecoilState(storySongState);
  const content = useRecoilValue(storyContentState);
  const [titlePlaceholder, setTitlePlaceholder] = useState("제목을 입력해주세요");

  const [submitButton, setSubmitButton] = useState(false);
  
  useEffect(() => {
    if (userInfo?.userNick) {
      if(data != '200') {
        useCustomToast("error","이미 신청한 사연이 있습니다.")
        
      } else {
        setSubmitButton(true);
      }
    } else {
      setTitlePlaceholder(LOGIN_REQUEST_STRING)
      useCustomToast("warning", LOGIN_REQUEST_STRING);
    }
  }, [userInfo]);

  const resestState = () => {
    useResetRecoilState(storyTitleState)
    useResetRecoilState(storyContentState)
    useResetRecoilState(storySongState)
  }

  useEffect(() => {
    if(storyReqeustData) {
      // 사연 신청완료 되면 리코일 초기화
      resestState();
    }
  },[storyReqeustData])

  const allStory = useRecoilValue(allStorySelector);

  const requestStoryEvent = () => {
    mutate(allStory);
  };

  return (
    <>
      <div className={style.story}>
        {/* {isLoading ? "나 로딩중" : "로딩안함"} */}
        <div className={style.group}>
          <span className={style.content_label}>제목</span>
          <Input
            style={{ width: "80%", marginLeft: "2%" }}
            placeholder={titlePlaceholder}
            input={title}
            setInput={setTitle}
            disabled={userInfo?.userNick ? false : true}
          />
        </div>
        <div className={style.group}>
          <span className={style.content_label} style={{ marginRight: "20px" }}>
            신청곡
          </span>
          <SongSearch status={200} placeholder=" 가수 이름 / 노래 제목" width={85} setRequestSong={setSong} />
        </div>
        <div className={style.group}>
          <span className={style.content_label}>내용</span>
          <div className={style.content} style={{ marginLeft: "2%" }}>
            {content.map((v, i) => (
              <ContentBox key={uuidv4()} {...v} index={i} />
            ))}
            <ContentPlus />
          </div>
        </div>

        <div>
          {isLoading ? (
            <LoadingSpinner />
          ) : (
            <Button
              content="등록하기"
              style={submitButton ? {} : disable}
              onClick={requestStoryEvent}
            />
          )}
        </div>
      </div>
    </>
  );
};
