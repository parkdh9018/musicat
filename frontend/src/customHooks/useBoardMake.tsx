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
    console.log("그만해");
  }

  return arr;
}
