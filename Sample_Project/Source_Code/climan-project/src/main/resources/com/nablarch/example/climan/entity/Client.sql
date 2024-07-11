FIND_CLIENT =
SELECT
    client_id,
    client_name,
    industry_code
FROM
    client
WHERE
    $if (clientName) { client_name like :clientName% }
    AND $if (industryCode) { industry_code = :industryCode }
ORDER BY client_name ASC

FIND_CLIENT_BY_CLIENT_NAME =
SELECT
    client_id,
    client_name,
    industry_code
FROM
    client
WHERE
    client_name = :clientName