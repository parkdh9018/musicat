import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faPencil } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import style from "@/pages/home/songRequest/contentBox/ContentBox.module.css";

interface ContentBoxProps {
  index: number;
}

export const ContentBox = ({ index }: ContentBoxProps) => {
  const dumyOption = [
    { value: "default", name: "나레이션" },
    { value: "male", name: "남성" },
    { value: "female", name: "여성" },
  ];

  const [selectdumy, setSelectdumy] = useState("default");

  return (
    <>
      <div className={style.contentBox}>
        <div>
          {index} <SelectBox options={dumyOption} setValue={setSelectdumy} />
          <FontAwesomeIcon icon={faTrash} onClick={() => {}} />
        </div>
        <div>
          아무튼 대사 입니다.
          <FontAwesomeIcon icon={faPencil} onClick={() => {}} />
        </div>
      </div>
    </>
  );
};
