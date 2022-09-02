-- 失敗回数をリセットするSQL
RESET_FAILED_COUNT =
UPDATE system_account
   SET failed_count = CASE WHEN ? = 0 THEN failed_count ELSE 0 END,
       last_login_date_time = ?
 WHERE user_id = ?

-- 失敗回数をインクリメント＆アカウントロックを行うSQL
UPDATE_FAILED_COUNT = 
UPDATE system_account
   SET failed_count = ?
 WHERE user_id = ?
