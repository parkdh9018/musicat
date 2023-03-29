import anime from "animejs";
import { useEffect } from "react";
import style from "./Tape.module.css";

export const Tape = () => {
  let an: anime.AnimeInstance;

  const textWidth = 200;
  const textContent = "뉴진스 - ditto";
  const BackGroundImage_src = "/img/tape/background_img_test.png";

  useEffect(() => {
    an = anime({
      targets: `.${style.gearImg}`,
      rotate: "361",
      easing: "linear",
      loop: true,
      duration: 3000,
    });

    anime({
      targets: `.${style.text}`,
      translateX: textWidth,
      duration: 6000,
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
          <div className={style.text}>{textContent}</div>
        </div>
        <img
          className={style.tapeImg + " " + style.fixedRatio}
          src="/img/tape/tape.png"
        />
        <img
          className={style.gearImg + " " + style.left}
          src="/img/tape/gear.png"
        />
        <img
          className={style.gearImg + " " + style.right}
          src="/img/tape/gear.png"
        />
        <img className={style.fixedRatio} src={BackGroundImage_src} />
      </div>
      {/* <button onClick={pauseEvent}>pause</button>
      <button onClick={playEvent}>play</button> */}
    </>
  );
};
