import style from "./TapeButtons.module.css";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/common/button/Button";

export const TapeButtons = () => {
  const navigate = useNavigate();

  return (
    <div className={style.bg}>
      <Button content="재생" onClick={() => {}} className={style.play} />
      <Button content="정지" onClick={() => {}} className={style.stop} />
      <Button content="채팅" onClick={() => {}} className={style.unclicked} />
      <Button
        content="사연"
        onClick={() => {
          navigate("/story");
        }}
        className={style.clicked}
      />
      <Button content="노래" onClick={() => {}} className={style.unclicked} />
      <Button content="설명" onClick={() => {}} className={style.unclicked} />
    </div>
  );
};

// 각 버튼의 상태: 클릭상태,  컨텐츠
// 기능: onClick => navigate
