# アーキテクチャテストについて

このモジュールはアーキテクチャテストを行うためだけのモジュールです。

システムで使用する資材は配置しないでください。

proman-common, proman-web, proman-batchにあるすべてのクラスに対してテストを行うため、

proman-web, proman-batchのどちらにも依存しています。

## 実行方法

[MavenでのArchUnitのテスト実行方法](../../../サンプルプロジェクト開発ガイド/PGUT工程/proman-style-guide/java/staticanalysis/archunit/docs/Maven-settings.md)を参照してください。

## 注意事項

テストを実行する前に最新の内容を反映するため、proman-web, proman-batchそれぞれで `mvn install` を行ってください。
