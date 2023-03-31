import { Buffer } from "buffer";

export interface UserTokenData {
  userSeq: number;
  userNick: string;
}

export function useTokenData(): UserTokenData | undefined {
  const token = sessionStorage.getItem("token");
  if (!token) return undefined;
  const base64Payload = token.split(".")[1];
  const payload = Buffer.from(base64Payload, "base64");
  const result = JSON.parse(payload.toString());

  return {
    userSeq: result.sub,
    userNick: result.userNickname,
  };
}
