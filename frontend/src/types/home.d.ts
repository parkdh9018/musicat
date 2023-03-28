export interface StoryContent {
  speaker: string;
  content: string;
}
export interface StoryRequest {
  userSeq: number; // TODO : 나중에 제거
  storyTitle: string;
  storyContent: StoryContent[];
  storyMusicTitle: string;
  storyMusicArtist: string;
}

// 백엔드 변수명 바뀐거 보고 다시 업데이트
export interface Song {
  userSeq: number;
  isMusicPlayed: boolean;
  musicTitle: string;
  musicArtist: string;
  musicLength: TimeRanges | null;
  musicImage: ImageData;
  //
  musicSeq: number;
  musicCreatedAt: Date;
}
