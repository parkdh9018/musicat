import { $ } from "@/connect/axios/setting";
import { useQuery } from "@tanstack/react-query";
import { Song } from "@/types/home";
import { useRecoilValue } from "recoil";
import { userInfoState } from "@/atoms/user.atom";

export function getSongList() {
  async function fetchSongList(): Promise<Song[]> {
    const { data } = await $.get("/music/");
    return data;
  }
  const query = useQuery(["SongRequset"], fetchSongList, {
    refetchInterval: 10000,
  });
  return query;
}

export function getSongSearch(search: string) {
  return $.get(`/music/search?queryString=${search}`);
}

export function postSongRequest(req: Song) {
  return $.post("/music/request", req);
}

export function getYoutubeSearch(
  title: string,
  artist: string,
  spotifyMusicDuration: number
) {
  return $.get(
    `/music/search/youtube/?musicTitle=${title}&musicArtist=${artist}&spotifyMusicDuration=${spotifyMusicDuration}`
  );
}

export function getLastFmSearch(title: string, artist: string) {
  return $.get(
    `/music/search/lastfm/?musicTitle=${title}&musicArtist=${artist}`
  );
}

export function getAlredayRegistedSong() {
  const userInfo = useRecoilValue(userInfoState);
  const { data, isLoading } = useQuery(
    ["AlredayRegistedSong"],
    async () => {
      const request = await $.get("/music/unique")
        .then((res) => res.status)
        .catch((e) => e.response.status);
      return request;
    },
    { enabled: !!userInfo.userRole }
  );
  return { data };
}
