import ReactPlayer from "react-player";
import { volumeState } from "@/atoms/song.atom";
import { useRecoilValue } from "recoil";

export const YoutubePlay = () => {
  const volume = useRecoilValue(volumeState);
  const videoId = "A1tZgPAcpjE";

  return (
    <div>
      YoutubePlay
      <ReactPlayer
        url={`https://www.youtube.com/embed/${videoId}`}
        controls
        playing={true}
        volume={volume}
        width="1px"
        height="1px"
      />
      ;
    </div>
  );
};
