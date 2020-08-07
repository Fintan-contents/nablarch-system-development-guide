package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.dao.DaoFactory;
import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import nablarch.common.dao.DaoContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project service
 *
 * @author Goro Kumano
 */
public class ProjectService {

    /**
     * Universal DAO
     */
    private final DaoContext universalDao;

    /**
     * Number of items displayed per page
     */
    private static final long RECORDS_PER_PAGE = 20L;

    /**
     * Constructor.
     */
    public ProjectService() {
        this(DaoFactory.create());
    }

    /**
     * Constructor.
     *
     * @param universalDao Universal DAO
     */
    ProjectService(DaoContext universalDao) {
        this.universalDao = universalDao;
    }

    /**
     * Acquires entire division.
     *
     * @return Division list
     */
    public List<Organization> findAllDivision() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DIVISION");
    }

    /**
     * Acquires entire department.
     *
     * @return Department list
     */
    public List<Organization> findAllDepartment() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DEPARTMENT");
    }


    /**
     * Acquires specified organization.
     *
     * @param organizationId Organization ID
     * @return Organization
     */
    public Organization findOrganizationById(Integer organizationId) {
        Object[] param = {organizationId};
        return universalDao.findById(Organization.class, param);
    }

    /**
     * Registers project.
     *
     * @param project Project to be registered
     */
    public void insertProject(Project project) {
        universalDao.insert(project);
    }

    /**
     * Update the project.
     *
     * @param project Target of the update
     */
    public void updateProject(Project project) {
        universalDao.update(project);
    }

    /**
     * Search for a project.
     *
     * @param condition search criteria
     * @return Project List (with paging)
     */
    public List<ProjectWithOrganizationDto> listProject(ProjectSearchConditionDto condition) {
        return universalDao
                .per(RECORDS_PER_PAGE)
                .page(condition.getPageNumber())
                .findAllBySqlFile(ProjectWithOrganizationDto.class, "FIND_PROJECT_WITH_ORGANIZATION", condition);
    }

    /**
     * Find one project detail.
     *
     * @param projectId project ID
     * @return project detail
     */
    public ProjectWithOrganizationDto findProjectByIdWithOrganization(Integer projectId) {
        Map<String, Integer> condition = new HashMap<>();
        condition.put("projectId", projectId);
        return universalDao.findBySqlFile(ProjectWithOrganizationDto.class, "FIND_PROJECT_WITH_ORGANIZATION_BY_PROJECT_ID", condition);
    }

    /**
     * Find one project detail.
     *
     * @param projectId project ID
     * @return project detail
     */
    public Project findProjectById(Integer projectId) {
        return universalDao.findById(Project.class, projectId);
    }
}
