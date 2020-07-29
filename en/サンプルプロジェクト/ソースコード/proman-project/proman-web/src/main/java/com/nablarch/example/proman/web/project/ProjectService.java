package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import com.nablarch.example.proman.common.dao.DaoFactory;
import nablarch.common.dao.DaoContext;
import nablarch.common.dao.EntityList;

import java.util.List;

/**
 * Project service
 *
 * @author TIS
 */
public class ProjectService {

    /**
     * Universal DAO
     */
    private final DaoContext universalDao;

    /**
     * Constructor.
     */
    public ProjectService() {
        this(DaoFactory.create());
    }

    /**
     * Constructor.
     * @param universalDao: Universal DAO
     */
    ProjectService(DaoContext universalDao) {
        this.universalDao = universalDao;
    }

    /**
     * Acquires entire division.
     * @return: Division list
     */
    public List<Organization> findAllDivision() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DIVISION");
    }

    /**
     * Acquires entire department.
     * @return: Department list
     */
    public List<Organization> findAllDepartment() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DEPARTMENT");
    }

    /**
     * Acquires specified organization.
     * @param organizationId Organization ID
     * @return Organization
     */
    public Organization findOrganizationById(Integer organizationId) {
        Object[] param = { organizationId };
        return universalDao.findById(Organization.class, param);
    }

    /**
     * Registers project.
     * @param project: Project to be registered
     */
    public void insertProject(Project project) {
        universalDao.insert(project);
    }


}
