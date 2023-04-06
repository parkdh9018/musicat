from shared_state import current_state, chat_readable
import api_chatgpt
import api_naver_tts
from pydub import AudioSegment
import my_util
import database
import re
import kafka_handler
import os
from my_logger import setup_logger
from collections import defaultdict

logger = setup_logger()
user_check = user_check = defaultdict(int)

##############################################

count = 1

async def clear_user_check():
    global user_check

    user_check.clear()

async def process_chat_data(data):
    global count
    global user_check
    if current_state.get_state() == "chat" and chat_readable.get_state() == True:
        logger.info(f'채팅 넘어옴{data}')
        chat_cleaned = re.sub(r"[^가-힣A-Za-z0-9\s]+", " ", data["content"])
        user_seq = int(data["userSeq"])

        if user_check[user_seq] >= 7:
            return

        if len(chat_cleaned) > 7 and len(chat_cleaned) < 200:
            validate_result = await api_chatgpt.validate_chat_gpt(chat_cleaned)
            logger.info(f'채팅 validate 확인 {validate_result}')
            if 'True' in validate_result or 'true' in validate_result:
                user_nickname = database.find_user_nickname(user_seq)
                chat_reaction = await api_chatgpt.chat_reaction_gpt(user_nickname, chat_cleaned)
                logger.info(chat_reaction)
                current_count = count
                count = count + 1
                if count > 100 :
                    count = 1
                tts_path = f'./tts/chat/{current_count}.mp3'
                await api_naver_tts.generate_tts_clova(chat_reaction, tts_path, "nminseo")
                # await api_naver_tts.generate_tts_test(chat_reaction, tts_path)
                mp3path = await my_util.create_mp3_url("chat", f'{current_count}.mp3')
                user_check[user_seq] += 1
                logger.info(user_check)
                chat_length = len(AudioSegment.from_file(tts_path))
                playlist = [
                    {"type": "mp3", "path" : mp3path, "length" : chat_length}
                    ]
                radio_state = {
                    "state": "chat",  "playlist": playlist
                }
                await kafka_handler.send_state("radioState", radio_state)

##############################################