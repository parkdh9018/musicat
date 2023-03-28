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

export const Story = () => {
  const disable: React.CSSProperties = {
    opacity: "0.5",
    pointerEvents: "none",
  };

  const userSeq = useRecoilValue(userInfoState).userSeq;

  const { button, mutate, setButton, isLoading } = storyHook(userSeq);

  const [title, setTitle] = useRecoilState(storyTitleState);
  const [song, setSong] = useRecoilState(storySongState);
  const content = useRecoilValue(storyContentState);

  useEffect(() => {
    if (userSeq > 0) {
      setButton(true);
    } else {
      useCustomToast("warning", "로그인이 필요합니다.");
    }
  }, [userSeq]);

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
            input={title}
            setInput={setTitle}
          />
        </div>
        <div className={style.group}>
          <span className={style.content_label} style={{ marginRight: "20px" }}>
            신청곡
          </span>
          <SongSearch width={85} setRequestSong={setSong} />
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
              style={button ? {} : disable}
              onClick={requestStoryEvent}
            />
          )}
        </div>
      </div>
    </>
  );
};
