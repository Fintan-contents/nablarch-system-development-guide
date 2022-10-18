package com.nablarch.example.proman.web.project;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import com.nablarch.example.proman.web.common.FormTestSupport;

/**
 * ProjectCreateFormのテスト
 */
class ProjectCreateFormTest extends FormTestSupport<ProjectCreateForm> {

    /**
     * 正常系（プロジェクト開始日＜プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriod() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/31");
        boolean result = (Boolean) doMethod(ProjectCreateForm.class, "isValidProjectPeriod", form);
        assertTrue("プロジェクト開始日＜プロジェクト終了日", result);
    }

    /**
     * 正常系（プロジェクト開始日＝プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodEqual() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectCreateForm.class, "isValidProjectPeriod", form);
        assertTrue("プロジェクト開始日＝プロジェクト終了日", result);
    }

    /**
     * 異常系（プロジェクト開始日＞プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodSmallerThanTo() {
        ProjectCreateForm form = new ProjectCreateForm();
        form.setProjectStartDate("2022/01/31");
        form.setProjectEndDate("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectCreateForm.class, "isValidProjectPeriod", form);
        assertFalse("プロジェクト開始日＞プロジェクト終了日", result);
    }
}
