import { userInfoState } from "@/atoms/user.atom";
import { $ } from "@/connect/axios/setting";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useRecoilValue } from "recoil";

interface UserSeq {
  userSeq: number;
}

interface ModifyRequest extends UserSeq {
  userNickname: string;
}

// 회원 정보 수정
export function putModifyUser(payload: ModifyRequest) {
  const { data, isLoading } = useMutation(["putModifyUser"], async () => {
    const { data } = await $.put(`/user/nickname`, payload);
    return data;
  });

  return { data, isLoading };
}

// 회원 탈퇴
export function deleteUser(userSeq: string) {
  const { data, isLoading } = useMutation(["deleteUser"], async () => {
    return await $.delete(`/user?userSeq=${userSeq}`);
  });

  return { data, isLoading };
}

// 회원 정보 상세 조회(이메일 / 가입일)
export function getUserDetailInfo() {
  const { data, isLoading } = useQuery(["getUserDetailInfo"], async () => {
    return await $.get(`/user/detail`);
  });
  return { data, isLoading };
}

// 회원의 츄르갯수 조회
export function getUserMoney() {
  const userInfo = useRecoilValue(userInfoState);
  const { data, isLoading } = useQuery(
    ["getUserMoney"],
    async () => {
      return await $.get(`/user/money`);
    },
    { enabled: !!userInfo.userRole }
  );
  return { data, isLoading };
}

// 회원의 읽지않은 메세지 갯수 조회
export function getUserUnreadMsgNum() {
  const userInfo = useRecoilValue(userInfoState);
  const { data, isLoading } = useQuery(
    ["getUserUnreadMsgNum"],
    async () => {
      return await $.get(`/user/unread-message`);
    },
    { enabled: !!userInfo.userRole }
  );

  return { data, isLoading };
}

// 회원의 설정 조회
export function getUserConfig() {
  const userInfo = useRecoilValue(userInfoState);
  const { data, isLoading } = useQuery(
    ["getUserConfig"],
    async () => {
      return await $.get(`/user/config`);
    },
    { enabled: !!userInfo.userRole }
  );

  return { data, isLoading };
}

// 회원 재화 리스트 조회
export function getUserMoneyList(pageNum: number) {
  const { data, isLoading } = useQuery(
    ["getUserMoneyList", pageNum],
    async () => {
      return await $.get(`/user/money-log?page=${pageNum ? pageNum - 1 : 0}`);
    }
  );

  return { data, isLoading };
}

// 회원 재화 내역 상세 조회
export function getUserMoneyDetail(moneyLogSeq: number) {
  const { data, isLoading } = useQuery(
    ["getUserMoneyDetail", moneyLogSeq],
    async () => {
      return await $.get(`/user/money/detail?moneyLogSeq=${moneyLogSeq}`);
    }
  );

  return { data, isLoading };
}
