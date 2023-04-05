import style from "./About.module.css";
import AboutImg01 from "@/asset/img/about.png";

export const About = () => {
  return (
    <div className={style.about}>
      <img src={AboutImg01} className={style.AboutImg}></img>
    </div>
  );
};
