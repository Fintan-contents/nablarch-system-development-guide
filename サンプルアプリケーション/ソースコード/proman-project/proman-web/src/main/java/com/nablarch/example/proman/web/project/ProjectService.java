package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import com.nablarch.example.proman.common.dao.DaoFactory;
import nablarch.common.dao.DaoContext;
import nablarch.common.dao.EntityList;

import java.util.List;

/**
 * プロジェクトサービス
 *
 * @author TIS
 */
public class ProjectService {

    /**
     * 共通DAO
     */
    private final DaoContext universalDao;

    /**
     * コンストラクタ。
     */
    public ProjectService() {
        this(DaoFactory.create());
    }

    /**
     * コンストラクタ。
     * @param universalDao 共通DAO
     */
    ProjectService(DaoContext universalDao) {
        this.universalDao = universalDao;
    }
    /**
     * 全部の事業部を取得する。
     * @return 事業部リスト
     */
    public List<Organization> findAllDivision() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DIVISION");
    }

    /**
     * 全部の部門を取得する。
     * @return 部門リスト
     */
    public List<Organization> findAllDepartment() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DEPARTMENT");
    }


    /**
     * 指定した組織を取得する。
     * @param organizationId 組織ID
     * @return 組織
     */
    public Organization findOrganizationById(Integer organizationId) {
        Object[] param = { organizationId };
        return universalDao.findById(Organization.class, param);
    }

    /**
     * プロジェクトを登録する。
     * @param project 登録対象
     */
    public void insertProject(Project project) {
        universalDao.insert(project);
    }


}
