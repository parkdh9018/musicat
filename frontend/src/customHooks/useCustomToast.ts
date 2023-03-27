import { toast } from "react-toastify";

type ToastType = "success" | "info" | "warning" | "error";
export function useCustomToast(type: ToastType,content:string) {
  toast[type](content, {
    position: "bottom-right",
    autoClose: 2000,
    hideProgressBar: true,
    closeOnClick: true,
    pauseOnHover: true,
    draggable: true,
    progress: undefined,
    theme: "light",
  });
}
