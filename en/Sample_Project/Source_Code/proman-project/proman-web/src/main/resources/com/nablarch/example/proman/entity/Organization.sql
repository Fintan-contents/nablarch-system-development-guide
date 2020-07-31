FIND_ORGANIZATION_BY_UPPER_ORGANIZATIONID =
SELECT
    organization_id,
    organization_name,
    upper_organization
FROM
    organization
WHERE
    upper_organization = ?
ORDER BY
    organization_id

FIND_ALL_DIVISION =
SELECT
    organization_id,
    organization_name,
    upper_organization
FROM
    organization
WHERE
    upper_organization IS NULL
ORDER BY
    organization_id

FIND_ALL_DEPARTMENT =
SELECT
    organization_id,
    organization_name,
    upper_organization
FROM
    organization
WHERE
    upper_organization IS NOT NULL
ORDER BY
    organization_id