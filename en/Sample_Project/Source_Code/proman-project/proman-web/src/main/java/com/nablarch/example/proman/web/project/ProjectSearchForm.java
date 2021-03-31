package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.common.util.DateRelationUtil;
import com.nablarch.example.proman.common.util.MoneyRelationUtil;
import com.nablarch.example.proman.web.common.SearchFormBase;
import nablarch.core.util.StringUtil;
import nablarch.core.validation.ee.Domain;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project search form
 */
public class ProjectSearchForm extends SearchFormBase implements Serializable {
    /** Division ID */
    @Domain("organizationId")
    private String divisionId;
    /** Organization Id */
    @Domain("organizationId")
    private String organizationId;
    /** Project type */
    @Valid
    private List<ProjectType> projectType;
    /** Project classification */
    @Valid
    private List<ProjectClass> projectClass;
    /** Sales from */
    @Domain("amountOfMoney")
    private String salesFrom;
    /** Sales to */
    @Domain("amountOfMoney")
    private String salesTo;
    /** Start date from */
    @Domain("date")
    private String projectStartDateFrom;
    /** Start date to */
    @Domain("date")
    private String projectStartDateTo;
    /** Ebd date from */
    @Domain("date")
    private String projectEndDateFrom;
    /** End date to */
    @Domain("date")
    private String projectEndDateTo;
    /** Project name */
    @Domain("projectName")
    private String projectName;

    /**
     * Acquires division ID.
     *
     * @return division ID
     */

    public String getDivisionId() {
        return divisionId;
    }

    /**
     * Set division ID.
     *
     * @param divisionId division ID
     */
    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Acquires organization Id 
     *
     * @return organization Id
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Set organization Id.
     *
     * @param organizationId organization Id
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Acquires project type.
     *
     * @return project type
     */
    public String[] getProjectType() {
        if (projectType == null) {
            return null;
        }
        return projectType.stream()
                .map(ProjectType::getProjectType)
                .toArray(String[]::new);
    }

    /**
     * Set project type.
     *
     * @param projectType project type
     */
    public void setProjectType(String[] projectType) {
        if (projectType == null) {
            this.projectType = null;
            return;
        }
        final List<ProjectType> projectTypeList = Arrays.stream(projectType)
                .filter(StringUtil::hasValue)
                .map(p -> {
                    ProjectType bean = new ProjectType();
                    bean.setProjectType(p);
                    return bean;
                })
                .collect(Collectors.toList());
        this.projectType = projectTypeList.isEmpty() ? null : projectTypeList;
    }

    /**
     * Acquires project classification.
     *
     * @return Project classification
     */
    public String[] getProjectClass() {

        if (projectClass == null) {
            return null;
        }
        return projectClass.stream()
                .map(ProjectClass::getProjectClass)
                .toArray(String[]::new);
    }

    /**
     * Set Project classification.
     *
     * @param projectClass Project classification.
     */
    public void setProjectClass(String[] projectClass) {
        if (projectClass == null) {
            this.projectClass = null;
            return;
        }
        final List<ProjectClass> projectClassList = Arrays.stream(projectClass)
                .filter(StringUtil::hasValue)
                .map(p -> {
                    ProjectClass bean = new ProjectClass();
                    bean.setProjectClass(p);
                    return bean;
                })
                .collect(Collectors.toList());
        this.projectClass = projectClassList.isEmpty() ? null : projectClassList;
    }

    /**
     * Acquires sales from.
     *
     * @return sales from
     */
    public String getSalesFrom() {
        return salesFrom;
    }

    /**
     * Set sales from.
     *
     * @param salesFrom sales from
     */
    public void setSalesFrom(String salesFrom) {
        this.salesFrom = salesFrom;
    }

    /**
     * Acquires sales to.
     *
     * @return sales to
     */
    public String getSalesTo() {
        return salesTo;
    }

    /**
     * Set sales to.
     *
     * @param salesTo sales to
     */
    public void setSalesTo(String salesTo) {
        this.salesTo = salesTo;
    }

