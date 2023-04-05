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
import {
  getAlertList,
  patchReadAllAlerts,
} from "@/connect/axios/queryHooks/alert";
import style from "./Notice.module.css";
import { useLocation, useNavigate } from "react-router-dom";
import { useCustomToast } from "@/customHooks/useCustomToast";

export const Notice = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const page = searchParams.get("page");
  const search = searchParams.get("search") || "";

  const [input, setInput] = useState(search);
  const navigate = useNavigate();
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const { data: notice } = getTop3Notice();
  const { data: alertList } = getAlertList(Number(page), search);
  const { mutate: readAll } = patchReadAllAlerts(search, Number(page));

  /** 사이드 Nav 초기화 */
  useEffect(() => {
    setNowSideNav("알림 / 공지사항");
  }, []);

  useEffect(() => {
    if (input.length > 20) {
      useCustomToast("warning", "검색 내용은 20자를 넘을 수 없습니다!");
      setInput(input.slice(0, 19));
    }
  }, [input]);

  return (
    <div style={{ padding: "40px 3%" }}>
      <Board
        data={notice}
        grid={"20% 50% 30%"}
        headRow={["번호", "제목", "날짜"]}
        type={"noticeAll"}
        url={"/mypage/notice/n"}
      />
      <Board
        data={alertList?.content}
        grid={"20% 50% 30%"}
        headRow={[]}
        type={"noticeList"}
        url={"/mypage/notice/"}
      />
      <div style={{ animation: "0.7s ease-in-out loadEffect6" }}>
        <div
          style={{
            textAlign: "right",
          }}
        >
          <Button
            content="모두읽음"
            onClick={() => {
              readAll();
            }}
            style={{ marginTop: "10px" }}
          />
        </div>
        <Pagenation
          number={alertList?.number}
          first={alertList?.first}
          last={alertList?.last}
          totalPages={alertList?.totalPages}
          url={`?search=${search ? search : ""}&page=`}
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
              navigate(`?search=${input ? input : ""}&page=1`);
            }}
            style={{ margin: "0 5px" }}
          />
        </div>
      </div>
    </div>
  );
};
