# from gtts import gTTS
import os
import sys
import urllib.request
import urllib.parse
import aiohttp
from shared_env import client_id, client_secret

async def generate_tts_clova(text, name, speaker):
    val = {
        "speaker": speaker,
        "volume": "0",
        "speed": "0",
        "pitch": "0",
        "text": text,
        "format": "mp3"
    }
    url = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts"
    headers = {
        "X-NCP-APIGW-API-KEY-ID": client_id,
        "X-NCP-APIGW-API-KEY": client_secret
    }

    async with aiohttp.ClientSession() as session:
        async with session.post(url, headers=headers, data=val) as response:
            rescode = response.status
            if rescode == 200:
                print(f"{name}.mp3 저장")
                response_body = await response.read()
                with open(name, 'wb') as f:
                    f.write(response_body)
            else:
                print(f"Error Code: {rescode}")
                print(await response.text())