import { useEffect, useState } from "react";
import { getSongSearch } from "@/connect/axios/queryHooks/music";
import style from "./SongSearch.module.css";

interface Props {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

export const SongSearch = () => {
  const [isFocused, setIsFocused] = useState(false);
  const [isSearched, setIsSearched] = useState(false);
  const [search, setSearch] = useState("");
  const [isClick, setIsClicked] = useState(false);

  const songSearch = getSongSearch(search);

  async function getGuestHouseList() {
    const { data } = await getSongSearch(search);
    console.log(data);
  }

  // useEffect(() => {
  //   console.log(result);
  // }, [search]);

  const onFocus = () => {
    setIsFocused(true);
  };

  const onBlur = () => {
    setIsFocused(false);
  };

  const onChangeValue = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };

  const onClickSearch = () => {
    getGuestHouseList();

    setIsClicked(true);
    // 검색,,,, api 호출
    console.log(search);

    setIsSearched(true); // isFetching으로 바꾸기
    // isFetching == true

    console.log("드랍박스 보여주고 검색 api 호출");
  };

  const onClickReq = () => {
    console.log("음악 신청 api 호출!!");
  };

  return (
    <div className={style.searachBox}>
      <input
        className={style.inputText}
        type="text"
        onFocus={onFocus}
        value={search}
        onChange={onChangeValue}
      />
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
              <div>Link 1</div>
              <div>Link 2</div>
              <div>Link 3</div>
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
