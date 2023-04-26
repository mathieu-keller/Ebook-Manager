CREATE SEQUENCE collection_seq START 1;
select setval('collection_seq',  (SELECT MAX(ID) FROM COLLECTION));

CREATE SEQUENCE book_seq START 1;
select setval('book_seq',  (SELECT MAX(ID) FROM BOOK));

CREATE SEQUENCE title_seq START 1;
select setval('title_seq',  (SELECT MAX(ID) FROM TITLE));

CREATE SEQUENCE creator_seq START 1;
select setval('creator_seq',  (SELECT MAX(ID) FROM CREATOR));

CREATE SEQUENCE contributor_seq START 1;
select setval('contributor_seq',  (SELECT MAX(ID) FROM CONTRIBUTOR));

CREATE SEQUENCE publisher_seq START 1;
select setval('publisher_seq',  (SELECT MAX(ID) FROM PUBLISHER));

CREATE SEQUENCE identifier_seq START 1;
select setval('identifier_seq',  (SELECT MAX(ID) FROM IDENTIFIER));

CREATE SEQUENCE subject_seq START 1;
select setval('subject_seq',  (SELECT MAX(ID) FROM SUBJECT));

CREATE SEQUENCE language_seq START 1;
select setval('language_seq',  (SELECT MAX(ID) FROM LANGUAGE));

CREATE SEQUENCE ebook_user_seq START 1;
select setval('ebook_user_seq',  (SELECT MAX(ID) FROM EBOOK_USER));

DROP SEQUENCE HIBERNATE_SEQUENCE;