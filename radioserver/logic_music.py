import database
import api_naver_tts
import api_chatgpt
import asyncio
import os
from pydub import AudioSegment
from my_util import create_mp3_url
from my_logger import setup_logger

logger = setup_logger()

##############################################

async def process_music_data(data):
    """
    kafka로 넘어온 노래 정보를 이용해 GPT 응답을 생성합니다
    """
    music_seq = int(data["musicSeq"])
    logger.info(f'[Music] : GPT 응답 생성중 (musicSeq = {music_seq})')

    user_nickname = database.find_user_nickname(data["userSeq"])
    artist = data["musicArtist"]
    title = data["musicTitle"]
    release_date = data["musicReleaseDate"]

    music_intro = api_chatgpt.music_intro_gpt(artist, title, release_date)
    music_outro = api_chatgpt.music_outro_gpt(artist, title, user_nickname)

    database.update_intro_outro(music_seq, music_intro, music_outro)

    logger.info(f'[Music] : GPT 응답 생성 완료 (musicSeq = {music_seq})')

##############################################

async def process_remain_music_data(data):
    """
    kafka로 넘어온 노래 정보를 이용해 GPT 응답을 생성합니다
    """
    music_seq = int(data["music_seq"])

    logger.info(f'[Music] : GPT 응답 생성중 (musicSeq = {music_seq})')

    user_nickname = database.find_user_nickname(data["user_seq"])
    artist = data["music_artist"]
    title = data["music_title"]
    release_date = data["music_release_date"]

    music_intro = api_chatgpt.music_intro_gpt(artist, title, release_date)
    music_outro = api_chatgpt.music_outro_gpt(artist, title, user_nickname)

    database.update_intro_outro(music_seq, music_intro, music_outro)

    logger.info(f'[Music] : GPT 응답 생성 완료 (musicSeq = {music_seq})')

##############################################

async def process_music_state():
    """
    노래의 intro와 outro에 대한 음성 파일을 생성하고 상태 정보를 반환합니다
    """
    music = database.find_oldest_unplayed_music()
    if not music:
        return None
    logger.info(f'[Music Process] 노래 상태를 생성합니다 {music["music_seq"]}')
    
    music_intro = music["music_intro"]
    music_outro = music["music_outro"]

    tts_path = "./tts/music/"

    music_intro_filename = os.path.join(tts_path, "intro.mp3")
    music_outro_filename = os.path.join(tts_path, "outro.mp3")

    # await api_naver_tts.generate_tts_clova(music_intro, music_intro_filename, "ngoeun")
    # await api_naver_tts.generate_tts_clova(music_outro, music_outro_filename, "ngoeun")
    await api_naver_tts.generate_tts_test(music_intro, music_intro_filename)
    await api_naver_tts.generate_tts_test(music_outro, music_outro_filename)

    intro_length = len(AudioSegment.from_file(music_intro_filename))
    outro_length = len(AudioSegment.from_file(music_outro_filename))

    intro_url = await create_mp3_url("music", "intro.mp3")
    outro_url = await create_mp3_url("music", "outro_mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": f'https://www.youtube.com/embed/{music["music_youtube_id"]}', "length": music["music_length"] / 100,
         "artist" : music["music_artist"], "title" : music["music_title"], "image" : music["music_image"]},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "music",
        "seq": music["music_seq"],
        "playlist": playlist
    }
    return data

##############################################