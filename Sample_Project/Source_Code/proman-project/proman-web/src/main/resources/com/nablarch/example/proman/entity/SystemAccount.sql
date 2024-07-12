--------------------------------------------------------------------------------
-- 利用可能なシステムアカウント情報を取得するSQL
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
-- ログインIDを元にシステムアカウント情報を取得するSQL
--------------------------------------------------------------------------------
FIND_SYSTEM_ACCOUNT_BY_AK =
SELECT
  *
FROM
  system_account
WHERE
  login_id = ?