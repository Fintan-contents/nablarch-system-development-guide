package com.nablarch.example.proman.web.project;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.validator.Required;

import java.io.Serializable;

/**
 * Project Details Form
 */
public class ProjectDetailInitialForm implements Serializable {
    /** Pproject ID */
    @Required
    @Domain("projectId")
    private String projectId;

    /**
     *  Acquires project ID.
     *
     * @return project ID
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     * Set project ID.
     *
     * @param projectId project ID
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
