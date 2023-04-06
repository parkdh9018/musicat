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
import { useEffect, useState } from "react";
import { storyHook } from "@/connect/axios/queryHooks/story";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { LoadingSpinner } from "@/pages/common/loadingSpinner/LoadingSpinner";
import { useTokenData } from "@/customHooks/useTokenData";
import { useResetRecoilState } from "recoil";
import { getUserMoney } from "@/connect/axios/queryHooks/user";

const LOGIN_REQUEST_STRING = "로그인이 필요합니다.";
const TITLE_LENGTH_MAX = 30;

export const Story = () => {
  const disable: React.CSSProperties = {
    opacity: "0.5",
    pointerEvents: "none",
  };

  // const userInfo = useRecoilValue(userInfoState);

  const userInfo = useTokenData();
  const { data: moneyData } = getUserMoney();

  const { data, mutate, storyReqeustData, isLoading } = storyHook(
    userInfo?.userSeq ? userInfo.userSeq : -1
  );

  const resetTitle = useResetRecoilState(storyTitleState);
  const resetContent = useResetRecoilState(storyContentState);
  const resetSong = useResetRecoilState(storySongState);

  const [title, setTitle] = useRecoilState(storyTitleState);
  const [song, setSong] = useRecoilState(storySongState);
  const content = useRecoilValue(storyContentState);

  const [titlePlaceholder, setTitlePlaceholder] =
    useState("로그인을 해야 신청이 가능합니다");
  // const [submitButton, setSubmitButton] = useState(false);
  // const [titleDisable, setTitleDisable] = useState(true);
  const [submitState, setSubmitState] = useState<boolean>(false);

  useEffect(() => {
    if (userInfo?.userNick) {
      if (data == "200") {
        // 사연 신청 가능할때
        setSubmitState(true);
        setTitlePlaceholder("제목을 입력해주세요");
      } else if (data == "409") {
        // 사연 신청 불가능할때
        useCustomToast("error", "이미 신청한 사연이 있습니다");
        setTitlePlaceholder("이미 신청한 사연이 있습니다");
      }
    } else {
      setTitlePlaceholder(LOGIN_REQUEST_STRING);
      useCustomToast("warning", LOGIN_REQUEST_STRING);
    }
  }, [data]);

  useEffect(() => {
    if (storyReqeustData) {
      // 사연 신청완료 되면 리코일 초기화
      resetTitle();
      resetContent();
      resetSong();
      setSubmitState(false);
      // setSubmitButton(false);
    }
  }, [storyReqeustData]);

  useEffect(() => {
    if (title.length >= TITLE_LENGTH_MAX) {
      useCustomToast(
        "warning",
        `글자수 ${TITLE_LENGTH_MAX}자 초과는 제한됩니다`
      );
      setTitle((prev) => prev.slice(0, TITLE_LENGTH_MAX));
    }
  }, [title]);

  const allStory = useRecoilValue(allStorySelector);

  const requestStoryEvent = () => {
    if (!moneyData || moneyData.data.userMoney < 50) {
      useCustomToast("error", "츄르가 부족합니다");
      return;
    }

    mutate(allStory);
  };

  return (
    <>
      <div
        className={style.story}
        style={{ animation: "0.7s ease-in-out loadEffect3" }}
      >
        {/* {isLoading ? "나 로딩중" : "로딩안함"} */}
        <div className={style.group}>
          <span className={style.content_label}>제목</span>
          <Input
            style={{ width: "80%" }}
            placeholder={titlePlaceholder}
            input={title}
            setInput={setTitle}
            disabled={!submitState}
          />
        </div>
        <div className={style.group}>
          <span className={style.content_label}>신청곡</span>
          {submitState ? (
            <SongSearch
              status={200}
              placeholder=" 가수 이름 / 노래 제목"
              width={85}
              setRequestSong={setSong}
            />
          ) : (
            <Input
              style={{ width: "80%" }}
              placeholder=""
              input={""}
              setInput={() => {}}
              disabled={true}
            />
          )}
        </div>
        <div className={style.group}>
          <span>내용</span>
          <div className={style.content}>
            {content.map((v, i) => (
              <ContentBox key={uuidv4()} {...v} index={i} />
            ))}
            {submitState ? (
              <ContentPlus />
            ) : (
              <div className={style.contentNotLogin}></div>
            )}
          </div>
        </div>

        <div>
          {isLoading ? (
            <LoadingSpinner />
          ) : (
            <Button
              content="등록하기"
              style={submitState ? {} : disable}
              onClick={requestStoryEvent}
            />
          )}
        </div>
      </div>
    </>
  );
};
