# サンプルプロジェクト開発ガイド

- [サンプルプロジェクト開発ガイド](#サンプルプロジェクト開発ガイド)
  - [開発の流れ](#開発の流れ)
  - [要件定義工程](#要件定義工程)
    - [要件定義者](#要件定義者)
      - [画面モックアップ作成](#画面モックアップ作成)
  - [設計工程](#設計工程)
    - [設計者](#設計者)
      - [設計（共通）](#設計共通)
      - [画面設計](#画面設計)
      - [Webサービス設計](#webサービス設計)
    - [データモデラ―](#データモデラ)
      - [データ定義](#データ定義)
      - [DB設計](#db設計)
  - [PGUT工程](#pgut工程)
    - [アプリケーションエンジニア](#アプリケーションエンジニア)
      - [アプリ実装（共通）](#アプリ実装共通)
      - [アプリ実装（Web）](#アプリ実装web)
      - [アプリ実装（バッチ）](#アプリ実装バッチ)
      - [アプリ実装（REST）](#アプリ実装rest)
      - [クラス単体テスト作成（共通）](#クラス単体テスト作成共通)
      - [クラス単体テスト作成・実施（Web）](#クラス単体テスト作成実施web)
      - [クラス単体テスト作成・実施（バッチ）](#クラス単体テスト作成実施バッチ)
      - [クラス単体テスト作成・実施（REST）](#クラス単体テスト作成実施rest)
      - [取引単体テスト実施（共通）](#取引単体テスト実施共通)
      - [取引単体テスト実施（Web）](#取引単体テスト実施web)
      - [取引単体テスト実施（バッチ）](#取引単体テスト実施バッチ)
  - [チーム開発環境](#チーム開発環境)

サンプルプロジェクトの開発の進め方を記載します。
サンプルプロジェクトの開発に携わる人は、この開発ガイドを熟読し、この開発ガイドに従い開発を進めて下さい。

この開発ガイドはアーキテクトが維持管理します。
この開発ガイドについて質問や疑問がある場合はアーキテクトに相談してください。

この開発ガイドは要件定義工程～PGUT工程を対象にしています。

## 開発の流れ

開発の流れは以下を参照してください。

- [Nablarchを使用した開発の流れ](../設計書/Nablarchを使用した開発の流れ.xlsx?raw=true)

工程とロールの組み合わせで作業内容と成果物を示しています。
自分が担当するロールについて作業内容と成果物を確認してください。

以降は開発の流れで示したロールとタスク毎にガイドを記載します。

## 要件定義工程

### 要件定義者

#### 画面モックアップ作成

- [画面モックアップの配置場所](../設計書/A1_プロジェクト管理システム/010_要件定義/020_画面モックアップ)
- [画面モックアップ作成ガイド](要件定義工程/画面モックアップ作成ガイド.md)
- [UI標準](../設計書/A1_プロジェクト管理システム/020_方式設計/020_開発標準/010_設計標準)
  - 画面モックアップ作成時はUI標準を参照してください。
- [設計書フォーマット](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)
  - 画面一覧と画面遷移図は画面モックアップ作成時に合わせて作成します。

## 設計工程

### 設計者

#### 設計（共通）

- 設計書の配置場所
  - [プロジェクト管理システムの設計書配置場所](../設計書/A1_プロジェクト管理システム/030_アプリ設計)
  - [顧客管理システムの設計書配置場所](../設計書/B1_顧客管理システム/030_アプリ設計)
- [設計書フォーマット](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)
  - Nablarch開発標準で提供されているフォーマットをそのまま使用します。
- [設計工程におけるテスト準備](設計工程/設計工程におけるテスト準備.md)（単体テストケース設計）
  - 設計工程でテストデータとテストケースを作成します。詳細は上のリンク先を参照してください。
- [SQLファイル作成](設計工程/SQLファイル作成.md)（SQL検証）
  - 設計工程でSQLを作成します。詳細は上のリンク先を参照してください。

#### 画面設計

- [スクリーンショットの取得方法](設計工程/スクリーンショットの取得方法.md)

#### Webサービス設計

- [WebAPIのURL設計](設計工程/WebAPIのURL設計.md)

### データモデラ―

#### データ定義

- [ドメイン定義の進め方](設計工程/ドメイン定義の進め方.md)
- [コード設計の進め方](設計工程/コード設計の進め方.md)

#### DB設計

- [DB設計標準](../設計書/A1_プロジェクト管理システム/020_方式設計/020_開発標準/010_設計標準)
  - 論理設計と物理設計の両方で参照してください。

## PGUT工程

### アプリケーションエンジニア

#### アプリ実装（共通）

- 進め方
  - [PG・UT作業の完了条件チェックリスト](PGUT工程/checklist/PG・UT作業の完了条件チェックリスト.xlsx?raw=true)
    - PG・UT作業の完了条件チェックリストの導入事例シートに従い作業してください。
  - [プログラマー向け成果物セルフチェックリスト](PGUT工程/checklist/プログラマー向け成果物セルフチェックリスト.xlsx?raw=true)
    - 導入事例シートの中で出てくるセルフチェックには上のリンク先のファイルを使用してください。
- ルール
  - [バージョン管理ルール](PGUT工程/バージョン管理ルール.md)
  - [プロジェクト・パッケージ構成](PGUT工程/pg/プロジェクト・パッケージ構成.md)
  - [コーディングに関する命名規約](PGUT工程/pg/コーディングに関する命名規約.md)
  - [静的解析チェック違反発生時の対応方法](PGUT工程/pg/静的解析チェック違反発生時の対応方法.md)
- 作業方法
  - [開発環境構築ガイド](PGUT工程/開発環境構築ガイド.md)
  - [コーディング規約のチェック方法](PGUT工程/pg/コーディング規約のチェック方法.md)
- 実装方法
  - [Serviceクラスの実装方法](PGUT工程/pg/Serviceクラスの実装方法.md)

#### アプリ実装（Web）

- [アプリケーション構成（Web）](設計工程/アプリケーション構成（Web）.md)
- [一般的な処理に関する実装方法（Web）](PGUT工程/pg/一般的な処理に関する実装方法（Web）.md)
- [エラー発生時のハンドリング方法（Web）](PGUT工程/pg/エラー発生時のハンドリング方法（Web）.md)

#### アプリ実装（バッチ）

- [アプリケーション構成（バッチ）](設計工程/アプリケーション構成（バッチ）.md)
- [一般的な処理に関する実装方法（バッチ）](PGUT工程/pg/一般的な処理に関する実装方法（バッチ）.md)

#### アプリ実装（REST）

- [アプリケーション構成（REST）](設計工程/アプリケーション構成（REST）.md)


#### クラス単体テスト作成（共通）

- 進め方
  - [PG・UT作業の完了条件チェックリスト](PGUT工程/checklist/PG・UT作業の完了条件チェックリスト.xlsx?raw=true)
  - [プログラマー向け成果物セルフチェックリスト](PGUT工程/checklist/プログラマー向け成果物セルフチェックリスト.xlsx?raw=true)
- ルール
  - [ユニットテストのJavadocに関する規約](PGUT工程/ut/ユニットテストのJavaDocに関する規約.md)

#### クラス単体テスト作成・実施（Web）

- [単体テストの考え方（Web）](PGUT工程/ut/単体テストの考え方（Web）.md)

#### クラス単体テスト作成・実施（バッチ）

- [単体テストの考え方（バッチ）](PGUT工程/ut/単体テストの考え方（バッチ）.md)

#### クラス単体テスト作成・実施（REST）

- [単体テストの考え方（REST）](PGUT工程/ut/単体テストの考え方（REST）.md)


#### 取引単体テスト実施（共通）

- 進め方
  - [PG・UT作業の完了条件チェックリスト](PGUT工程/checklist/PG・UT作業の完了条件チェックリスト.xlsx?raw=true)
- テスト方法
  - [エビデンスの取得方法（ログとDBダンプ）](PGUT工程/ut/エビデンスの取得方法（ログとDBダンプ）.md)

#### 取引単体テスト実施（Web）

- [単体テストの考え方（Web）](PGUT工程/ut/単体テストの考え方（Web）.md)
- [取引単体テストのテスト方法（Web）](PGUT工程/ut/取引単体テストのテスト方法（Web）.md) 
- [取引単体テストの自動実行方法（Web）](PGUT工程/ut/取引単体テストの自動実行方法（Web）.md) 

#### 取引単体テスト実施（バッチ）

- [プログラマー向け成果物セルフチェックリスト](PGUT工程/checklist/プログラマー向け成果物セルフチェックリスト.xlsx?raw=true)
- [単体テストの考え方（バッチ）](PGUT工程/ut/単体テストの考え方（バッチ）.md)
- [ユニットテストのJavadocに関する規約](PGUT工程/ut/ユニットテストのJavaDocに関する規約.md)


## チーム開発環境

> ＜サンプルプロジェクトを参考にされる方へ＞  
> サンプルプロジェクトのチーム開発環境（RedmineやJenkinsなど）は公開していないため、以下はリンクにしていません。
> 実際のプロジェクトでは各ツールへのリンクを張るようにして、開発メンバがすぐに各ツールへアクセスできるようにしておくことを推奨します。

- Redmine
  - タスク、課題、不具合の管理に使用します。
  - アーキチームへの改善要望や静的チェックの変更依頼等、チーム間の依頼を管理するのにも使用します。
- NEXUS
  - サードパーティーのJARやテスト工程でリリースされたアプリケーションを管理するのに使用します。
- Jenkins
  - CIに使用します。CIの内容は[CIの説明](開発環境/CIの説明.md)を参照してください。
- デモサーバ
  - デモ用にアプリケーションをデプロイするのに使用します。
- Rocket.Chat
  - チャットツール。
  - メンバー間の普段のコミュニケーションや、CIの結果通知を受け取るのに使用します。