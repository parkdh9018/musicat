export function useBoardMake(
  type: string,
  data: any,
  userRole: string
): JSX.Element[] {
  const arr = [];

  if (type === "noticeAll") {
    arr.push(
      <span
        style={
          userRole !== "ROLE_ADMIN" ? { color: "var(--blue-color)" } : undefined
        }
      >
        {userRole === "ROLE_ADMIN" ? data.noticeSeq : "공지"}
      </span>
    );
    arr.push(
      <span
        style={
          userRole !== "ROLE_ADMIN" ? { color: "var(--blue-color)" } : undefined
        }
      >
        {data.noticeTitle}
      </span>
    );
    arr.push(<span>{data.noticeCreatedAt}</span>);
  }

  if (type === "noticeList") {
    arr.push(
      <span
        style={
          data.alertIsRead
            ? { color: "var(--fade-font-color)" }
            : { color: "var(--font-color)" }
        }
      >
        {data.alertSeq}
      </span>
    );
    arr.push(
      <span
        style={
          data.alertIsRead
            ? { color: "var(--fade-font-color)" }
            : { color: "var(--font-color)" }
        }
      >
        {data.alertTitle}
      </span>
    );
    arr.push(
      <span
        style={
          data.alertIsRead
            ? { color: "var(--fade-font-color)" }
            : { color: "var(--font-color)" }
        }
      >
        {data.alertCreatedAt}
      </span>
    );
  }

  if (type === "userMoney") {
    arr.push(<span>{data.moneyLogCreatedAt}</span>);
    if (data.moneyLogChange > 0)
      arr.push(
        <span style={{ color: "var(--green-color)" }}>
          +{data.moneyLogChange}
        </span>
      );
    else if (data.moneyLogChange < 0)
      arr.push(
        <span style={{ color: "var(--red-color)" }}>{data.moneyLogChange}</span>
      );
    else arr.push(<span>{data.moneyLogChange}</span>);
    arr.push(<span>{data.moneyLogType}</span>);
  }

  if (type === "userManage") {
    arr.push(<span>{data.userSeq}</span>);
    arr.push(<span>{data.userNickname}</span>);
    arr.push(<span>{data.userEmail}</span>);
    arr.push(<span>{data.userCreatedAt}</span>);
    arr.push(
      <span
        style={
          data.userIsChattingBan
            ? { color: "var(--red-color)" }
            : { color: "var(--green-color)" }
        }
      >
        {data.userIsChattingBan ? "금지" : "허용"}
      </span>
    );
    arr.push(
      <span
        style={
          data.userIsBan
            ? { color: "var(--red-color)" }
            : { color: "var(--green-color)" }
        }
      >
        {data.userIsBan ? "금지" : "허용"}
      </span>
    );
  }

  return arr;
}
