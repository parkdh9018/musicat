import { useQuery } from "@tanstack/react-query";
import { $ } from "../setting";

interface Badge {
  badgeSeq: number;
  badgeName: string;
  badgeCost: number;
}

interface Background {
  backgroundSeq: number;
  backgroundName: string;
  backgroundCost: number;
}

interface Theme {
  themeSeq: number;
  themeName: string;
  themeCost: number;
}

interface Item extends Badge, Background, Theme {}

// 뱃지 전체 조회
export function getBadge() {
  const { data, isLoading } = useQuery(
    ["getBadge"],
    async (): Promise<Item[]> => {
      const { data } = await $.get(`/item/badge`);
      return data;
    }
  );

  return { data, isLoading };
}

// 배경 전체 조회
export function getBackground() {
  const { data, isLoading } = useQuery(
    ["getBackground"],
    async (): Promise<Item[]> => {
      const { data } = await $.get(`/item/background`);
      return data;
    }
  );

  return { data, isLoading };
}

// 테마 전체 조회
export function getTheme() {
  const { data, isLoading } = useQuery(
    ["getTheme"],
    async (): Promise<Item[]> => {
      const { data } = await $.get(`/item/theme`);
      return data;
    }
  );

  return { data, isLoading };
}

// 아이탬 바꾸기
export function putItem(type: number, seq: number) {
  if (type === 1) return $.put("/item/badge", { badgeSeq: seq });
  if (type === 2) return $.put("/item/background", { backgroundSeq: seq });
  if (type === 3) return $.put("/item/theme", { themeSeq: seq });
}
