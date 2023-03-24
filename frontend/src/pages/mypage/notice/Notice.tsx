import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import { useSetRecoilState } from "recoil";
import { nowSideNavState } from "@/atoms/common.atom";
import { getTop3Notice } from "@/connect/axios/queryHooks/notice";
import { getAlertList } from "@/connect/axios/queryHooks/alert";
import style from "./Notice.module.css";
import { useLocation } from "react-router-dom";

export const Notice = () => {
  const location = useLocation();
  console.log(location);
  const [input, setInput] = useState("");
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const { data: notice } = getTop3Notice();
  const { data: alertList } = getAlertList(1);

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("알림 / 공지사항");
  }, []);

  return (
    <div style={{ padding: "40px 3%" }}>
      <Board
        data={notice}
        grid={"20% 50% 30%"}
        headRow={["번호", "제목", "날짜"]}
        type={"noticeAll"}
        url={"/mypage/notice/"}
      />
      {/* 이 위부분 Board는 파란색으로 칠해야 한다. */}
      <Board
        data={alertList?.content}
        grid={"20% 50% 30%"}
        headRow={[]}
        type={"noticeList"}
        url={"/mypage/notice/n"}
      />
      <div style={{ textAlign: "right" }}>
        <Button
          content="모두읽음"
          onClick={() => {
            return;
          }}
          style={{ marginTop: "10px" }}
        />
      </div>
      <Pagenation
        number={1}
        first={false}
        last={false}
        totalPages={3}
        url={""}
      />
      <div className={style.bottom_search}>
        <Input
          input={input}
          setInput={setInput}
          placeholder={""}
          style={{ width: "45%" }}
        />
        {!input && (
          <div className={style.placeholder}>
            <FontAwesomeIcon icon={faMagnifyingGlass} />
            <span> 제목 + 내용</span>
          </div>
        )}
        <Button
          content="검색"
          onClick={() => {
            return;
          }}
          style={{ margin: "0 5px" }}
        />
      </div>
    </div>
  );
};
