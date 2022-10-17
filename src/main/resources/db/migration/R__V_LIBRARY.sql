CREATE OR REPLACE VIEW V_LIBRARY AS
SELECT
    COALESCE(C.ID, B.ID) AS ID, COALESCE(C.NAME, ARRAY_TO_STRING(ARRAY(SELECT T.TITLE
                                                                       FROM
                                                                           TITLE T
                                                                       WHERE T.BOOK_ID = B.ID AND T.TITLE_TYPE = 'main'),
                                                                 ' ')) AS TITLE
                              , CASE
                                    WHEN C.NAME IS NOT NULL
                                        THEN
                                        'collection'
                                        ELSE
                                        'book'
        END AS ITEM_TYPE
                              , COUNT(B.*) AS BOOK_COUNT
FROM
    BOOK B
        LEFT JOIN COLLECTION C ON B.COLLECTION_ID = C.ID
GROUP BY
    COALESCE(C.ID, B.ID), COALESCE(C.ID, B.ID), COALESCE(C.NAME, ARRAY_TO_STRING(ARRAY(SELECT T.TITLE
                                                                                       FROM
                                                                                           TITLE T
                                                                                       WHERE T.BOOK_ID = B.ID AND T.TITLE_TYPE = 'main'),
                                                                                 ' '))
                        , CASE
                              WHEN C.NAME IS NOT NULL
                                  THEN
                                  'collection'
                                  ELSE
                                  'book'
        END;

