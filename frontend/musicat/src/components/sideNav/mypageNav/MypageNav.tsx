import { nowSideNav } from "@/atoms/common.atom";
import { memberInfo } from "@/atoms/user.atom";
import { useNavigate } from "react-router-dom";
import { useRecoilValue, useResetRecoilState } from "recoil";
import { v4 as uuidv4 } from "uuid";
import style from "./MypageNav.module.css";

interface MypageNavProps {
  sideNavTitle: string;
  sideNavContent: string[];
  urls: string[];
}

export const MypageNav = ({
  sideNavTitle,
  sideNavContent,
  urls,
}: MypageNavProps) => {
  const nowNav = useRecoilValue(nowSideNav);
  const logout = useResetRecoilState(memberInfo);
  const navigate = useNavigate();

  return (
    <div className={style.side_board}>
      <h4>{sideNavTitle}</h4>
      <hr />
      {sideNavContent.map((data, i) => {
        return (
          <p
            key={uuidv4()}
            className={nowNav === data ? style.now : undefined}
            onClick={() => {
              if (data === "로그아웃") {
                logout();
                navigate("/");
              } else {
                navigate(urls[i]);
              }
            }}
          >
            {data}
          </p>
        );
      })}
      <hr />
    </div>
  );
};
