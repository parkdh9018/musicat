import style from "./About.module.css";
import AboutImg from "@/asset/img/about.png";

export const About = () => {
  return (
    <div className={style.about}>
      <img src={AboutImg} className={style.AboutImg}></img>
    </div>
  );
};
