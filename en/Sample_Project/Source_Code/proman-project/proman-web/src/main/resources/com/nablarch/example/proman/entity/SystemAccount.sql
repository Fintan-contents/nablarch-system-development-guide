--------------------------------------------------------------------------------
-- SQL to retrieve available system account information
--------------------------------------------------------------------------------
FIND_SYSTEM_ACCOUNT =
SELECT
  *
FROM
  system_account
WHERE
  login_id = ?
  AND ? BETWEEN effective_date_from AND effective_date_to

--------------------------------------------------------------------------------
-- SQL to retrieve system account information based on login ID
--------------------------------------------------------------------------------
FIND_SYSTEM_ACCOUNT_BY_AK =
SELECT
  *
FROM
  system_account
WHERE
  login_id = ?
