# アーキテクチャテストについて

このモジュールはアーキテクチャテストを行うためだけのモジュールです。

システムで使用する資材は配置しないでください。

proman-common, proman-web, proman-batchにあるすべてのクラスに対してテストを行うため、

proman-web, proman-batchのどちらにも依存しています。

## 注意事項

テストを実行する前に最新の内容を反映するため、proman-web, proman-batchそれぞれで `mvn install` を行ってください。
