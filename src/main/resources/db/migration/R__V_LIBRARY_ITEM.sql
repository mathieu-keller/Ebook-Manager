CREATE OR REPLACE VIEW V_LIBRARY_ITEM AS
SELECT
    BOOK.ID
  , CONCAT(ARRAY_TO_STRING(ARRAY(SELECT T.TITLE
                          FROM
                              TITLE T
                          WHERE T.BOOK_ID = BOOK.ID AND T.TITLE_TYPE = 'main'), ' ')) as title
  , BOOK.COVER
  , CONCAT(ARRAY_TO_STRING(ARRAY(SELECT S.NAME
                                 FROM
                                     SUBJECT S
                                         LEFT JOIN SUBJECT2BOOK S2B ON S2B.SUBJECT_ID = S.ID
                                 WHERE S2B.BOOK_ID = BOOK.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT C.NAME
                                FROM
                                    CREATOR C
                                        LEFT JOIN CREATOR2BOOK C2B ON C2B.CREATOR_ID = C.ID
                                WHERE C2B.BOOK_ID = BOOK.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT P.NAME
                                FROM
                                    PUBLISHER P
                                        LEFT JOIN PUBLISHER2BOOK P2B ON P2B.PUBLISHER_ID = P.ID
                                WHERE P2B.BOOK_ID = BOOK.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT C.NAME
                                FROM
                                    CONTRIBUTOR C
                                        LEFT JOIN CONTRIBUTOR2BOOK C2B ON C2B.CONTRIBUTOR_ID = C.ID
                                WHERE C2B.BOOK_ID = BOOK.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT T.TITLE
                                FROM
                                    TITLE T
                                WHERE T.BOOK_ID = BOOK.ID), ' ')) AS SEARCH_TERMS
FROM
    BOOK;
