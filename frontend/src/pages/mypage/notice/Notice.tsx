import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import { useEffect, useState } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import style from "./Notice.module.css";
import { useSetRecoilState } from "recoil";
import { nowSideNavState } from "@/atoms/common.atom";
import {
  getAlertList,
  getTop3Notice,
  temp,
} from "@/connect/axios/queryHooks/notice";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { $ } from "@/connect/axios/setting";

export const Notice = () => {
  const [input, setInput] = useState("");
  const setNowSideNav = useSetRecoilState(nowSideNavState);
  const { data: notice } = getTop3Notice();
  const { data: alertList } = getAlertList(1);

  //
  const queryClient = useQueryClient();

  async function fetchAlert() {
    const { data } = await $.patch(`/alert`, {
      alertSeq: 2,
      alertIsRead: true,
    });
    return data;
  }

  const { mutate: readAlert } = useMutation(fetchAlert, {
    onMutate: async () => {
      await queryClient.cancelQueries(["AlertListUser", 1]);

      const oldData = queryClient.getQueryData(["AlertListUser", 1]);

      queryClient.setQueryData(["AlertListUser", 1], (oldData: any) => {
        const newData = [...oldData];

        newData.forEach((content) => {
          if (content.alertSeq === 2) {
            content.alertIsRead = true;
            return;
          }
        });
        return newData;
      });
      return { oldData };
    },
    onError: (_error, _variables, context) => {
      queryClient.setQueryData(["AlertListUser", 1], context?.oldData);
    },
    onSettled: () => {
      queryClient.invalidateQueries(["AlertListUser", 1]);
    },
  });

  //

  const aaa = temp(1, 1);

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
      />
      {/* 이 위부분 Board는 파란색으로 칠해야 한다. */}
      <Board
        data={alertList}
        grid={"20% 50% 30%"}
        headRow={[]}
        type={"noticeList"}
        pageNum={1}
      />
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
        <Button
          content="검색"
          onClick={() => {
            //  readAlert();
            aaa();
          }}
          style={{ margin: "0 5px" }}
        />
      </div>
    </div>
  );
};
