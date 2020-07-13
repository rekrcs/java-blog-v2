#  캐릭터SET 설정
SET NAMES utf8m64;

# DB 생성
DROP DATABASE IF EXISTS site33;
CREATE DATABASE site33;
USE site33;

# 카테고리 테이블 생성
DROP TABLE IF EXISTS cateItem;
CREATE TABLE cateItem (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
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
    hit INT(10) UNSIGNED NOT NULL
);

#조회수 칼럼 추가
#ALTER TABLE article ADD COLUMN hit INT(10) UNSIGNED NOT NULL AFTER `body`;

INSERT INTO article SET
regDate = NOW(),
updateDate = NOW(),
cateItemId = 3,
displayStatus = 1,
title = 'sql 문법 정리',
`body` = '';

SELECT *
FROM article;

CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(100) NOT NULL UNIQUE,
    loginPw CHAR(200) NOT NULL,
    `name` CHAR(100) NOT NULL,
    email CHAR(200) NOT NULL,
    nickname CHAR(200) NOT NULL
);

SELECT *
FROM `member`;

CREATE TABLE `articleReply` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    articleId INT(10) UNSIGNED NOT NULL,
    `body` TEXT NOT NULL,
     memberId INT(10) UNSIGNED NOT NULL
);

SELECT * 
FROM articleRely;