from gtts import gTTS

import urllib.parse
import aiohttp
from shared_env import client_id, client_secret
from my_logger import setup_logger

logger = setup_logger()

##############################################

async def generate_tts_clova(text : str, path : str, speaker : str):
    """
    네이버 클로바 TTS API를 사용해 TTS를 생성합니다
    """
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
                logger.info(f"[Naver Clova TTS]{path}.mp3 저장")
                response_body = await response.read()
                with open(path, 'wb') as f:
                    f.write(response_body)
            else:
                logger.info(f"[Naver Clova TTS] Error Code: {rescode}")
                logger.info(await response.text())

##############################################

async def generate_tts_test(text, path):
    """
    gTTS를 이용해 음성 파일을 생성합니다
    """
    tts = gTTS(text=text, lang='ko')
    tts.save(path)

##############################################