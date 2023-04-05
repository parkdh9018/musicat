class SharedStateStr:
    def __init__(self):
        self.current_state = "opening"

    def set_state(self, state: str):
        self.current_state = state

    def get_state(self) -> str:
        return self.current_state
    

class SharedStateBool:
    def __init__(self):
        self.current_state = True

    def set_state(self, state: bool):
        self.current_state = state

    def get_state(self) -> bool:
        return self.current_state


current_state = SharedStateStr()
chat_readable = SharedStateBool()
radio_health = SharedStateBool()