SET DB_CLOSE_DELAY -1;
;
CREATE USER IF NOT EXISTS SA SALT '5ff1a3a7e7de9fcd' HASH 'afdd6def9eec7f1d97e07b0253e2dd2b91e911b38741ecebb40c48e0870244d7' ADMIN;
CREATE SEQUENCE PUBLIC.HIBERNATE_SEQUENCE START WITH 10000;
CREATE MEMORY TABLE PUBLIC.ARTICLE(
  ID BIGINT NOT NULL,
  ABSTRACT CLOB NOT NULL,
  CREATED_AT DATE,
  JOURNAL VARCHAR(255) NOT NULL,
  JOURNAL_NUMBER VARCHAR(255),
  JOURNAL_VOLUME VARCHAR(255),
  NB_CITATIONS INTEGER,
  NB_VIEWS INTEGER,
  PAGES VARCHAR(255),
  PRICE FLOAT,
  PUBLICATION_MONTH VARCHAR(255),
  PUBLICATION_YEAR INTEGER NOT NULL,
  PUBLISHER VARCHAR(255),
  REFERENCE VARCHAR(255) NOT NULL,
  TITLE VARCHAR(255) NOT NULL,
  URL VARCHAR(255) NOT NULL,
  CATEGORY_ID BIGINT NOT NULL,
  CREATOR_USER_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.ARTICLE ADD CONSTRAINT PUBLIC.CONSTRAINT_F PRIMARY KEY(ID);

CREATE MEMORY TABLE PUBLIC.ARTICLE_AUTHORS(
 AUTHOR_ID BIGINT NOT NULL,
 ARTICLE_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.ARTICLE_AUTHORS ADD CONSTRAINT PUBLIC.CONSTRAINT_A PRIMARY KEY(AUTHOR_ID, ARTICLE_ID);

CREATE MEMORY TABLE PUBLIC.ARTICLE_KEYWORDS(
  ARTICLE_ID BIGINT NOT NULL,
  TAG_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.ARTICLE_KEYWORDS ADD CONSTRAINT PUBLIC.CONSTRAINT_9 PRIMARY KEY(ARTICLE_ID, TAG_ID);

CREATE MEMORY TABLE PUBLIC.ARTICLE_REFERENCES(
  ARTICLE_ID BIGINT NOT NULL,
  REFERENCE_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.ARTICLE_REFERENCES ADD CONSTRAINT PUBLIC.CONSTRAINT_1 PRIMARY KEY(ARTICLE_ID, REFERENCE_ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.ARTICLE_REFERENCES;
CREATE MEMORY TABLE PUBLIC.AUTHOR(
  ID BIGINT NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  SLUG VARCHAR(255) NOT NULL
);
ALTER TABLE PUBLIC.AUTHOR ADD CONSTRAINT PUBLIC.CONSTRAINT_7 PRIMARY KEY(ID);

CREATE MEMORY TABLE PUBLIC.BOOKMARK(
  ID BIGINT NOT NULL,
  CREATED_AT DATE,
  NAME VARCHAR(255),
  USER_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.BOOKMARK ADD CONSTRAINT PUBLIC.CONSTRAINT_A6 PRIMARY KEY(ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.BOOKMARK;
CREATE MEMORY TABLE PUBLIC.BOOKMARK_ARTICLE(
  BOOKMARK_ID BIGINT NOT NULL,
  ARTICLE_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.BOOKMARK_ARTICLE ADD CONSTRAINT PUBLIC.CONSTRAINT_C PRIMARY KEY(BOOKMARK_ID, ARTICLE_ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.BOOKMARK_ARTICLE;
CREATE MEMORY TABLE PUBLIC.DOMAIN(
  ID BIGINT NOT NULL,
  NAME VARCHAR(255) NOT NULL
);
ALTER TABLE PUBLIC.DOMAIN ADD CONSTRAINT PUBLIC.CONSTRAINT_78 PRIMARY KEY(ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.DOMAIN;
CREATE MEMORY TABLE PUBLIC.FOLLOWER_TAGS(
  FOLLOWER_ID BIGINT NOT NULL,
  TAG_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.FOLLOWER_TAGS ADD CONSTRAINT PUBLIC.CONSTRAINT_3 PRIMARY KEY(FOLLOWER_ID, TAG_ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.FOLLOWER_TAGS;
CREATE MEMORY TABLE PUBLIC.RESEARCH_GROUP(
  ID BIGINT NOT NULL,
  LINK VARCHAR(255) NOT NULL,
  CREATED_AT DATE,
  NAME VARCHAR(255) NOT NULL,
  NOMBRE INTEGER CHECK (NOMBRE >= 1)
);
ALTER TABLE PUBLIC.RESEARCH_GROUP ADD CONSTRAINT PUBLIC.CONSTRAINT_7E PRIMARY KEY(ID);
-- 0 +/- SELECT COUNT(*) FROM PUBLIC.RESEARCH_GROUP;
CREATE MEMORY TABLE PUBLIC.STATE_OF_THE_ART(
  ID BIGINT NOT NULL,
  CREATED_AT DATE,
  DESCRIPTION VARCHAR(255),
  REFERENCE VARCHAR(255) NOT NULL,
  TITLE VARCHAR(255) NOT NULL,
  USER_ID BIGINT
);
ALTER TABLE PUBLIC.STATE_OF_THE_ART ADD CONSTRAINT PUBLIC.CONSTRAINT_E PRIMARY KEY(ID);

CREATE MEMORY TABLE PUBLIC.STATE_OF_THE_ART_ARTICLES(
  SOTA_ID BIGINT NOT NULL,
  ARTICLE_ID BIGINT NOT NULL
);

CREATE MEMORY TABLE PUBLIC.STATE_OF_THE_ART_TAGS(
  SOTA_ID BIGINT NOT NULL,
  TAG_ID BIGINT NOT NULL
);

CREATE MEMORY TABLE PUBLIC.TAG(
  ID BIGINT NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  SLUG VARCHAR(255) NOT NULL
);
ALTER TABLE PUBLIC.TAG ADD CONSTRAINT PUBLIC.CONSTRAINT_14 PRIMARY KEY(ID);

CREATE MEMORY TABLE PUBLIC.UNIVERSITY(
  ID BIGINT NOT NULL,
  NAME VARCHAR(255) NOT NULL,
  WEBSITE_URL VARCHAR(255) NOT NULL
);
ALTER TABLE PUBLIC.UNIVERSITY ADD CONSTRAINT PUBLIC.CONSTRAINT_2 PRIMARY KEY(ID);

CREATE MEMORY TABLE PUBLIC.UNIVERSITY_CURRENT(
  UNIVERSITY_ID BIGINT NOT NULL,
  USER_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.UNIVERSITY_CURRENT ADD CONSTRAINT PUBLIC.CONSTRAINT_4 PRIMARY KEY(UNIVERSITY_ID, USER_ID);

CREATE MEMORY TABLE PUBLIC.USER(
  ID BIGINT NOT NULL,
  CREATED_AT DATE,
  DOMAIN VARCHAR(255),
  EMAIL VARCHAR(255),
  FIRST_NAME VARCHAR(255),
  LAST_NAME VARCHAR(255),
  PASSWORD VARCHAR(255) NOT NULL,
  USERNAME VARCHAR(255) NOT NULL,
  CURRENT_UNIVERTY_ID BIGINT,
  PROFILE_ID BIGINT
);
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.CONSTRAINT_27 PRIMARY KEY(ID);

CREATE MEMORY TABLE PUBLIC.USER_FOLLOWER(
  USER_ID BIGINT NOT NULL,
  FOLLOWING_ID BIGINT NOT NULL
);

CREATE MEMORY TABLE PUBLIC.USER_GROUP(
  USER_ID BIGINT NOT NULL,
  GROUP_ID BIGINT NOT NULL
);
ALTER TABLE PUBLIC.USER_GROUP ADD CONSTRAINT PUBLIC.CONSTRAINT_C6 PRIMARY KEY(USER_ID, GROUP_ID);

CREATE MEMORY TABLE PUBLIC.USER_PROFILE(
  ID BIGINT NOT NULL,
  DESCRIPTION VARCHAR(255),
  FACEBOOK_URL VARCHAR(255),
  LINKED_IN_URL VARCHAR(255),
  AVATAR_URL VARCHAR(255),
  STATUS VARCHAR(255),
  TWITTER_URL VARCHAR(255),
  USER_ID BIGINT
);
ALTER TABLE PUBLIC.USER_PROFILE ADD CONSTRAINT PUBLIC.CONSTRAINT_CE PRIMARY KEY(ID);

ALTER TABLE PUBLIC.TAG ADD CONSTRAINT PUBLIC.UK_1WDPSED5KNA2Y38HNBGRNHI5B UNIQUE(NAME);
ALTER TABLE PUBLIC.AUTHOR ADD CONSTRAINT PUBLIC.UK_A247BD901TOI15S4MUCFSJ4UR UNIQUE(SLUG);
ALTER TABLE PUBLIC.ARTICLE ADD CONSTRAINT PUBLIC.UK_LA4E9L2G2OTN85R9IH4BTNMI UNIQUE(URL);
ALTER TABLE PUBLIC.RESEARCH_GROUP ADD CONSTRAINT PUBLIC.UK_TQNW4F009P2YD3LLM7TWCAKJ8 UNIQUE(NAME);
ALTER TABLE PUBLIC.UNIVERSITY ADD CONSTRAINT PUBLIC.UK_ANVWIS0T0Q2J0UXQCPV64YU90 UNIQUE(WEBSITE_URL);
ALTER TABLE PUBLIC.TAG ADD CONSTRAINT PUBLIC.UK_1AFK1Y1O95L8OXXJXSQVELM3O UNIQUE(SLUG);
ALTER TABLE PUBLIC.ARTICLE ADD CONSTRAINT PUBLIC.UK_RTI6XU203K7HFOHPU89214W6C UNIQUE(REFERENCE);
ALTER TABLE PUBLIC.DOMAIN ADD CONSTRAINT PUBLIC.UK_GA2SQP4LBOBLQV6OKS9ORYD9Q UNIQUE(NAME);
ALTER TABLE PUBLIC.UNIVERSITY ADD CONSTRAINT PUBLIC.UK_RU212K5VIB3YVU360FUY3H1G5 UNIQUE(NAME);
ALTER TABLE PUBLIC.STATE_OF_THE_ART ADD CONSTRAINT PUBLIC.UK_PQJRE1SMT4PLX5JTT8C1Y4DL6 UNIQUE(TITLE);
ALTER TABLE PUBLIC.ARTICLE ADD CONSTRAINT PUBLIC.UK_571GX7OQO5XPMGOCEGAIDLCU9 UNIQUE(TITLE);
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.UK_SB8BBOUER5WAK8VYIIY4PF2BX UNIQUE(USERNAME);
ALTER TABLE PUBLIC.AUTHOR ADD CONSTRAINT PUBLIC.UK_OR6K6JMYWERXBME223C988BMG UNIQUE(NAME);
ALTER TABLE PUBLIC.BOOKMARK ADD CONSTRAINT PUBLIC.UK_QG861JFEHKC890J3YENMK1X8L UNIQUE(USER_ID);
ALTER TABLE PUBLIC.STATE_OF_THE_ART ADD CONSTRAINT PUBLIC.UK_4UEX11Q5KIV2875K1P0YLX10L UNIQUE(REFERENCE);
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.UK_9V6E6KC6G2AN7GD5QC02GQ6YO UNIQUE(PROFILE_ID);
ALTER TABLE PUBLIC.BOOKMARK ADD CONSTRAINT PUBLIC.FK3OGDXSXA4TX6VNDYVPK1FK1AM FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE ADD CONSTRAINT PUBLIC.FK93OG73GBTF4GU5BG9LNC0RFKO FOREIGN KEY(CREATOR_USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE_REFERENCES ADD CONSTRAINT PUBLIC.FKQ84XVM8KWU6X3BS310XPOKL5P FOREIGN KEY(ARTICLE_ID) REFERENCES PUBLIC.ARTICLE(ID) NOCHECK;
ALTER TABLE PUBLIC.USER_FOLLOWER ADD CONSTRAINT PUBLIC.FK7LYUFBUI36W4JUL3ML7P4K5AR FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.FOLLOWER_TAGS ADD CONSTRAINT PUBLIC.FKJOEX3ARXL68MM3HGOQ7V6D0EA FOREIGN KEY(TAG_ID) REFERENCES PUBLIC.TAG(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE ADD CONSTRAINT PUBLIC.FKR1RVPEFQ856PKU6RY42T8AS6U FOREIGN KEY(CATEGORY_ID) REFERENCES PUBLIC.TAG(ID) NOCHECK;
ALTER TABLE PUBLIC.STATE_OF_THE_ART_ARTICLES ADD CONSTRAINT PUBLIC.FK25RGVV5368M2QL32R9MOT5WD1 FOREIGN KEY(SOTA_ID) REFERENCES PUBLIC.STATE_OF_THE_ART(ID) NOCHECK;
ALTER TABLE PUBLIC.STATE_OF_THE_ART_TAGS ADD CONSTRAINT PUBLIC.FKSYYAXO1U3SSB8KIVUQ9KH72S6 FOREIGN KEY(TAG_ID) REFERENCES PUBLIC.TAG(ID) NOCHECK;
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.FKEVA8BRHN7JMTO69O5UNQ0U7IT FOREIGN KEY(CURRENT_UNIVERTY_ID) REFERENCES PUBLIC.UNIVERSITY(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE_AUTHORS ADD CONSTRAINT PUBLIC.FK33EBMLEPMLUHTMDCXCQVTR103 FOREIGN KEY(AUTHOR_ID) REFERENCES PUBLIC.ARTICLE(ID) NOCHECK;
ALTER TABLE PUBLIC.USER_GROUP ADD CONSTRAINT PUBLIC.FKSBEB7HERS5B9RNVCCLMNVOT5N FOREIGN KEY(GROUP_ID) REFERENCES PUBLIC.RESEARCH_GROUP(ID) NOCHECK;
ALTER TABLE PUBLIC.BOOKMARK_ARTICLE ADD CONSTRAINT PUBLIC.FKSW09R2CHNIBIDV5QRXOTTXOOJ FOREIGN KEY(BOOKMARK_ID) REFERENCES PUBLIC.BOOKMARK(ID) NOCHECK;
ALTER TABLE PUBLIC.FOLLOWER_TAGS ADD CONSTRAINT PUBLIC.FKB2NVFI2ATB8HHHCX3CMQH86CX FOREIGN KEY(FOLLOWER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.USER_FOLLOWER ADD CONSTRAINT PUBLIC.FK23901XECCK2PGEWTESGJP6P73 FOREIGN KEY(FOLLOWING_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE_KEYWORDS ADD CONSTRAINT PUBLIC.FKIDMGMYOLURSCRSOS0ESE1MU8P FOREIGN KEY(TAG_ID) REFERENCES PUBLIC.TAG(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE_KEYWORDS ADD CONSTRAINT PUBLIC.FKIMDOMUAH3TA4HMEB77E68M0WL FOREIGN KEY(ARTICLE_ID) REFERENCES PUBLIC.ARTICLE(ID) NOCHECK;
ALTER TABLE PUBLIC.USER_PROFILE ADD CONSTRAINT PUBLIC.FK6KWJ5LK78PNHWOR4PGOSVB51R FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE_REFERENCES ADD CONSTRAINT PUBLIC.FK8EAWEWN44YVIVQ0Y13NAA7R3R FOREIGN KEY(REFERENCE_ID) REFERENCES PUBLIC.ARTICLE(ID) NOCHECK;
ALTER TABLE PUBLIC.UNIVERSITY_CURRENT ADD CONSTRAINT PUBLIC.FKHC7OL0YAVH5D7IWKI8975QCMY FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.USER ADD CONSTRAINT PUBLIC.FKP8VF411F7WBMTU75QKJGV6DPF FOREIGN KEY(PROFILE_ID) REFERENCES PUBLIC.USER_PROFILE(ID) NOCHECK;
ALTER TABLE PUBLIC.STATE_OF_THE_ART_ARTICLES ADD CONSTRAINT PUBLIC.FK84IKKES9UTRX6T55TJGC2JIYH FOREIGN KEY(ARTICLE_ID) REFERENCES PUBLIC.ARTICLE(ID) NOCHECK;
ALTER TABLE PUBLIC.ARTICLE_AUTHORS ADD CONSTRAINT PUBLIC.FK2N7F44D637JPR8I8AK6H6IEEC FOREIGN KEY(ARTICLE_ID) REFERENCES PUBLIC.AUTHOR(ID) NOCHECK;
ALTER TABLE PUBLIC.STATE_OF_THE_ART_TAGS ADD CONSTRAINT PUBLIC.FK4XUBPMGS27OENKFXGI2N7HTI4 FOREIGN KEY(SOTA_ID) REFERENCES PUBLIC.STATE_OF_THE_ART(ID) NOCHECK;
ALTER TABLE PUBLIC.STATE_OF_THE_ART ADD CONSTRAINT PUBLIC.FKPLWR874KK4RXI3CNQN6PEIA4F FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
ALTER TABLE PUBLIC.BOOKMARK_ARTICLE ADD CONSTRAINT PUBLIC.FKD7PV9AMC1YK0FDQBNHFYL8FTJ FOREIGN KEY(ARTICLE_ID) REFERENCES PUBLIC.ARTICLE(ID) NOCHECK;
ALTER TABLE PUBLIC.UNIVERSITY_CURRENT ADD CONSTRAINT PUBLIC.FKHQ70GENSTSFBK2O3OYPG7GYKE FOREIGN KEY(UNIVERSITY_ID) REFERENCES PUBLIC.UNIVERSITY(ID) NOCHECK;
ALTER TABLE PUBLIC.USER_GROUP ADD CONSTRAINT PUBLIC.FK1C1DSW3Q36679VAIQWVTV36A6 FOREIGN KEY(USER_ID) REFERENCES PUBLIC.USER(ID) NOCHECK;
