-- SQL to reset the failure count
RESET_FAILED_COUNT =
UPDATE system_account
   SET failed_count = CASE WHEN ? = 0 THEN failed_count ELSE 0 END,
       last_login_date_time = ?
 WHERE user_id = ?

-- SQL that increments the number of failures & performs account lock
UPDATE_FAILED_COUNT =
UPDATE system_account
   SET failed_count = ?
 WHERE user_id = ?
