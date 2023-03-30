import ReactPlayer from "react-player";

export const YoutubePlay = () => {
  const videoId = "JEJGD9mIYQQ";
  return (
    <div>
      YoutubePlay
      <ReactPlayer
        url={`https://www.youtube.com/embed/${videoId}`}
        controls
        playing={true}
        // volume={volume} 리코일로 받아오기
        width="1px"
        height="1px"
      />
      ;
    </div>
  );
};
