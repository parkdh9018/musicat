import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPencil } from "@fortawesome/free-solid-svg-icons";
import style from "./ContentBox.module.css";
import { useState } from "react";
import {
  deleteStoryContent,
  editStoryConent,
  editStorySpeaker,
} from "@/atoms/story.atoms";
import { useClickOutside } from "@mantine/hooks";
import { Input } from "@/components/common/input/Input";

// TODO : atoms에 있는 type과 하나로 통합 하면 좋을듯
interface ContentBoxProps {
  index: number;
  type: "normal" | "narr";
  value: string;
  speaker: string;
}
export const ContentBox = ({
  index,
  type,
  value,
  speaker,
}: ContentBoxProps) => {
  const dumyOption = [
    { value: "male", name: "남성" },
    { value: "female", name: "여성" },
  ];

  const [editstate, setEditState] = useState(false);
  const [editText, setEditText] = useState(value);

  const useEditSpeaker = editStorySpeaker();
  const useDelete = deleteStoryContent();
  const useEdit = editStoryConent();

  const useEditSpeakerCallback = (value: string) => {
    useEditSpeaker(index, value);
  };

  const ref = useClickOutside(() => {
    useEdit(index, editText);
    setEditState(false);
  });

  const deleteClick = (e: any) => {
    e.stopPropagation();
    useDelete(index);
  };

  const editClick = (e: any) => {
    e.stopPropagation();
    setEditState(true);
  };

  return (
    <>
      <div className={style.contentBox}>
          <div className={style.item_number}>{index + 1}</div>
          {type == "normal" ? (
            <div className={style.speaker}>
              <SelectBox
                defaultValue={speaker}
                options={dumyOption}
                setValue={useEditSpeakerCallback}
              />
            </div>
          ) : (
            <div className={style.speaker}>나레이션</div>
          )}
          <div className={style.delete_icon}>
            <FontAwesomeIcon icon={faTrash} onClick={deleteClick} />
          </div>
          {editstate ? (
            <div className={style.edit + " " +style.edit_input} ref={ref} >
              <Input input={editText} style={{marginBottom : 0}} setInput={setEditText} />
            </div>
          ) : (
            <div className={style.edit} onClick={editClick}>{editText}</div>
          )}
          <FontAwesomeIcon className={style.edit_icon} icon={faPencil} onClick={editClick} />
        </div>
    </>
  );
};
