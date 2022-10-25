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
  AND ? BETWEEN apply_start_date AND apply_end_date

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
