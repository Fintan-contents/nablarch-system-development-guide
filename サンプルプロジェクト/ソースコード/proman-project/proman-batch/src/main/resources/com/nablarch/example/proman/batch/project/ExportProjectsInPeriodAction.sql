FIND_PROJECT_IN_PERIOD =
SELECT
    project_id projectId,
    project_name projectName,
    project_type projectType,
    project_class projectClass,
    project_start_date projectStartDate,
    project_end_date projectEndDate,
    organization_id organizationId,
    client_id clientId,
    pm_kanji_name projectManager,
    pl_kanji_name projectLeader,
    note note,
    amount_sales sales,
    update_version version
FROM
    project
WHERE
    project_start_date <= ?
    AND project_end_date >= ?
ORDER BY
    project_start_date, project_end_date, project_name

