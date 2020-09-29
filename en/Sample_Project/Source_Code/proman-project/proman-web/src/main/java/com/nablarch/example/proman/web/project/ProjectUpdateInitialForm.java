package com.nablarch.example.proman.web.project;

import nablarch.core.validation.ee.Domain;
import nablarch.core.validation.validator.Required;

import java.io.Serializable;

/**
 * Form from Project Details to Project Updates.
 */
public class ProjectUpdateInitialForm implements Serializable {
    /** Project ID */
    @Required
    @Domain("projectId")
    private String projectId;

    /**
     * Acquires project ID.
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
