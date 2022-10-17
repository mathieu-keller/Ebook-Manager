CREATE OR REPLACE VIEW V_LIBRARY_SEARCH AS
SELECT
    B.ID
  , CONCAT(ARRAY_TO_STRING(ARRAY(SELECT T.TITLE
                                 FROM
                                     TITLE T
                                 WHERE T.BOOK_ID = B.ID AND T.TITLE_TYPE = 'main'), ' ')) AS TITLE
  , CONCAT(ARRAY_TO_STRING(ARRAY(SELECT S.NAME
                                 FROM
                                     SUBJECT S
                                         LEFT JOIN SUBJECT2BOOK S2B ON S2B.SUBJECT_ID = S.ID
                                 WHERE S2B.BOOK_ID = B.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT CR.NAME
                                FROM
                                    CREATOR CR
                                        LEFT JOIN CREATOR2BOOK C2B ON C2B.CREATOR_ID = CR.ID
                                WHERE C2B.BOOK_ID = B.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT P.NAME
                                FROM
                                    PUBLISHER P
                                        LEFT JOIN PUBLISHER2BOOK P2B ON P2B.PUBLISHER_ID = P.ID
                                WHERE P2B.BOOK_ID = B.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT CO.NAME
                                FROM
                                    CONTRIBUTOR CO
                                        LEFT JOIN CONTRIBUTOR2BOOK C2B ON C2B.CONTRIBUTOR_ID = CO.ID
                                WHERE C2B.BOOK_ID = B.ID), ' ')
        , ' '
        , ARRAY_TO_STRING(ARRAY(SELECT T.TITLE
                                FROM
                                    TITLE T
                                WHERE T.BOOK_ID = B.ID), ' ')
               , ' '
               , C.NAME) AS SEARCH_TERMS
FROM
    BOOK B
        LEFT JOIN COLLECTION C ON C.ID = B.COLLECTION_ID;
