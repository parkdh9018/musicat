import { useState } from "react";
import style from "./SongDetailNav.module.css";
import { Modal } from "@/components/common/modal/Modal";
import { SongDetailModal } from "./songDetailModal/SongDetailModal";
import { ArtistDetailModal } from "./artistDetailModal/ArtistDetailModal";
import { AlbumDetailModal } from "./albumDetailModal/AlbumDetailModal";

export const SongDetailNav = () => {
  const [isSongDetailModalOpen, setIsSongDetailModalOpen] = useState(false);
  const [isArtistDetailModalOpen, setIsArtistDetailModalOpen] = useState(false);
  const [isAlbumDetailModalOpen, setIsAlbumDetailModalOpen] = useState(false);

  const onSongDetail = () => {
    setIsSongDetailModalOpen(true);
  };

  const onArtistDetail = () => {
    setIsArtistDetailModalOpen(true);
  };

  const onAlbumDetail = () => {
    setIsAlbumDetailModalOpen(true);
  };

  return (
    <>
      <div className={style.songDeatilNav}>
        <div onClick={() => onSongDetail()}>곡 정보</div>
        <div onClick={() => onArtistDetail()}>아티스트 정보</div>
        <div onClick={() => onAlbumDetail()}>앨범정보</div>
      </div>
      {isSongDetailModalOpen && (
        <Modal
          setModalOpen={setIsSongDetailModalOpen}
          children={<SongDetailModal />}
        />
      )}
      {isArtistDetailModalOpen && (
        <Modal
          setModalOpen={setIsArtistDetailModalOpen}
          children={<ArtistDetailModal />}
        />
      )}
      {isAlbumDetailModalOpen && (
        <Modal
          setModalOpen={setIsAlbumDetailModalOpen}
          children={<AlbumDetailModal />}
        />
      )}
    </>
  );
};
