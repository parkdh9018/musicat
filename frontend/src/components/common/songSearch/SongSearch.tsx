import { useState, useEffect, useRef } from "react";
import style from "./SongSearch.module.css";
import {
  getLastFmSearch,
  getSongSearch,
  getYoutubeSearch,
} from "@/connect/axios/queryHooks/music";
import { v4 as uuidv4 } from "uuid";
import { Song } from "@/types/home";
import { useCustomToast } from "@/customHooks/useCustomToast";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import { nowYoutubeSearchState, searchState } from "@/atoms/song.atom";
import Swal from "sweetalert2";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { userInfoState } from "@/atoms/user.atom";

interface SongSearchProps {
  setRequestSong: any; // 선택한 음악 객체 반환하는 함수
  width: number; // % 값을 주면 됩니다.
  placeholder: string;
  status: number | undefined;
}

export const SongSearch = ({
  setRequestSong,
  width,
  placeholder,
  status,
}: SongSearchProps) => {
  const userInfo = useRecoilValue(userInfoState);
  const [isSearching, setIsSearching] = useState(false);
  const [search, setSearch] = useRecoilState(searchState);
  const setYoutubeSearch = useSetRecoilState(nowYoutubeSearchState);
  const [searchResults, setSearchResults] = useState<Song[]>([]);
  const [timeoutId, setTimeoutId] = useState<NodeJS.Timeout>();
  const [noResults, setNoResults] = useState(false);
  const inputRef = useRef<HTMLInputElement>(null);

  // 스포티파이에 보낼 검색어(인풋)
  const onChangeValue = (e: React.ChangeEvent<HTMLInputElement>) => {
    // {}[]^|%\ 입력 불가...
    if (!userInfo.userRole) {
      Swal.fire({
        icon: "warning",
        title: "",
        text: "로그인이 필요한 서비스 입니다!",
        confirmButtonText: "닫기",
      }).then(() => {
        e.target.value = "";
      });
    }
    setSearch(e.target.value);
    if (!e.target.value) {
      setRequestSong(undefined);
      return;
    }
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
    if (search) setTimeoutId(setTimeout(() => searchSpotify(), 230));
  };

  // 스포티파이 검색 결과중에 신청할 곡을 선택했을 때
  const onClickSelectSong = async (e: React.MouseEvent<HTMLButtonElement>) => {
    setYoutubeSearch(true);
    // 타입스크립트가 버튼의 값을 string으로 요구해서 변환 과정 필요
    const value = e.currentTarget.value;
    const selectedSong = JSON.parse(value);

    const searchedTitleArtist = `${selectedSong.musicTitle}_${selectedSong.musicArtist}`;
    searchedTitleArtist.length > 65
      ? setSearch(searchedTitleArtist.slice(0, 62) + "...")
      : setSearch(searchedTitleArtist);

    // 유튜브 검색결과 확인
    const result = await getYoutubeSearch(
      selectedSong.musicTitle,
      selectedSong.musicArtist,
      selectedSong.spotifyMusicDuration
    ).catch((e) => e.response);

    // LastFm 검색결과 확인
    // let result = await getLastFmSearch(
    //   selectedSong.musicTitleEn,
    //   selectedSong.musicArtistEn
    // ).catch((e) => e.response.status);
    // if (result === 500) {
    //   result = await getLastFmSearch(
    //     selectedSong.musicTitle + selectedSong.musicArtist,
    //     ""
    //   ).catch((e) => e.response.status);
    //   if (result === 500) {
    //     result = await getLastFmSearch(selectedSong.musicTitle, "").catch(
    //       (e) => e.response.status
    //     );
    //   }
    // }
    console.log("노래데이터");
    console.log(result);

    if (result.status === 200 && result.data.musicYoutubeId) {
      setYoutubeSearch(false);
      setRequestSong({
        ...selectedSong,
        musicLength: result.data.musicLength,
        musicYoutubeId: result.data.musicYoutubeId,
      });
    } else {
      Swal.fire({
        icon: "error",
        title: "",
        text: "재생할 수 없는 곡입니다. 다른곡을 선택해주세요.",
        confirmButtonText: "닫기",
      }).then(() => {
        setYoutubeSearch(false);
        setRequestSong(undefined);
        setSearch("");
      });
    }
  };

  useEffect(() => {
    if (search.length > 65) {
      useCustomToast("warning", "검색어는 65자를 넘을 수 없습니다!");
      setSearch(search.slice(0, 64));
    }
  }, [search]);

  // 스포티파이 검색 결과 목록
  const songSearchedList: JSX.Element[] = searchResults.map((searchResult) => (
    <div key={uuidv4()}>
      <button
        className={style.searchReasult}
        onClick={onClickSelectSong}
        value={JSON.stringify(searchResult)}
      >
        <img
          src={searchResult.musicImage}
          alt="사진"
          className={style.songImg}
        />
        <span>
          <div className={style.songText}>{searchResult.musicTitle}</div>
          <div className={style.songText}>{searchResult.musicArtist}</div>
        </span>
      </button>
      <hr className={style.hr} />
    </div>
  ));

  return (
    <span className={style.searachBox} style={{ width: `${width}%` }}>
      <input
        className={style.inputText}
        type="text"
        value={search}
        onChange={onChangeValue}
        onKeyUp={onKeyUpSearch}
        disabled={status === 409}
        ref={inputRef}
      />
      {!search && (
        <div className={style.placeholder}>
          {status === 200 ? <FontAwesomeIcon icon={faMagnifyingGlass} /> : null}
          <span>{placeholder}</span>
        </div>
      )}
      {inputRef.current === document.activeElement ? (
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
