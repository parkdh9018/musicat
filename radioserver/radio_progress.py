from collections import deque
from shared_state import current_state
import kafka_handler
import logic_music, logic_story
from my_logger import setup_logger

logger = setup_logger()

queue = deque(['story', 'chat', 'music', 'chat', 'music', 'chat', 'music'])

##############################################

async def radio_progress():
    """
    라디오 진행 함수
    """
    global queue

    current_state.set_state(queue.popleft())
    logger.info(f'[Radio] : 현재 라디오 상태 : {current_state.get_state()}')
    queue.append(current_state.get_state())


    if current_state.get_state() == 'story':
        radio_state = await logic_story.process_story_state()
    elif current_state.get_state() == 'chat':
        print("chat now")
    elif current_state.get_state() == 'music':
        radio_state = await logic_music.process_music_state()

    await kafka_handler.send_state("radioState", radio_state)

##############################################

async def reset_radio():
    """
    라디오 상태 초기화 함수
    """
    global queue
    queue = deque(['story', 'chat', 'music', 'chat', 'music', 'chat', 'music'])
    logger.info('[Radio] : 라디오 상태 초기화 완료')

##############################################