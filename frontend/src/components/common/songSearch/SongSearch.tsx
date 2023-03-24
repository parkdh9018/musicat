import { useEffect, useState } from "react";
import { getSongSearch } from "@/connect/axios/queryHooks/music";
import style from "./SongSearch.module.css";
import { $ } from "@/connect/axios/setting";

interface Props {
  onClick: React.MouseEventHandler<HTMLButtonElement>;
}

export const SongSearch = () => {
  const [isFocused, setIsFocused] = useState(false);
  const [isSearched, setIsSearched] = useState(false);
  const [search, setSearch] = useState("");
  const [searchResult, setSearchResult] = useState([]);
  // const songSearch = getSongSearch(search);

  const onFocus = () => {
    setIsFocused(true);
  };

  const onBlur = () => {
    setIsFocused(false);
  };

  const onChangeValue = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearch(e.target.value);
  };

  const onClickSearch = async () => {
    setIsSearched(true);
    console.log(searchResult);

    // data 가져 오기 전까지 검색중,,, 띄우고
    const SearchResult: any = await $.get(
      `/music/search?queryString=${search}`
    );
    setSearchResult(SearchResult);
    console.log(searchResult);

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
