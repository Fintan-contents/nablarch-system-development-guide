# Nablarch概要

**公式ドキュメント**:
- [Nablarch概要](https://fintan.jp/page/1868/4/)
- [Nablarch概要](https://nablarch.github.io/docs/LATEST/doc/)
- [Nablarch概要](https://nablarch.github.io/docs/LATEST/doc/about_nablarch/versionup_policy.html)
- [Nablarch概要](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/nablarch/architecture.html)
- [Nablarch概要](https://nablarch.github.io/docs/LATEST/doc/migration/index.html)
- [Nablarch概要](https://nablarch.github.io/docs/LATEST/doc/releases/index.html)

---

## identity

Nablarchは、TISの豊富な基幹システム構築経験から得られたナレッジを集約したJavaアプリケーション開発/実行基盤です。

**provider**: TIS株式会社

**license**: Apache License 2.0

**repository**: https://github.com/nablarch

**characteristics**:

- 金融・決済等のミッションクリティカルシステムでの豊富な導入実績
- 包括的なドキュメント（フレームワーク、開発ガイド、開発標準、ツール）
- 長期的な安定性と信頼性の重視
- アクティブなセキュリティ更新とメンテナンス
- 複数の実行環境をサポート（ウェブ、ウェブサービス、バッチ、メッセージング）

---

## versioning

**scheme**: メジャー.アップデート形式（例: 6u3）。プロダクトバージョン番号はマイナーバージョンアップ時にインクリメント、アップデート番号はリビジョンアップまたはバグフィックス時にインクリメントされる。

**current**:

**version**: 6u3

**release_date**: 2025年3月27日

**note**: Nablarch 6の最新アップデートリリース。Jakarta EE 10対応、Java 17以上が必要。

**active_versions**:

- 項目 1:
  **version**: 6u3

  **status**: 最新

  **java**: 17以上

  **ee**: Jakarta EE 10

  **maintenance**: アクティブ

- 項目 2:
  **version**: 5u26

  **release_date**: 2025年5月16日

  **status**: メンテナンス中

  **java**: 8以上（Java 11使用時は追加設定必要）

  **ee**: Java EE 7/8

  **maintenance**: セキュリティパッチと不具合対応


---

## requirements

**nablarch6**:

**java**: Java 17以上

**java_note**: Nablarch 6のモジュールはJava 17でコンパイルされているため、動作にはJava 17以上が必要

**ee**: Jakarta EE 10

**build_tool**: Maven 3.x以降

**namespace**: 名前空間がjavax.*からjakarta.*に変更

**nablarch5**:

**java**: Java 8以上

**java_note**: Java 11以上で使用する場合は追加設定が必要（詳細は移行ガイド参照）

**ee**: Java EE 7/8

**build_tool**: Maven

---

## compatibility

**policy**: フレームワークのバージョンアップは、公開APIに対して後方互換性を維持します。基本的にバージョンの差し替えと設定ファイルの変更のみでバージョンアップ可能です。

**public_api**:

**definition**: @Publishedアノテーションが付与されたAPIが公開API

**scope**: 公開APIのみ後方互換性を保証。非公開APIは後方互換性が維持されないバージョンアップを行う場合があるため、プロジェクトでは非公開APIを使用しないこと

**compatibility_scope**: フレームワーク（アプリケーションフレームワークとテスティングフレームワーク）のみが対象。ドキュメント、開発標準、ツールは後方互換性維持の対象外

**exceptions**:

- フレームワークが出力するログのレベル・文言に対する変更
- 後方互換を維持したまま修正できない不具合への対応
- JDKバージョンアップに起因する問題で後方互換を維持できない場合
- セキュリティ対応

**upgrade_process**: 使用するNablarchのバージョンの差し替えと設定ファイルの変更が基本。後方互換性が維持されない変更の場合はリリースノートに内容と移行方法を明記

---

## environment

Java実行環境があれば動作可能で、OS依存なし。

**app_servers**:

**nablarch6**:

- Jetty 12
- Jakarta EE 10対応APサーバ

**nablarch5**:

- Tomcat 8
- Jetty 6/9
- Java EE 7/8対応APサーバ

**databases**:

**embedded**: H2 Database（開発・テスト用）

**supported**: JDBCドライバが提供されるRDBMS全般

**verified**:

- Oracle Database
- PostgreSQL
- Microsoft SQL Server
- IBM DB2

**os**: Java実行環境があればOSを問わず動作（Windows、Linux、macOS等で動作確認済み）

---

## architecture

**overview**: Nablarchアプリケーションフレームワークは、ハンドラキュー、インターセプタ、ライブラリの3つの主要構成要素から成ります。

**handler_queue**:

**description**: リクエストやレスポンスに対する横断的な処理を行うハンドラ群を、予め定められた順序に沿って定義したキュー。サーブレットフィルタのチェーン実行と同様の方式で処理を実行

**responsibility**: リクエストのフィルタリング（アクセス権限制御等）、リクエスト・レスポンスの変換、リソースの取得・解放（データベース接続等）

**interceptor**:

**description**: 実行時に動的にハンドラキューに追加されるハンドラ。Jakarta EEのJakarta Contexts and Dependency Injectionで定義されているインターセプタと同じように処理を実行

**use_case**: 特定のリクエストの場合のみ処理を追加する場合や、リクエストごとに設定値を切り替えて処理を実行したい場合に適している

**library**:

**description**: データベースアクセス、ファイルアクセス、ログ出力など、ハンドラから呼び出されるコンポーネント群

**examples**:

- UniversalDao（データベースアクセス）
- データバインド
- ファイルアクセス
- ログ出力

**configuration**: コンポーネント設定はXMLファイルで行い、システムリポジトリで管理される

---

## processing-types

**type**: ウェブアプリケーション

**description**: Nablarchアプリケーションフレームワークを使用してウェブアプリケーションを開発するためのフレームワーク

**use_case**: 画面を持つエンドユーザー向けのウェブシステム開発

**type**: RESTfulウェブサービス

**description**: Jakarta RESTful Web Servicesで規定されているアノテーションを使用して容易にRESTfulウェブサービスを構築できるフレームワーク

**use_case**: 外部システム連携、API提供、マイクロサービスアーキテクチャでのサービス間通信

**type**: Nablarchバッチ（都度起動）

**description**: 日次や月次など、定期的にプロセスを起動してバッチ処理を実行する方式

**use_case**: 定期的なデータ処理、集計処理、レポート生成

**type**: Nablarchバッチ（常駐/テーブルキュー）

**description**: プロセスを起動しておき、一定間隔でバッチ処理を実行する方式。ただし新規開発ではdb_messagingの使用を推奨

**use_case**: オンライン処理で作成された要求データを定期的に一括処理する場合（既存システムでの使用を想定）

**type**: Jakarta Batch

**description**: Jakarta Batch（旧JSR352）に準拠したバッチアプリケーションフレームワーク。情報が少なく有識者のアサインが難しいため、新規開発ではNablarchバッチの使用を推奨

**use_case**: Jakarta Batch標準への準拠が必要な場合、既存Jakarta Batch資産の活用

**type**: メッセージング

**description**: MOM（Message Oriented Middleware）ベースとDBキューベースの2種類のメッセージングフレームワークを提供

**use_case**: 非同期処理、システム間の疎結合な連携、負荷分散された処理


---

## ecosystem

**official_contents**:

- 項目 1:
  **name**: Nablarch解説書

  **url**: https://nablarch.github.io/docs/LATEST/doc/

  **description**: Nablarchアプリケーションフレームワークの機能や使い方を詳細に解説した技術ドキュメント

  **target**: 開発者向け

- 項目 2:
  **name**: Nablarchシステム開発ガイド

  **url**: https://fintan.jp/page/252/

  **description**: Nablarchを使ってシステムを開発するエンジニアに対して、開発開始前・開発中にすべきこと、参照すべきものを示すガイド

  **target**: プロジェクト全体向け

- 項目 3:
  **name**: 開発標準

  **url**: https://fintan.jp/page/1868/#development-standards

  **description**: システム開発における成果物作成時に従うべきガイドライン。設計標準、設計書フォーマット・サンプルを含む

  **target**: プロジェクト全体向け

- 項目 4:
  **name**: 開発ツール

  **url**: https://nablarch.github.io/docs/LATEST/doc/development_tools/index.html

  **description**: 効率的なJava静的チェック、テスティングフレームワーク、アプリケーション開発時に使える便利なツール群

  **target**: 開発者向け

- 項目 5:
  **name**: トレーニングコンテンツ

  **url**: https://fintan.jp/page/1868/

  **description**: Nablarchの学習に役立つトレーニング資料や教育コンテンツ

  **target**: 学習者向け


---
