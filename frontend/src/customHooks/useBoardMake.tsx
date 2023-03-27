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
    //
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
