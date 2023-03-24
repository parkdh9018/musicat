import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import style from "./NoticeManage.module.css";
import { useSetRecoilState } from "recoil";
import { nowSideNavState } from "@/atoms/common.atom";
import { useNavigate } from "react-router-dom";

export const NoticeManage = () => {
  const [input, setInput] = useState("");
  const navigate = useNavigate();
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const dumyData = [
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
  ];

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("공지사항");
  }, []);
  return (
    <div style={{ padding: "40px 3%" }}>
      <Board
        data={dumyData}
        grid={"20% 50% 30%"}
        headRow={["번호", "제목", "날짜"]}
      />
      <div style={{ textAlign: "right" }}>
        <Button
          content="공지작성"
          onClick={() => {
            navigate("/mypage/notice-manage/new");
          }}
          style={{ marginTop: "10px" }}
        />
      </div>
      <Pagenation
        number={1}
        first={false}
        last={false}
        totalPages={3}
        url={"/"}
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
