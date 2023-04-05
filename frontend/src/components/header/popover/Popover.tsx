import { logoutUser, userInfoState } from "@/atoms/user.atom";
import { Link, useNavigate } from "react-router-dom";
import { useRecoilValue, useResetRecoilState } from "recoil";
import style from "./Popover.module.css";

export const Popover = ({
  isPopoverOn,
  setIsPopoverOn,
}: {
  isPopoverOn: boolean;
  setIsPopoverOn: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const userInfo = useRecoilValue(userInfoState);
  const navigate = useNavigate();
  const clear = useResetRecoilState(userInfoState);
  const userComponent = (
    <>
      <Link to="/mypage/myinfo">
        <div
          className={style.content_text}
          onClick={() => setIsPopoverOn(!isPopoverOn)}
        >
          나의 정보 관리
        </div>
      </Link>
      <Link to="/mypage/notice">
        <div
          className={style.content_text}
          onClick={() => setIsPopoverOn(!isPopoverOn)}
        >
          알림 / 공지사항
        </div>
      </Link>
      <Link to="/mypage/inventory">
        <div
          className={style.content_text}
          onClick={() => setIsPopoverOn(!isPopoverOn)}
        >
          인벤토리
        </div>
      </Link>
      <Link to="">
        <div
          className={style.content_text}
          onClick={() => {
            logoutUser(clear, navigate);
            setIsPopoverOn(!isPopoverOn);
          }}
        >
          로그아웃
        </div>
      </Link>
    </>
  );

  const adminComponent = (
    <>
      <Link to="/mypage/notice-manage">
        <div
          className={style.content_text}
          onClick={() => setIsPopoverOn(!isPopoverOn)}
        >
          공지사항
        </div>
      </Link>
      <Link to="/mypage/user-manage">
        <div
          className={style.content_text}
          onClick={() => setIsPopoverOn(!isPopoverOn)}
        >
          유저관리
        </div>
      </Link>
      <Link
        to=""
        onClick={() => {
          logoutUser(clear, navigate);
          setIsPopoverOn(!isPopoverOn);
        }}
      >
        <div className={style.content_text}>로그아웃</div>
      </Link>
    </>
  );
  return (
    <div
      className={isPopoverOn ? style.popover : style.popover + " " + style.off}
    >
      <div className={style.triangle}></div>
      <div className={style.content}>
        {userInfo.userRole === "ROLE_ADMIN" ? adminComponent : userComponent}
      </div>
    </div>
  );
};
