import anime from "animejs";
import { useEffect, useState } from "react";
import style from "./Tape.module.css";
import { useRecoilValue } from "recoil";
import { musicState, playNowState } from "@/atoms/song.atom";
let an: anime.AnimeInstance;

export const Tape = () => {

  const [textContent, setTextContent] = useState("");
  const [backGroundImage, setBackGroundImage] = useState("");

  const musicData = useRecoilValue(musicState);
  const playNow = useRecoilValue(playNowState);

  useEffect(() => {

    if(!an) return;

    if (playNow) {
      an.play();
    } else {
      an.pause();
    }
  }, [playNow]);

  useEffect(() => {
    setBackGroundImage(musicData.image || "/img/tape/init.png");
    setTextContent(
      musicData.title
        ? musicData.artist + " / " + musicData.title
        : " ------ musicat ------ "
    );
  }, [musicData]);

  useEffect(() => {
    // const width = textContent.length * 11;

    an = anime({
      targets: `.${style.gearImg}`,
      rotate: "361",
      easing: "linear",
      loop: true,
      duration: 5000,
    });

    anime({
      targets: `.${style.text}`,
      translateX: -320,
      duration: 6000,
      round: 1, // round the text to integer values
      easing: "linear",
      loop: true,
    });
  }, []);

  return (
    <>
      <div className={style.tape}>
        <div className={style.songName}>
          <div
            className={style.text}
            style={{ fontFamily: "TapeFont", transform: "translateX(200px)" }}
          >
            {"  " + textContent}
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
    </>
  );
};
