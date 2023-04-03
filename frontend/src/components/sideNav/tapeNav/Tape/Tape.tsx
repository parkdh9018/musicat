import anime from "animejs";
import { useEffect, useRef, useState } from "react";
import style from "./Tape.module.css";
import { useRecoilValue } from "recoil";
import { musicState } from "@/atoms/song.atom";

export const Tape = () => {
  let an: anime.AnimeInstance;

  // const textContent = "뉴진스 - ditto ffff";
  // const backGroundImage = "/img/tape/background_img_test.png";

  const [textContent, setTextContent] = useState("뉴진스 - ditto ffff");
  const [backGroundImage, setBackGroundImage] = useState(
    "/img/tape/default.png"
  );

  const musicData = useRecoilValue(musicState);

  useEffect(() => {
    console.log(musicData);
    setBackGroundImage("/img/tape/Logo.png");
  }, [musicData]);

  useEffect(() => {
    const width = textContent.length * 11;

    an = anime({
      targets: `.${style.gearImg}`,
      rotate: "361",
      easing: "linear",
      loop: true,
      duration: 5000,
    });

    anime({
      targets: `.${style.text}`,
      translateX: -width,
      duration: 4000,
      round: 1, // round the text to integer values
      easing: "linear",
      loop: true,
    });
  }, []);

  const pauseEvent = () => {
    an.pause();
  };

  const playEvent = () => {
    an.play();
  };

  return (
    <>
      <div className={style.tape}>
        <div className={style.songName}>
          <div className={style.text} style={{ fontFamily: "TapeFont" }}>
            {textContent}
          </div>
        </div>
        <img className={style.tapeImg} src="/img/tape/tape.png" />
        <img
          className={style.gearImg + " " + style.left}
          src="/img/tape/gear.png"
        />
        <img
          className={style.gearImg + " " + style.right}
          src="/img/tape/gear.png"
        />
        <img
          className={style.fixedRatio}
          style={{ objectFit: "cover" }}
          src={backGroundImage}
        />
      </div>
      {/* <button onClick={pauseEvent}>pause</button>
      <button onClick={playEvent}>play</button> */}
    </>
  );
};
