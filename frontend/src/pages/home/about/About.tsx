import ReactPlayer from "react-player";

export const About = () => {
  const videoId = "Km71Rr9K-Bw";
  return (
    <div>
      <h1>iframe test!</h1>
      <ReactPlayer
        url={`https://www.youtube.com/embed/${videoId}`}
        controls
        playing={true}
      />
      {/* <iframe
        src={`https://www.youtube.com/embed/${videoId}?&autoplay=1?`}
      ></iframe> */}
    </div>
  );
};
