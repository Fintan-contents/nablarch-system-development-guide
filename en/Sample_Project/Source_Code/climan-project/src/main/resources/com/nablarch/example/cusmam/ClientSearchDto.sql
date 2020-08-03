FIND_CLIENT=
SELECT
    CLIENT_ID,
    CLIENT_NAME,
    INDUSTRY_CODE
FROM
    CLIENT
WHERE
    $if(clientId) {CLIENT_ID = :clientId}
    AND $if(clientName) {CLIENT_NAME LIKE :%clientName%}
    AND $if(industryCode) {INDUSTRY_CODE = :industryCode}

FIND_CLIENT_BY_ID=
SELECT
    CLIENT_ID,
    CLIENT_NAME,
    INDUSTRY_CODE
FROM
    CLIENT
WHERE
    CLIENT_ID = :clientId