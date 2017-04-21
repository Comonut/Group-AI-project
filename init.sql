/*
Navicat SQLite Data Transfer

Source Server         : Test
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-04-21 01:06:07
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

-- ----------------------------
-- Records of sqlite_sequence
-- ----------------------------
DELETE FROM "main"."sqlite_sequence";
INSERT INTO "main"."sqlite_sequence" VALUES ('Music', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('Profile', 0);
INSERT INTO "main"."sqlite_sequence" VALUES ('Sample', 0);
