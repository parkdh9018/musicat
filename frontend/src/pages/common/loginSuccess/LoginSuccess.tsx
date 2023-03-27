import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import { LoadingSpinner } from "../loadingSpinner/LoadingSpinner";

export const LoginSuccess = () => {
  console.log("?????????????");
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const token: any = searchParams.get("token");

  useEffect(() => {
    localStorage.setItem("token", token);
    console.log("이거?");
    window.close();
  }, []);

  return (
    <div style={{ width: "100%", height: "100%" }}>
      <LoadingSpinner />
    </div>
  );
};
