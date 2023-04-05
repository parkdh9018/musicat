export interface StoryContent {
  speaker: string;
  content: string;
}
export interface StoryRequest {
  userSeq: number; // TODO : 나중에 제거
  storyTitle: string;
  storyContent: StoryContent[];
  storySong : Song
}

export interface Song {
  musicSeq: number;
  userSeq: number;
  musicTitle: string;
  musicGenre: string;
  musicArtist: string;
  musicAlbum: string;
  musicImage: string;
  musicYoutubeId: string;
  musicLength: number;
  musicReleaseDate: string;
  musicCreatedAt: string;
  musicIsPlayed: boolean;
}

// export interface Song {
//   userSeq: number;
//   isMusicPlayed: boolean;
//   musicTitle: string;
//   musicArtist: string;
//   musicLength: TimeRanges | null;
//   musicImage: ImageData;
//   //
//   musicSeq: number;
//   musicCreatedAt: Date;
// }
