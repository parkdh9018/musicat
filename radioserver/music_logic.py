import database
import tts
import chatgpt
import asyncio
import os
from pydub import AudioSegment


async def process_music_data(data):
    # user_nickname = database.find_user_nickname(data["user_seq"])
    user_nickname = "라면부엉"
    artist = data["musicArtist"]
    title = data["musicTitle"]
    release_date = data["musicReleaseDate"]
    music_seq = data["musicSeq"]
    music_intro = chatgpt.music_intro_gpt(artist, title, release_date)
    music_outro = chatgpt.music_outro_gpt(artist, title, user_nickname)
    database.update_intro_outro(music_seq, music_intro, music_outro)


async def process_music_state():
    music = database.find_oldest_unplayed_music()
    if not music:
        return None
    
    music_intro = music["music_intro"]
    music_outro = music["music_outro"]

    tts_path = "./tts/music/"

    music_intro_filename = os.path.join(tts_path, "intro.mp3")
    music_outro_filename = os.path.join(tts_path, "outro.mp3")

    await tts.generate_tts_clova(music_intro, music_intro_filename, "ngoeun")
    await tts.generate_tts_clova(music_outro, music_outro_filename, "ngoeun")

    intro_length = len(AudioSegment.from_file(music_intro_filename))
    outro_length = len(AudioSegment.from_file(music_outro_filename))

    playlist = [
        {"type": "mp3", "path": music_intro_filename, "length": intro_length},
        {"type": "youtube", "path": music["music_youtube_id"], "length": music["music_youtube_length"]},
        {"type": "mp3", "path": music_outro_filename, "length": outro_length}
    ]
    data = {
        "state": data["current_state"],
        "playlist": playlist
    }
    return data


    