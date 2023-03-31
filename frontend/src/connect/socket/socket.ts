import SockJS from "sockjs-client";
import Stomp from "stompjs";

const URL = "https://musicat.kr/api/ws";

// 소켓 객체 => 싱글톤 패턴
class SocketManager {
  private static instance: SocketManager;
  private stompClient: Stomp.Client | null = null;

  private constructor() {}

  static getInstance(): SocketManager {
    if (!SocketManager.instance) {
      SocketManager.instance = new SocketManager();
    }
    return SocketManager.instance;
  }

  connect(): Stomp.Client {
    if (!this.stompClient) {
      const socket = new SockJS(URL);
      this.stompClient = Stomp.over(socket);
    }
    return this.stompClient;
  }

  // 이제 여기에 정지버튼 누를 시 연결을 끊는 함수를 넣어주자
  // + chatListState에 채팅이 종료되었습니다는 메세지를 추가해 준다.
}

export default SocketManager;
