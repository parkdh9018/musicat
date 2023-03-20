import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPencil } from "@fortawesome/free-solid-svg-icons";
import style from "./ContentBox.module.css";
import { useEffect, useState } from "react";
import { deleteStoryContent, storyContentState, editStoryConent, editStorySpeaker } from "@/atoms/story.atoms";
import { useClickOutside } from "@mantine/hooks";
import { Input } from "@/components/common/input/Input";
import { useRecoilValue } from "recoil";

// TODO : atoms에 있는 type과 하나로 통합 하면 좋을듯
interface ContentBoxProps {
  index: number;
  type: "normal" | "narr";
  value: string;
  speaker : string
}
export const ContentBox = ({ index, type, value, speaker }: ContentBoxProps) => {
  const dumyOption = [
    { value: "male", name: "남성" },
    { value: "female", name: "여성" },
  ];

  const storycontent = useRecoilValue(storyContentState);

  const [selectbox, setSelectbox] = useState<string>("");
  const [editstate, setEditState] = useState(false);
  const [editText, setEditText] = useState(value);
  // const [defaultSpeaker, setDefaultSpeaker] = useState(speaker);

  const useDelete = deleteStoryContent();
  const useEditSpeaker = editStorySpeaker();
  const useEdit = editStoryConent();

  // useEffect(() => {
  //   setDefaultSpeaker(speaker);
  //   console.log("e")
  // },[])

  useEffect(() => {
    // console.log(selectbox)
    // if(defaultSpeaker == "")
    // useEditSpeaker(index, "female");
  },[selectbox]);

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
        <div>
          {index + 1}
          {type == "normal" ? (
            <SelectBox defaultValue={speaker} options={dumyOption} setValue={setSelectbox} />
          ) : (
            <>나레이션</>
          )}
          <FontAwesomeIcon icon={faTrash} onClick={deleteClick} />
        </div>
        <div>
          {editstate ? (
            <div ref={ref}>
              <Input input={editText} setInput={setEditText} />
            </div>
          ) : (
            <div onClick={editClick}>
              {editText}
            </div>
          )}
          <FontAwesomeIcon icon={faPencil} onClick={editClick} />
        </div>
      </div>
    </>
  );
};
