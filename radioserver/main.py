import asyncio
import kafka_handler
from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import FileResponse, StreamingResponse
from shared_state import radio_health
from radio_progress import reset_radio
import os
import database
import logic_story
import logic_music
import api_naver_tts
import api_chatgpt
from my_logger import setup_logger

app = FastAPI()

logger = setup_logger()

##############################################

async def set_remain_gpt_reaction():
    logger.info("[Main] : *** 서버가 꺼져있을 때 추가된 데이터에 작업 ***")
    remain_story = database.find_null_intro_outro_story()
    remain_music = database.find_null_intro_outro_music()
    if remain_story is not None:
        for story in remain_story:
            data = await logic_story.process_verify_remain_story_data(story)
            kafka_handler.send_state("storyValidateResult", data)
    if remain_music is not None:
        for music in remain_music:
            await logic_music.process_remain_music_data(music)
        

##############################################

@app.on_event("startup")
async def startup_event():
    radio_health.set_state(False)
    asyncio.create_task(set_remain_gpt_reaction())
    asyncio.create_task(kafka_handler.consume_finish_state("finishState"))
    asyncio.create_task(kafka_handler.consume_verify_story("verifyStory"))
    asyncio.create_task(kafka_handler.consume_chat("chat"))
    asyncio.create_task(kafka_handler.consume_music("musicRequest"))
    asyncio.create_task(kafka_handler.consume_finish_chat("finishChat"))

##############################################

@app.get("/")
def read_root():
    return {"Hello": "World"}

##############################################

@app.get("/switch")
def switch_radio():
    if radio_health.get_state() is False:
        reset_radio()
        radio_health.set_state(True)
        return {"health" : "True"}
    else:
        radio_health.set_state(False)
        return {"health" : "False"}

##############################################

def generate_file_stream(filepath: str, start: int = 0, end: int = None):
    with open(filepath, 'rb') as file:
        file.seek(start)
        chunk_size = 8192
        while True:
            if end is not None and file.tell() + chunk_size > end:
                chunk_size = end - file.tell() + 1
            data = file.read(chunk_size)
            if not data:
                break
            yield data

@app.get("/tts/{path}/{filename}")
async def send_tts(request: Request, path: str, filename: str):
    filepath = os.path.join(f"./tts/{path}", filename)
    if not os.path.isfile(filepath):
        raise HTTPException(status_code=404, detail="재생할 파일이 존재하지 않습니다.")

    range_header = request.headers.get('Range')
    start, end = None, None

    if range_header:
        range_type, range_values = range_header.split('=')
        if range_type.strip() == 'bytes':
            range_values = range_values.split('-')
            start = int(range_values[0]) if range_values[0] else None
            end = int(range_values[1]) if len(range_values) > 1 and range_values[1] else None

    file_size = os.path.getsize(filepath)
    response_headers = {
        'Content-Type': 'audio/mpeg',
        'Accept-Ranges': 'bytes'
    }

    if start is not None:
        end = end or file_size - 1
        response_headers['Content-Range'] = f'bytes {start}-{end}/{file_size}'
        content_length = end - start + 1
        response_headers['Content-Length'] = str(content_length)
        return StreamingResponse(generate_file_stream(filepath, start, end), status_code=206, headers=response_headers)
    else:
        response_headers['Content-Length'] = str(file_size)
        return StreamingResponse(generate_file_stream(filepath), status_code=200, headers=response_headers)
    
##############################################

@app.post("/chat")
async def chat_endpoint(user: str, message: str):
    assistant_response = await api_chatgpt.chat_reaction_gpt(user, message)
    return {"response": assistant_response}