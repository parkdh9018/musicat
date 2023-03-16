import anime from "animejs";
import { useEffect } from "react";
import style from "./Tape.module.css";

export const Tape = () => {

  let an:  anime.AnimeInstance;

  useEffect(() => {
    an = anime({
      targets: `.${style.gearImg}`,
      rotate: "361",
      easing: "linear",
      loop: true,
      duration: 3000,
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
        <div className={style.songName}>뉴진스 - ditto</div>
        <img className={style.tapeImg} src="/public/img/tape/tape.png" />
        <img className={style.gearImg} style={{top: '110px', left: '103px'}} src="/public/img/tape/gear.png"/>
        <img className={style.gearImg} style={{top: '110px', left: '230px'}} src="/public/img/tape/gear.png"/>
        <img className={style.tapeBackImg} src="/public/img/tape/background_img_test.png"/>
      </div>
      <button onClick={pauseEvent}>pause</button>
      <button onClick={playEvent}>play</button>
    </>
  );
};
