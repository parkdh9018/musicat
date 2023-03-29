import { useState, useEffect } from "react";
import style from "./SongSearch.module.css";
import {
  getSongSearch,
  getYoutubeSearch,
} from "@/connect/axios/queryHooks/music";
import { v4 as uuidv4 } from "uuid";
import { Song } from "@/types/home";

interface SongSearchProps {
  setRequestSong: any; // 선택한 음악 객체 반환하는 함수
  width: number; // % 값을 주면 됩니다.
}

export const SongSearch = ({ setRequestSong, width }: SongSearchProps) => {
  const [isFocused, setIsFocused] = useState(false);
  const [isSearching, setIsSearching] = useState(false);
  const [search, setSearch] = useState("");
  const [searchResults, setSearchResults] = useState<Song[]>([]);
  const [timeoutId, setTimeoutId] = useState<NodeJS.Timeout>();
  const [noResults, setNoResults] = useState(false);

  const onFocus = () => {
    setIsFocused(true);
  };

  const onBlur = () => {
    // 여기서 검색 결과나 상태에 맞춰 블러 처리 할지 말지 로직 넣기
    setIsFocused(false);
  };

  // 스포티파이에 보낼 검색어(인풋)
  const onChangeValue = (e: React.ChangeEvent<HTMLInputElement>) => {
    // {}[]^|%\ 입력 불가...
    // if (e.target.value === "") {
    //   setNoResults(false);
    // }
    setSearch(e.target.value);
    setNoResults(false);
  };

  // 입력 값 스포티파이에서 검색
  const searchSpotify = async () => {
    const SearchResult = await getSongSearch(search);
    // 검색 결과가 있는 경우 드랍다운에 들어갈 데이터 설정
    if (SearchResult.data.length > 0) {
      setSearchResults(SearchResult.data);
    } else {
      // 검색 결과가 없는 경우 "검색 결과가 없습니다." 드랍다운 보이게
      setSearchResults([]);
      setNoResults(true);
    }
    clearTimeout(timeoutId);
  };

  const onKeyUpSearch = () => {
    if (search === "") {
      // 검색창이 비면 드랍다운 안보이게
      setSearchResults([]);
      setIsSearching(false);
    } else {
      // 검색어가 있는 경우 '검색중...' 드랍다운 보이게
      setIsSearching(true);
      setSearchResults([]);
    }
    // 검색어 입력이 1초이상 없는 경우에만 스포티파이 검색 실행
    if (timeoutId) clearTimeout(timeoutId);
    setTimeoutId(setTimeout(() => searchSpotify(), 1000));
  };

  // 스포티파이 검색 결과중에 신청할 곡을 선택했을 때
  const onClickSelectSong = async (e: React.MouseEvent<HTMLButtonElement>) => {
    setIsFocused(false);
    // 타입스크립트가 버튼의 값을 string으로 요구해서 변환 과정 필요
    const value = e.currentTarget.value;
    const selectedSong = JSON.parse(value);
    setSearch(`${selectedSong.musicTitle}_${selectedSong.musicArtist}`);

    // 유튜브 검색결과 확인
    const result = await getYoutubeSearch(
      selectedSong.musicTitle,
      selectedSong.musicArtist
    );

    if (result.status === 200) {
      console.log(result.status);
      console.log(result);

      setRequestSong({
        ...selectedSong,
        musicLength: result.data.musicLength,
        musicYoutubeId: result.data.musicYoutubeId,
      });
      setIsFocused(false);
    } else {
      alert("재생할 수 없는 곡입니다. 다른곡을 선택해주세요.");
    }
  };

  // 스포티파이 검색 결과 목록
  const songSearchedList: JSX.Element[] = searchResults.map((searchResult) => (
    <span key={uuidv4()}>
      <button
        className={style.searchReasult}
        onClick={onClickSelectSong}
        value={JSON.stringify(searchResult)}
      >
        {searchResult.musicTitle}_{searchResult.musicArtist}
      </button>
      <hr />
    </span>
  ));

  return (
    <span className={style.searachBox} style={{ width: `${width}%` }}>
      <input
        className={style.inputText}
        type="text"
        onFocus={onFocus}
        value={search}
        onChange={onChangeValue}
        onKeyUp={onKeyUpSearch}
        placeholder="가수나 노래 제목을 입력하세요"
      />
      {isFocused ? (
        <span>
          {isSearching ? (
            <div className={style.dropdownContent}>
              {searchResults.length > 0 ? (
                <>{songSearchedList}</>
              ) : (
                <div>{noResults ? "검색 결과가 없습니다." : "검색중.."}</div>
              )}
            </div>
          ) : null}
        </span>
      ) : null}
    </span>
  );
};
