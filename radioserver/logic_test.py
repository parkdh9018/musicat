from gtts import gTTS
import database
from pydub import AudioSegment
import my_util
import os
import json
from collections import deque
from shared_state import current_state
import kafka_handler
from my_logger import setup_logger

logger = setup_logger()

##############################################

async def generate_tts_test(text, path):
    """
    gTTS를 이용해 음성 파일을 생성합니다
    """
    tts = gTTS(text=text, lang='ko')
    tts.save(path)

##############################################

async def process_verify_story_data_test(data):
    """
    테스트용 검증 로직 (카프카)
    """
    story_seq = int(data["storySeq"])
    logger.info(f'[Test] : 고정값으로 사연 검증 (storySeq = {story_seq})')
    story_reaction = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 스토리 리액션입니다."
    music_introduce = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 뮤직 인트로듀스입니다."
    story_outro = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 스토리 아웃트로입니다."
    database.update_story(story_seq, story_reaction + music_introduce, story_outro)
    database.verify_story(story_seq, True, False)

##############################################

async def process_verify_remain_story_data_test(data):
    """
    테스트용 검징 로직(DB)
    """
    story_seq = int(data["story_seq"])
    logger.info(f'[Test] : 고정값으로 사연 검증 (storySeq = {story_seq})')
    story_reaction = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 스토리 리액션입니다."
    music_introduce = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 뮤직 인트로듀스입니다."
    story_outro = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 스토리 아웃트로입니다."
    database.update_story(story_seq, story_reaction + music_introduce, story_outro)
    database.verify_story(story_seq, True, False)

##############################################

async def process_story_state_test():
    """
    테스트용 처리 로직(사연)
    """
    story = database.find_story()
    if not story:
        return await no_story_data_test()
    # user_nickname = database.find_user_nickname(story["user_seq"])
    logger.info("[Test] : 사연 읽기")
    user_nickname = "제발요"

    story_content = story["story_content"]
    story_content_list = json.loads(story_content)

    story_opening = "이번 사연은 " + user_nickname + " 님이 보내주신 사연입니다."
    story_reaction = story["story_reaction"]
    story_outro = story["story_outro"]

    tts_path = "./tts/story/"
    # 파일명 지정
    story_opening_filename = os.path.join(tts_path, "story_opening.mp3")
    story_reaction_filename = os.path.join(tts_path, "story_reaction.mp3")
    story_outro_filename = os.path.join(tts_path, "outro.mp3")

    # TTS 생성
    await generate_tts_test(story_opening, story_opening_filename)
    await generate_tts_test(story_reaction, story_reaction_filename)
    await generate_tts_test(story_outro, story_outro_filename)
    
    # Story TTS 생성
    story_tts_list = []
    story_content_list = json.loads(story_content)
    for i in range(len(story_content_list)):
        current_text = story_content_list[i]
        await generate_tts_test(current_text['content'], os.path.join(tts_path, f"{i}.mp3"))
        story_tts_list.append(os.path.join(tts_path, f'{i}.mp3'))
    merged_story_tts = await merge_audio(story_tts_list, 500)
    merged_story_tts_filename = os.path.join(tts_path, 'merged_story.mp3')
    merged_story_tts.export(merged_story_tts_filename, format='mp3')

    intro_tts_list = []
    intro_tts_list.append(story_opening_filename)
    intro_tts_list.append(merged_story_tts_filename)
    intro_tts_list.append(story_reaction_filename)
    merged_intro_tts = await merge_audio(intro_tts_list, 1000)
    merged_intro_tts_filename = os.path.join(tts_path, 'intro.mp3')
    merged_intro_tts.export(merged_intro_tts_filename, format='mp3')

    intro_length = len(AudioSegment.from_file(merged_intro_tts_filename))
    outro_length = len(AudioSegment.from_file(story_outro_filename))

    intro_url = await my_util.create_mp3_url("story", "intro.mp3")
    outro_url = await my_util.create_mp3_url("story", "outro.mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": f'https://www.youtube.com/embed/{story["music_youtube_id"]}', "length": story["story_music_length"]},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "story",
        "playlist": playlist
    }
    os.remove(story_opening_filename)
    os.remove(story_reaction_filename)
    os.remove(merged_story_tts_filename)
    for story_tts_path in story_tts_list:
        os.remove(story_tts_path)
    return data

##############################################

async def no_story_data_test():
    """
    테스트용 처리 로직(사연 없음)
    """
    logger.info("[Test] : 사연 없음")
    intro = "인트로입니다 이번 사연은 데이터베이스에 데이터가 없어 임의로 생성된 사연입니다. 개발하기 너무 힘들어요 정말 미쳐버릴거같아요. 인트로입니다"
    outro = "아웃트로입니다 이번 사연은 데이터베이스에 데이터가 없어 임의로 생성된 사연입니다. 개발하기 너무 힘들어요 정말 미쳐버릴거같아요. 아웃트로입니다"
    await generate_tts_test(intro, "./tts/story/intro.mp3")
    await generate_tts_test(outro, "./tts/story/outro.mp3")
    
    intro_length = len(AudioSegment.from_file("./tts/story/intro.mp3"))
    outro_length = len(AudioSegment.from_file("./tts/story/outro.mp3"))

    intro_url = await my_util.create_mp3_url("story", "intro.mp3")
    outro_url = await my_util.create_mp3_url("story", "outro.mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": "https://www.youtube.com/embed/A1tZgPAcpjE", "length": "202000"},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "story",
        "playlist": playlist
    }
    return data

