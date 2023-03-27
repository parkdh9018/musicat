import { useState, useEffect } from "react";
import style from "./SongSearch.module.css";
import {
  getSongSearch,
  postSongRequest,
  Song,
} from "@/connect/axios/queryHooks/music";
import { v4 as uuidv4 } from "uuid";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

export const SongSearch = () => {
  const [isFocused, setIsFocused] = useState(false);
  const [isSearching, setIsSearching] = useState(false);
  const [search, setSearch] = useState("");
  const [searchResults, setSearchResults] = useState<Song[]>([]);
  const [requestSong, setRequestSong] = useState<Song>();
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
    setSearch(e.target.value);
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
    setRequestSong(selectedSong);
    setIsFocused(false);
  };

  // 스포티파이 검색 결과 목록
  const songSearchedList: JSX.Element[] = searchResults.map((searchResult) => (
    <div key={uuidv4()}>
      <button
        className={style.searchReasult}
        onClick={onClickSelectSong}
        value={JSON.stringify(searchResult)}
      >
        {searchResult.musicTitle}_{searchResult.musicArtist}
      </button>
      <hr />
    </div>
  ));

  // 서버에 신청곡 전송
  const onClickReq = async () => {
    if (requestSong) {
      // console.log("음악 신청 api 호출!!");
      const req = { ...requestSong, userSeq: 4 }; // 리코일 유저 만들어지면 유저 시퀀스 가져와서 적용하기
      // console.log(requestSong);
      const result = await postSongRequest(req);
      setSearch("");

      // 신청한 곡이 아직 플레이 리스트에 남아있는 경우
      if (result.data.status === 1) {
        alert("내가 신청한 곡이 재생된 이후에 다시 신청할 수 있습니다!");
      }
      // 다른 사람이 이미 신청한 곡인 경우
      if (result.data.status === 2) {
        alert(
          `다른 사용자가 해당 곡을 이미 신청했습니다. 해당 곡은 ${result.data.playOrder}번째로 재생될 예정입니다.`
        );
      }
      // 유튜브 검색 결과가 없어 재생할 수 없는 경우
      if (result.data.status === 3) {
        alert("죄송합니다 재생할 수 없는 곡입니다. 다른곡을 신청해주세요");
      }

      // console.log(result);
    }
  };

  return (
    <div className={style.searachBox}>
      <input
        className={style.inputText}
        type="text"
        onFocus={onFocus}
        value={search}
        onChange={onChangeValue}
        // onBlur={onBlur} 이걸 넣으면 적용이 안됌
        onKeyUp={onKeyUpSearch}
      />
      {isFocused ? (
        <>
          <button className={style.searchBtn} onBlur={onBlur}>
            <FontAwesomeIcon icon={faMagnifyingGlass} />
          </button>
          <div className={style.dropdown}>
            {isSearching ? (
              <div className={style.dropdownContent}>
                {searchResults.length > 0 ? (
                  <>{songSearchedList}</>
                ) : (
                  <div>{noResults ? "검색 결과가 없습니다." : "검색중.."}</div>
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
