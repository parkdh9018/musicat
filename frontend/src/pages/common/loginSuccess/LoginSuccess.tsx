import { useLocation } from "react-router-dom";
import { LoadingSpinner } from "../loadingSpinner/LoadingSpinner";

export const LoginSuccess = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const token: any = searchParams.get("token");
  console.log("성공적으로 들어옴");
  console.log(token);
  console.log("토큰 세팅 시작");
  localStorage.setItem("token", token);
  console.log("세팅 완료");
  window.close();

  return (
    <div style={{ width: "100%", height: "100%" }}>
      <LoadingSpinner />
    </div>
  );
};
