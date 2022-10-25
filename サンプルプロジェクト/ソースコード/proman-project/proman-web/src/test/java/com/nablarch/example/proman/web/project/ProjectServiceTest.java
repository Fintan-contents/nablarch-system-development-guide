package com.nablarch.example.proman.web.project;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.nablarch.example.proman.entity.Organization;
import com.nablarch.example.proman.entity.Project;
import com.nablarch.example.proman.web.common.dao.DaoStub;

import nablarch.common.dao.DaoContext;
import nablarch.common.dao.EntityList;
import nablarch.common.exclusivecontrol.OptimisticLockException;
import nablarch.core.util.DateUtil;

/**
 * ProjectServiceのテスト
 */
class ProjectServiceTest {

    /**
     * findAllDivisionのテスト
     */
    @Test
    void testFindAllDivision() {
        EntityList<Organization> organizationList = new EntityList<Organization>();
        Organization organization1 = new Organization();
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("組織１");
        organization1.setUpperOrganization(null);
        organizationList.add(organization1);
        Organization organization2 = new Organization();
        organization2.setOrganizationId(2);
        organization2.setOrganizationName("組織２");
        organization2.setUpperOrganization(null);
        organizationList.add(organization2);
        ProjectService sut = new ProjectService(new DaoStub() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId) {
                return (EntityList<T>) organizationList;
            }
        });

        List<Organization> result = sut.findAllDivision();
        assertThat(organizationList, is(result));
    }

    /**
     * findAllDepartmentのテスト
     */
    @Test
    void testFindAllDepartment() {
        EntityList<Organization> organizationList = new EntityList<Organization>();
        Organization organization1 = new Organization();
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("組織１");
        organization1.setUpperOrganization(null);
        organizationList.add(organization1);
        Organization organization2 = new Organization();
        organization2.setOrganizationId(2);
        organization2.setOrganizationName("組織２");
        organization2.setUpperOrganization(null);
        organizationList.add(organization2);
        ProjectService sut = new ProjectService(new DaoStub() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId) {
                return (EntityList<T>) organizationList;
            }
        });

        List<Organization> result = sut.findAllDepartment();
        assertThat(organizationList, is(result));
    }

    /**
     * findOrganizationByIdのテスト
     */
    @Test
    void testFindOrganizationById() {
        Organization organization1 = new Organization();
        organization1.setOrganizationId(1);
        organization1.setOrganizationName("組織１");
        organization1.setUpperOrganization(null);
        ProjectService sut = new ProjectService(new DaoStub() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                return (T) organization1;
            }
        });

        Organization result = sut.findOrganizationById(1);
        assertThat(organization1, is(result));
    }

    /**
     * insertProjectのテスト
     */
    @Test
    void testInsertProject() {
        final Map<String, Boolean> invoked = new HashMap<String, Boolean>();
        ProjectService sut = new ProjectService(new DaoStub() {
            @Override
            public <T> void insert(T entity) {
                invoked.put("insert", true);
            }
        });

        sut.insertProject(new Project());
        assertTrue(invoked.containsKey("insert"));
    }

    /**
     * updateProjectのテスト
     */
    @Test
    void testUpdateProject() {
        final Map<String, Boolean> invoked = new HashMap<String, Boolean>();
        ProjectService sut = new ProjectService(new DaoStub() {
            @Override
            public <T> int update(T entity) throws OptimisticLockException {
                invoked.put("update", true);
                return 1;
            }
        });
        sut.updateProject(new Project());
        assertTrue(invoked.containsKey("update"));
    }

    /**
     * listProjectのテスト
     */
    @Test
    void testListProject() {
        EntityList<ProjectWithOrganizationDto> projectList = new EntityList<ProjectWithOrganizationDto>();
        ProjectWithOrganizationDto dto1 = new ProjectWithOrganizationDto();
        dto1.setProjectId(1);
        dto1.setProjectName("プロジェクト１");
        dto1.setProjectType("1");
        dto1.setProjectClass("1");
        dto1.setProjectStartDate(DateUtil.getDate("20221001"));
        dto1.setProjectEndDate(DateUtil.getDate("20231001"));
        dto1.setOrganizationId(1);
        dto1.setOrganizationName("組織１");
        dto1.setClientId(1);
        dto1.setPmKanjiName("プロジェクトマネージャー１");
        dto1.setPlKanjiName("リーダー１");
        projectList.add(dto1);
        ProjectWithOrganizationDto dto2 = new ProjectWithOrganizationDto();
        dto2.setProjectId(2);
        dto2.setProjectName("プロジェクト２");
        dto2.setProjectType("1");
        dto2.setProjectClass("1");
        dto2.setProjectStartDate(DateUtil.getDate("20221201"));
        dto2.setProjectEndDate(DateUtil.getDate("20231001"));
        dto2.setOrganizationId(2);
        dto2.setOrganizationName("組織２");
        dto2.setClientId(2);
        dto2.setPmKanjiName("プロジェクトマネージャー２");
        dto2.setPlKanjiName("リーダー２");
        projectList.add(dto1);
        final Map<String, Long> invoked = new HashMap<String, Long>();
        ProjectService sut = new ProjectService(new DaoStub() {
            @Override
            public DaoContext per(long per) {
                invoked.put("per", per);
                return this;
            }

            @Override
            public DaoContext page(long page) {
                invoked.put("page", page);
                return this;
            }

            @SuppressWarnings("unchecked")
            @Override
            public <T> EntityList<T> findAllBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return (EntityList<T>) projectList;
            }
        });

        ProjectSearchConditionDto condition = new ProjectSearchConditionDto();
        condition.setPageNumber(1L);
        List<ProjectWithOrganizationDto> result = sut.listProject(condition);        
        assertThat(20L, is(invoked.get("per")));
        assertThat(1L, is(invoked.get("page")));
        assertThat(projectList, is(result));
    }

    /**
     * findProjectByIdWithOrganizationのテスト
     */
    @Test
    void testFindProjectByIdWithOrganization() {
        ProjectWithOrganizationDto dto1 = new ProjectWithOrganizationDto();
        dto1.setProjectId(1);
        dto1.setProjectName("プロジェクト１");
        dto1.setProjectType("1");
        dto1.setProjectClass("1");
        dto1.setProjectStartDate(DateUtil.getDate("20221001"));
        dto1.setProjectEndDate(DateUtil.getDate("20231001"));
        dto1.setOrganizationId(1);
        dto1.setOrganizationName("組織１");
        dto1.setClientId(1);
        dto1.setPmKanjiName("プロジェクトマネージャー１");
        dto1.setPlKanjiName("リーダー１");
        ProjectService sut = new ProjectService(new DaoStub() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> T findBySqlFile(Class<T> entityClass, String sqlId, Object params) {
                return (T) dto1;
            }
        });

        ProjectWithOrganizationDto result = sut.findProjectByIdWithOrganization(1);
        assertThat(dto1, is(result));
    }

    /**
     * findProjectByIdのテスト
     */
    @Test
    void testFindProjectById() {
        Project project1 = new Project();
        project1.setProjectId(1);
        project1.setProjectName("プロジェクト１");
        project1.setProjectType("1");
        project1.setProjectClass("1");
        project1.setProjectStartDate(DateUtil.getDate("20221001"));
        project1.setProjectEndDate(DateUtil.getDate("20231001"));
        project1.setOrganizationId(1);
        project1.setClientId(1);
        project1.setPmKanjiName("プロジェクトマネージャー１");
        project1.setPlKanjiName("リーダー１");
        ProjectService sut = new ProjectService(new DaoStub() {
            @SuppressWarnings("unchecked")
            @Override
            public <T> T findById(Class<T> entityClass, Object... id) {
                return (T) project1;
            }
        });
        Project result = sut.findProjectById(1);
        assertThat(project1, is(result));
    }

}
