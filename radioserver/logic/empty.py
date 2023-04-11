from database.database import find_random_music
# import api.naver_clova
import os
from pydub import AudioSegment
from util.util import create_mp3_url
from util.logger import setup_logger

logger = setup_logger()

async def process_empty_state():
    """
    노래 데이터가 아무 것도 없을 때 동작할 내용입니다
    """
    music = find_random_music()
    
    logger.info(f'[Empty Process] 노래가 없으므로 임의 노래를 재생합니다.')
    tts_path = "./tts/mymusic/"
    music_intro_filename = os.path.join(tts_path, f"intro{music['music_seq']}.mp3")
    music_outro_filename = os.path.join(tts_path, f"outro{music['music_seq']}.mp3")

    intro_length = len(AudioSegment.from_file(music_intro_filename))
    outro_length = len(AudioSegment.from_file(music_outro_filename))

    intro_url = await create_mp3_url("mymusic", f"intro{music['music_seq']}.mp3")
    outro_url = await create_mp3_url("mymusic", f"outro{music['music_seq']}.mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": f'https://www.youtube.com/embed/{music["music_youtube_id"]}', "length": music["music_length"],
         "artist" : music["music_artist"], "title" : music["music_title"], "image" : music["music_image"]},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "music",
        "seq": music["music_seq"],
        "playlist": playlist
    }
    return data