import style from "./Board.module.css";
import { useNavigate } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";
import { useBoardMake } from "@/customHooks/useBoardMake";
import { useRecoilValue } from "recoil";
import { userInfoState } from "@/atoms/user.atom";

/** headRow : 맨 첫번째 row에 무엇을 넣을 것인가? 제목 내용 등등등
 *  grid : 각각의 내용들에 어느정도의 width를 할당할 것인가? 데이터 예시 ex) "40% 30% 30%"
 *  data : 말 그대로 부모에서 내려주는 데이터
 *  url : 클릭시 모달이 아니라 detail 이동일 경우 이동할 url
 *  setIsModalOpen : 클릭시 모달이 뜨는 경우 모달을 on 시켜주는 함수
 */

interface BoardProps {
  headRow: string[];
  grid: string;
  data?: any[];
  url?: string;
  type: "noticeAll" | "noticeList" | "userMoney" | "userManage";
  setIsModalOpen?: (bol: boolean) => void;
  boardColumnClick?: (seq: number, nickname: string) => void;
  setMoneySeq?: (num: number) => void;
}

export const Board = ({
  headRow,
  grid,
  data,
  url,
  type,
  setIsModalOpen,
  boardColumnClick,
  setMoneySeq,
}: BoardProps) => {
  const navigate = useNavigate();
  const userInfo = useRecoilValue(userInfoState);

  return (
    <>
      {headRow.length !== 0 && (
        <div
          className={style.header_container}
          style={{ gridTemplateColumns: grid }}
        >
          {headRow.map((content) => {
            return (
              <div
                key={uuidv4()}
                style={headRow.length > 4 ? { fontSize: "0.85rem" } : undefined}
              >
                {content}
              </div>
            );
          })}
        </div>
      )}
      {data?.map((content) => {
        return (
          <div
            key={uuidv4()}
            className={style.content_container}
            style={{ gridTemplateColumns: grid }}
          >
            {useBoardMake(type, content, userInfo.userRole).map(
              (contentRow: any) => {
                return (
                  <div
                    key={uuidv4()}
                    onClick={() => {
                      setMoneySeq
                        ? setMoneySeq(content.moneyLogSeq)
                        : undefined;

                      setIsModalOpen ? setIsModalOpen(true) : undefined;

                      url
                        ? navigate(
                            url + `${content.alertSeq || content.noticeSeq}`
                          )
                        : undefined;

                      boardColumnClick
                        ? boardColumnClick(
                            content.userSeq,
                            content.userNickname
                          )
                        : undefined;
                    }}
                  >
                    {contentRow}
                  </div>
                );
              }
            )}
          </div>
        );
      })}
    </>
  );
};
