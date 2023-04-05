import { getUserMoneyDetail } from "@/connect/axios/queryHooks/user";
import style from "./MyinfoModal.module.css";

interface MyinfoModalProps {
  dataSeq: number;
}

export const MyinfoModal = ({ dataSeq }: MyinfoModalProps) => {
  const { data } = getUserMoneyDetail(dataSeq);

  return (
    <div className={style.myinfoModal}>
      <p>
        날짜<span>{data?.data.moneyLogCreatedAt}</span>
      </p>
      <p>
        구분
        <span>
          {data?.data.moneyLogType} /
          <span
            className={data?.data.moneyLogChange < 0 ? style.red : style.green}
            style={{ marginLeft: "5px" }}
          >
            {data?.data.moneyLogChange} 츄르
          </span>
        </span>
      </p>
      <p>
        상세<span>{data?.data.moneyLogDetail}</span>
      </p>
    </div>
  );
};
