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
  AND ? BETWEEN to_date(apply_start_date, 'YYYYMIDD') AND to_date(apply_end_date, 'YYYYMIDD')

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
