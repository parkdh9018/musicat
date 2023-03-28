import asyncio
import kafka_handler
from fastapi import FastAPI, BackgroundTasks, File, HTTPException
from fastapi.responses import FileResponse
from collections import deque
from typing import List
from shared_state import current_state, chat_readable, radio_health
import json
import story_logic
import music_logic
import empty_logic
import os


app = FastAPI()

queue = deque(['story', 'chat', 'chat', 'chat', 'music', 'chat', 'chat', 'chat', 'music', 'chat', 'chat', 'chat', 'music'])

state_transition_allowed = asyncio.Event()


async def process_finish_state_data():
    global state_transition_allowed
    state_transition_allowed.set()

async def radio_progress_cycle():
    radio_health.set_state(False)
    while True:
        if (radio_health.get_state()):
            await kafka_handler.send_state("radioState", {'state': 'opening'})
            break
    # opening_message = generate_opening_message()
    # print(opening_message)
    
    while True:
        if (radio_health.get_state()):
            await state_transition_allowed.wait()

            current_state.set_state(queue.popleft())
            print(f'Processing: {current_state.get_state()}')

            queue.append(current_state.get_state())

            if current_state.get_state() == 'story':
                radio_state = await story_logic.process_story_state()
                if (radio_state != None):
                    await kafka_handler.send_state("radioState", radio_state)
                else:
                    # radio_state = await empty_logic.process_empty_story_state()
                    radio_state = await empty_logic.process_empty_music_state()
                    await kafka_handler.send_state("radioState", radio_state)
            elif current_state.get_state() == 'chat':
                # chat_readable.set_state(True)
                print("chat now")
            elif current_state.get_state() == 'music':
                radio_state = await music_logic.process_music_state()
                if (radio_state != None):
                    await kafka_handler.send_state("radioState", radio_state)
                else:
                    radio_state = await empty_logic.process_empty_music_state()
                    await kafka_handler.send_state("radioState", radio_state)

            state_transition_allowed.clear()

async def set_remain_gpt_reaction():
    print("서버가 꺼져있을 때 추가된 데이터에 작업 (API 비용 떄문에 구현 아직 안함)")


@app.on_event("startup")
async def startup_event():
    asyncio.create_task(set_remain_gpt_reaction())
    asyncio.create_task(radio_progress_cycle())
    asyncio.create_task(kafka_handler.consume_finish_state("finishState", process_finish_state_data))
    asyncio.create_task(kafka_handler.consume_verify_story("verifyStory"))
    asyncio.create_task(kafka_handler.consume_chat("chat"))
    asyncio.create_task(kafka_handler.consume_music("musicRequest"))

@app.get("/")
def read_root():
    return {"Hello": "World"}

@app.get("/switch")
def switch_radio():
    if (radio_health.get_state == False):
        radio_health.set_state(True)
    else:
        radio_health.set_state(False)

@app.get("/tts/{path}/{filename}")
async def send_tts(path: str, filename: str):
    filepath = os.path.join(f"./tts/{path}", filename)
    if os.path.isfile(filepath):
        return FileResponse(filepath, media_type="audio/mpeg")
    else:
        raise HTTPException(status_code=404, detail="재생할 파일이 존재하지 않습니다.")
    

