import api_naver_tts
from datetime import datetime

##############################################

async def create_opening():
    now = datetime.now()

    month = now.month
    day = now.day
    weekday = now.strftime("%A")
    weekday_ko = {
        'Monday': '월요일',
        'Tuesday': '화요일',
        'Wednesday': '수요일',
        'Thursday': '목요일',
        'Friday': '금요일',
        'Saturday': '토요일',
        'Sunday': '일요일'
    }

    message = f"{month}월 {day}일 {weekday_ko[weekday]}, 여러분의 친구 Musicat입니다."
    
##############################################