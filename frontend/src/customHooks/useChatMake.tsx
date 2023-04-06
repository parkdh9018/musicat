import { v4 as uuidv4 } from "uuid";
import { UserTokenData } from "./useTokenData";
import { Chat } from "@/atoms/chat.atom";
import { imageBadge } from "@/pages/mypage/inventory/badgeConfig";

const badgeColor = ["none", "red", "skyblue", "green", "gray"];

export function useChatMake(
  data: Chat,
  userInfo: UserTokenData | undefined
): JSX.Element {
  // 종료되었습니다 메세지 일 때 or 공지 메세지? => sender를 바꿔서 사용?

  // 내가 보낸 메세지 일 때
  if (Number(data.badgeSeq) === 0)
    return (
      <div key={uuidv4()}>
        <span style={{ color: "gray" }}>{data.content}</span>
      </div>
    );

  if (Number(userInfo?.userSeq) === data.senderSeq)
    return (
      <div key={uuidv4()}>
        {data.badgeSeq - 1 ? (
          <img
            src={imageBadge[data.badgeSeq - 1]}
            style={{
              backgroundColor: "white",
              display: "inline-block",
              height: "1rem",
              width: "1rem",
              // backgroundColor: `${badgeColor[data.badgeSeq - 1]}`,
              position: "relative",
              top: "3px",
              marginRight: "4px",
              borderRadius: "100%",
            }}
          />
        ) : null}

        <span style={{ color: "var(--blue-color)" }}>
          {data.sender} : {data.content}
        </span>
      </div>
    );

  // 상대방이 보낸 메세지 일 때
  return (
    <div key={uuidv4()}>
      {data.badgeSeq - 1 ? (
        <img
          src={imageBadge[data.badgeSeq - 1]}
          style={{
            backgroundColor: "white",
            display: "inline-block",
            height: "1rem",
            width: "1rem",
            position: "relative",
            top: "3px",
            marginRight: "4px",
            borderRadius: "100%",
          }}
        />
      ) : null}

      <span>
        {data.sender} : {data.content}
      </span>
    </div>
  );
}
