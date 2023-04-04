import { atom } from "recoil";

interface broadcastStateType {
  operation : string;
  dataType : string;
}

export const broadcastState = atom<broadcastStateType>({
  key: "broadcastOperationState",
  default: {
    operation: "ON AIR",
    dataType : "" 
  }
});
