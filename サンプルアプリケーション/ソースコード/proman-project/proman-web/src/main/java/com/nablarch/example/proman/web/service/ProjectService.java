package com.nablarch.example.proman.web.service;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import com.nablarch.example.proman.common.dao.DaoFactory;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationDto;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationSearchConditionDto;
import com.nablarch.example.proman.web.dto.ProjectWithOrganizationSearchResultDto;
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
     * 指定された事業部の配下の部門を取得する。
     * @param organizationId 事業部の組織ID
     * @return 部門リスト
     */
    public List<Organization> findOrganizationByUpperOrganizationId(Integer organizationId) {
        Object[] param = { organizationId };
        return universalDao.findAllBySqlFile(Organization.class, "FIND_ORGANIZATION_BY_UPPER_ORGANIZATIONID", param);
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
     * プロジェクトを検索する。
     * @param projectWithOrganizationSearchConditionDto 検索条件
     * @return 取得したプロジェクトと組織紐づいてるレコードのリスト
     */
    public EntityList<ProjectWithOrganizationSearchResultDto> findProjectWithOrganizationByCondition(
            ProjectWithOrganizationSearchConditionDto projectWithOrganizationSearchConditionDto) {
        return universalDao
                .page(projectWithOrganizationSearchConditionDto.getPageNumber())
                .per(10L)
                .findAllBySqlFile(ProjectWithOrganizationSearchResultDto.class,
                        "FIND_PROJECT_WITH_ORGANIZATION_BY_ALL",
                        projectWithOrganizationSearchConditionDto);
    }

    /**
     * プロジェクト詳細を検索する。
     * @param projectId プロジェクトID
     * @return 取得したプロジェクトと組織紐づいてるレコードのリスト
     */
    public ProjectWithOrganizationDto findProjectWithOrganizationById(Integer projectId) {
        Object[] param = { projectId };
        return universalDao.findBySqlFile(ProjectWithOrganizationDto.class, "FIND_PROJECT_WITH_ORGANIZATION_BY_ID", param);
    }

    /**
     * プロジェクトを登録する。
     * @param project 登録対象
     */
    public void insertProject(Project project) {
        universalDao.insert(project);
    }

    /**
     * プロジェクトを更新する。
     * @param project 更新対象
     * @return 更新したレコード数
     */
    public int updateProject(Project project) {
        return universalDao.update(project);
    }

    /**
     * プロジェクトを削除する。
     * @param project 削除対象
     * @return 削除したレコード数
     */
    public int  deleteProject(Project project) {
        return universalDao.delete(project);
    }

}
