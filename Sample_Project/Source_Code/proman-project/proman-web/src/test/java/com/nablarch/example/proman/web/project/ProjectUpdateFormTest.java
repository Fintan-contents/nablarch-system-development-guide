package com.nablarch.example.proman.web.project;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * ProjectUpdateFormのテスト
 *
 */
class ProjectUpdateFormTest {

    /**
     * 正常系（プロジェクト開始日＜プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriod() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/31");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "プロジェクト開始日＜プロジェクト終了日");
    }

    /**
     * 正常系（プロジェクト開始日＝プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodEqual() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/01");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertTrue(result, "プロジェクト開始日＝プロジェクト終了日");
    }

    /**
     * 異常系（プロジェクト開始日＞プロジェクト終了日）
     */
    @Test
    void testIsValidProjectPeriodSmallerThanTo() {
        ProjectUpdateForm form = new ProjectUpdateForm();
        form.setProjectStartDate("2022/01/31");
        form.setProjectEndDate("2022/01/01");
        boolean result = form.isValidProjectPeriod();
        assertFalse(result, "プロジェクト開始日＞プロジェクト終了日");
    }
}
