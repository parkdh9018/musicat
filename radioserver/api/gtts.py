from gtts import gTTS
from util.logger import setup_logger

logger = setup_logger()

##############################################

async def generate_tts(text : str, path : str, speaker : str):
    """ gTTS를 이용해 TTS 음성 파일을 생성합니다.

    Args:
        text (str): 텍스트
        path (str): 파일 경로
        speaker (str): 화자 이름 (사용하지 않음)
    """
    
    logger.info(f"[gtts] 들어온 텍스트 : {text}, 파일 저장 위치 : {path}, 화자 : {speaker}")
    
    tts = gTTS(text = text, lang='ko')
    tts.save(path)
    
    logger.info(f"[gtts] {path}.mp3 저장")

##############################################