##############################################

async def merge_audio(files, term=2000):
    """
    테스트용 음성 합성 로직
    """
    merged = None
    for file in files:
        if merged is None:
            merged = AudioSegment.from_file(file)
        else:
            merged += AudioSegment.silent(duration=term)
            merged += AudioSegment.from_file(file)
    return merged

##############################################

async def process_music_data_test(data):
    """
    테스트용 음악 인트로 아웃트로 생성 로직(카프카)
    """
    music_seq = int(data["musicSeq"])
    logger.info(f'[Test] : 고정값으로 노래 응답 생성 (musicSeq = {music_seq})')
    music_intro = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 뮤직 인트로입니다."
    music_outro = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 뮤직 아웃트로입니다."
    database.update_intro_outro(music_seq, music_intro, music_outro)
    logger.info(f'[Test] : 고정값으로 노래 응답 생성 완료 (musicSeq = {music_seq})')

##############################################

async def process_remain_music_data_test(data):
    """
    테스트용 음악 인트로 아웃트로 생성 로직(DB)
    """
    music_seq = int(data["music_seq"])
    logger.info(f'[Test] : 고정값으로 노래 응답 생성 (musicSeq = {music_seq})')
    music_intro = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 뮤직 인트로입니다."
    music_outro = "데이터 교환 테스트중입니다. 현재 재생되는 음원은 뮤직 아웃트로입니다."
    database.update_intro_outro(music_seq, music_intro, music_outro)
    logger.info(f'[Test] : 고정값으로 노래 응답 생성 완료 (musicSeq = {music_seq})')

##############################################

async def process_music_state_test():
    """
    테스트용 처리 로직(음악)
    """
    music = database.find_oldest_unplayed_music()
    if not music:
        return await no_music_data_test()
    
    logger.info("[Test] : 음악 재생")

    music_intro = music["music_intro"]
    music_outro = music["music_outro"]

    tts_path = "./tts/music/"

    music_intro_filename = os.path.join(tts_path, "intro.mp3")
    music_outro_filename = os.path.join(tts_path, "outro.mp3")

    await generate_tts_test(music_intro, music_intro_filename)
    await generate_tts_test(music_outro, music_outro_filename)

    intro_length = len(AudioSegment.from_file(music_intro_filename))
    outro_length = len(AudioSegment.from_file(music_outro_filename))

    intro_url = await my_util.create_mp3_url("music", "intro.mp3")
    outro_url = await my_util.create_mp3_url("music", "outro_mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": f'https://www.youtube.com/embed/{music["music_youtube_id"]}', "length": music["music_length"]},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "music",
        "playlist": playlist
    }
    return data

##############################################

async def no_music_data_test():
    """
    테스트용 처리 로직(음악 없음)
    """
    logger.info("[Test] : 음악 없음")

    intro = "인트로입니다 이번 노래는 데이터베이스에 데이터가 없어 임의로 생성된 노래입니다"
    outro = "인트로입니다 이번 노래는 데이터베이스에 데이터가 없어 임의로 생성된 노래입니다"
    await generate_tts_test(intro, "./tts/music/intro.mp3")
    await generate_tts_test(outro, "./tts/music/outro.mp3")
    
    intro_length = len(AudioSegment.from_file("./tts/music/intro.mp3"))
    outro_length = len(AudioSegment.from_file("./tts/music/outro.mp3"))

    intro_url = await my_util.create_mp3_url("music", "intro.mp3")
    outro_url = await my_util.create_mp3_url("music", "outro.mp3")

    playlist = [
        {"type": "mp3", "path": intro_url, "length": intro_length},
        {"type": "youtube", "path": "https://www.youtube.com/embed/A1tZgPAcpjE", "length": "202000"},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "music",
        "playlist": playlist
    }
    return data

queue = deque(['story', 'chat', 'music', 'chat', 'music', 'chat', 'music'])

##############################################

async def radio_progress_test():
    """
    테스트용 라디오 루틴
    """
    global queue
    current_state.set_state(queue.popleft())
    logger.info(f'[Test] : 현재 상태 : {current_state.get_state()}')

    queue.append(current_state.get_state())

    if current_state.get_state() == 'story':
        radio_state = await process_story_state_test()
        await kafka_handler.send_state("radioState", radio_state)
    elif current_state.get_state() == 'chat':
        print("일단 ㅇㅋ")
    elif current_state.get_state() == 'music':
        radio_state = await process_music_state_test()
        await kafka_handler.send_state("radioState", radio_state)

##############################################