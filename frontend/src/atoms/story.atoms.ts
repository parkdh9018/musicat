import { Song, StoryContent, StoryRequest } from "@/types/home";
import { atom, selector, useRecoilCallback } from "recoil";
import { userInfoState } from "./user.atom";


export const storyTitleState = atom<string>({
  key: "title",
  default: "",
});

export const storySongState = atom<Song>({
  key: "songTitle",
  default: {
    userSeq: 0,
    isMusicPlayed: false,
    musicTitle: "",
    musicArtist: "",
    musicLength: null,
    musicImage: "" as unknown as ImageData,
    musicSeq: 0,
    musicCreatedAt: "" as unknown as Date,
  },
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
    const user = get(userInfoState);
    const storySong = get(storySongState);

    return {
      userSeq: user.userSeq,
      storyTitle,
      storyContent,
      storySong,
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
