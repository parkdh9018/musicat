import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import style from "./Notice.module.css";
import { useSetRecoilState } from "recoil";
import { nowSideNav } from "@/atoms/common.atom";

export const Notice = () => {
  const [input, setInput] = useState("");
  const setNowSideNav = useSetRecoilState(nowSideNav);
  const dumyData = [
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
  ];

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("알림 / 공지사항");
  }, []);
  return (
    <div style={{ padding: "40px 3%" }}>
      <Board
        data={dumyData}
        grid={"20% 50% 30%"}
        headRow={["번호", "제목", "날짜"]}
      />
      {/* 이 위부분 Board는 파란색으로 칠해야 한다. */}
      <Board data={dumyData} grid={"20% 50% 30%"} headRow={[]} />
      <div style={{ textAlign: "right" }}>
        <Button
          content="모두읽음"
          onClick={() => {}}
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
        <Button content="검색" onClick={() => {}} style={{ margin: "0 5px" }} />
      </div>
    </div>
  );
};
