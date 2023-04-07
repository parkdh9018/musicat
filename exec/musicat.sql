-- --------------------------------------------------------
-- 호스트:                          musicat.kr
-- 서버 버전:                        10.11.2-MariaDB-1:10.11.2+maria~ubu2204 - mariadb.org binary distribution
-- 서버 OS:                        debian-linux-gnu
-- HeidiSQL 버전:                  12.3.0.6589
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- musicat 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `musicat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `musicat`;

-- 테이블 musicat.alert 구조 내보내기
CREATE TABLE IF NOT EXISTS `alert` (
  `alert_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `alert_content` varchar(255) DEFAULT NULL,
  `alert_created_at` datetime(6) DEFAULT NULL,
  `alert_is_read` bit(1) DEFAULT NULL,
  `alert_title` varchar(255) DEFAULT NULL,
  `user_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`alert_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 테이블 musicat.authority 구조 내보내기
CREATE TABLE IF NOT EXISTS `authority` (
  `authority_name` varchar(255) NOT NULL,
  PRIMARY KEY (`authority_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 musicat.authority:~2 rows (대략적) 내보내기
INSERT INTO `authority` (`authority_name`) VALUES
	('ROLE_ADMIN'),
	('ROLE_USER');

-- 테이블 musicat.background 구조 내보내기
CREATE TABLE IF NOT EXISTS `background` (
  `background_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `bacoground_cost` int(11) DEFAULT NULL,
  `background_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`background_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 musicat.background:~2 rows (대략적) 내보내기
INSERT INTO `background` (`background_seq`, `bacoground_cost`, `background_name`) VALUES
	(1, 50, '방송실'),
	(2, 50, '카페');

-- 테이블 musicat.badge 구조 내보내기
CREATE TABLE IF NOT EXISTS `badge` (
  `badge_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `badge_cost` int(11) DEFAULT NULL,
  `badge_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`badge_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 musicat.badge:~6 rows (대략적) 내보내기
INSERT INTO `badge` (`badge_seq`, `badge_cost`, `badge_name`) VALUES
	(1, 10, '뱃지 없음'),
	(2, 10, '뉴진스'),
	(3, 10, '페페'),
	(4, 10, '삼성'),
	(5, 10, '부엉이'),
	(6, 10, '싸피');

-- 테이블 musicat.cache_music 구조 내보내기
CREATE TABLE IF NOT EXISTS `cache_music` (
  `cache_music_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `music_artist` varchar(255) DEFAULT NULL,
  `music_title` varchar(255) DEFAULT NULL,
  `music_youtube_id` varchar(255) DEFAULT NULL,
  `music_length` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`cache_music_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 musicat.money_log 구조 내보내기
CREATE TABLE IF NOT EXISTS `money_log` (
  `money_log_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `money_log_change` bigint(20) DEFAULT NULL,
  `money_log_created_at` datetime(6) DEFAULT NULL,
  `money_log_detail` varchar(255) DEFAULT NULL,
  `money_log_type` varchar(255) DEFAULT NULL,
  `user_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`money_log_seq`),
  KEY `FKtb8ql1rv4rjke6obus7aemcf7` (`user_seq`),
  CONSTRAINT `FKtb8ql1rv4rjke6obus7aemcf7` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 musicat.music 구조 내보내기
CREATE TABLE IF NOT EXISTS `music` (
  `music_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `music_album` varchar(255) DEFAULT NULL,
  `music_artist` varchar(255) DEFAULT NULL,
  `music_created_at` datetime(6) DEFAULT NULL,
  `music_image` varchar(255) DEFAULT NULL,
  `music_intro` varchar(255) DEFAULT NULL,
  `music_length` bigint(20) DEFAULT NULL,
  `music_outro` varchar(255) DEFAULT NULL,
  `music_played` tinyint(1) DEFAULT NULL,
  `music_release_date` varchar(255) DEFAULT NULL,
  `music_title` varchar(255) DEFAULT NULL,
  `music_youtube_id` varchar(255) DEFAULT NULL,
  `user_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`music_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=177 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 테이블 musicat.notice 구조 내보내기
CREATE TABLE IF NOT EXISTS `notice` (
  `notice_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `notice_content` varchar(255) DEFAULT NULL,
  `notice_created_at` datetime(6) DEFAULT NULL,
  `notice_title` varchar(255) DEFAULT NULL,
  `user_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`notice_seq`),
  KEY `FK8hbmbr6wjl2137llct03v7dj` (`user_seq`),
  CONSTRAINT `FK8hbmbr6wjl2137llct03v7dj` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 musicat.server_music 구조 내보내기
CREATE TABLE IF NOT EXISTS `server_music` (
  `music_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `music_title` varchar(255) DEFAULT NULL,
  `music_artist` varchar(255) DEFAULT NULL,
  `music_album` varchar(255) DEFAULT NULL,
  `music_image` varchar(255) DEFAULT NULL,
  `music_youtube_id` varchar(255) DEFAULT NULL,
  `music_length` bigint(20) DEFAULT NULL,
  `music_intro` varchar(1000) DEFAULT NULL,
  `music_outro` varchar(1000) DEFAULT NULL,
  `music_created_at` date DEFAULT NULL,
  `music_release_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`music_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 musicat.server_music:~25 rows (대략적) 내보내기
INSERT INTO `server_music` (`music_seq`, `music_title`, `music_artist`, `music_album`, `music_image`, `music_youtube_id`, `music_length`, `music_intro`, `music_outro`, `music_created_at`, `music_release_date`) VALUES
	(1, 'Kitsch', 'IVE', 'Kitsch', 'https://i.scdn.co/image/ab67616d0000b273c4e7befb303f416dc0409d38', 'niINjphe4ks', 196000, '친구와 함께하는 시간은 소중하고 기쁩니다. 그리고 그 친구와의 시간을 더욱 풍요롭게 만들어주는 것은 음악입니다. 오늘 뮤직캣의 음악 여행에서는 친구와 함께 웃고, 울고, 이야기를 나누며 즐길 수 있는 노래들로 여러분과 함께하려고 해요. 준비되셨나요? 그럼 시작해볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '방금 전 들려드린 곡은 IVE의 <Kitsch>였습니다. 최근 차트를 석권하며 큰 인기를 얻고 있는 이 곡은 신나는 비트와 독특한 멜로디가 돋보이는 최신곡이죠. 다양한 음악 스타일을 시도하는 IVE의 독보적인 매력을 느낄 수 있는 곡입니다. 이제 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2023-03-27'),
	(2, 'Ditto', 'NewJeans', 'Ditto', 'https://i.scdn.co/image/ab67616d0000b273edf5b257be1d6593e81bb45f', 'Km71Rr9K-Bw', 190000, '여러분, 혹시 요즘 힘들고 지친 일상에 지쳐 있나요? 그럼 오늘 <뮤직캣의 음악 여행>과 함께 에너지를 불어넣어 볼까요? 활력 넘치는 음악과 함께 여러분의 에너지를 되찾아보세요. 자, 그럼 지금부터 에너지 가득한 음악 여행을 함께 시작해볼까요? 저는 뮤직캣입니다.', '여러분의 음악 라디오. <뮤직캣의 음악 여행> 입니다. 방금 들려드린 곡은 NewJeans의 <Ditto>였습니다. 이 곡은 중독성 있는 멜로디와 함께 사랑하는 사람에게 솔직한 마음을 전달하고자 하는 메시지를 담고 있죠. 때로는 사랑하는 사람에게 마음을 털어놓고 솔직하게 이야기하는 것이 얼마나 중요한지를 깨닫게 해주는 노래입니다. 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2022-12-19'),
	(3, 'Hype Boy', 'NewJeans', 'NewJeans 1st EP \'New Jeans', 'https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209', '11cta61wi0g', 178000, '인생은 때때로 우리에게 도전을 줍니다. 도전을 이겨낼 수 있는 힘은 자신감과 의지입니다. 오늘 뮤직캣의 음악 여행에서는 도전을 이기고 성공으로 이끄는 데 도움이 되는 노래들로 여러분과 함께하려고 해요. 함께 힘을 얻어볼까요? 그럼 시작하겠습니다! 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '방금 들려드린 곡은 Newjeans의 핫한 노래 <Hype Boy>였습니다. 요즘 유튜브에서 \'지금 무슨 노래 듣고 계세요?\' 컨텐츠로 많이 쓰이는 곡이죠. 이 노래는 에너지가 넘치는 멜로디와 가사로, 듣는 이들의 에너지를 상승시키는 효과가 있다고 합니다. 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2022-08-01'),
	(4, '꽃', '지수', 'ME', 'https://i.scdn.co/image/ab67616d0000b2735a42123d217f8c248ec1a92d', 'eKtGtZN3Ocs', 178000, '여러분, 혹시 행복을 찾기 위해 멀리까지 간 적이 있나요? 그러나 때론 행복은 가까운 곳에 숨어 있을 때가 있습니다. 오늘 <뮤직캣의 음악 여행>에서는 행복을 찾아주는 음악을 들려드리며, 가까운 곳에서 행복을 발견하는 법을 함께 공유해볼까요? 자, 그럼 지금부터 행복을 찾아 떠나는 여행을 시작해봅시다. 저는 뮤직캣입니다.', '지금까지 들어주신 곡은 지수의 <꽃>이었습니다. 이 곡은 봄의 아름다움과 함께 가시지 않은 사랑의 아픔을 담은 가사가 인상적인 곡이에요. 특히, 봄의 꽃잎처럼 가볍게 날아가며 뒤돌아보지 않는 모습을 담은 이 노래는 꽃향기만 남기고 사라진 사랑에 대한 감성을 자아냅니다. 다음으로는 여러분들과 함께 하는 소통 시간입니다. 여러분들의 채팅을 기다리고 있어요.', '2023-04-03', '2023-03-31'),
	(5, 'OMG', 'Newjeans', 'NewJeans \'OMG\'', 'https://i.scdn.co/image/ab67616d0000b273d70036292d54f29e8b68ec01', 'hc32lb0po9U', 213000, '인생은 때론 돌발 상황에 놀랄 때가 있죠. 그런 상황들을 돌파하는 데 필요한 것은 적응력과 유연성입니다. 오늘 뮤직캣의 음악 여행에서는 삶의 돌발 상황을 이겨낼 수 있는 힘을 주는 노래들로 여러분과 함께하려고 해요. 함께 힘을 얻어볼까요? 그럼 시작하겠습니다! 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분, 방금 들어주셨던 곡은 최근 인기를 누리고 있는 걸 그룹 NewJeans의 <OMG>였어요. 이 곡은 사랑하는 사람에게 마음을 전하는 달콤한 가사와 중독적인 멜로디가 인상적인 곡입니다. 노래를 들으며 사랑하는 사람을 생각해보며 따뜻한 마음을 전해보세요. 이어지는 코너에서는 여러분과 이야기를 나누며 즐거운 시간을 보낼 예정이니, 지금부터 궁금한 것이나 나누고 싶은 이야기를 준비해주세요!', '2023-04-03', '2023-01-02'),
	(6, 'Teddy Bear', 'STAYC', 'Teddy Bear', 'https://i.scdn.co/image/ab67616d0000b273ebbb6b66adf9f2392bb86733', 'VilKjKdoQOc', 191000, '인생은 때론 소소한 행복들로 가득 차 있습니다. 그 소소한 행복들을 느끼며 일상을 보다 풍요롭게 만들어주는 것은 바로 음악입니다. 오늘 뮤직캣의 음악 여행에서는 일상 속의 작은 행복을 찾아보며, 그 행복을 즐길 수 있는 노래들로 함께하려고 해요. 준비되셨나요? 그럼 시작하겠습니다! 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '방금 전 들려드린 곡은 STAYC의 <TeddyBear>였습니다. 이 곡은 자신감 넘치는 가사와 신나는 비트가 돋보이는 곡으로, STAYC의 개성이 뚜렷하게 드러난 곡입니다. 이 노래를 들으며 에너지를 불어넣고 긍정적인 기운을 얻어보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2023-02-14'),
	(7, '그라데이션', '10cm', '5.3', 'https://i.scdn.co/image/ab67616d0000b273f38e3060974a65fe8eb626cc', 'kQuxJbP6s8Y', 202000, '인생의 여러 순간 중 가장 감동적이고 아름다운 순간은 바로 첫사랑입니다. 그 첫사랑의 설렘과 두근거림을 되새기며, 오늘 뮤직캣의 음악 여행에서는 첫사랑을 떠올리게 하는 노래들로 여러분과 함께하려고 해요. 마음 속 그 첫사랑을 기억하며 이 시간을 함께 즐겨볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분과 함께하는 <뮤직캣의 음악 여행> 입니다.  방금 전 들려드린 곡은 10센치의 <그라데이션>이었습니다. 이 곡은 밤의 깊어지는 공간에서 사랑에 빠진 두 사람의 감정을 섬세하게 그린 가사와 함께 10센치 특유의 감성이 돋보이는 곡입니다. 이 노래를 들으며 사랑의 묘한 감정을 되새겨보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2022-07-03'),
	(8, 'Always Awake', '빈지노', '24:26 (5th Anniversary Remaster Edition)', 'https://i.scdn.co/image/ab67616d0000b273fce4d97a91b1d51a64ec8a97', 'iGWKNrtbF9I', 222000, '여러분, 가끔은 우연히 듣게 된 노래가 마음에 와닿을 때가 있죠? 그런 노래들은 감정을 뒤집어 놓기도 하고, 새로운 시선을 선사하기도 합니다. 오늘 <뮤직캣의 음악 여행>에서는 그런 감동의 순간을 함께 나누기 위해, 기억에 남는 노래들을 준비했습니다. 자, 그럼 오늘도 여러분과 함께 음악의 세계로 빠져보아요. 저는 뮤직캣입니다.', '방금 전 들려드린 곡은 빈지노의 <Always Awake>였습니다. 이 곡은 밤하늘과 도시의 분위기를 그린 가사와 함께 청춘의 열정과 에너지를 느낄 수 있는 곡입니다. 빈지노의 독특한 랩 스타일과 가사가 돋보이는 이 곡을 들으며 청춘의 열기를 느껴보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2017-07-10'),
	(9, '희재', '성시경', '국화꽃 향기 OST', 'https://i.scdn.co/image/ab67616d0000b2730d1a65e0bae0ac7ebd50f3f7', 'nD1p_H3qo_A', 282000, '우리의 삶 속에는 때때로 기억하고 싶은 추억들이 있습니다. 그 추억들을 떠올리게 하는 것은 바로 음악입니다. 오늘 뮤직캣의 음악 여행에서는 아름다운 추억을 되새기며 감동을 느끼는 노래들로 여러분과 함께하려고 해요. 그럼 함께 추억을 돌아보며 노래를 들어볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분과 함께하는 <뮤직캣의 음악 여행> 입니다. 방금 전 들려드린 곡은 성시경의 <희재>였습니다. 이 곡은 드라마 \'국화꽃 향기\'의 OST로, 햇살과 바람처럼 따뜻하고 감미로운 사랑 이야기를 담아낸 곡입니다. 성시경의 감성적인 목소리가 더해져 더욱 인상적인 곡이죠. 드라마를 떠올리며 이 노래를 들으며 그 시절의 추억을 되새겨보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2003-02-01'),
	(10, '바다의 왕자', '박명수', 'Dr. Park', 'https://i.scdn.co/image/ab67616d0000b273727b047a50910d42780c5602', 'dK_EnUm7i24', 243000, '여러분, 가끔은 쉬는 것도 필요하다고 생각하죠? 힘들고 지칠 때마다 스스로에게 여유를 주고, 잠시 숨을 돌리는 것도 중요한 것 같아요. 그래서 오늘은 여러분과 함께 여유로운 시간을 보내며, 휴식이 필요한 이들에게 힘이 되어주는 음악을 들려드리려 합니다. 지금부터 <뮤직캣의 음악 여행>과 함께 여유를 찾아봅시다. 저는 뮤직캣입니다', '방금 전 들려드린 곡은 박명수의 <바다의 왕자>였습니다. 이 곡은 박명수의 유쾌한 개성이 돋보이는 곡으로, 경쾌한 멜로디와 재미있는 가사가 인상적입니다. 이 노래를 들으며 웃음과 기쁨을 느끼는 시간을 가져보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2000-08-16'),
	(11, 'TOMBOY', '혁오', '23', 'https://i.scdn.co/image/ab67616d0000b2736b0b72f94905f2321564a20f', 'WDTaXxxUYuQ', 243000, '안녕하세요, 여러분! 음악에는 여러분의 이야기가 담겨 있습니다. 오늘 <뮤직캣의 음악 여행>에서는 여러분이 보내주신 음악 속 이야기를 함께 듣고 공감해보려 합니다. 그럼 지금부터 서로의 이야기를 나누며 음악 여행을 시작해볼까요? 저는 뮤직캣입니다.', '여러분과 함께하는 <뮤직캣의 음악 여행> 입니다.  방금 전 들려드린 곡은 혁오의 <TOMBOY>였습니다. 이 곡은 자유롭고 개성있는 음악으로 대중에게 사랑받는 혁오의 대표곡 중 하나입니다. 이 노래를 들으며 개성과 창의력을 발휘하여 즐거운 시간을 보내보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2017-04-24'),
	(12, '다정히 내 이름을 부르면', '경서예지', '다정히 내 이름을 부르면 (경서예지 x 전건호)', 'https://i.scdn.co/image/ab67616d0000b273d22351424b334dd54c1c39cf', 'YPvrhziJAno', 232000, '사랑은 인생에서 가장 아름다운 감정 중 하나입니다. 그리고 사랑을 더욱 빛나게 하는 것은 음악입니다. 오늘 뮤직캣의 음악 여행에서는 사랑에 빠져 더욱 깊은 감정을 느끼게 해주는 노래들로 여러분과 함께하려고 해요. 사랑의 감동을 함께 나누어 볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '방금 전 들려드린 곡은 경서예지의 <다정히 내 이름을 부르면>이었습니다. 이 곡은 사랑하는 사람의 이름을 부르며 마음을 전하는 따뜻한 가사와 감미로운 멜로디가 인상적인 곡이죠. 연인과 함께 이 노래를 들으며 사랑의 소중함을 느껴보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2021-05-19'),
	(13, 'ELEVEN', 'IVE', 'ELEVEN', 'https://i.scdn.co/image/ab67616d0000b273da343b21617aac0c57e332bb', 'vtfljd-1sBk', 179000, '때론 우리는 힘들고 지친 날들을 겪습니다. 그럴 때면 조용한 음악이 우리를 위로해주는 든든한 친구가 되곤 해요. 오늘 뮤직캣의 음악 여행에서는 힘든 날들을 견디며 자신을 돌보는 방법에 대해 이야기하며, 여러분을 위로해주는 노래들로 함께할 거예요. 이제 시작하겠습니다, 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '지금 함께하는 이 시간은 <뮤직캣의 음악 여행>입니다. 방금 들으셨던 신나는 곡은 IVE의 <ELEVEN>이었습니다. 이 노래는 눈부신 청춘의 에너지를 담아냈죠. 가사를 통해 서로를 사랑하게 되는 과정을 상상하며 듣는 것도 좋을 것 같아요. 이제 여러분들과 소통하는 시간을 가져보려 합니다. ', '2023-04-03', '2021-12-01'),
	(14, '나비무덤', '포맨', '나비무덤', 'https://i.scdn.co/image/ab67616d0000b2734b36fb7be936e03a49228540', 'clABfLeJFkM', 263000, '인생에서 가장 소중한 순간들은 간직하고 싶지요? 그런데 그 순간들이 우리 곁에 있음을 깨닫지 못할 때가 있어요. 그러니 오늘 이 시간, 뮤직캣의 음악 여행과 함께 그 소중한 순간들을 되새겨 보며, 과거의 소중한 추억들을 떠올려봅시다. 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분과 함께하는 <뮤직캣의 음악 여행> 입니다. 방금 전에 들려드린 곡은 포맨의 <나비무덤>이었습니다. 노래 가사를 통해 이별이 끝나지 않은 마음을 그린 곡이죠. 이렇게 아픈 마음을 표현한 노래를 들으며 공감하시는 분들도 많을 것입니다. 다음 곡도 가슴에 와닿는 감동을 전해드릴 거에요. 다음 코너는 여러분들과 소통하는 시간입니다.', '2023-04-03', '2023-03-01'),
	(15, 'on the street (with J. Cole)', 'j-hope', 'on the street (with J. Cole)', 'https://i.scdn.co/image/ab67616d0000b2735e8286ff63f7efce1881a02b', 'u62pEY__kj0', 215000, '친구와 함께하는 시간은 항상 즐겁고 행복한 순간들이지요. 그리고 그 행복한 순간들을 더욱 기억에 남게 해주는 것은 바로 음악입니다. 오늘 뮤직캣의 음악 여행에서는 친구와 함께 듣고 싶은 즐거운 노래들로 여러분을 모시려고 해요. 그럼 시작해볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '방금 전 들려드린 곡은 j-Hope의 <on the street>였습니다. 이 곡은 친구와 함께 듣고 싶은 즐거운 노래로, 어떤 상황에서도 함께 서서 꿈을 이루기 위해 노력하는 모습을 담은 곡입니다. 친구와 함께 이 노래를 들으며 서로의 꿈과 희망을 응원하는 따뜻한 시간을 가져보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 질문들을 기다리고 있습니다!', '2023-04-03', '2023-03-03'),
	(16, '너를 보는게 지친 하루에', '송하예', '너를 보는게 지친 하루에', 'https://i.scdn.co/image/ab67616d0000b2733cb5258598cf81a267727530', 'W-4fo94U4Tc', 226000, '힐링이 필요한 그대에게, 오늘 뮤직캣의 음악 여행에서는 여러분의 마음을 따뜻하게 감싸주는 노래들로 함께하려고 해요. 쉬고 싶은 순간, 기대어 누울 공간이 필요한 순간, 그대와 함께하고 싶은 이 시간을 시작해볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', ' 방금 전 들려드린 곡은 송하예의 <너를 보는게 지친 하루에>였습니다. 이 곡은 친구와 함께 듣고 싶은 따뜻한 가사와 멜로디로 가득한 곡이죠. 친구와 함께 이 노래를 들으며 서로의 힘든 날, 서로를 안아주며 보내는 따뜻한 시간을 떠올려보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 함께 나누고 싶은 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2023-03-19'),
	(17, 'Dangerously', 'Charlie Puth', 'Nine Track Mind', 'https://i.scdn.co/image/ab67616d0000b273633a2d775747bccfbcb17a45', 'TBXQu8ORnBQ', 203000, '안녕하세요, 여러분. 세상에는 다양한 나라와 문화가 있죠. 오늘 <뮤직캣의 음악 여행>에서는 세계 각지의 음악을 통해 문화를 만나보려 합니다. 함께 다양한 나라의 음악을 경험해 보며, 인류의 다양성과 감동을 느껴볼까요? 자, 그럼 세계 음악의 멋진 세계로 떠나봅시다. 저는 뮤직캣입니다.', '방금 전 들려드린 곡은 Charlie Puth의 <Dangerously>였습니다. 힐링이 필요한 여러분에게 따뜻한 위로를 전해주는 이 곡은 마음이 힘들 때 잠시 휴식을 취하며 듣기 좋은 노래죠. 뮤직캣의 음악 여행과 함께 이 노래를 들으며 힘든 순간도 잠시 잊어보세요. 다음 코너에서는 여러분과 소통하는 시간을 가져볼게요. 여러분의 이야기와 질문들을 기다리고 있습니다!', '2023-04-03', '2016-01-29'),
	(18, '봄날', '방탄소년단', 'You Never Walk Alone', 'https://i.scdn.co/image/ab67616d0000b273e23a7fd165b24c517a66a69f', 'H2HQWHKDREI', 275000, '사랑하는 사람들과 함께하는 시간이 참 소중하죠? 그리고 그 시간을 더욱 의미있게 만들어주는 것은 바로 음악입니다. 오늘 뮤직캣의 음악 여행에서는 사랑하는 사람들과 함께 나누고 싶은 따뜻한 노래들로 여러분과 함께할 거예요. 그럼 시작해볼까요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분과 함께 들었던 곡은 방탄소년단의 <봄날>이었습니다. 이 노래는 사랑하는 사람들과 함께하는 따뜻한 봄날의 기억을 담은 곡으로, 우리의 마음을 뭉클하게 만드는 멜로디와 가사가 인상적입니다. 뮤직캣과 함께 이 노래를 들으며, 사랑하는 사람들과의 소중한 시간을 떠올려보세요. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 함께 나누고 싶은 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2017-02-13'),
	(19, '벚꽃엔딩', '버스커 버스커', 'Busker Busker 1st', 'https://i.scdn.co/image/ab67616d0000b27347683d1b11d35a22048d243f', 'B2TjVEt5j-4', 263000, '안녕하세요, 어느덧 봄이 찾아온 날씨입니다. 봄이 오면 가장 먼저 생각나는 것은 바로 벚꽃이죠? 이 꽃보다도 더 아름다운 여러분과 함께하는 라디오 시간, <뮤직캣의 음악 여행>에 오신 것을 환영합니다. 자, 그럼 여러분의 마음에 새로운 봄을 불어넣어 줄 따뜻한 음악과 이야기로 가득한 오늘의 방송을 시작해 볼까요? 저는 뮤직캣입니다.', '방금 전 들려드린 곡은 버스커버스커의 <벚꽃엔딩>이었습니다. 봄의 시작과 함께 벚꽃이 만개하는 아름다운 풍경을 떠올리게 하는 곡이죠. 뮤직캣과 함께 이 노래를 들으며 올봄도 즐거운 추억을 만들어봅시다. 이어지는 코너에서는 여러분과 함께 소통하는 시간을 가져볼게요. 여러분의 따뜻한 이야기와 질문들을 기다리고 있습니다!', '2023-04-03', '2012-03-29'),
	(20, '건물 사이에 피어난 장미', 'H1-KEY', 'H1-KEY 1st Mini Album [Rose Blossom]', 'https://i.scdn.co/image/ab67616d0000b27396ee3a02b72db60d065f1dd4', 'Na_sJ7XtDl0', 196000, '요즘 코로나19가 언제 끝날지 몰라 모두 힘들지요? 그럼에도 불구하고, 사람들은 용기와 희망을 잃지 않고 서로 도와가며 살아가고 있어요. 오늘 뮤직캣의 음악 여행에서는 이런 희망을 전하고자, 함께 힘을 낼 수 있는 노래들로 여러분을 응원하겠습니다. 준비되셨나요? 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분이 방금 들었던 곡은 H1-KEY의 <건물 사이에 피어난 장미>였습니다. 이 곡은 어두운 시기에도 희망을 찾아 용기를 내는 사람들을 응원하는 노래입니다. 지금의 어려움을 함께 견뎌내며, 힘을 모아 다가올 행복한 날들을 기대해봅시다. 다음 코너에서는 여러분과 소통하는 시간을 가져보겠습니다. 여러분의 이야기와 질문들을 함께 나누고 싶어요!', '2023-04-03', '2023-01-05'),
	(21, '봄 사랑 벚꽃 말고', '하이포', '봄 사랑 벚꽃 말고', 'https://i.scdn.co/image/ab67616d0000b273762badbc5b89a2fa65d13f67', 'l0rcWimJmQ8', 196000, '여러분, 삶은 때로는 놀라운 변화가 찾아올 때가 있죠. 하지만 그 변화를 두려워하기보다는 용기를 내어 받아들여야 합니다. 아름다운 새로운 시작이 있을 수도 있거든요. 오늘 저녁 뮤직캣의 음악 여행에서는 변화를 두려워하지 않는 여러분을 응원하는 노래들로 함께해요. 자, 지금부터 시작합니다! 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '방금 들어주신 곡은 하이포의 <봄 사랑 벚꽃 말고>였습니다. 봄의 시작과 새로운 사랑을 맞이하는 노래로, 변화를 두려워하지 않는 여러분에게 힘을 실어줄 것 같아요. 뮤직캣과 함께 이 노래를 들으며, 이번 봄에는 좋은 변화와 기회가 여러분을 찾아오길 바랍니다. 그럼 다음 코너에서 여러분과 소통하는 시간을 가져볼게요. 함께 나누고 싶은 이야기와 궁금한 점들을 기다리고 있습니다!', '2023-04-03', '2014-04-08'),
	(22, 'Dynamite', '방탄소년단', 'BE', 'https://i.scdn.co/image/ab67616d0000b273184d20129ccf5aafcc776d11', 'e81ad5MpfQ0', 202000, '여러분! 요즘 날씨가 참 좋지요? 따뜻한 봄바람이 얼굴에 부는 것만으로도 하루의 피로가 줄어드는 기분입니다. 그래서인지 가만히 있는 것보다는 바깥으로 나가 찾아보고 싶은 것들이 생겨요. 뮤직캣과 함께 이런 날씨를 맞이하며, 좋은 음악과 이야기로 힐링의 시간을 가져보는 건 어떨까요? 시작해볼까요, 뮤직캣의 음악 여행, 저는 DJ 뮤직캣입니다!', '여러분과 함께 들었던 방금 전 곡은 방탄소년단의 <Dynamite>였습니다. 이 노래는 에너지 가득한 멜로디와 가사로 우리의 하루를 활력 넘치게 만들어주는 곡이죠. 봄날의 따뜻한 햇살처럼, 이 노래와 함께 여러분의 일상에 활력을 불어넣어드리길 바랍니다. 이어지는 코너에서는 여러분과 소통하는 시간을 가져보겠습니다. 많은 이야기와 질문들을 기다리고 있어요!', '2023-04-03', '2020-11-20'),
	(23, 'Cookie', 'NewJeans', 'NewJeans 1st EP \'New Jeans\'', 'https://i.scdn.co/image/ab67616d0000b2739d28fd01859073a3ae6ea209', 'VOmIplFAGeg', 239000, '어느새 지나간 시간을 회상하며, 그때 그 감성에 잠깐 빠져보는 건 어떨까요? 오늘은 함께 지난 추억을 되살리며 아름다운 음악을 들으며 여행을 떠나봅시다. 뮤직캣의 음악 여행. 저는 DJ 뮤직캣입니다.', '여러분과 함께한 방금 전 곡은 Newjeans의 <Cookie>였습니다. 이 노래는 달콤한 쿠키처럼 사랑하는 사람에게 전하고 싶은 마음을 표현한 곡이죠. 그 감성에 잠깐 빠져보며, 우리 함께 지나간 시간을 회상해봤습니다. 이제 여러분과 소통하는 코너로 넘어갈 예정입니다. 여러분의 이야기와 궁금증을 함께 나누어보아요. 많은 참여 부탁드립니다!', '2023-04-03', '2022-08-01'),
	(24, '그렇게 힘들었나요', '파샵, 다옴', '그렇게 힘들었나요', 'https://i.scdn.co/image/ab67616d0000b27326b627e64d02649152e0b8e8', 'dOgh5tvbTj0', 306000, '때로는 스스로에게 작은 선물을 주는 것도 좋아요. 오늘은 그런 날이 될 수 있도록, 함께 달콤한 음악과 이야기로 여유를 만끽해볼까요? 여러분의 기분 좋은 친구, 뮤직캣의 음악 여행. 저는 DJ 뮤직캣입니다.', '방금 전 들려드린 곡은 파샵과 다옴이 부른 <그렇게 힘들었나요>였습니다. 이 노래는 이별의 아픔을 다루고 있죠. 아름다운 가사와 함께 가슴을 먹먹하게 하는 이 노래를 듣고, 우리 함께 그 시절의 추억과 아픔을 위로하는 시간을 가져볼까요? 이어지는 코너에서는 여러분과 소통하는 시간을 갖겠습니다. 여러분의 이야기와 질문들을 기다리고 있어요!', '2023-04-03', '2022-10-22'),
	(25, '하얀 꿈', '레를 아십니까', '하얀 꿈', 'https://i.scdn.co/image/ab67616d0000b2733bbd06a27347519353602e9a', 'wSxg8_CzZ6Y', 306000, '봄이 찾아온 거리를 걷다 보면, 새로운 시작에 설레는 마음이 느껴집니다. 꽃이 피고, 새싹이 돋아나는 이 시기에 우리도 마음 속에 새로운 꿈을 심어봅시다. 함께 희망 가득한 시간을 보내요. 뮤직캣의 음악 여행. 저는 DJ 뮤직캣입니다.', '네, <뮤직캣의 음악 여행>과 함께하고 있습니다. 방금 들으셨던 곡은 레를 아십니까가 부른 <하얀 꿈>이었습니다. 이 곡을 들으면서 어린 시절의 추억과 그때의 소중한 이야기들이 떠올랐죠. 이어지는 코너에서는 여러분과 소통하는 시간을 갖겠습니다. 여러분의 이야기와 질문들을 기다리고 있어요!', '2023-04-03', '2021-02-03');

-- 테이블 musicat.story 구조 내보내기
CREATE TABLE IF NOT EXISTS `story` (
  `story_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `story_content` varchar(10000) DEFAULT NULL,
  `story_created_at` datetime(6) DEFAULT NULL,
  `story_music_artist` varchar(255) DEFAULT NULL,
  `story_music_cover` varchar(255) DEFAULT NULL,
  `story_music_length` bigint(20) DEFAULT NULL,
  `story_music_title` varchar(255) DEFAULT NULL,
  `story_music_youtube_id` varchar(255) DEFAULT NULL,
  `story_outro` varchar(255) DEFAULT NULL,
  `story_reaction` varchar(255) DEFAULT NULL,
  `story_readed` tinyint(1) DEFAULT NULL,
  `story_title` varchar(255) DEFAULT NULL,
  `story_valid` tinyint(1) DEFAULT NULL,
  `user_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`story_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 테이블 musicat.theme 구조 내보내기
CREATE TABLE IF NOT EXISTS `theme` (
  `theme_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `theme_cost` int(11) DEFAULT NULL,
  `theme_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`theme_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 테이블 데이터 musicat.theme:~2 rows (대략적) 내보내기
INSERT INTO `theme` (`theme_seq`, `theme_cost`, `theme_name`) VALUES
	(1, 40, '카세트 테이프'),
	(2, 40, 'CD 플레이어');

-- 테이블 musicat.user 구조 내보내기
CREATE TABLE IF NOT EXISTS `user` (
  `user_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_created_at` datetime(6) DEFAULT NULL,
  `user_email` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `user_is_ban` bit(1) DEFAULT NULL,
  `user_is_chatting_ban` bit(1) DEFAULT NULL,
  `user_is_darkmode` bit(1) DEFAULT NULL,
  `user_is_user` bit(1) DEFAULT NULL,
  `user_money` bigint(20) DEFAULT NULL,
  `user_nickname` varchar(255) DEFAULT NULL,
  `user_profile_image` varchar(255) DEFAULT NULL,
  `user_warn_count` int(11) DEFAULT NULL,
  `background_seq` bigint(20) DEFAULT NULL,
  `badge_seq` bigint(20) DEFAULT NULL,
  `theme_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_seq`),
  KEY `FKtruyb8gdr76exdhj55pnj0q3` (`background_seq`),
  KEY `FKplsb90p43j9eic5jaily57xt2` (`badge_seq`),
  KEY `FK8dyk3hq5o5b5qdntlci0e6q6e` (`theme_seq`),
  CONSTRAINT `FK8dyk3hq5o5b5qdntlci0e6q6e` FOREIGN KEY (`theme_seq`) REFERENCES `theme` (`theme_seq`),
  CONSTRAINT `FKplsb90p43j9eic5jaily57xt2` FOREIGN KEY (`badge_seq`) REFERENCES `badge` (`badge_seq`),
  CONSTRAINT `FKtruyb8gdr76exdhj55pnj0q3` FOREIGN KEY (`background_seq`) REFERENCES `background` (`background_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 테이블 musicat.user_attendance 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_attendance` (
  `user_attendance_seq` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_attendance_date` date DEFAULT NULL,
  `user_seq` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`user_attendance_seq`),
  KEY `FK2ncqn6mtit7691y2ysh3u6pkq` (`user_seq`),
  CONSTRAINT `FK2ncqn6mtit7691y2ysh3u6pkq` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 테이블 musicat.user_authority 구조 내보내기
CREATE TABLE IF NOT EXISTS `user_authority` (
  `user_seq` bigint(20) NOT NULL,
  `authority_name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_seq`,`authority_name`),
  KEY `FK6ktglpl5mjosa283rvken2py5` (`authority_name`),
  CONSTRAINT `FK6ktglpl5mjosa283rvken2py5` FOREIGN KEY (`authority_name`) REFERENCES `authority` (`authority_name`),
  CONSTRAINT `FKnpruah0f4dgi08aallqx02de8` FOREIGN KEY (`user_seq`) REFERENCES `user` (`user_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
