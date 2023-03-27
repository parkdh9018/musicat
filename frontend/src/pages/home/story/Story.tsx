import style from "./Story.module.css";
import { Input } from "@/components/common/input/Input";
import { Button } from "@/components/common/button/Button";
import { ContentBox } from "./contentBox/ContentBox";
import { ContentPlus } from "./contentPlus/ContentPlus";
import { v4 as uuidv4 } from "uuid";
import {
  allStorySelector,
  songTitleState,
  storyContentState,
  storyTitleState,
} from "@/atoms/story.atoms";
import { useRecoilState, useRecoilValue } from "recoil";
import { SongSearch } from "@/components/common/songSearch/SongSearch";
import { postRequestStory } from "@/connect/axios/queryHooks/story";

export const Story = () => {

  const [title, setTitle] = useRecoilState(storyTitleState);
  const content = useRecoilValue(storyContentState);
  const [songTitle, setSongTitle] = useRecoilState(songTitleState);

  const allStory = useRecoilValue(allStorySelector);

  const requestStoryEvent = () => {
    postRequestStory(allStory);
  }

  return (
    <>
      <div className={style.story}>
        <div className={style.group}>
          <span className={style.content_label}>제목</span>
          <Input style={{ width: "80%", marginLeft: "2%" }} input={title} setInput={setTitle} />
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
        <span className={style.content_label}>신청곡</span>
        <SongSearch/>
         <div>
          <Button content="등록하기" onClick={requestStoryEvent} />
        </div>
      </div>
    </>
  );
};
