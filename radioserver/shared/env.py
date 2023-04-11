import os
from dotenv import load_dotenv

# 환경 변수를 호출하는 코드 모음

# 환경 변수 호출
load_dotenv()

# 환경 변수 정의
client_id = os.environ.get("CLIENT_ID")
client_secret = os.environ.get("CLIENT_SECRET")
openai_api_key = os.environ.get("OPENAI_API_KEY")
mariadb_config = os.environ.get("MARIADB_CONFIG")
fastapi_url = os.environ.get("FASTAPI_URL")