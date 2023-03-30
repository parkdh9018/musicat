from collections import deque
from shared_state import current_state
import kafka_handler
import music_logic, empty_logic, story_logic

queue = deque(['story', 'chat', 'chat', 'chat', 'music', 'chat', 'chat', 'chat', 'music', 'chat', 'chat', 'chat', 'music'])


async def radio_progress():
    global queue
    current_state.set_state(queue.popleft())
    print(f'current state is : {current_state.get_state()}')

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

async def reset_radio():
    global queue
    queue = deque(['story', 'chat', 'chat', 'chat', 'music', 'chat', 'chat', 'chat', 'music', 'chat', 'chat', 'chat', 'music'])