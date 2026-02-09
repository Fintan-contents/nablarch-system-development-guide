# CLAUDE.md - プロジェクト管理システム（Proman）開発ガイド

## プロジェクト概要

**Proman**（**Pro**ject **Man**agement System）は、Nablarchフレームワークを使用したプロジェクト管理システムです。

### モジュール構成

| モジュール名 | 役割 | 説明 |
|------------|------|------|
| `proman-common` | 共通モジュール | 基盤部品、Entity、ドメインクラス等のプロジェクト共有コンポーネント |
| `proman-web` | Webモジュール | Webアプリケーション（UIとAction） |
| `proman-batch` | バッチモジュール | バッチ処理（夜間処理等） |
| `proman-jmeter` | テストモジュール | 回帰テスト自動実行ツール |

### 技術スタック

- **Java**: 21
- **Framework**: Nablarch 6u2
- **Jakarta EE**: 10.0.0
- **Database**: PostgreSQL
- **Connection Pool**: HikariCP 3.3.1
- **Build Tool**: Maven
- **Testing**: JUnit 5, ArchUnit
- **Static Analysis**: Checkstyle, SpotBugs, FindSecBugs
- **Code Coverage**: Jacoco
- **CI/CD**: Jenkins

## 開発ルール

### 1. コーディング規約

#### 1.1 静的解析ツールの遵守

- **Checkstyle**: `tools/static-analysis/checkstyle/nablarch-checkstyle.xml`の設定に従う
- **SpotBugs**: バグパターンを検出し、必ず修正する
- **FindSecBugs**: セキュリティ脆弱性を検出し、必ず修正する
- **使用不許可APIチェック**: Nablarchの公開APIのみを使用する

#### 1.2 セキュリティ

以下の脆弱性を作り込まないこと：
- SQLインジェクション
- XSS（クロスサイトスクリプティング）
- コマンドインジェクション
- パストラバーサル
- CSRF（クロスサイトリクエストフォージェリ）

#### 1.3 コーディングスタイル

- Java標準のコーディング規約に準拠
- インデント：スペース4つ
- 改行コード：LF
- 文字コード：UTF-8
- 命名規則：
  - クラス名：PascalCase
  - メソッド名・変数名：camelCase
  - 定数：UPPER_SNAKE_CASE

### 2. ファイル配置規約

```
proman-project/
├── proman-common/          # 共通モジュール
│   ├── src/main/java/      # Javaソース
│   │   └── com/nablarch/example/proman/
│   │       ├── entity/     # Entityクラス（DB定義と対応）
│   │       ├── common/     # 共通ユーティリティ
│   │       └── ...
│   ├── src/main/resources/ # 設定ファイル・SQLファイル等
│   └── src/test/           # テストコード
├── proman-web/             # Webモジュール
│   ├── src/main/java/      # Javaソース
│   │   └── com/nablarch/example/proman/web/
│   │       ├── action/     # Actionクラス（Controller）
│   │       ├── form/       # Formクラス（入力検証）
│   │       └── ...
│   ├── src/main/webapp/    # JSP、HTML、CSS、JavaScript
│   └── src/test/           # テストコード
├── proman-batch/           # バッチモジュール
│   ├── src/main/java/      # Javaソース
│   └── src/test/           # テストコード
└── tools/                  # 静的解析設定ファイル
    └── static-analysis/
```

### 3. テスト方針

#### 3.1 単体テスト

- JUnit 5を使用
- テストカバレッジ：原則として全てのビジネスロジックをカバー
- Entityクラスはカバレッジ対象外（自動生成のため）
- テストクラス名：`<対象クラス名>Test`
- テストメソッド名：日本語可（例：`testログイン成功時の動作()`）

#### 3.2 ArchUnit

- アーキテクチャテストを実施
- レイヤー間の依存関係をテストで保証
- パッケージ構造の規約をテストで保証

#### 3.3 テストデータ

- `src/test/resources/data/`にCSV形式で配置
- テストクラス単位またはテストメソッド単位でデータを準備

### 4. データベース

#### 4.1 Entity

- `proman-common/src/main/java/com/nablarch/example/proman/entity/`に配置
- Gsp-dba-maven-pluginで自動生成
- **手動編集禁止**：スキーマ変更後に再生成する

#### 4.2 SQL

- SQLファイルは`src/main/resources/`配下に配置
- Nablarchの命名規則に従う
- 動的SQLはNablarchのSQL記述ルールに従う

