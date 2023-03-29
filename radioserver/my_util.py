from shared_env import fastapi_url

# 리스트 형태로 들어온 사연 파싱
async def parse_story_content(contents):
    content_list = []
    for content in contents:
        content_list.append(content["content"])
    parsed_story = " ".join(content_list)
    return parsed_story

async def create_mp3_url(state:str, filename:str):
    return f"{fastapi_url}/{state}/{filename}"
