import style from "./About.module.css";
import AboutImg01 from "@/asset/img/about01.png";
import AboutImg02 from "@/asset/img/about02.png";
import AboutImg03 from "@/asset/img/about03.png";

export const About = () => {
  return (
    <div className={style.about}>
      <div>
        <img src={AboutImg01} className={style.AboutImg}></img>
      </div>
      <div>
        <img src={AboutImg02} className={style.AboutImg}></img>
      </div>
      <div>
        <img src={AboutImg03} className={style.AboutImg}></img>
      </div>
    </div>
  );
};
