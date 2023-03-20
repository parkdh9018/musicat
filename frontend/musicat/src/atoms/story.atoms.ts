import { atom, useRecoilCallback } from "recoil";

interface content {
  type: "normal" | "narr";
  speaker : "male" | "female" | "narr";
  value: string;
}

export const storyTitleState = atom<string>({
  key: "title",
  default: "",
});

export const storyContentState = atom<content[]>({
  key: "content",
  default: [{ type: "normal", speaker: "male", value: "" }],
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
      set(storyContentState, (prev) => prev.filter((v, i) => i !== index));
    };
  }, [storyContentState]);

  return callback;
};

export const editStoryConent = () => {
  const callback = useRecoilCallback(({ set }) => {
    return (index: number, value : string) => {
      set(storyContentState, (prev) => {
        const pr = [...prev];
        pr[index] = {...pr[index], value};
        return pr;
      });
    };
  }, [storyContentState]);

  return callback;
};

export const editStorySpeaker = () => {
  const callback = useRecoilCallback(({ set }) => {
    return (index: number, speaker : "male" | "female" | "narr") => {
      set(storyContentState, (prev) => {
        const pr = [...prev];
        pr[index] = {...pr[index], speaker};
        return pr;
      });
    };
  }, [storyContentState]);

  return callback;
};

