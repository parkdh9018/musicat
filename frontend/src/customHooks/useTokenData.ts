import { Buffer } from "buffer";

export function useTokenData() {
  const token = localStorage.getItem("token");
  if (!token) return undefined;
  const base64Payload = token.split(".")[1];
  const payload = Buffer.from(base64Payload, "base64");
  const result = JSON.parse(payload.toString());

  return {
    userSeq: result.sub,
    userNick: result.userNickname,
  };
}
