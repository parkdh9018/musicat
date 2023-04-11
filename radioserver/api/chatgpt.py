from shared.env import openai_api_key
from util.logger import measure_execution_time, setup_logger
import aiohttp
import json
import api.prompt

logger = setup_logger()

async def call_openai_api(messages, temperature):
    """chatGPT API 호출 함수

    Args:
        messages (list[dict]): chatGPT 메세지
        temperature (number): temperature

    Returns:
        string: 인공지능 응답
    """
    
    url = "https://api.openai.com/v1/chat/completions"
    api_key = openai_api_key

    headers = {
        "Accept": "application/json",
        "Content-Type": "application/json",
        "Authorization": f"Bearer {api_key}"
    }

    data = {
        "model": "gpt-3.5-turbo",
        "messages": messages,
        "temperature": temperature,
    }

    async with aiohttp.ClientSession() as session:
        async with session.post(url, headers=headers, data=json.dumps(data)) as response:
            result = await response.json()
            assistant_message = result["choices"][0]["message"]["content"].strip()
            return assistant_message

##############################################

@measure_execution_time
async def story_reaction_gpt(param : str):
    """사연에 대한 리액션을 생성하는 함수

    Args:
        param (str): 사연

    Returns:
        string: 인공지능 응답 
    """
    
    messages=[
        {"role": "system", "content": api.prompt.story_reaction_system},
        {"role": "user", "content": f'reaction to this. {param}'}
    ]
    temperature=0.35
    
    result = await call_openai_api(messages, temperature)
    return (result)

##############################################


past_chats = [
    {"role": "system", "content": api.prompt.chat_reaction_system}
] + api.prompt.chat_bias

@measure_execution_time
async def add_chat_to_history(user: str, message: str, assistant_message: str = None):
    global past_chats
    past_chats.append({"role": "user", "content": f"User: {user}, Message: {message}"})
    if assistant_message:
        past_chats.append({"role": "assistant", "content": assistant_message})

    while sum([len(chat["content"]) for chat in past_chats]) > 2000:
        if past_chats[1]["role"] == "user":
            past_chats.pop(1)
            past_chats.pop(1)
        else:
            past_chats.pop(1)

@measure_execution_time
async def chat_reaction_gpt(user: str, message: str):
    """
    채팅에 대한 리액션을 생성합니다
    """
    global past_chats
    await add_chat_to_history(user, message)
    messages = past_chats 
    temperature = 0.8
    result = await call_openai_api(messages, temperature)
    assistant_response = result
    await add_chat_to_history(user, message, assistant_response)
    return assistant_response

@measure_execution_time
async def force_flush_chat():
    global past_chats
    past_chats = [
        {"role": "system", "content": api.prompt.chat_reaction_system}
    ] + api.prompt.chat_bias

##############################################

@measure_execution_time
async def music_intro_gpt(artist : str, title : str, release_date : str):
    """
    노래의 소개를 생성합니다
    """
    intro_prompt = {"role": "user", "content": f'Artist : {artist}, Title : {title}, Release Date : {release_date}'}
    messages = api.prompt.music_intro_bias
    messages.append(intro_prompt)
    temperature = 0.8
    result = await call_openai_api(messages, temperature)
    return (result)

##############################################

@measure_execution_time
async def music_outro_gpt(artist, title, user):
    """
    노래의 감상을 생성합니다
    """
    outro_prompt = {"role": "user", "content": f'Artist : {artist}, Title : {title}, User : {user}'}
    messages = api.prompt.music_outro_bias
    messages.append(outro_prompt)
    temperature = 0.8
    result = await call_openai_api(messages, temperature)
    return (result)

##############################################

@measure_execution_time
async def validate_story_gpt(param):
    """
    사연을 검증해 True 또는 False를 반환합니다
    """
    messages=[
        {"role": "system", "content": "You are a machine that can only say True or False. Answer in less than 5 letters"},
        {"role": "user", "content": 'Role: Return False if the input story contains any of the following: profanity, gender discrimination, origin discrimination, political bias, appearance discrimination, criticism, sexual harassment, racial discrimination, age discrimination, religious discrimination. If the input message follows the format of a radio story, return True.Interesting or enjoyable stories, stories that need comfort, trivial everyday tales, and short stories also return True.'},
        {"role": "assistant", "content": "I understand my role well!"},
        {"role": "user", "content": f'story : "{param}"'}
    ]
    temperature=1.0
    result = await call_openai_api(messages, temperature)
    return (result)

##############################################

@measure_execution_time
async def validate_chat_gpt(param):
    """
    채팅을 검증합니다
    """
    messages=[
        {"role": "system", "content": "You are a machine that can only say True or False. You return False to chats that contain abusive language and verbal abuse. If not, returns True. Answer in less than 5 letters"},
        {"role": "user", "content": 'Role: You are a chat filter for a radio host who is receiving messages while on air. If the radio host can answer the message, return True; if the message is difficult for them to answer, return False.'},
        {"role": "assistant", "content": "I understand my role well!"},
        {"role": "user", "content": f'chat : "{param}"'}
    ]
    temperature=1.0
    result = await call_openai_api(messages, temperature)
    return (result)

##############################################
