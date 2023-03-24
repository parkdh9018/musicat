import { $ } from "@/connect/axios/setting";
import { useQuery } from "@tanstack/react-query";

// 백엔드 변수명 바뀐거 보고 다시 업데이트
export interface Song {
  memberSeq: number;
  musicTitle: string;
  musicArtist: string;
  musicLength: TimeRanges | null;
  musicImage: ImageData;
  //
  musicSeq: number;
  musicCreatedAt: Date;
}

export function getSongList() {
  async function fetchSongList(): Promise<Song[]> {
    const { data } = await $.get("/music/");
    return data;
  }
  const query = useQuery(["SongRequset"], fetchSongList);
  return query;
}

export function getSongSearch(search: string) {
  return $.get(`/music/search?queryString=${search}`);
}

export function postSongRequest(req: Song) {
  $.post("/music/request", req);
}
