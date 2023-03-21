import style from "./MyinfoModal.module.css";

interface MyinfoModalProps {
  dataSeq: number;
}

export const MyinfoModal = ({ dataSeq }: MyinfoModalProps) => {
  return (
    <div className={style.myinfoModal}>
      <p>
        날짜<span>2022년 12월 14일(수) 18:23</span>
      </p>
      <p>
        구분
        <span>
          노래 신청 /
          <span className={style.red} style={{ marginLeft: "5px" }}>
            -40 츄르
          </span>
        </span>
      </p>
      <p>
        상세<span>신청곡 / LE SSERAFIM - ANTIFRAGILE</span>
      </p>
    </div>
  );
};
