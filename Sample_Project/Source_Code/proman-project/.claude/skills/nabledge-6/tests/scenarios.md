# Test Scenarios for Nabledge-6 Skill

## Metadata

- **Version**: 1.1.0
- **Created**: 2026-02-09
- **Updated**: 2026-02-10
- **Total Scenarios**: 30 (5 per category)
- **Purpose**: Validate nabledge-6 skill workflows (keyword-search + section-judgement + code-analysis)

---

## Category 1: Handlers (5 scenarios)

### handlers-001: データリードハンドラの使い方

**Question**: データリードハンドラでファイルを読み込むにはどうすればいいですか？

**Expected Keywords**:
- DataReadHandler
- DataReader
- ファイル読み込み
- データ入力
- レコード処理

**Expected Sections**: overview, usage

**Knowledge File**: handlers/batch/data-read-handler.json

**Expected Relevance**: High

---

### handlers-002: トランザクションのロールバック

**Question**: トランザクション管理ハンドラでロールバックする方法は？

**Expected Keywords**:
- TransactionManagementHandler
- ロールバック
- rollback
- エラー処理
- トランザクション制御

**Expected Sections**: rollback, error-handling

**Knowledge File**: handlers/common/transaction-management-handler.json

**Expected Relevance**: High

---

### handlers-003: データベース接続管理の設定

**Question**: データベース接続管理ハンドラの設定方法を教えてください

**Expected Keywords**:
- DbConnectionManagementHandler
- データベース接続
- コネクション管理
- 設定
- コンポーネント定義

**Expected Sections**: configuration, setup

**Knowledge File**: handlers/common/db-connection-management-handler.json

**Expected Relevance**: High

---

### handlers-004: トランザクションのコミットタイミング

**Question**: トランザクションのコミットタイミングはいつですか？

**Expected Keywords**:
- コミット
- commit
- トランザクション
- タイミング
- 正常終了

**Expected Sections**: commit, lifecycle

**Knowledge File**: handlers/common/transaction-management-handler.json

**Expected Relevance**: High

---

### handlers-005: 大量データの処理

**Question**: データリードハンドラで大量データを処理するには？

**Expected Keywords**:
- 大量データ
- データ処理
- バッチ
- DataReader
- ループ処理

**Expected Sections**: large-data, performance

**Knowledge File**: handlers/batch/data-read-handler.json

**Expected Relevance**: High

---

## Category 2: Libraries (5 scenarios)

### libraries-001: ページング実装

**Question**: UniversalDaoでページングを実装したい

**Expected Keywords**:
- ページング
- paging
- per
- page
- Pagination
- EntityList

**Expected Sections**: paging

**Knowledge File**: libraries/universal-dao.json

**Expected Relevance**: High

---

### libraries-002: 楽観的ロック

**Question**: UniversalDaoで楽観的ロックを使う方法は？

**Expected Keywords**:
- 楽観的ロック
- @Version
- OptimisticLockException
- 排他制御
- バージョンカラム

**Expected Sections**: optimistic-lock

**Knowledge File**: libraries/universal-dao.json

**Expected Relevance**: High

---

### libraries-003: SQL実行

**Question**: データベースアクセスでSQLを実行する方法を教えてください

**Expected Keywords**:
- SQL実行
- Database
- SqlPStatement
- クエリ
- 検索

**Expected Sections**: sql-execution, query

**Knowledge File**: libraries/database-access.json

**Expected Relevance**: High

---

### libraries-004: ファイルパス取得

**Question**: ファイルパス管理でファイルパスを取得するには？

**Expected Keywords**:
- ファイルパス
- FilePathSetting
- 論理名
- 物理パス
- パス取得

**Expected Sections**: usage, configuration

**Knowledge File**: libraries/file-path-management.json

**Expected Relevance**: High

---

### libraries-005: 業務日付取得

**Question**: 業務日付を取得する方法は？

**Expected Keywords**:
- 業務日付
- SystemTimeUtil
- 日付取得
- システム日付

