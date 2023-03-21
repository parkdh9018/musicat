import { Board } from "@/components/common/board/Board";
import { Button } from "@/components/common/button/Button";
import { Input } from "@/components/common/input/Input";
import { Modal } from "@/components/common/modal/Modal";
import { Pagenation } from "@/components/common/pagenation/Pagenation";
import { SelectBox } from "@/components/common/selectBox/SelectBox";
import { useState } from "react";

export const Chat = () => {
  const [test, setTest] = useState("");

  const dumyOption = [
    { value: "none", name: "성별" },
    { value: "male", name: "남성as" },
    { value: "female", name: "여성" },
  ];

  const dumyData = [
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
    { a: 213, b: 1234, c: 12345 },
  ];

  const [selectdumy, setSelectdumy] = useState("none");
  const [isModalOpen, setModal] = useState(false);

  return (
    <div style={{ minHeight: "100%", height: "auto" }}>
      Chat
      <Button
        content={"강rkd"}
        onClick={() => {}}
        style={{
          borderRadius: "100px",
          fontSize: "16px",
          paddingRight: "20px",
          paddingLeft: "20px",
        }}
      />
      <button onClick={() => setModal(true)}>모달열기</button>
      <Input
        input=""
        placeholder={"이거 placeholder 들어감"}
        type={test}
        setInput={setTest}
      />
      <SelectBox options={dumyOption} setValue={setSelectdumy} />
      <Pagenation
        number={5}
        first={false}
        last={false}
        totalPages={14}
        url={""}
      />
      <Board
        data={dumyData}
        grid={"40% 30% 30%"}
        headRow={["1번", "2번", "3번"]}
      />
      <button onClick={() => setModal(true)}>모달열기</button>
      {isModalOpen && (
        <Modal
          setModalOpen={setModal}
          children={
            <div>
              모달이 등장!!! 등장!!! 등장 !!!
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
              <br />
            </div>
          }
        />
      )}
    </div>
  );
};
