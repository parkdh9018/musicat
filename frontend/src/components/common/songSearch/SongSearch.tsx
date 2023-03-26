import { useEffect, useState } from "react";
import style from "./SongSearch.module.css";
import { $ } from "@/connect/axios/setting";
import { Song } from "@/connect/axios/queryHooks/music";
import { v4 as uuidv4 } from "uuid";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

export const SongSearch = () => {
  const [isFocused, setIsFocused] = useState(false);
  const [isSearched, setIsSearched] = useState(false);
  const [search, setSearch] = useState("");
  const [searchResults, setSearchResults] = useState<Song[]>([]);
  const [requestSong, setRequestSong] = useState<Song>();

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
    if (search) {
      setSearchResults([]);
      setIsSearched(true);
      // 여기 타입 설정 어카지....ㅠ
      const SearchResult: any = await $.get(
        `/music/search?queryString=${search}`
      );
      setSearchResults(SearchResult.data);
    }
  };

  const onClickSelectSong = (e: React.MouseEvent<HTMLButtonElement>) => {
    const value = e.currentTarget.value;
    const selectedSong = JSON.parse(value);
    setRequestSong(selectedSong);
    console.log(selectedSong);
    setSearch(`${selectedSong.musicTitle}_${selectedSong.musicArtist}`);
    setIsFocused(false);
  };

  // 검색 결과 없을 때 보여줄 화면 만들기
  const songSearchedList: JSX.Element[] = searchResults.map((searchResult) => (
    <>
      <button
        className={style.searchReasult}
        key={uuidv4()}
        onClick={onClickSelectSong}
        value={JSON.stringify(searchResult)}
      >
        {searchResult.musicTitle}_{searchResult.musicArtist}
      </button>
      <hr />
    </>
  ));

  const onClickReq = async () => {
    console.log("음악 신청 api 호출!!");
    console.log(requestSong);
    // song.ts 파일로 빼내기
    $.post("/music/request", { ...requestSong, userSeq: 1 });
  };

  return (
    <div className={style.searachBox}>
      <input
        className={style.inputText}
        type="text"
        onFocus={onFocus}
        value={search}
        onChange={onChangeValue}
        onKeyUp={onClickSearch}
      />
      {isFocused ? (
        <>
          <button
            className={style.searchBtn}
            onClick={onClickSearch}
            onBlur={onBlur}
          >
            <FontAwesomeIcon icon={faMagnifyingGlass} />
          </button>
          <div className={style.dropdown}>
            {isSearched ? (
              <div className={style.dropdownContent}>
                {searchResults.length > 0 ? (
                  <>{songSearchedList}</>
                ) : (
                  <div>검색중..</div>
                )}
              </div>
            ) : null}
          </div>
        </>
      ) : (
        <button className={style.requestBtn} onClick={onClickReq}>
          20C
        </button>
      )}
    </div>
  );
};
