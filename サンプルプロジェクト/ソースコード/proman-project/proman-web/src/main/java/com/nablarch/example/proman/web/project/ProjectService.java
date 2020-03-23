package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.dao.DaoFactory;
import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import nablarch.common.dao.DaoContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * プロジェクトサービス
 *
 * @author Goro Kumano
 */
public class ProjectService {

    /**
     * 共通DAO
     */
    private final DaoContext universalDao;

    /**
     * 1ページあたりの表示件数
     */
    private static final long RECORDS_PER_PAGE = 20L;

    /**
     * コンストラクタ。
     */
    public ProjectService() {
        this(DaoFactory.create());
    }

    /**
     * コンストラクタ。
     *
     * @param universalDao 共通DAO
     */
    ProjectService(DaoContext universalDao) {
        this.universalDao = universalDao;
    }

    /**
     * 全部の事業部を取得する。
     *
     * @return 事業部リスト
     */
    public List<Organization> findAllDivision() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DIVISION");
    }

    /**
     * 全部の部門を取得する。
     *
     * @return 部門リスト
     */
    public List<Organization> findAllDepartment() {
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ALL_DEPARTMENT");
    }


    /**
     * 指定した組織を取得する。
     *
     * @param organizationId 組織ID
     * @return 組織
     */
    public Organization findOrganizationById(Integer organizationId) {
        Object[] param = {organizationId};
        return universalDao.findById(Organization.class, param);
    }

    /**
     * プロジェクトを登録する。
     *
     * @param project 登録対象
     */
    public void insertProject(Project project) {
        universalDao.insert(project);
    }

    /**
     * プロジェクトを更新する。
     *
     * @param project 更新対象
     */
    public void updateProject(Project project) {
        universalDao.update(project);
    }

    /**
     * プロジェクトを検索する。
     *
     * @param condition 検索条件
     * @return プロジェクト一覧（ページングあり）
     */
    public List<ProjectWithOrganizationDto> listProject(ProjectSearchConditionDto condition) {
        return universalDao
                .per(RECORDS_PER_PAGE)
                .page(condition.getPageNumber())
                .findAllBySqlFile(ProjectWithOrganizationDto.class, "FIND_PROJECT_WITH_ORGANIZATION", condition);
    }

    /**
     * プロジェクトの詳細を1件検索する。
     *
     * @param projectId プロジェクトID
     * @return プロジェクト詳細
     */
    public ProjectWithOrganizationDto findProjectByIdWithOrganization(Integer projectId) {
        Map<String, Integer> condition = new HashMap<>();
        condition.put("projectId", projectId);
        return universalDao.findBySqlFile(ProjectWithOrganizationDto.class, "FIND_PROJECT_WITH_ORGANIZATION_BY_PROJECT_ID", condition);
    }

    /**
     * プロジェクトの詳細を1件検索する。
     *
     * @param projectId プロジェクトID
     * @return プロジェクト詳細
     */
    public Project findProjectById(Integer projectId) {
        return universalDao.findById(Project.class, projectId);
    }
}
