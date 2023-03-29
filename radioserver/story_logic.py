import database
import tts
import chatgpt
from pydub import AudioSegment
import json
import asyncio
import os
import my_util

async def process_verify_story_data(data):
    story_seq = int(data["storySeq"])
    print(f'[Story] : 사연 검증, GPT 응답 생성중 (storySeq = {story_seq})')
    story_content = data["storyContent"]
    user_nickname = database.find_user_nickname(data["userSeq"])
    music_title = data["storyMusicTitle"]
    music_artist = data["storyMusicArtist"]
    story_cleaned = await my_util.parse_story_content(json.loads(story_content))
    if len(story_cleaned) > 500:
        database.verify_story(story_seq, 0, 1)
        print(f'[Story] : GPT 응답 생성 실패 - 사연 길이 초과 (storySeq = {story_seq})')
        return
    validate_result = chatgpt.validate_story_gpt(story_cleaned)
    print(validate_result)
    if 'True' in validate_result or 'true' in validate_result:
        story_reaction = chatgpt.story_reaction_gpt(story_cleaned)
        music_introduce = user_nickname + "님의 신청곡은" + music_artist + "의 " + music_title + "입니다."
        story_outro = chatgpt.music_outro_gpt(music_artist, music_title, user_nickname)
        database.update_story(story_seq, story_reaction + music_introduce, story_outro)
        database.verify_story(story_seq, 1, 0)
        print(f'[Story] : GPT 응답 생성 완료 (storySeq = {story_seq})')
    else:
        database.verify_story(story_seq, 0, 1)
        print(f'[Story] : GPT 응답 생성 실패 - 사연 적합도 낮음 (storySeq = {story_seq})')

async def process_verify_remain_story_data(data):
    story_seq = int(data["story_seq"])
    print(f'[Story] : 사연 검증, GPT 응답 생성중 (storySeq = {story_seq})')
    story_content = data["story_content"]
    user_nickname = database.find_user_nickname(data["user_seq"])
    music_title = data["story_music_title"]
    music_artist = data["story_music_artist"]
    print(user_nickname)
    print(music_title)
    print(music_artist)
    story_cleaned = await my_util.parse_story_content(json.loads(story_content))
    if len(story_cleaned) > 500:
        database.verify_story(story_seq, 0, 1)
        print(f'[Story] : GPT 응답 생성 실패 - 사연 길이 초과 (storySeq = {story_seq})')
        return
    validate_result = chatgpt.validate_story_gpt(story_cleaned)
    print(validate_result)
    if 'True' in validate_result or 'true' in validate_result:
        story_reaction = chatgpt.story_reaction_gpt(story_cleaned)
        music_introduce = user_nickname + "님의 신청곡은" + music_artist + "의 " + music_title + "입니다."
        story_outro = chatgpt.music_outro_gpt(music_artist, music_title, user_nickname)
        database.update_story(story_seq, story_reaction + music_introduce, story_outro)
        database.verify_story(story_seq, 1, 0)
        print(f'[Story] : GPT 응답 생성 완료 (storySeq = {story_seq})')
    else:
        database.verify_story(story_seq, 0, 1)
        print(f'[Story] : GPT 응답 생성 실패 - 사연 적합도 낮음 (storySeq = {story_seq})')

async def process_story_state():
    story = database.find_story()
    if not story:
        return None
    
    user_nickname = database.find_user_nickname(story["user_seq"])

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
    await tts.generate_tts_clova(story_opening, story_opening_filename, "nihyun")
    await tts.generate_tts_clova(story_reaction, story_reaction_filename, "nihyun")
    await tts.generate_tts_clova(story_outro, story_outro_filename, "nihyun")

    # Story TTS 생성
    story_tts_list = []
    story_content_list = json.loads(story_content)
    for i in range(len(story_content_list)):
        current_text = story_content_list[i]
        await tts.generate_tts_clova(current_text['content'], os.path.join(tts_path, f"{i}.mp3"), current_text['speaker'])
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
        {"type": "youtube", "path": story["storyMusicYoutubeId"], "length": story["storyMusicLength"]},
        {"type": "mp3", "path": outro_url, "length": outro_length}
    ]
    data = {
        "state": "story",
        "object": story,
        "playlist": playlist
    }
    os.remove(story_opening_filename)
    os.remove(story_reaction_filename)
    os.remove(merged_story_tts_filename)
    for story_tts_path in story_tts_list:
        os.remove(story_tts_path)
    return data

async def merge_audio(files, term=2000):
    merged = None
    for file in files:
        if merged is None:
            merged = AudioSegment.from_file(file)
        else:
            merged += AudioSegment.silent(duration=term)
            merged += AudioSegment.from_file(file)
    return merged
