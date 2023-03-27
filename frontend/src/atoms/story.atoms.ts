import { StoryContent, StoryRequest } from "@/types/home";
import { atom, selector, useRecoilCallback } from "recoil";


export const storyTitleState = atom<string>({
  key: "title",
  default: "",
});

export const songTitleState = atom<string>({
  key: "songTitle",
  default: "",
});

export const storyContentState = atom<StoryContent[]>({
  key: "content",
  default: [{ speaker: "narr", content: "" }],
});

export const allStorySelector = selector({
  key: "allStory",
  get: ({ get }):StoryRequest => {
    const storyTitle = get(storyTitleState);
    const storyContent = get(storyContentState);
    // TODO : 일단 고정값, 나중에 수정 필요
    // const storyMusicName = get(songTitleState);

    return {
      userSeq: 2,
      storyTitle,
      storyContent,
      // TODO : 일단 고정값, 나중에 수정 필요
      storyMusicArtist: "NewJeans",
      storyMusicTitle: "Ditto",
    }
  },
});

export const addStoryContent = () => {
  const callback = useRecoilCallback(({ set }) => {
    return (payload: StoryContent) => {
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
      return (index: number, content: string) => {
        set(storyContentState, (prev) => {
          const pr = [...prev];
          pr[index] = { ...pr[index], content };
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
