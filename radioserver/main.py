import asyncio
import kafka_handler
from fastapi import FastAPI, HTTPException
from fastapi.responses import FileResponse
from shared_state import radio_health
from radio_progress import reset_radio
import os
import database
import opening_logic
import story_logic
import music_logic


app = FastAPI()

async def set_remain_gpt_reaction():
    print("*** 서버가 꺼져있을 때 추가된 데이터에 작업 ***")
    remain_story = database.find_null_intro_outro_story()
    remain_music = database.find_null_intro_outro_music()
    if remain_story is not None:
        for story in remain_story:
            await story_logic.process_verify_story_data(story)
    if remain_music is not None:
        for music in remain_music:
            await music_logic.process_music_data(music)


@app.on_event("startup")
async def startup_event():
    radio_health.set_state(False)
    asyncio.create_task(set_remain_gpt_reaction())
    asyncio.create_task(kafka_handler.consume_finish_state("finishState"))
    asyncio.create_task(kafka_handler.consume_verify_story("verifyStory"))
    asyncio.create_task(kafka_handler.consume_chat("chat"))
    asyncio.create_task(kafka_handler.consume_music("musicRequest"))

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/switch")
def switch_radio():
    if radio_health.get_state() is False:
        reset_radio()
        radio_health.set_state(True)
        return {"health" : "True"}
    else:
        radio_health.set_state(False)
        return {"health" : "False"}

@app.get("/tts/{path}/{filename}")
async def send_tts(path: str, filename: str):
    filepath = os.path.join(f"./tts/{path}", filename)
    if os.path.isfile(filepath):
        return FileResponse(filepath, media_type="audio/mpeg")
    else:
        raise HTTPException(status_code=404, detail="재생할 파일이 존재하지 않습니다.")
    

