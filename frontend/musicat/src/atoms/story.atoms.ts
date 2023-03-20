import { atom, useRecoilCallback } from "recoil";

interface content {
  index: number;
  type: "normal" | "narr";
  value: string;
}

export const storyTitleState = atom<string>({
  key: "title",
  default: "",
});

export const storyContentState = atom<content[]>({
  key: "content",
  default: [{ index: 1, type: "normal", value: "" }],
});

export const addStoryContent = () => {
  const callback = useRecoilCallback(({ set }) => {
    return (payload: content) => {
      set(storyContentState, (prev) => [...prev, payload]);
    };
  }, []);

  return callback;
};


export const deleteStoryContent = () => {
  const callback = useRecoilCallback(({ set }) => {
    return (index: number) => {
      set(storyContentState, (prev) => prev.filter((v, i) => i !== (index-1)));
    };
  }, []);

  return callback;
};
