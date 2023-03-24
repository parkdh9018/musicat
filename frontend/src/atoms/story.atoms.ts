import { atom, selector, useRecoilCallback } from "recoil";

interface content {
  type: "normal" | "narr";
  speaker: string;
  value: string;
}

export const storyTitleState = atom<string>({
  key: "title",
  default: "",
});

export const songTitleState = atom<string>({
  key: "songTitle",
  default: "",
});

export const storyContentState = atom<content[]>({
  key: "content",
  default: [{ type: "normal", speaker: "male", value: "" }],
});

export const allStorySelector = selector({
  key: "allStory",
  get: ({ get }) => {
    const storyTitle = get(storyTitleState);
    const storyContents = get(storyContentState);
    const storyMusicName = get(songTitleState);

    // TODO : api에 맞게 수정필요
    return JSON.stringify({ storyTitle, storyContents, storyMusicName });
  },
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
  const callback = useRecoilCallback(
    ({ set }) => {
      return (index: number) => {
        set(storyContentState, (prev) => prev.filter((v, i) => i !== index));
      };
    },
    [storyContentState]
  );

  return callback;
};

export const editStoryConent = () => {
  const callback = useRecoilCallback(
    ({ set }) => {
      return (index: number, value: string) => {
        set(storyContentState, (prev) => {
          const pr = [...prev];
          pr[index] = { ...pr[index], value };
          return pr;
        });
      };
    },
    [storyContentState]
  );

  return callback;
};

export const editStorySpeaker = () => {
  const callback = useRecoilCallback(
    ({ set }) => {
      return (index: number, speaker: string) => {
        set(storyContentState, (prev) => {
          const pr = [...prev];
          pr[index] = { ...pr[index], speaker };
          return pr;
        });
      };
    },
    [storyContentState]
  );

  return callback;
};
