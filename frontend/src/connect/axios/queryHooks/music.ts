import { $ } from "@/connect/axios/setting";
import { useQuery } from "@tanstack/react-query";
import { Song } from "@/types/home";

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
  return $.post("/music/request", req);
}

export function getYoutubeSearch(title: string, artist: string) {
  return $.get(
    `/music/search/youtube/?musicTitle=${title}&musicArtist=${artist}`
  );
}
