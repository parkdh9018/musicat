import aiohttp
from shared.env import client_id, client_secret
from util.logger import setup_logger

logger = setup_logger()

##############################################

async def generate_tts(text : str, path : str, speaker : str):
    """naver clova를 이용해 TTS 음성 파일을 생성합니다.

    Args:
        text (str): 텍스트
        path (str): 파일 경로
        speaker (str): 화자 이름
    """
    
    logger.info(f"[Naver Clova TTS] 들어온 텍스트 : {text}, 파일 저장 위치 : {path}, 화자 : {speaker}")
    
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
                logger.info(f"[Naver Clova TTS] {path}.mp3 저장")
                response_body = await response.read()
                with open(path, 'wb') as f:
                    f.write(response_body)
            else:
                logger.info(f"[Naver Clova TTS] Error Code: {rescode}")

##############################################