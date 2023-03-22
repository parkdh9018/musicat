interface Album {
  title: string;
  releaseDate: Date | null;
}

export const AlbumDetailModal = () => {
  const album: Album = { title: "dummy title", releaseDate: null };

  return <div>AlbumDetailModal</div>;
};
