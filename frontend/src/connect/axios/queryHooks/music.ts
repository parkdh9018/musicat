import { $ } from "@/connect/axios/setting";
import { useQuery } from "@tanstack/react-query";

interface Song {
  memberSeq: number;
  musicName: string;
  musicArtist: string;
  musicLength: TimeRanges | null;
  musicCover: ImageData | null;
  //
  musicSeq: number;
  musicCreatedAt: Date | null;
}

export function getSongList() {
  async function fetchSongList(): Promise<Song[]> {
    const { data } = await $.get("/music");
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
