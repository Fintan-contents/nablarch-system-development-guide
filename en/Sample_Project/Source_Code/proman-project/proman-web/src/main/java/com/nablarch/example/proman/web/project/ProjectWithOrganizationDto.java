package com.nablarch.example.proman.web.project;

import com.nablarch.example.proman.entity.Project;

/**
 * Project Search Results DTO
 */
public class ProjectWithOrganizationDto extends Project {
    /** Division name*/
    private String divisionName;
    /** Organization name */
    private String organizationName;

    /**
     * Acquires Division name.
     *
     * @return Division name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * Set Division name.
     *
     * @param divisionName Division name
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    /**
     * Acquires Organization name.
     *
     * @return Organization name 
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Set Organization name.
     *
     * @param organizationName Organization name 
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
