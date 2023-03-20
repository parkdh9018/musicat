import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPencil } from "@fortawesome/free-solid-svg-icons";
import style from "./ContentBox.module.css";
import { useState } from "react";
import { deleteStoryContent } from "@/atoms/story.atoms";

// TODO : atoms에 있는 type과 하나로 통합 하면 좋을듯
interface ContentBoxProps {
  index: number;
  type: "normal" | "narr";
  value: string;
}

export const ContentBox = ({ index, type, value }: ContentBoxProps) => {
  const dumyOption = [
    { value: "male", name: "남성" },
    { value: "female", name: "여성" },
  ];

  const useDelete = deleteStoryContent();

  const [selectdumy, setSelectdumy] = useState("male");

  const deleteClick = () => {
    useDelete(index)
  }

  return (
    <>
      <div className={style.contentBox}>
        <div>
          {index} 
          {type == "normal" ? <SelectBox options={dumyOption} setValue={setSelectdumy} /> : <>나레이션</>}
          <FontAwesomeIcon icon={faTrash} onClick={deleteClick} />
        </div>
        <div>
          {value}
          <FontAwesomeIcon icon={faPencil} onClick={() => {}} />
        </div>
      </div>
    </>
  );
};
