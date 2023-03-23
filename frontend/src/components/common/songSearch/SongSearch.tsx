import { useState } from "react";
import style from "./SongSearch.module.css";

interface Props {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

export const SongSearch = () => {
  const [isFocused, setIsFocused] = useState(false);
  const [isSearched, setIsSearched] = useState(false);

  const onFocus = () => {
    setIsFocused(true);
  };

  const onBlur = () => {
    setIsFocused(false);
  };

  const onClickSearch = () => {
    setIsSearched(true);
    console.log("드랍박스 보여주고 검색 api 호출");
  };

  const onClickReq = () => {
    console.log("음악 신청 api 호출!!");
  };

  return (
    <div className={style.searachBox}>
      <input className={style.inputText} type="text" onFocus={onFocus} />
      {isFocused ? (
        <div className={style.dropdown}>
          <button
            className={style.searchBtn}
            onClick={onClickSearch}
            onBlur={onBlur}
          >
            검색
          </button>
          {isSearched ? (
            <div className={style.dropdownContent}>
              <a href="#">Link 1</a>
              <a href="#">Link 2</a>
              <a href="#">Link 3</a>
            </div>
          ) : null}
        </div>
      ) : (
        <button className={style.requestBtn} onClick={onClickReq}>
          신청
        </button>
      )}
    </div>
  );
};
