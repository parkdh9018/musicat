import { Broadcast } from "@/components/broadcast/Broadcast";
import { RouteObject } from "react-router-dom";

const router: RouteObject[] = [
  {
    path: "/",
    element: <Broadcast />,
  },
];

export default router;
