import { useState } from "react";
import { Input } from "@/components/common/input/Input";
import { Button } from "@/components/common/button/Button";
import style from "./SongSearch.module.css";

export const SongSearch = () => {
  const [title, setTitle] = useState("");

  const onClick = () => {
    console.log("clicked!!");
  };

  return (
    <div className={style.search}>
      <Input
        style={{ width: "100%", marginRight: "3%", marginBottom: "0px" }}
        type="text"
        placeholder="검색어를 입력하세요"
        input={title}
        setInput={setTitle}
      />

      <Button
        content="chur"
        onClick={onClick}
        style={{ width: "height", fontSize: "50%", textAlign: "center" }}
      />
    </div>
  );
};
