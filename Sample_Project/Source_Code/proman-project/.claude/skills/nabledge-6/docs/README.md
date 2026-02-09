# Nabledge-6 知識ドキュメント

このディレクトリには、Nabledge-6の知識ファイル（JSON）から自動変換された人向けMarkdownファイルが格納されています。


### 概要

- [Nablarch概要](overview.md)

### 処理方式

- [Nablarchバッチ（都度起動型・常駐型）](features/processing/nablarch-batch.md)

### ライブラリ

- [業務日付の管理](features/libraries/business-date.md)
- [データバインド](features/libraries/data-bind.md)
- [データベースアクセス（JDBCラッパー）](features/libraries/database-access.md)
- [ファイルパス管理](features/libraries/file-path-management.md)
- [ユニバーサルDAO](features/libraries/universal-dao.md)

### ハンドラ

#### 共通ハンドラ

- [データベース接続管理ハンドラ](features/handlers/common/db-connection-management-handler.md)
- [トランザクション制御ハンドラ](features/handlers/common/transaction-management-handler.md)

#### バッチハンドラ

- [データリードハンドラ](features/handlers/batch/data-read-handler.md)

### ツール（NTF: Nablarch Testing Framework）

- [NTFアサーション・期待値検証](features/tools/ntf-assertion.md)
- [NTFバッチリクエスト単体テスト](features/tools/ntf-batch-request-test.md)
- [NTF（Nablarch Testing Framework）概要](features/tools/ntf-overview.md)
- [NTFテストデータ](features/tools/ntf-test-data.md)

### アダプタ

- [SLF4Jアダプタ](features/adapters/slf4j-adapter.md)

### チェック項目

- [セキュリティチェック項目](checks/security.md)

### リリースノート

- [リリースノート 6u3](releases/6u3.md)
