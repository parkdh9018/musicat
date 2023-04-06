import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import style from "./ContentBox.module.css";
import {
  KeyboardEventHandler,
  MouseEventHandler,
  useRef,
  useState,
} from "react";
import {
  deleteStoryContent,
  editStoryConent,
  editStorySpeaker,
} from "@/atoms/story.atoms";
import { StoryContent } from "@/types/home";
import { TTS_OPTION } from "./speakerConfig";

// TODO : atoms에 있는 type과 하나로 통합 하면 좋을듯
interface ContentBoxProps extends StoryContent {
  index: number;
}
export const ContentBox = ({ index, speaker, content }: ContentBoxProps) => {
  const textareaRef = useRef<HTMLTextAreaElement>(null);
  const [editstate, setEditState] = useState(false);
  const [editText, setEditText] = useState(content);

  const useEditSpeaker = editStorySpeaker();
  const useDelete = deleteStoryContent();
  const useEdit = editStoryConent();

  const useEditSpeakerCallback = (value: string) => {
    useEditSpeaker(index, value);
  };

  const deleteClick: MouseEventHandler = (e) => {
    e.stopPropagation();
    useDelete(index);
  };

  const editClick: MouseEventHandler = (e) => {
    e.stopPropagation();
    setEditState(true);
  };

  const editEnter: KeyboardEventHandler = (e) => {
    e.stopPropagation();
    if (e?.key === "Enter") {
      useEdit(index, editText);
    }
  };

  const autoResize = () => {
    if (!textareaRef.current) return;

    setEditText(textareaRef.current.value);
    textareaRef.current.style.height =
      "auto"; /* 높이를 자동으로 조정하기 위해 초기화 */
    textareaRef.current.style.height =
      textareaRef.current.scrollHeight + "px"; /* 높이를 스크롤 높이로 설정 */
  };

  return (
    <>
      <div className={style.contentBox} onKeyDown={editEnter}>
        <div className={style.item_number}>{index + 1}</div>
        {speaker == "narr" ? (
          <div className={style.speaker}>나레이션</div>
        ) : (
          <div className={style.speaker}>
            <SelectBox
              style={{ marginTop: "0px", width: "100%" }}
              defaultValue={speaker}
              options={TTS_OPTION}
              setValue={useEditSpeakerCallback}
            />
          </div>
        )}
        <div className={style.delete_icon}>
          <FontAwesomeIcon icon={faTrash} onClick={deleteClick} />
        </div>
        {editstate ? (
          <div className={style.edit}>
            <textarea
              ref={textareaRef}
              style={{ width: "100%" }}
              className={style.edit_textarea}
              onInput={autoResize}
              value={editText}
            />
          </div>
        ) : (
          <div className={style.edit} onClick={editClick}>
            {editText ? (
              editText
            ) : (
              <div className={style.edit_text}>내용을 입력해주세요</div>
            )}
          </div>
        )}
      </div>
    </>
  );
};
