package com.nablarch.example.proman.batch.service;

import com.nablarch.example.proman.batch.common.bean.ProjectDto;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * プロジェクト検索処理のユニットテスト
 *
 * @author TIS
 */
public class ProjectServiceTest {

    /**
     * プロジェクトのフォーマット処理が正常に呼び出し可能かテストをする。
     * <p>
     * 例外なく、対象項目のフォーマット結果が正しければ正常に呼び出されたと判定する。
     * </p>
     */
    @Test
    public void testFormatProjectForCsv() {
        ProjectDto project = new ProjectDto();
        project.setProjectStartDate("2018-05-01");
        project.setProjectEndDate("2018-05-31");

        // テスト対象実行
        ProjectService service = new ProjectService();
        ProjectDto result = service.formatProjectForCsv(project);

        // 取得レコードの項目を比較
        assertThat(result.getProjectStartDate(), is("2018/05/01"));
        assertThat(result.getProjectEndDate(), is("2018/05/31"));
    }
}
