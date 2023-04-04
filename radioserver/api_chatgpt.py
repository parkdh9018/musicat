import openai
from shared_env import openai_api_key

# OpenAI API 키 (.env를 통해 설정)
openai.api_key = openai_api_key

##############################################

async def story_reaction_gpt(param : str):
    """
    사연에 대한 리액션을 생성합니다
    """
    result = openai.ChatCompletion.create(
        model = "gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "You are a Korean radio host. Read the story and respond appropriately. Mandatory : within 200 characters, finalize your answer, Skip the process of calling the user by name and skip saying greetings"},
            {"role": "user", "content": f'reaction to this. {param}'}
        ],
        temperature=0.35
    )
    return (result.choices[0].message.content.strip())

##############################################

example_chat = [
    {"role": "user", "content": "Remember your concept before answering. You are musicat, a music radio host. You have to pretend not to be AI. Our radio program uses the point of Churu to do everything. You should always give users a plausible answer. But you have to pretend you don't know anything about professional knowledge. You should also put together the conversations so far and give a proper answer. you are 20 years old. you are girl, you live in 역삼 멀티캠퍼스."},
    {"role": "assistant", "content": "I remembered this. I now carry out the mission according to the concept provided."},
    {"role": "user", "content": "User: 라면부엉, Message: DJ님 취미가 뭐에요?"},
    {"role": "assistant", "content": "라면부엉님이 DJ님 취미가 뭐에요? 라고 해주셨네요. 저는 음악 감상이 참 좋아요."}
]

past_chats = [
    {"role": "system", "content": "Role: Respond appropriately to chat as a streamer. Mandatory: within 100 characters, no emoji"}
] + example_chat

async def add_chat_to_history(user: str, message: str, assistant_message: str = None):
    global past_chats
    past_chats.append({"role": "user", "content": f"User: {user}, Message: {message}"})
    if assistant_message:
        past_chats.append({"role": "assistant", "content": assistant_message})

async def chat_reaction_gpt(user: str, message: str):
    """
    채팅에 대한 리액션을 생성합니다
    """
    global past_chats
    add_chat_to_history(user, message)
    result = openai.ChatCompletion.create(
        model="gpt-3.5-turbo",
        messages=past_chats[-1000:],
        temperature=0.5
    )
    assistant_response = result.choices[0].message.content.strip()
    await add_chat_to_history(user, message, assistant_response)
    return assistant_response

async def reset_past_chats():
    global past_chats
    initial_chat = [
        {"role": "system", "content": "Role: Respond appropriately to chat as a streamer. Mandatory: within 100 characters, no emoji"}
    ] + example_chat
    past_chats = initial_chat.copy()

##############################################

async def music_intro_gpt(artist : str, title : str, release_date : str):
    """
    노래의 소개를 생성합니다
    """
    result = openai.ChatCompletion.create(
        model = "gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "Role: You are the host of Korean music radio. Before playing the song request, we will do one sentence of introduction. Mandatory : Within 100 characters, without being monotonous, a real human speech, No uncertain information, start with 이번에 들려드릴 곡은"},
            {"role": "user", "content": 'Artist : Artist, Title : Title, Release Date : 2023-01-23'},
            {"role": "assistant", "content": '이번에 들려드릴 곡은 따끈따끈한 신곡이죠, Artist의 Title입니다'},
            {"role": "user", "content": 'Artist : Artist, Title : Title, Release Date : 2001-04-25'},
            {"role": "assistant", "content": '이 노래를 들으면 어렸을 때의 향수가 느껴지는 것 같아요. 참 좋은 노래죠. Artist의 Title 듣고 오실게요.'},
            {"role": "user", "content": 'Artist : Artist, Title : Title, Release Date : 2016-11-05'},
            {"role": "assistant", "content": '이번 곡은 2016년에 발매된 Artist의 Title입니다. 들으면서 함께 기분 좋은 하루 보내시길 바랄게요'},
            {"role": "user", "content": f'Artist : {artist}, Title : {title}, Release Date : {release_date}'}
        ],
        temperature=0.3
    )
    return (result.choices[0].message.content.strip())

##############################################

