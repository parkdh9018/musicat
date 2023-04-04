import { RouteObject } from "react-router-dom";
import { Home } from "@/pages/home/Home";
import { LoginSuccess } from "./common/loginSuccess/LoginSuccess";
import { Page404 } from "./common/page404/Page404";
import { ExceptionPage } from "./ExceptionPage";
import { About } from "./home/about/About";
import { Chat } from "./home/chat/Chat";
import { Introduce } from "./home/introduce/Introduce";
import { SongRequest } from "./home/songRequest/SongRequest";
import { Story } from "./home/story/Story";
import { Inventory } from "./mypage/inventory/Inventory";
import { Myinfo } from "./mypage/myinfo/Myinfo";
import { Mypage } from "./mypage/Mypage";
import { Notice } from "./mypage/notice/Notice";
import { NoticeDetail } from "./mypage/noticeDetail/NoticeDetail";
import { NoticeManage } from "./mypage/noticeManage/NoticeManage";
import { NoticeManageModify } from "./mypage/noticeManageModify/NoticeManageModify";
import { UserManage } from "./mypage/userManage/UserManage";
import { NormalPage } from "./NormalPage";

const router: RouteObject[] = [
  {
    path: "/",
    element: <NormalPage />,
    children: [
      {
        path: "/",
        element: <Home />,
        children: [
          { index: true, element: <Chat /> },
          { path: "introduce", element: <Introduce /> },
          { path: "songRequest", element: <SongRequest /> },
          { path: "story", element: <Story /> },
          { path: "about", element: <About /> },
        ],
      },
      {
        path: "/mypage",
        element: <Mypage />,
        children: [
          { path: "inventory", element: <Inventory /> },
          { path: "myinfo", element: <Myinfo /> },
          { path: "notice", element: <Notice /> },
          { path: "notice/:noticeSeq", element: <NoticeDetail /> },
          { path: "notice-manage", element: <NoticeManage /> },
          { path: "notice-manage/:noticeSeq", element: <NoticeManageModify /> },
          { path: "user-manage", element: <UserManage /> },
        ],
      },
    ],
  },
  {
    path: "/except",
    element: <ExceptionPage />,
    children: [
      { path: "login-success", element: <LoginSuccess /> },
      { path: "404", element: <Page404 /> },
    ],
  },
];

export default router;
