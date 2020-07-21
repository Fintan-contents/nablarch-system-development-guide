# Sample Project Development Guide

- [Flow of development](#Flow-of-development)
- [Requirements definition phase](#Requirements-definition-phase)
  - [Requirements definer](#Requirements-definer)
    - [Screen mockup creation](#Screen-mockup-creation)
- [Design phase](#Design-phase)
  - [Designer](#Designer)
    - [Design (common)](#Design-common)
    - [Screen design](#Screen-design)
    - [Web service design](#Web-service-design)
  - [Data modeler](#Data-modeler)
    - [Data definition](#Data-definition)
    - [DB design](#DB-design)
- [PGUT phase](#PGUT-phase)
  - [Application engineer](#Application-engineer)
    - [PGUT (common)](#PGUT-common)
    - [PGUT（Web）](#PGUT-Web)
    - [PGUT (batch)）](#PGUT-batch)
    - [PGUT（REST）](#PGUT-REST)
- [Team development environment](#Team-development-environment)

Describes how to proceed with the development of the sample project.
If you are engaged in developing a sample project, please read this development guide carefully and proceed with development according to this development guide.

This development guide is maintained by the architect.
If you have any questions or doubts about this development guide, consult the architect.

This development guide covers from design to the PGUT phase.

## Flow of development

See below for the development flow.

- [Flow of development using Nablarch](../設計書/Nablarchを使用した開発の流れ.xlsx?raw=true)

Work contents and deliverables are shown by the combination of phase and role.
Check your work and deliverables for your role.

Guide is described for each role and task shown in the development flow.

## Requirements definition phase

### Requirements definer

#### Screen mockup creation

- [Placement location of screen mockup](../設計書/A1_プロジェクト管理システム/010_要件定義/020_画面モックアップ)
- [Screen mockup creation guide](要件定義工程/画面モックアップ作成ガイド.md)
- [UI standard](../設計書/A1_プロジェクト管理システム/020_方式設計/020_開発標準/010_設計標準)
  - Please refer to UI standard when creating screen mockups.
- [Design document format](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)
  - Screen list and screen transition diagram are created at the time of screen mockup creation.

## Design phase

### Designer

#### Design (common)

- [Placement location of design document](../設計書/A1_プロジェクト管理システム/030_アプリ設計)
- [Design document format](https://github.com/nablarch-development-standards/nablarch-development-standards/tree/master/030_%E8%A8%AD%E8%A8%88%E3%83%89%E3%82%AD%E3%83%A5%E3%83%A1%E3%83%B3%E3%83%88/010_%E3%83%95%E3%82%A9%E3%83%BC%E3%83%9E%E3%83%83%E3%83%88)
  - Use the format provided by the Nablarch development standard.
- [Test preparation in the design phase](設計工程/設計工程におけるテスト準備.md)（Unit test case design）
  - Create test data and test cases during the design phase. For more information, click the link above.
- [Create SQL file](設計工程/SQLファイル作成.md)（SQL validation）
  - Create SQL in the design phase. For more information, click the link above.

#### Screen design

- [Method to take a screenshot](設計工程/スクリーンショットの取得方法.md)

#### Web service design

- [WebAPI URL design](設計工程/WebAPIのURL設計.md)

### Data modeler

#### Data definition

- [Method to proceed with domain definition](設計工程/ドメイン定義の進め方.md)
- [Method to proceed with code design](設計工程/コード設計の進め方.md)

#### DB design

- [DB design standards](../設計書/A1_プロジェクト管理システム/020_方式設計/020_開発標準/010_設計標準)
  - Refer to both logical and physical designs.

## PGUT phase

### Application engineer

#### PGUT (common)

- Progress method
  - [PG/UT work completion condition checklist](PGUT工程/checklist/PG・UT作業の完了条件チェックリスト.xlsx?raw=true)
    - Please work according to the introduction example sheet of condition checklist for PG/UT work completion.
  - [Deliverable self-checklist for programmers](PGUT工程/checklist/プログラマー向け成果物セルフチェックリスト.xlsx?raw=true)
    - Use the files from the above links for the self-checks that in the case study sheet.
- Rules
  - [Version management rules](PGUT工程/バージョン管理ルール.md)
  - [Package configuration](PGUT工程/pg/パッケージ構成.md)
  - [Naming convention for coding](PGUT工程/pg/コーディングに関する命名規約.md)
  - [Conventions for unit test of JavaDoc](PGUT工程/ut/ユニットテストのJavaDocに関する規約.md)
  - [Response method when a static analysis check violation occurs](PGUT工程/pg/静的解析チェック違反発生時の対応方法.md)
- Working method
  - [Development environment construction guide](PGUT工程/開発環境構築ガイド.md)
  - [Method to check coding conventions](PGUT工程/pg/コーディング規約のチェック方法.md)
- Implementation method
  - [Method to implement service class](PGUT工程/pg/Serviceクラスの実装方法.md)
- Test method
  - [How to get evidence (log and DB dump)](PGUT工程/ut/エビデンスの取得方法（ログとDBダンプ）.md)

#### PGUT (Web)
- [Application configuration (Web)](設計工程/アプリケーション構成（Web）.md)
- [Unit test concept (Web)](PGUT工程/ut/単体テストの考え方（Web）.md)
- [Implementation method for general processing (Web)](PGUT工程/pg/一般的な処理に関する実装方法（Web）.md)
- [Implementation method to perform validation at any timing (Web)](PGUT工程/pg/バリデーションを任意のタイミングで行う実装方法（Web）.md)
- [Handling method when an error occurs (Web)](PGUT工程/pg/エラー発生時のハンドリング方法（Web）.md)
- [Test method of subfunction unit test (Web)](PGUT工程/ut/取引単体テストのテスト方法（Web）.md) 
- [How to get evidence (screen)](PGUT工程/ut/エビデンスの取得方法（画面）.md)

#### PGUT (batch)
- [Application configuration (batch)](設計工程/アプリケーション構成（バッチ）.md)
- [Unit test concept (batch)](PGUT工程/ut/単体テストの考え方（バッチ）.md)
- [Implementation method for general processing (batch)](PGUT工程/pg/一般的な処理に関する実装方法（バッチ）.md)

#### PGUT (REST)
- [Application Configuration (REST)](設計工程/アプリケーション構成（REST）.md)
- [Unit test concept (REST)](PGUT工程/ut/単体テストの考え方（REST）.md)

## Team development environment

> ＜For those referring to the sample project＞  
> Since the team development environment (Redmine, Jenkins, etc.) of the sample project is not disclosed, the following is not linked. 
> Links are provided to each tool so that development members can access it immediately.

- Redmine
  - It is used to manage tasks, issues and defects.
  - It is also used to manage requests between teams, such as improvement requests to the arche team and changes to static checks.
- NEXUS
  - Used to manage third-party JARs and applications released during the testing phase.
- Jenkins
  - This is used for CI. See [CI description](開発環境/CIの説明.md) for contents of CI.
- Demo server
  - This server is used to deploy the application for demonstration.
