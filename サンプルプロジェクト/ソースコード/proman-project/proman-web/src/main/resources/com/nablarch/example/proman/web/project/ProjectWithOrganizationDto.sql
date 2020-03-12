FIND_PROJECT_WITH_ORGANIZATION =
SELECT
    project.project_id,
    project.project_name,
    project.project_type,
    project.project_class,
    project.project_manager,
    project.sales,
    project.project_start_date,
    project.project_end_date,
    project.version_no,
    division.organization_name division_name,
    department.organization_name
FROM
    project
LEFT OUTER JOIN organization AS department
  ON project.organization_id = department.organization_id
LEFT OUTER JOIN organization AS division
  ON department.upper_organization = division.organization_id
WHERE
    $if(divisionId) {division.organization_id = :divisionId}
  AND
    $if(organizationId) {department.organization_id = :organizationId}
  AND
    $if(projectType) {project.project_type in (:projectType[])}
  AND
    $if(projectClass) {project.project_class in (:projectClass[])}
  AND
    $if(salesFrom) {project.sales >= :salesFrom}
  AND
    $if(salesTo) {project.sales <= :salesTo}
  AND
    $if(projectStartDateFrom) {project.project_start_date >= :projectStartDateFrom}
  AND
    $if(projectStartDateTo) {project.project_start_date <= :projectStartDateTo}
  AND
    $if(projectEndDateFrom) {project.project_end_date >= :projectEndDateFrom}
  AND
    $if(projectEndDateTo) {project.project_end_date <= :projectEndDateTo}
  AND
    $if(projectName) {project.project_name like :%projectName%}
ORDER BY
    project.project_id
    
FIND_PROJECT_WITH_ORGANIZATION_BY_PROJECT_ID =
SELECT
    project.project_id,
    project.project_name,
    project.project_type,
    project.project_class,
    project.sales,
    project.client_id,
    project.project_manager,
    project.project_leader,
    project.project_start_date,
    project.project_end_date,
    project.note,
    project.version_no,
    division.organization_name division_name,
    department.organization_name
FROM
    project
LEFT OUTER JOIN organization AS department
  ON project.organization_id = department.organization_id
LEFT OUTER JOIN organization AS division
  ON department.upper_organization = division.organization_id
WHERE
    project.project_id = :projectId
