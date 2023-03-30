import database
import tts
import chatgpt
import asyncio
import os
from pydub import AudioSegment
from my_util import create_mp3_url


async def process_music_data(data):
    music_seq = int(data["musicSeq"])
    print(f'[Music] : GPT 응답 생성중 (musicSeq = {music_seq})')
    user_nickname = database.find_user_nickname(data["userSeq"])
    artist = data["musicArtist"]
    title = data["musicTitle"]
    release_date = data["musicReleaseDate"]
    music_intro = chatgpt.music_intro_gpt(artist, title, release_date)
    music_outro = chatgpt.music_outro_gpt(artist, title, user_nickname)
    database.update_intro_outro(music_seq, music_intro, music_outro)
    print(f'[Music] : GPT 응답 생성 완료 (musicSeq = {music_seq})')

async def process_remain_music_data(data):
    music_seq = int(data["music_seq"])
    print(f'[Music] : GPT 응답 생성중 (musicSeq = {music_seq})')
    user_nickname = database.find_user_nickname(data["user_seq"])
    artist = data["music_artist"]
    title = data["music_title"]
    release_date = data["music_release_date"]
    music_intro = chatgpt.music_intro_gpt(artist, title, release_date)
    music_outro = chatgpt.music_outro_gpt(artist, title, user_nickname)
    database.update_intro_outro(music_seq, music_intro, music_outro)
    print(f'[Music] : GPT 응답 생성 완료 (musicSeq = {music_seq})')

async def process_music_state():
    music = database.find_oldest_unplayed_music()
    if not music:
        data = {
            "state": "noMusic"
        }
        return data
    
    music_intro = music["music_intro"]
    music_outro = music["music_outro"]

    tts_path = "./tts/music/"

    music_intro_filename = os.path.join(tts_path, "intro.mp3")
    music_outro_filename = os.path.join(tts_path, "outro.mp3")

    await tts.generate_tts_clova(music_intro, music_intro_filename, "ngoeun")
    await tts.generate_tts_clova(music_outro, music_outro_filename, "ngoeun")

    intro_length = len(AudioSegment.from_file(music_intro_filename))
    outro_length = len(AudioSegment.from_file(music_outro_filename))

    intro_url = await create_mp3_url(music, "intro.mp3")
    outro_url = await create_mp3_url(music, "outro_mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": music["music_youtube_id"], "length": music["music_length"]},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "music",
        "object": music,
        "playlist": playlist
    }
    return data


    