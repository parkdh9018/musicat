import axios from "axios";

// axios 객체 생성
export const $ = axios.create({
  baseURL: "https://musicat.kr/api",
  headers: {
    "Content-Type": "application/json",
  },
});

$.interceptors.request.use((config) => {
  config.headers["token"] = sessionStorage.getItem("token");
  return config;
});
