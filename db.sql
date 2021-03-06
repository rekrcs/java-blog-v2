# DB 생성
DROP DATABASE IF EXISTS st_n33_blog;
CREATE DATABASE st_n33_blog;
USE st_n33_blog;

# 카테고리 테이블 생성
DROP TABLE IF EXISTS cateItem;
CREATE TABLE cateItem (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(100) NOT NULL UNIQUE
);

# 카테고리 추가
INSERT INTO cateItem SET 
regDate = NOW(), 
`name` = '알고리즘';

INSERT INTO cateItem 
SET regDate = NOW(), 
`name` = 'Java';

INSERT INTO cateItem SET 
regDate = NOW(), 
`name` = 'Servlet&JSP';

INSERT INTO cateItem SET 
regDate = NOW(), `name` = 'IT/일반';

INSERT INTO cateItem SET 
regDate = NOW(), 
`name` = '일상';

INSERT INTO cateItem 
SET regDate = NOW(), 
`name` = '활동';


# 게시물 테이블 생성
DROP TABLE IF EXISTS article;
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    cateItemId INT(10) UNSIGNED NOT NULL,
    displayStatus TINYINT(1) UNSIGNED NOT NULL,
    `title` CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
     memberId INT(10) UNSIGNED NOT NULL,
    hit INT(10) UNSIGNED NOT NULL  DEFAULT '0'
);

#조회수 칼럼 추가
#ALTER TABLE article ADD COLUMN hit INT(10) UNSIGNED NOT NULL AFTER `body`;

CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(100) NOT NULL UNIQUE,
    loginPw CHAR(200) NOT NULL,
    `name` CHAR(100) NOT NULL,
    email CHAR(200) NOT NULL,
    nickname CHAR(200) NOT NULL,
    `level` INT(1) UNSIGNED DEFAULT 0 NOT NULL,
    `mailAuthStatus` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0',
    `delStatus` TINYINT(1) UNSIGNED NOT NULL DEFAULT '0'
);

# 마스터 회원 생성
INSERT INTO `member` SET
regDate = NOW(),
updateDate = NOW(),
`loginId` = 'admin',
`loginPw` = SHA2('admin', 256),
`name` = 'admin',
`nickname` = 'admin',
`email` = 'admin@admin.com',
`level` = 10;

CREATE TABLE `articleReply` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    displayStatus TINYINT(1) NOT NULL,
    `body` TEXT NOT NULL,
     memberId INT(10) UNSIGNED NOT NULL
);

# 부가정보테이블 
# 댓글 테이블 추가
DROP TABLE IF EXISTS attr;
CREATE TABLE attr (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    `name` CHAR(100) NOT NULL UNIQUE,
    `value` TEXT NOT NULL
);

# attr 테이블에서 name 을 4가지 칼럼으로 나누기
ALTER TABLE `attr` DROP COLUMN `name`,
ADD COLUMN `relTypeCode` CHAR(20) NOT NULL AFTER `updateDate`,
ADD COLUMN `relId` INT(10) UNSIGNED NOT NULL AFTER `relTypeCode`,
ADD COLUMN `typeCode` CHAR(30) NOT NULL AFTER `relId`,
ADD COLUMN `type2Code` CHAR(30) NOT NULL AFTER `typeCode`,
CHANGE `value` `value` TEXT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL AFTER `type2Code`,
DROP INDEX `name`; 

# attr 유니크 인덱스 걸기
## 중복변수 생성금지
## 변수찾는 속도 최적화
ALTER TABLE `attr` ADD UNIQUE INDEX (`relTypeCode`, `relId`, `typeCode`, `type2Code`); 

## 특정 조건을 만족하는 회원 또는 게시물(기타 데이터)를 빠르게 찾기 위해서
ALTER TABLE `attr` ADD INDEX (`relTypeCode`, `typeCode`, `type2Code`);