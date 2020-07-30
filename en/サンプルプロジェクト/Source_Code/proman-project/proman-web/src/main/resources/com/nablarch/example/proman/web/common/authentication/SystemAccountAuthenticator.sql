-- SQL to reset the failure count
RESET_FAILED_COUNT =
update SYSTEM_ACCOUNT
   set FAILED_COUNT = case when ? = 0 then FAILED_COUNT else 0 end,
       LAST_LOGIN_DATE_TIME = ?
 where USER_ID = ?

-- SQL that increments the number of failures & performs account lock
UPDATE_FAILED_COUNT =
update SYSTEM_ACCOUNT
   set FAILED_COUNT = ?
 where USER_ID = ?
M
