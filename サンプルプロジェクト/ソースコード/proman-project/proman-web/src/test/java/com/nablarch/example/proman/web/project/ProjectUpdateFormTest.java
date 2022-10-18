package com.nablarch.example.proman.web.project;

import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;

import com.nablarch.example.proman.web.test.FormTestSupport;

/**
 * ProjectUpdateFormのテスト
 *
 */
class ProjectUpdateFormTest extends FormTestSupport<ProjectUpdateForm> {

    /**
     * 正常系（プロジェクト開始日＜プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriod() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/31");
        boolean result = (Boolean) doMethod(ProjectUpdateForm.class, "isValidProjectPeriod", form);
        assertTrue("プロジェクト開始日＜プロジェクト終了日", result);
    }

    /**
     * 正常系（プロジェクト開始日＝プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodEqual() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectUpdateForm.class, "isValidProjectPeriod", form);
        assertTrue("プロジェクト開始日＝プロジェクト終了日", result);
    }

    /**
     * 異常系（プロジェクト開始日＞プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodSmallerThanTo() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/31");
        form.setProjectEndDate("2022/01/01");
        boolean result = (Boolean) doMethod(ProjectUpdateForm.class, "isValidProjectPeriod", form);
        assertFalse("プロジェクト開始日＞プロジェクト終了日", result);
    }
}
