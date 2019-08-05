-------------------------------------------------------------------------------
-- 検索画面入力した項目による検索
-------------------------------------------------------------------------------
FIND_PROJECT_WITH_ORGANIZATION_BY_ALL =
SELECT
  P.PROJECT_ID AS PROJECT_ID
  , P.PROJECT_NAME AS PROJECT_NAME
  , C1.CODE_NAME AS PROJECT_TYPE
  , C2.CODE_NAME AS PROJECT_CLASS
  , P.PROJECT_START_DATE AS PROJECT_START_DATE
  , P.PROJECT_END_DATE AS PROJECT_END_DATE
  , P.ORGANIZATION_ID AS ORGANIZATION_ID
  , P.CLIENT_ID AS CLIENT_ID
  , P.PROJECT_MANAGER AS PROJECT_MANAGER
  , P.PROJECT_LEADER AS PROJECT_LEADER
  , P.NOTE AS NOTE
  , P.SALES AS SALES
  , P.VERSION AS VERSION
  , O.ORGANIZATION_ID AS ORGANIZATION_ID
  , O.ORGANIZATION_NAME AS ORGANIZATION_NAME
  , UO.ORGANIZATION_ID AS DIVISION_ID
  , UO.ORGANIZATION_NAME AS DIVISION_NAME
FROM
  PROJECT P
  , ORGANIZATION O
  , ORGANIZATION UO
  , CODE_NAME C1
  , CODE_NAME C2
WHERE
    1 = 1
    AND P.ORGANIZATION_ID = O.ORGANIZATION_ID
    AND O.UPPER_ORGANIZATION = UO.ORGANIZATION_ID
    AND $if(projectName) {PROJECT_NAME LIKE  :%projectName%}
    AND $if(organizationId) {O.ORGANIZATION_ID = :organizationId}
    AND $if(divisionId) {UO.ORGANIZATION_ID = :divisionId}
    AND P.PROJECT_TYPE = C1.CODE_VALUE
    AND $if(projectType) {C1.CODE_VALUE IN (:projectType[])}
    AND C1.CODE_ID = 'C0300001'
    AND P.PROJECT_CLASS = C2.CODE_VALUE
    AND $if(projectClass) {C2.CODE_VALUE IN (:projectClass[])}
    AND C2.CODE_ID = 'C0200001'
    AND $if(salesFrom) {SALES >= :salesFrom}
    AND $if(salesTo) {SALES <= :salesTo}
    AND $if(projectStartDateBegin) {PROJECT_START_DATE >= :projectStartDateBegin}
    AND $if(projectStartDateEnd) {PROJECT_START_DATE <= :projectStartDateEnd}
    AND $if(projectEndDateBegin) {PROJECT_END_DATE >= :projectEndDateBegin}
    AND $if(projectEndDateEnd) {PROJECT_END_DATE <= :projectEndDateEnd}
ORDER BY
    PROJECT_START_DATE, PROJECT_END_DATE, PROJECT_NAME