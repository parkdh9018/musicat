import { Broadcast } from "@/components/broadcast/Broadcast";
import { Header } from "@/components/header/Header";
import { Outlet } from "react-router-dom";

export const NormalPage = () => {
  return (
    <>
      <Header />
      <div style={{ backgroundColor: "var(--broadcast-color)" }}>
        <Broadcast />
      </div>
      <Outlet />
    </>
  );
};
