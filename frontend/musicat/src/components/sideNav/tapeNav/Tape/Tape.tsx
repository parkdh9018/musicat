import anime from "animejs";
import { useEffect } from "react";
import style from "./Tape.module.css";

export const Tape = () => {
  let an: anime.AnimeInstance;

  const textWidth = 200;
  const textContent = "뉴진스 - ditto"
  const BackGroundImage_src = "/public/img/tape/background_img_test.png"

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
      loop: true
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
        <div className={style.songName} style={{width: `${textWidth}px`}}>
          <div className={style.text}>{textContent}</div>
        </div>
        <img className={style.tapeImg} src="/public/img/tape/tape.png" />
        <img className={style.gearImg + " " + style.left} src="/public/img/tape/gear.png"/>
        <img className={style.gearImg + " " + style.right} src="/public/img/tape/gear.png"/>
        <img className={style.tapeBackImg} src={BackGroundImage_src}/>
      </div>     
      {/* <button onClick={pauseEvent}>pause</button>
      <button onClick={playEvent}>play</button> */}
    </>
  );
};