**Expected Sections**: overview, usage

**Knowledge File**: libraries/business-date.json

**Expected Relevance**: High

---

## Category 3: Tools (5 scenarios)

### tools-001: テストデータ準備

**Question**: NTFでテストデータを準備する方法を教えてください

**Expected Keywords**:
- テストデータ
- NTF
- データ準備
- Excel
- データベース

**Expected Sections**: preparation, setup

**Knowledge File**: tools/ntf-test-data.json

**Expected Relevance**: High

---

### tools-002: アサーション機能

**Question**: NTFのアサーション機能の使い方は？

**Expected Keywords**:
- アサーション
- 検証
- 期待値
- 実測値
- NTF

**Expected Sections**: assertion, verification

**Knowledge File**: tools/ntf-assertion.json

**Expected Relevance**: High

---

### tools-003: バッチのリクエスト単体テスト

**Question**: バッチのリクエスト単体テストを実行するには？

**Expected Keywords**:
- リクエスト単体テスト
- バッチテスト
- NTF
- テスト実行
- BatchRequestTestSupport

**Expected Sections**: test-execution, setup

**Knowledge File**: tools/ntf-batch-request-test.json

**Expected Relevance**: High

---

### tools-004: テストデータ初期化

**Question**: テストデータの初期化はどうやりますか？

**Expected Keywords**:
- 初期化
- データクリア
- セットアップ
- テストデータ
- 前処理

**Expected Sections**: initialization, cleanup

**Knowledge File**: tools/ntf-test-data.json

**Expected Relevance**: High

---

### tools-005: NTF基本的な使い方

**Question**: NTFの基本的な使い方を教えてください

**Expected Keywords**:
- NTF
- 自動テストフレームワーク
- テスト実行
- JUnit
- テストケース

**Expected Sections**: overview, getting-started

**Knowledge File**: tools/ntf-overview.json

**Expected Relevance**: High

---

## Category 4: Processing (5 scenarios)

### processing-001: バッチの基本構造

**Question**: Nablarchバッチの基本構造を教えてください

**Expected Keywords**:
- バッチ
- 基本構造
- アーキテクチャ
- ハンドラ構成
- 処理フロー

**Expected Sections**: overview, architecture

**Knowledge File**: processing/nablarch-batch.json

**Expected Relevance**: High

---

### processing-002: バッチアクション実装

**Question**: バッチアクションの実装方法は？

**Expected Keywords**:
- BatchAction
- アクション実装
- バッチ処理
- execute
- ビジネスロジック

**Expected Sections**: action-implementation, business-logic

**Knowledge File**: processing/nablarch-batch.json

**Expected Relevance**: High

---

### processing-003: 大量データ処理

**Question**: バッチで大量データを処理する方法は？

**Expected Keywords**:
- 大量データ
- データ処理
- ループ処理
- DataReader
- パフォーマンス

**Expected Sections**: large-data-processing, performance

**Knowledge File**: processing/nablarch-batch.json

**Expected Relevance**: High

---

### processing-004: バッチのエラーハンドリング

**Question**: バッチのエラーハンドリングはどうすればいいですか？

**Expected Keywords**:
- エラーハンドリング
- 例外処理
- エラー処理
- リトライ
- 異常終了

**Expected Sections**: error-handling, exception

**Knowledge File**: processing/nablarch-batch.json

**Expected Relevance**: High

---

### processing-005: バッチの起動方法

**Question**: バッチの起動方法を教えてください

**Expected Keywords**:
- バッチ起動
- Main
- コマンドライン
- 起動クラス
- 実行

**Expected Sections**: launch, execution

**Knowledge File**: processing/nablarch-batch.json

**Expected Relevance**: High

---

## Category 5: Adapters (5 scenarios)

### adapters-001: SLF4Jアダプタの設定

**Question**: SLF4Jアダプタの設定方法を教えてください

**Expected Keywords**:
- SLF4J
- アダプタ
- 設定
- ログ出力
- log4j

**Expected Sections**: configuration, setup

