from shared_state import current_state, chat_readable
import chatgpt
import tts
import re
import os

async def process_chat_data(data):
    if (current_state.get_state() == "chat" and chat_readable.get_state() == True):
        chat_cleaned = re.sub(r"[^가-힣A-Za-z0-9\s]+", " ", data["content"])
        if (len(chat_cleaned) > 7 and len(chat_cleaned < 100)):
            validate_result = chatgpt.validate_chat_gpt(chat_cleaned)
            if 'True' in validate_result or 'true' in validate_result:
                chat_readable.set_state(False)
                chat_reaction = data["user_nickname"] + "님 " + chat_cleaned + "."
                chat_reaction = chat_reaction + chatgpt.chat_reaction_gpt(chat_cleaned)
                tts_path = "./tts/chat"
                chat_tts_filename = "chat.mp3"
                os.path.join(tts_path, chat_tts_filename)
                tts.generate_tts_clova(chat_reaction, chat_tts_filename, "nihyun")