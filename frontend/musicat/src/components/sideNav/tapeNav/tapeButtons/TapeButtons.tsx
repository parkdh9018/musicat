import style from "./TapeButtons.module.css";
import { useNavigate } from "react-router-dom";

export const TapeButtons = () => {
  const navigate = useNavigate();

  const onClick = (pageAdress: string) => {
    navigate(`/${pageAdress}`);
  };

  return (
    <div className={style.bg}>
      <span className={style.play} onClick={onClick("")}>
        재생
      </span>
      <span className={style.stop} onClick={onClick("")}>
        정지
      </span>
      <span className={style.nonClicked} onClick={onClick("")}>
        채팅
      </span>
      <span className={style.clicked} onClick={onClick("story")}>
        사연
      </span>
      <span className={style.nonClicked} onClick={onClick("songRequest")}>
        노래
      </span>
      <span className={style.nonClicked} onClick={onClick("about")}>
        설명
      </span>
    </div>
  );
};

// 각 버튼의 상태: 클릭상태,  컨텐츠
// 기능: onClick => navigate
