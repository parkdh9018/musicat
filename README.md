# MusiCat

![프로젝트 로고](로고_이미지_링크)

프로젝트 주소: [프로젝트_주소](https://musicat.kr)

[UCC 영상 바로가기](UCC_영상_링크)

프로젝트 기간: 2023.02.27 ~ 2023.04.07

## 프로젝트 소개 (배경, 개요)
프로젝트 배경 및 개요에 대한 설명을 작성해주세요.

## 프로젝트 기능 소개
- 기능1
- 기능2
- 기능3
- 기능4

## 사용 기술
* 이슈 관리 : Jira
* 형상 관리 : Git, Gitlab
* 의사소통, 협업: Notion, Mattermost, Discord
* 개발환경
    * OS : Window10
    * IDE : Intellij, VSCode
    * EC2 : Ubuntu 20.04 LTS (GNU/Linux 5.4.0-1018-aws x86_64)
    * Database : Mariadb 10.6
    * SSH : Windows Terminal, MobaXterm
    * CI/CD : Jenkins
    * Reverse Proxy : Nginx
    * SSL : CertBot, Let's Encrypt
* 프론트엔드 (React)
    * Typescript
    * React
    * Recoil
    * React-Query
    * Vite
    * sockjs-Client
    * stompjs
    * threejs
* 백엔드 (SpringBoot)
    * Springboot Starter Data JPA
    * Springboot Starter Websocket
    * Springboot Starter Security
    * JWT
    * Spring kafka
    * google api services youtube v3 (youtube data api v3)
    * google http client gson
    * jsoup
    * lombok
    * spring boot devtools
    * mariadb java client
* 백엔드 (RadioServer)
    * FastAPI
    * asyncio
    * pydub
    * mariadb
    * kafka
    * gunicorn
    * uvicorn

## 프로젝트 파일 구조
### 백엔드
```bash
.
├── gradle
│   └── wrapper
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── musicat
    │   │           ├── Oauth
    │   │           ├── auth
    │   │           ├── config
    │   │           ├── controller
    │   │           │   ├── item
    │   │           │   ├── notice
    │   │           │   ├── radio
    │   │           │   └── user
    │   │           ├── data
    │   │           │   ├── dto
    │   │           │   │   ├── alert
    │   │           │   │   │   ├── request
    │   │           │   │   │   └── response
    │   │           │   │   ├── chat
    │   │           │   │   ├── item
    │   │           │   │   ├── music
    │   │           │   │   ├── notice
    │   │           │   │   ├── radio
    │   │           │   │   ├── socket
    │   │           │   │   ├── spotify
    │   │           │   │   ├── story
    │   │           │   │   └── user
    │   │           │   ├── entity
    │   │           │   │   ├── item
    │   │           │   │   ├── notice
    │   │           │   │   ├── radio
    │   │           │   │   └── user
    │   │           │   └── repository
    │   │           │       ├── item
    │   │           │       ├── notice
    │   │           │       ├── radio
    │   │           │       └── user
    │   │           ├── handler
    │   │           ├── interceptor
    │   │           ├── jwt
    │   │           ├── service
    │   │           │   ├── chat
    │   │           │   ├── item
    │   │           │   ├── kafka
    │   │           │   ├── notice
    │   │           │   ├── radio
    │   │           │   ├── socket
    │   │           │   └── user
    │   │           └── util
    │   │               └── builder
    │   └── resources
    │       └── static
    └── test
        └── java
            └── com
                └── musicat
```

### 프론트엔드
```bash
.
├── public
│   ├── graphic
│   │   ├── animation
│   │   ├── background
│   │   │   ├── 1
│   │   │   └── 2
│   │   └── cat
│   └── img
│       ├── background
│       ├── badge
│       ├── cd
│       ├── pagebackground
│       ├── tape
│       └── theme
└── src
    ├── asset
    │   ├── font
    │   └── img
    ├── atoms
    ├── components
    │   ├── broadcast
    │   │   ├── graphicCanvas
    │   │   │   └── background
    │   │   └── radioPlayer
    │   ├── common
    │   │   ├── board
    │   │   ├── button
    │   │   ├── input
    │   │   ├── modal
    │   │   ├── pagenation
    │   │   ├── selectBox
    │   │   └── songSearch
    │   ├── header
    │   │   ├── onairSign
    │   │   └── popover
    │   └── sideNav
    │       ├── mypageNav
    │       └── tapeNav
    │           ├── CDplayer
    │           ├── Tape
    │           ├── tapeButtons
    │           └── volumeBar
    ├── connect
    │   ├── axios
    │   │   └── queryHooks
    │   └── socket
    ├── customHooks
    ├── pages
    │   ├── common
    │   │   ├── loadingSpinner
    │   │   ├── loginSuccess
    │   │   └── page404
    │   ├── home
    │   │   ├── about
    │   │   ├── chat
    │   │   ├── songRequest
    │   │   │   └── songList
    │   │   │       └── songDetailModal
    │   │   └── story
    │   │       ├── contentBox
    │   │       └── contentPlus
    │   └── mypage
    │       ├── inventory
    │       │   └── inventoryModal
    │       ├── myinfo
    │       │   └── myinfoModal
    │       ├── notice
    │       ├── noticeDetail
    │       ├── noticeManage
    │       ├── noticeManageModify
    │       └── userManage
    │           └── SelectedUsers
    └── types
```
### 라디오 서버
```bash
.
├── api_chatgpt.py
├── api_naver_tts.py
├── database.py
├── kafka_handler.py
├── logic_chat.py
├── logic_empty.py
├── logic_music.py
├── logic_opening.py
├── logic_story.py
├── main.py
├── my_logger.py
├── my_util.py
├── radio_progress.py
├── shared_env.py
├── shared_state.py
└── tts
    └── mymusic
```

## 역할 분배
| 이름 | 사진 | 역할 | 정보 |
| ---- | ---- | ---- | ---- |
| 이름1 | ![이름1_사진](이름1_사진_링크) | 역할1 | 정보1 |
| 이름2 | ![이름2_사진](이름2_사진_링크) | 역할2 | 정보2 |
| 이름3 | ![이름3_사진](이름3_사진_링크) | 역할3 | 정보3 |
| 이름4 | ![이름4_사진](이름4_사진_링크) | 역할4 | 정보4 |
| 이름5 | ![이름5_사진](이름5_사진_링크) | 역할5 | 정보5 |
| 이름6 | ![이름6_사진](이름6_사진_링크) | 역할6 | 정보6 |

## 프로젝트 산출물

### 기능 기획서
  ![기능 기획서](./image/work1.png)
  ![기능 기획서](./image/work2.png)

  ---

### 시스템 아키택쳐
  ![아키텍쳐](./image/system.png)

  ---

### API 명세서
  ![API 명세서](./image/api1.png)
  ![API 명세서](./image/api2.png)
  ![API 명세서](./image/api3.png)
  ![API 명세서](./image/api4.png)
  ![API 명세서](./image/api5.png)
  ![API 명세서](./image/api6.png)
  ![API 명세서](./image/api7.png)
  ![API 명세서](./image/api8.png)

  ---

### ERD 다이어그램
  ![ERD](./image/ERD.png)

  ---

### MockUp & Design
  ![mockup](./image/mockup1.png)
  ![mockup](./image/mockup2.png)
  ![mockup](./image/mockup3.png)

  ---

## 서비스 동작 이미지와 설명
![서비스_동작_이미지](서비스_동작_이미지_GIF_링크)

- 설명1
- 설명2
- 설명

## 프로젝트 참고 링크

- [노션](https://ramen-buang.notion.site/SSAFY-2-MusiCat-6ce1496529df4689bdae266db3d50466) 프로젝트Notino - Musicat
- [결과물2](자기 githut 링크) GitHub - Link


