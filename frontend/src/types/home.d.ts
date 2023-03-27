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