### 5. Nablarch固有のルール

#### 5.1 Actionクラス（Web）

- `proman-web/src/main/java/com/nablarch/example/proman/web/action/`に配置
- クラス名：`<機能名>Action`
- メソッドは業務アクションメソッドとして実装
- `ExecutionContext`を引数に取る

#### 5.2 Formクラス

- `proman-web/src/main/java/com/nablarch/example/proman/web/form/`に配置
- Bean Validationアノテーションで入力検証を定義
- ドメインバリデーションとの組み合わせ

#### 5.3 バッチアクション

- `proman-batch/src/main/java/com/nablarch/example/proman/batch/`に配置
- `BatchAction`インターフェースを実装

### 6. Maven

#### 6.1 ビルドコマンド

```bash
# クリーン＆ビルド
mvn clean install

# 静的解析実行
mvn checkstyle:check spotbugs:check

# テスト実行
mvn test

# カバレッジレポート生成
mvn jacoco:report
```

#### 6.2 モジュール間依存

- `proman-web` → `proman-common`
- `proman-batch` → `proman-common`
- 循環依存禁止

### 7. バージョン管理（Git）

#### 7.1 コミットの単位

- **目的単位でコミットを分割する**
  - 1つのコミットには1つの目的（機能追加、バグ修正、リファクタリング等）のみを含める
  - 複数の異なる目的の変更を1つのコミットにまとめない
  - テストコードの追加・修正は対応する実装コードと同じコミットに含めてよい

#### 7.2 コミットメッセージ

- 簡潔かつ明確に記述する
- 何を変更したかではなく、なぜ変更したかを記述する
- 日本語で記述する
- 形式：`<種別>: <変更内容の概要>`
  - 種別の例：`feat`（機能追加）、`fix`（バグ修正）、`refactor`（リファクタリング）、`test`（テスト追加）、`docs`（ドキュメント）

#### 7.3 コミット前の確認

- 静的解析（Checkstyle、SpotBugs）が通ることを確認
- 単体テストが全て成功することを確認
- 不要なファイル（IDE設定ファイル、ビルド成果物等）を含めない

#### 7.4 プッシュ

- **コミット後は必ずプッシュする**
- 作業内容の喪失を防ぐため、コミットとプッシュは一連の操作として実施する
- リモートリポジトリに作業内容を常に保存することで安全性を確保する

## Claude作業時の注意事項

### 作業ディレクトリの制限

**重要**: `/home/tie303177/work/nablarch-system-development-guide/Sample_Project/Source_Code/proman-project` 配下のファイルのみを対象に作業すること。

- このディレクトリ外のファイルへのアクセス・読み込み・変更は禁止
- 親ディレクトリや他のプロジェクトへの移動は禁止
- 全てのファイルパスは `proman-project` をルートとして扱う

### 必須確認事項

1. **ファイル読み込み**: コードを変更する前に必ず対象ファイルを読み込んで現状を把握する
2. **静的解析**: コード変更後は静的解析ツールのチェックを通過させる
3. **テスト**: 既存テストを壊さないこと、必要に応じて新規テストを追加する
4. **セキュリティ**: OWASP Top 10の脆弱性を作り込まない

### 作業手順

1. **要件確認**: ユーザーの要求を正確に理解する（不明点は質問する）
2. **影響範囲調査**: 変更対象のファイルと依存関係を把握する
3. **実装**: Nablarchの規約に従って実装する
4. **テスト**: 単体テストを実行し、動作を確認する
5. **静的解析**: Checkstyle、SpotBugsを実行し、問題がないことを確認する

### 禁止事項

- Entityクラスの手動編集
- 静的解析エラーの放置
- テストなしでのコード変更
- セキュリティを考慮しないコーディング
- 過度なリファクタリング（要求されていない変更）
- 未使用の機能追加（YAGNI原則に反する）
- 不要なコメントやドキュメントの追加

### 推奨事項

- シンプルで保守性の高いコードを書く
- Nablarchのベストプラクティスに従う
- 既存コードのスタイルに合わせる
- コミットメッセージは簡潔かつ明確に

## 参考情報

- [Nablarch公式ドキュメント](https://nablarch.github.io/docs/LATEST/doc/)
- プロジェクト開発ガイド：`../../サンプルプロジェクト開発ガイド/`
- 静的解析設定：`tools/static-analysis/`
