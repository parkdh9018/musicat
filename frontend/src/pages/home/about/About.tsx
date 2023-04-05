import style from "./About.module.css";
import AboutImg from "@/asset/img/about.png";

export const About = () => {
  return (
    <div
      className={style.about}
      style={{ animation: "0.7s ease-in-out loadEffect3" }}
    >
      <img src={AboutImg} className={style.AboutImg}></img>
    </div>
  );
};
