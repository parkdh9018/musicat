import { Outlet } from "react-router-dom";

export const ExceptionPage = () => {
  return (
    <div>
      예외 페이지임
      <Outlet />
    </div>
  );
};
