import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash } from "@fortawesome/free-solid-svg-icons";
import style from "./ContentBox.module.css";
import {
  KeyboardEventHandler,
  MouseEventHandler,
  useEffect,
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
import { useClickOutside } from "@mantine/hooks";

interface ContentBoxProps extends StoryContent {
  index: number;
}

const TEXT_LENGTH_MAX = 200;

export const ContentBox = ({ index, speaker, content }: ContentBoxProps) => {

  const editRef = useClickOutside(() => {
    useEdit(index, editText);
    setEditState(false)}
  );
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  const [editstate, setEditState] = useState(false);
  const [editText, setEditText] = useState(content);

  const useEditSpeaker = editStorySpeaker();
  const useDelete = deleteStoryContent();
  const useEdit = editStoryConent();

  useEffect(() => {
    if (editText.length >= TEXT_LENGTH_MAX) {
      setEditText((prev) => prev.slice(0, TEXT_LENGTH_MAX));
    }
  }, [editText]);

  useEffect(() => {
    textareaRef.current?.focus();
  }, [editstate]);

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

  const onTextFocus = () => {
    if (!textareaRef.current) return;

    textareaRef.current.style.height =
      "auto"; /* 높이를 자동으로 조정하기 위해 초기화 */
    textareaRef.current.style.height =
      textareaRef.current.scrollHeight + "px"; /* 높이를 스크롤 높이로 설정 */
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
              style={{ marginTop: "0px", width: "70%" }}
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
          <div className={style.edit} ref={editRef}>
            <textarea
              ref={textareaRef}
              style={{ width: "100%" }}
              className={style.edit_textarea}
              onInput={autoResize}
              onFocus={onTextFocus}
              value={editText}
            />
            <div className={style.textLengthLimit}>
              {editText.length} / {TEXT_LENGTH_MAX}
            </div>
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