async def music_outro_gpt(artist, title, user):
    """
    노래의 감상을 생성합니다
    """
    result = openai.ChatCompletion.create(
        model = "gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "Role : You are the host of Korean music radio. You already listen song now. After playing the song request, we will say a brief comment. Mandatory : Within 150 characters, a real human speech, No greeting"},
            {"role": "user", "content": 'Artist : Artist, Title : Title, User : user'},
            {"role": "assistant", "content": 'User님이 신청해주신 Title, 잘 들었습니다. User님께는 소정의 포인트를 보내드릴게요. 다음 코너는 여러분들과 채팅으로 소통하는 시간입니다. 여러가지 질문들을 해주시면 답변해드릴게요.'},
            {"role": "user", "content": 'Artist : Artist, Title : Title, User : user'},
            {"role": "assistant", "content": 'Artist의 Title이었습니다. 참 좋은 노래인 것 같아요. User님 받으신 포인트 잘 확인하시고, 다음에 또 신청해주세요. 이제부터는 여러분들과 함께 만들어나가는 소통 시간입니다. 많은 채팅 부탁드려요'},
            {"role": "user", "content": 'Artist : Artist, Title : Title, User : user'},
            {"role": "assistant", "content": '좋은 노래 잘 들었습니다. User님 포인트 보내 드릴테니까, 노래 자주 신청하시면 좋겠습니다. 이제 궁금한 점을 물어 볼 수 있는 소통 시간입니다. 여러분들의 채팅을 읽고 답변 해드릴게요. 많은 채팅 부탁드려요'},
            {"role": "user", "content": f'Artist : {artist}, Title : {title}, User : {user}'}
        ],
        temperature=0.3
    )
    return (result.choices[0].message.content.strip())

##############################################

async def validate_story_gpt(param):
    """
    사연을 검증해 True 또는 False를 반환합니다
    """
    result = openai.ChatCompletion.create(
        model = "gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "You are a machine that can only say True or False. Returns false if the radio story contains one of the following. {curses, aggressive speech, sexual shame} Returns true if it contains any of the following. {Joy, sadness, tiredness, need support, need encouragement, need empathy, be happy} Answer in less than 5 letters "},
            {"role": "user", "content": f'classify this story : "{param}"'}
        ],
        temperature=0.1
    )
    return (result.choices[0].message.content.strip())

##############################################

async def validate_chat_gpt(param):
    """
    채팅을 검증합니다
    """
    result = openai.ChatCompletion.create(
        model = "gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "You are a machine that can only say True or False. You return False to chats that contain abusive language and verbal abuse. If not, returns True. Answer in less than 5 letters"},
            {"role": "user", "content": f'If it is a chat that a Korean radio host can answer, it is true. classify this chat : "{param}"'}
        ],
        temperature=0.1
    )
    return (result.choices[0].message.content.strip())

##############################################

async def opening_story_gpt():
    """
    오프닝 멘트를 생성합니다
    """
    result = openai.ChatCompletion.create(
        model = "gpt-3.5-turbo",
        messages=[
            {"role": "system", "content": "Role: You are a Korean radio host. Some examples are given by user. You have to write a radio presentation like the example. Create a different topic than the example. Mandatory: Write in Korean, without greeting, between 100 and 300 characters, in colloquial form, and use a proper mix of 다 and 요 as the ending words, Situation: Radio in progress. "},
            {"role": "user", "content": '영화 마션에서 화성에 홀로 남겨진 주인공 맷 데이먼이 이런 명대사를 남깁니다. 문제가 생기면 해결하고, 그 다음에도 문제가 생기면 또 해결하면 될 뿐이야. 그게 전부고, 그러다보면 살아서 돌아가게 돼 네, 평소에 우린 영화처럼 생존에 위협을 받는 상황도 아닌데 너무 많은 걸 미리 고민하면서 살죠. 혹시 아직까지도 머릿속에 잔 걱정이 한가득이라면요, 맷 데이먼처럼 문제가 생기면 해결하면 된다는 마인드를 가져보는건 어떨까요? 여러분의 라디오 진행자 뮤직캣입니다.'},
            {"role": "user", "content": '직장 동료들이 저를 신기해 하더라고요. 두 시간 방송하고 나면 진도 빠지고 축 쳐질 법도 한데, 여전히 너무 신나 있대요. 퇴근길에 광대 승천과 콧노래는 기본이잖아요? 저만 그런가요? 어쩌면 라디오하는 내내 여러분이 채워주신 좋은 기운 덕분인지도 모르겠네요. 여러분의 라디오 진행자 뮤직캣입니다.'},
            {"role": "user", "content": '아이가 어떤 잘못을 했을 때 스스로 반성할 수 있도록 일정 시간 혼자만의 장소에 두는 것. 이걸 생각하는 의자, 혹은 타임아웃 훈육법 이라고 한대요. 우리도 보이지 않는 생각하는 의자에 찾아가서 앉게 될 때 종종 있어요. 혼자 자책하기도 하고, 후회하기도 하고요. 거기서 하염없이 앉아있기만 하면 나아지는 게 없어요. 어느 정도까지만 아프고 힘든 다음에는 툭툭 털고 일어날 수 있게 나를 위한 타임아웃이 필요할 겁니다. 저는 여러분의 라디오 진행자 뮤직캣입니다.'}
        ],
        temperature=0.5
    )
    return (result.choices[0].message.content.strip())

##############################################
