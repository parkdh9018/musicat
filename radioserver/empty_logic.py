import tts
import chatgpt
from pydub import AudioSegment
import asyncio
import os

async def process_empty_story_state():
    print("DB에 아무 데이터도 없어 라디오 진행 멘트를 랜덤으로 생성합니다.")
    radio_comment = chatgpt.empty_story_gpt()

    print(radio_comment)
    tts_path = "./tts/empty"
    empty_story_filename = os.path.join(tts_path, "empty_story.mp3")
    await tts.generate_tts_clova(radio_comment, empty_story_filename, "ngoeun")
    radio_comment_length = len(AudioSegment.from_file(empty_story_filename))
    playlist = [
        {"type": "mp3", "path": empty_story_filename, "length": radio_comment_length}
    ]
    data = {
        "state": "story",
        "playlist": playlist
    }
    print(data)

    return data



async def process_empty_music_state():
    print("DB에 아무 데이터도 없어 임시로 노래를 재생합니다.")

    playlist = [
        {"type": "youtube", "path": "KSH-FVVtTf0", "length": 226000}
    ]

    data = {
        "state": "my_music",
        "playlist": playlist
    }

    return data