CREATE SEQUENCE HIBERNATE_SEQUENCE START 1;

CREATE TABLE BOOK (
    ID    INTEGER PRIMARY KEY,
    DATE  VARCHAR(4000),
    META  VARCHAR(4000),
    PATH  VARCHAR(4000)
);

CREATE TABLE CREATOR (
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(4000)
);


CREATE TABLE TITLE (
    ID          INTEGER PRIMARY KEY,
    TITLE       VARCHAR(4000),
    TITLE_TYPE  VARCHAR(4000),
    TITLE_ORDER INTEGER,
    BOOK_ID     INTEGER,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID)
);


CREATE TABLE CREATOR2BOOK (
    BOOK_ID    INTEGER,
    CREATOR_ID INTEGER,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID),
    FOREIGN KEY (CREATOR_ID) REFERENCES CREATOR(ID)
);

CREATE TABLE CONTRIBUTOR (
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(4000)
);

CREATE TABLE CONTRIBUTOR2BOOK (
    BOOK_ID        INTEGER,
    CONTRIBUTOR_ID INTEGER,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID),
    FOREIGN KEY (CONTRIBUTOR_ID) REFERENCES CONTRIBUTOR(ID)
);

CREATE TABLE PUBLISHER (
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(4000)
);

CREATE TABLE PUBLISHER2BOOK (
    BOOK_ID      INTEGER,
    PUBLISHER_ID INTEGER,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID),
    FOREIGN KEY (PUBLISHER_ID) REFERENCES PUBLISHER(ID)
);

CREATE TABLE IDENTIFIER (
    ID       INTEGER PRIMARY KEY,
    BOOK_ID  INTEGER,
    VALUE    VARCHAR(4000),
    IDENT_ID VARCHAR(4000),
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID)
);

CREATE TABLE SUBJECT (
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(4000)
);

CREATE TABLE SUBJECT2BOOK (
    BOOK_ID    INTEGER,
    SUBJECT_ID INTEGER,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID),
    FOREIGN KEY (SUBJECT_ID) REFERENCES SUBJECT(ID)
);

CREATE TABLE LANGUAGE (
    ID   INTEGER PRIMARY KEY,
    NAME VARCHAR(4000)
);

CREATE TABLE LANGUAGE2BOOK (
    BOOK_ID     INTEGER,
    LANGUAGE_ID INTEGER,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK(ID),
    FOREIGN KEY (LANGUAGE_ID) REFERENCES LANGUAGE(ID)
);