    /**
     * Acquires start date from.
     *
     * @return start date from
     */
    public String getProjectStartDateFrom() {
        return projectStartDateFrom;
    }

    /**
     * Set start date from.
     *
     * @param projectStartDateFrom start date from.
     */
    public void setProjectStartDateFrom(String projectStartDateFrom) {
        this.projectStartDateFrom = projectStartDateFrom;
    }

    /**
     * Acquires start date to.
     *
     * @return start date to
     */
    public String getProjectStartDateTo() {
        return projectStartDateTo;
    }

    /**
     * Set start date to.
     *
     * @param projectStartDateTo start date to
     */
    public void setProjectStartDateTo(String projectStartDateTo) {
        this.projectStartDateTo = projectStartDateTo;
    }

    /**
     * Acquires end date from.
     *
     * @return end date from
     */
    public String getProjectEndDateFrom() {
        return projectEndDateFrom;
    }

    /**
     * Set end date from.
     *
     * @param projectEndDateFrom end date from
     */
    void setProjectEndDateFrom(String projectEndDateFrom) {
        this.projectEndDateFrom = projectEndDateFrom;
    }

    /**
     * Acquires end date to.
     *
     * @return end date to
     */
    public String getProjectEndDateTo() {
        return projectEndDateTo;
    }

    /**
     * Set end date to.
     *
     * @param projectEndDateTo end date to
     */
    public void setProjectEndDateTo(String projectEndDateTo) {
        this.projectEndDateTo = projectEndDateTo;
    }

    /**
     * Acquires project name.
     *
     * @return project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Set project name.
     *
     * @param projectName project name
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Determines if the FROM/TO for Sales_Actual is set correctly.
     * If the FROM is set to an amount greater than TO, it is not correct.
     * If not, it is not a problem.
     *
     * @return False if an amount of money larger than TO has been set in FROM, otherwise true (including the case where both and/or FROM are undecided).
     */
    @AssertTrue(message = "{validator.priceRange.message}")
    public boolean isValidProjectSalesRange() {
        return MoneyRelationUtil.isValid(salesFrom, salesTo);
    }

    /**
     * Determines if the project start date FROM/TO is set correctly.
     * If the FROM is set to a date later than TO, it is not correct.
     * If not, no problem.
     *
     * @return False if a date after TO has been set in FROM, otherwise true (including the case where both and/or FROM and TO are undecided).
     */
    @AssertTrue(message = "{validator.dateRange.message}")
    public boolean isValidProjectStartDateRange() {
        return DateRelationUtil.isValid(projectStartDateFrom, projectStartDateTo);
    }

    /**
     * Determines if the project end date FROM/TO is set correctly.
     * If the FROM is set to a date later than TO, it is not correct.
     * If not, no problem.
     *
     * @return False if a date after TO has been set in FROM, otherwise true (including the case where both and/or FROM and TO are undecided).
     */
    @AssertTrue(message = "{validator.dateRange.message}")
    public boolean isValidProjectEndDateRange() {
        return DateRelationUtil.isValid(projectEndDateFrom, projectEndDateTo);
    }

    /**
     * Project Type Bean
     */
    private static class ProjectType implements Serializable {
        /**
         * Project type
         */
        @Domain("projectType")
        private String projectType;

        /**
         * Acquires project type.
         *
         * @return Project type
         */
        public String getProjectType() {
            return projectType;
        }

        /**
         * Set project type.
         *
         * @param projectType Project type
         */
        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }
    }

    /**
     * Project Classification Bean
     */
    private static class ProjectClass implements Serializable {
        /**
         * Project classification
         */
        @Domain("projectClass")
        private String projectClass;

        /**
         * get project classification.
         *
         * @return project class
         */
        public String getProjectClass() {
            return projectClass;
        }

        /**
         * Acquires Project classification.
         *
         * @param projectClass project class
         */
        public void setProjectClass(String projectClass) {
            this.projectClass = projectClass;
        }
    }
}
