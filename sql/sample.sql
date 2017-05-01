/*
Navicat SQLite Data Transfer

Source Server         : storage
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-04-29 18:57:32
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for LeftHand
-- ----------------------------
DROP TABLE IF EXISTS "main"."LeftHand";
CREATE TABLE "LeftHand" (
"Note"  TEXT NOT NULL,
"LeftHand"  TEXT NOT NULL,
"Profile"  INTEGER NOT NULL,
PRIMARY KEY ("Note" ASC, "LeftHand" ASC, "Profile" ASC),
CONSTRAINT "LeftHandpID" FOREIGN KEY ("Profile") REFERENCES "Profile" ("pID") ON DELETE CASCADE
);

-- ----------------------------
-- Records of LeftHand
-- ----------------------------

-- ----------------------------
-- Table structure for Music
-- ----------------------------
DROP TABLE IF EXISTS "main"."Music";
CREATE TABLE "Music" (
"mID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"Name"  TEXT NOT NULL,
"Date"  TEXT NOT NULL DEFAULT (DATETIME('now', 'localtime')),
"Profile"  INTEGER NOT NULL,
"Notes"  TEXT NOT NULL,
"Mode"  TEXT NOT NULL,
CONSTRAINT "MusicpID" FOREIGN KEY ("Profile") REFERENCES "Profile" ("pID") ON DELETE CASCADE
);

-- ----------------------------
-- Records of Music
-- ----------------------------

-- ----------------------------
-- Table structure for Profile
-- ----------------------------
DROP TABLE IF EXISTS "main"."Profile";
CREATE TABLE "Profile" (
"pID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"Name"  TEXT NOT NULL,
"Date"  TEXT NOT NULL DEFAULT (DATETIME('now', 'localtime'))
);

-- ----------------------------
-- Records of Profile
-- ----------------------------
INSERT INTO "main"."Profile" VALUES (1, 'Sample Profile', '2017-04-28 00:19:20');

-- ----------------------------
-- Table structure for Sample
-- ----------------------------
DROP TABLE IF EXISTS "main"."Sample";
CREATE TABLE "Sample" (
"sID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"Name"  TEXT NOT NULL,
"Date"  TEXT NOT NULL DEFAULT (DATETIME('now', 'localtime')),
"Profile"  INTEGER NOT NULL,
"Notes"  TEXT NOT NULL,
"Source"  INTEGER NOT NULL,
CONSTRAINT "SamplepID" FOREIGN KEY ("Profile") REFERENCES "Profile" ("pID") ON DELETE CASCADE
);

-- ----------------------------
-- Records of Sample
-- ----------------------------
INSERT INTO "main"."Sample" VALUES (1, 'Sample1', '2017-04-28 00:24:41', 1, '2,0,2,1.0;5,0,2,1.0;6,0,2,1.0;7,0,2,1.0;1,0,3,1.0;2,0,3,2.0;2,0,3,1.0;3,0,3,1.5;2,0,3,0.5;1,0,3,1.0;3,0,3,1.0;2,0,3,1.0;1,0,3,0.5;7,0,2,1.0;6,0,2,0.5;5,0,2,0.5;6,0,2,2.0;5,0,2,1.0;', 0);
INSERT INTO "main"."Sample" VALUES (2, 'Sample2', '2017-04-28 00:25:25', 1, '4,0,2,1.0;4,0,2,2.0;4,0,2,1.0;4,0,2,2.0;3,0,2,2.0;4,0,2,1.0;4,0,2,1.0;4,0,2,1.0;4,0,2,1.0;4,0,2,2.0;3,0,2,1.0;3,0,2,1.0;2,0,3,1.5;2,0,3,0.5;1,0,3,1.0;1,0,3,1.0;7,0,2,2.0;6:0,2,1.0;2,0,2,1.0;3,0,2,1.0;4,0,2,1.0;5,0,2,3.0;6,0,2,1.0;7,0,2,3.0;1,0,3,1.0;2,0,3,1.0;7,0,2,1.0;3,0,3,1.5;7,0,2,0.5;7,0,2,6.0;', 0);
INSERT INTO "main"."Sample" VALUES (3, 'Sample3', '2017-04-28 00:26:25', 1, '1,0,4,2.0;1,0,4,1.0;7,0,3,1.0;7,0,3,1.5;6,0,3,0.5;6,0,3,2.0;1,0,4,2.0;1,0,4,1.0;7,0,3,1.5;6,0,3,0.5;6,0,3,2.0;4,0,4,2.0;4,0,4,1.0;3,0,4,1.0;2,0,4,1.5;2,0,4,0.5;2,0,4,1.0;1,0,4,1.0;7,0,3,1.5;7,0,3,0.5;7,0,3,1.0;6,0,3,1.0;3,0,4,4.0;', 0);
INSERT INTO "main"."Sample" VALUES (4, 'Sample4', '2017-04-28 00:27:16', 1, '3,0,3,2.0;3,0,3,1.0;3,0,3,1.0;3,0,3,1.5;3,0,3,0.5;3,0,3,2.0;3,0,3,2.0;4,0,3,1.0;4,0,3,1.0;3,0,3,1.5;3,0,3,0.5;3,0,3,2.0;2,0,3,2.0;3,0,3,2.0;3,0,3,2.0;2,0,3,3.0;2,0,3,0.5;2,0,3,0.5;2,0,3,1.5;2,0,3,0.5;2,0,3,2.0;1,0,3,2.0;1,0,3,2.0;2,0,3,2.0;1,0,3,2.0;6,0,3,1.0;6,0,3,0.5;6,0,3,0.5;6,0,3,1.0;6,0,3,1.0;5,0,3,4.0;', 0);
INSERT INTO "main"."Sample" VALUES (5, 'Sample5', '2017-04-28 00:28:20', 1, '6,0,3,3.0;5,0,3,0.5;4,0,3,0.5;6,0,3,3.0;6,0,3,1.0;5,0,3,1.5;6,0,3,0.5;7,0,3,1.0;6,0,3,1.0;5,0,3,2.0;4,0,3,1.0;5,0,3,1.0;3,0,3,3.0;5,0,3,0.5;4,0,3,0.5;5,0,3,1.0;6,0,3,2.0;5,0,3,0.5;4,0,3,0.5;2,0,3,4.0;', 0);

-- ----------------------------
-- Table structure for sqlite_sequence
-- ----------------------------
DROP TABLE IF EXISTS "main"."sqlite_sequence";
CREATE TABLE sqlite_sequence(name,seq);

-- ----------------------------
-- Records of sqlite_sequence
-- ----------------------------
INSERT INTO "main"."sqlite_sequence" VALUES ('Music', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('Profile', 1);
INSERT INTO "main"."sqlite_sequence" VALUES ('Sample', 5);