**Knowledge File**: adapters/slf4j-adapter.json

**Expected Relevance**: High

---

### adapters-002: ログレベルの変更

**Question**: SLF4Jでログレベルを変更するには？

**Expected Keywords**:
- ログレベル
- 設定変更
- DEBUG
- INFO
- ERROR

**Expected Sections**: log-level, configuration

**Knowledge File**: adapters/slf4j-adapter.json

**Expected Relevance**: High

---

### adapters-003: ログファイル出力

**Question**: SLF4Jでログファイルを出力する設定は？

**Expected Keywords**:
- ログファイル
- ファイル出力
- Appender
- 設定
- ログ出力先

**Expected Sections**: file-output, appender

**Knowledge File**: adapters/slf4j-adapter.json

**Expected Relevance**: High

---

### adapters-004: Nablarchとの連携

**Question**: NablarchとSLF4Jを連携させる方法は？

**Expected Keywords**:
- 連携
- 統合
- Nablarch
- SLF4J
- アダプタ

**Expected Sections**: integration, overview

**Knowledge File**: adapters/slf4j-adapter.json

**Expected Relevance**: High

---

### adapters-005: ログフォーマット変更

**Question**: SLF4Jでログフォーマットを変更するには？

**Expected Keywords**:
- ログフォーマット
- フォーマット設定
- 出力形式
- パターン
- カスタマイズ

**Expected Sections**: format, pattern

**Knowledge File**: adapters/slf4j-adapter.json

**Expected Relevance**: High

---

## Category 6: Code Analysis (5 scenarios)

### code-analysis-001: ProjectActionの構造理解

**Question**: ProjectActionの構造を理解したい

**Target Code**: proman-web/src/main/java/com/nablarch/example/proman/web/action/ProjectAction.java

**Expected Components**:
- ProjectAction (Action)
- ProjectForm (Form)
- Project (Entity)
- UniversalDao (Nablarch)

**Expected Knowledge**:
- libraries/universal-dao.json
- libraries/data-bind.json

**Expected Output Sections**: Overview, Architecture, Components, Flow, Nablarch Framework Usage

**Expected Relevance**: High

---

### code-analysis-002: proman-batchモジュール全体の理解

**Question**: proman-batchモジュール全体の構造を教えてください

**Target Code**: proman-batch

**Expected Components**:
- BatchAction (multiple)
- Entity classes
- Nablarch handlers

**Expected Knowledge**:
- processing/nablarch-batch.json
- handlers/batch/data-read-handler.json
- handlers/common/transaction-management-handler.json

**Expected Output Sections**: Overview, Architecture, Components

**Expected Relevance**: High

---

### code-analysis-003: Formクラスの設計パターン

**Question**: Formクラスの設計パターンを理解したい

**Target Code**: proman-web/src/main/java/com/nablarch/example/proman/web/form

**Expected Components**:
- Form classes (multiple)
- Bean Validation annotations
- Domain validation

**Expected Knowledge**:
- libraries/data-bind.json

**Expected Output Sections**: Overview, Components, Nablarch Framework Usage

**Expected Relevance**: High

---

### code-analysis-004: Entityクラスの設計理解

**Question**: Entityクラスの設計を理解したい

**Target Code**: proman-common/src/main/java/com/nablarch/example/proman/entity

**Expected Components**:
- Entity classes (multiple)
- Table annotations
- UniversalDao integration

**Expected Knowledge**:
- libraries/universal-dao.json

**Expected Output Sections**: Overview, Architecture, Components

**Expected Relevance**: High

---

### code-analysis-005: ログイン機能の詳細実装

**Question**: ログイン機能の実装を詳しく知りたい

**Target Code**: proman-web/src/main/java/com/nablarch/example/proman/web/action/LoginAction.java

**Expected Components**:
- LoginAction (Action)
- LoginForm (Form)
- SystemAccount (Entity)
- UniversalDao (Nablarch)
- Bean Validation (Nablarch)

