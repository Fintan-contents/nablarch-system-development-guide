# SpotBugsガイド

プロジェクトで[SpotBugs](http://spotbugs.readthedocs.io/ja/latest/index.html)を利用する際の資材を提供します。

SpotBugsとは、FindBugsの後継ツールです。
FindBugsは開発がストップしてしまいましたが、そのソースコードをフォークしたものにJava 8以降への対応をしたものがSpotBugsです。
SpotBugsは現在も活発に開発が続けられています。
Java 8以降を使用するプロジェクトはFindBugsではなくSpotBugsの利用を推奨します。

このリポジトリには、設定方法、運用ルール等のドキュメントが含まれます。
※このリポジトリで解説するSpotBugsにはプラグインとして[使用不許可APIチェックツール](../unpublished-api/README.md)を組み込んでいます。
使用不許可APIチェックツールを使用しない場合は、SpotBugsの設定から使用不許可APIチェックツールのプラグイン設定を削除し、ドキュメントから使用不許可APIチェックツールに関する記述を削除してください。

- [SpotBugs利用ガイド](./docs/README.md)
- サンプルプロジェクト (`spotbugs-example`ディレクトリを参照)
