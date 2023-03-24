export function useBoardMake(type: string, data: any): JSX.Element[] {
  const arr = [];

  if (type === "noticeAll") {
    arr.push(<span style={{ color: "var(--blue-color)" }}>공지</span>);
    arr.push(
      <span style={{ color: "var(--blue-color)" }}>{data.noticeTitle}</span>
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
    console.log("그만해");
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