**Expected Knowledge**:
- libraries/universal-dao.json
- libraries/data-bind.json
- libraries/database-access.json

**Expected Output Sections**: Overview, Architecture, Components, Flow, Nablarch Framework Usage

**Expected Relevance**: High

---

## Evaluation Criteria

### 1. Workflow Execution

ワークフローが正しく実行されたか確認：

- [ ] keyword-search workflowが実行された
- [ ] section-judgement workflowが実行された
- [ ] 適切なツール呼び出しが行われた（Read, Bash+jq）
- [ ] index.toonが読み込まれた
- [ ] 候補セクションのindexが抽出された
- [ ] セクション内容が読み込まれた

### 2. Keyword Matching

期待されるキーワードが回答に含まれているか：

- **Threshold**: 80%以上のキーワードが含まれている
- キーワードの出現確認
- 関連する技術用語の使用

### 3. Section Relevance

適切なセクションが特定されたか：

- [ ] expected_sectionsに含まれるセクションが特定された
- [ ] 関連性の高いセクション（High relevance）が優先された
- [ ] 不要なセクション（None relevance）が除外された

### 4. Knowledge File Only

知識ファイルのみを使って回答したか：

- [ ] LLM訓練データを使用していない
- [ ] 外部知識を補足していない
- [ ] 知識ファイルに記載されている情報のみを使用
- [ ] セクションの引用が明示されている

### 5. Token Efficiency

トークン効率が適切か：

- **Target**: 5,000-15,000 tokens per query
- 無駄なファイル全体の読み込みがない
- セクション単位の抽出が機能している

### 6. Tool Call Efficiency

ツール呼び出し回数が適切か：

- **Target**: 10-20 tool calls per query
- Read: index.toon読み込み (1回)
- Bash+jq: .index抽出 (5-10回)
- Bash+jq: .sections抽出 (5-10回)

### 7. Code Analysis Workflow (code-analysis scenarios only)

code-analysisワークフローが正しく実行されたか確認：

- [ ] 対象コードが正しく識別された
- [ ] 依存関係が適切に分析された（Read, Grep, Glob使用）
- [ ] 構成要素が適切に分解された
- [ ] 関連するNablarch知識が検索された（keyword-search workflow実行）
- [ ] ドキュメントが生成された（Write tool使用）
- [ ] Markdown + Mermaid図形式で出力された
- [ ] ソースコードへの相対パスリンクが含まれている
- [ ] Nablarch知識ファイルへのリンクが含まれている

### 8. Code Analysis Output Quality (code-analysis scenarios only)

出力ドキュメントの品質が適切か：

- [ ] Overview, Architecture, Components, Flow, Nablarch Framework Usageセクションが含まれている
- [ ] Mermaid図（依存関係図、シーケンス図）が適切に生成されている
- [ ] 構成要素の説明が明確である
- [ ] ソースコードへのリンクが正しい（相対パス）
- [ ] Nablarch知識の引用が適切である
- [ ] Expected Componentsが全て記載されている
- [ ] Expected Knowledgeが参照されている
- [ ] ファイルパス形式: `work/YYYYMMDD/code-analysis-<target>.md`

---

## Usage

### Manual Testing

各シナリオを個別にテスト：

```bash
# Example: Test handlers-001
"データリードハンドラでファイルを読み込むにはどうすればいいですか？"
```

期待される動作：
1. "keyword-searchワークフローを実行します"と表示
2. Read index.toon
3. Bash+jq でセクション抽出
4. 回答にキーワードが含まれる

### Automated Testing

エージェントを使った自動評価：

```bash
# Load scenarios.json
# For each scenario:
#   - Execute question
#   - Evaluate workflow execution
#   - Check keyword matching
#   - Verify section relevance
#   - Calculate token usage
#   - Count tool calls
```

---

## Notes

- すべてのシナリオは実在する知識ファイルに基づいています
- Expected sectionsは実際のファイル構造に基づく推定です
- 知識ファイルに該当セクションがない場合は、"この情報は知識ファイルに含まれていません"と回答されることが期待されます
