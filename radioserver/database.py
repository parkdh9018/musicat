import mariadb
import json
from shared_env import mariadb_config

def connect_db():
    config = json.loads(mariadb_config)
    conn = mariadb.connect(**config)
    return conn

##############################################

def find_story():
    """
    읽어야 할 가장 오래된 사연을 찾습니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM story WHERE story_valid = TRUE AND story_readed = FALSE ORDER BY story_created_at LIMIT 1")
        story = cursor.fetchone()
        if story:
            column_names = [desc[0] for desc in cursor.description]
            return dict(zip(column_names, story))
        else:
            return None
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def find_null_intro_outro_music():
    """
    아직 GPT 응답이 생성되지 않은 모든 음악을 찾습니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM music WHERE music_intro IS NULL OR music_outro IS NULL")
        results = cursor.fetchall()
        # Get column names
        column_names = [column[0] for column in cursor.description]

        # Convert list of tuples to a list of dictionaries
        list_of_dicts = [dict(zip(column_names, row)) for row in results]
        return list_of_dicts
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def find_null_intro_outro_story():
    """
    아직 GPT 응답이 생성되지 않은 모든 사연을 찾습니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM story WHERE story_reaction IS NULL OR story_outro IS NULL")
        results = cursor.fetchall()
        # Get column names
        column_names = [column[0] for column in cursor.description]

        # Convert list of tuples to a list of dictionaries
        list_of_dicts = [dict(zip(column_names, row)) for row in results]
        return list_of_dicts
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def find_user_nickname(user_seq : int):
    """
    user_seq를 이용해 사용자 닉네임을 찾습니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT user_nickname FROM user WHERE user_seq = ?", (user_seq,))
        result = cursor.fetchone()
        if result:
            return result[0]
        else:
            return None
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def find_oldest_unplayed_music():
    """
    재생해야할 가장 오래된 노래를 찾습니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM music WHERE music_played = false AND music_intro IS NOT NULL AND music_outro IS NOT NULL ORDER BY music_seq LIMIT 1")
        result = cursor.fetchone()
        if result:
            column_names = [desc[0] for desc in cursor.description]
            return dict(zip(column_names, result))
        else:
            return None
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def update_intro_outro(music_seq : int, music_intro : str, music_outro : str):
    """
    노래에 GPT 응답을 저장합니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE music SET music_intro = ?, music_outro = ? WHERE music_seq = ?", (music_intro, music_outro, music_seq))
        conn.commit()
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def update_story(story_seq, story_reaction, story_outro):
    """
    사연에 GPT 응답을 저장합니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE story SET story_reaction = ?, story_outro = ? WHERE story_seq = ?", (story_reaction, story_outro, story_seq))
        conn.commit()
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def verify_story(story_seq : int, is_valid, is_readed):
    """
    사연에 검증 결과를 저장합니다
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE story SET story_valid = ?, story_readed = ? WHERE story_seq = ?", (is_valid, is_readed, story_seq))
        conn.commit()
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

# # 신청곡 찾아오기
# def find_song_request():
#     """
#     """
#     conn = connect_db()
#     cursor = conn.cursor()
#     try:
#         cursor.execute("SELECT * FROM music WHERE music_played = FALSE ORDER BY music_created_at LIMIT 1")
#         song_request = cursor.fetchone()
#         if song_request:
#             column_names = [desc[0] for desc in cursor.description]
#             return dict(zip(column_names, song_request))
#         else:
#             return None
#     except mariadb.Error as e:
#         print(f"Error: {e}")
#     finally:
#         cursor.close()
#         conn.close()

##############################################

def find_random_music():
    """
    데이터베이스에서 랜덤한 노래를 찾습니다.
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM server_music ORDER BY RAND() LIMIT 1")
        result = cursor.fetchone()
        if result:
            column_names = [desc[0] for desc in cursor.description]
            return dict(zip(column_names, result))
        else:
            return None
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def update_music_played_status(music_seq: int):
    """
    주어진 music_seq에 해당하는 노래의 music_played를 TRUE로 변경합니다.
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE music SET music_played = true WHERE music_seq = ?", (music_seq,))
        conn.commit()
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################

def update_story_readed_status(story_seq: int):
    """
    주어진 story_seq에 해당하는 스토리의 story_readed를 TRUE로 변경합니다.
    """
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE story SET story_readed = true WHERE story_seq = ?", (story_seq,))
        conn.commit()
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

##############################################