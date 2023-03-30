import mariadb
import json
from shared_env import mariadb_config

def connect_db():
    config = json.loads(mariadb_config)
    conn = mariadb.connect(**config)
    return conn

# 안 읽은 사연 찾기
def find_story():
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM music WHERE story_valid = 1 AND story_readed = 0 ORDER BY story_created_at LIMIT 1")
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

# intro와 outro가 없는 모든 music 찾기
def find_null_intro_outro_music():
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

# intro와 outro가 없는 모든 story 찾기
def find_null_intro_outro_story():
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

# userSeq로 사용자 닉네임 찾기
def find_user_nickname(user_seq):
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

# db에서 가장 큐에 늦게 들어온 노래 불러오기
def find_oldest_unplayed_music():
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

# music의 intro와 outro 추가하기
def update_intro_outro(music_seq, music_intro, music_outro):
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

# story의 intro와 outro 추가하기
def update_story(story_seq, story_reaction, story_outro):
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

# 사연 검증하기
def verify_story(story_seq, is_valid, is_readed):
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("UPDATE music SET story_valid = ?, story_readed = ? WHERE story_seq = ?", (is_valid, is_readed, story_seq))
        conn.commit()
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()


# 신청곡 찾아오기
def find_song_request():
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM music WHERE music_played = 0 ORDER BY music_created_at LIMIT 1")
        song_request = cursor.fetchone()
        if song_request:
            column_names = [desc[0] for desc in cursor.description]
            return dict(zip(column_names, song_request))
        else:
            return None
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()

# 기본곡 찾아오기
def find_basic_song():
    conn = connect_db()
    cursor = conn.cursor()
    try:
        cursor.execute("SELECT * FROM my_music ORDER BY music_played_at LIMIT 1")
        basic_song = cursor.fetchone()
        if basic_song:
            column_names = [desc[0] for desc in cursor.description]
            return dict(zip(column_names, basic_song))
        else:
            return None
    except mariadb.Error as e:
        print(f"Error: {e}")
    finally:
        cursor.close()
        conn.close()
