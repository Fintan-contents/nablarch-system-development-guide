# Nablarch環境構築


## アーキタイプからのプロジェクト生成


[ブランクプロジェクト](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/blank_project/index.html)
を参照して、プロジェクトを生成します。



### マルチモジュール構成について

システム要件により、Webアプリケーションだけでなくバッチアプリケーションも必要となることも少なくありません。
しかし、現時点で用意されているアーキタイプは「ウェブプロジェクト」「Nablarchバッチプロジェクト」のように
処理方式毎のシングルモジュールプロジェクトになっています。
そのため、これら複数のプロジェクトをどのようにしてまとめるかを考える必要があります。

逆にEntityやDomainなどシステムで共通に使用されるクラスやリソースについては、ウェブプロジェクトやバッチプロジェクトで重複させず、
共通ライブラリとして抽出しておく必要があります。

統合の方法は以下の2種類が考えられます。

#### マルチモジュールプロジェクトに統合する

ウェブ、バッチ、共通モジュールのように、複数のモジュールから構成される[マルチモジュールプロジェクト](https://maven.apache.org/guides/mini/guide-multiple-modules.html)に統合します。
マルチモジュール化するために、生成後のアーキタイププロジェクトを手作業で統合します。


#### そのまま独立したプロジェクトとして扱う

ウェブプロジェクト、バッチプロジェクト、共通プロジェクトをそれぞれ独立したシングルモジュールプロジェクトとして扱います。
この場合、各プロジェクトは依存関係だけで関連付けを行い、親子関係はもたせません。



## コンポーネント設定

アーキタイプで生成されたプロジェクトには、最小限の設定しかされていません。
このため、必要な設定をコンポーネント設定ファイルに追記していきます。

### TODOを解消する

アーキタイプで生成されたプロジェクトには、TODOコメントが入っている箇所があります（JDBCの接続URLなど）。
TODOコメントに記載された指示に従って修正を行います。

### 機能を追加する

アーキタイプで生成されたプロジェクトでは、Nablarchの全ての機能が有効化されているわけではありません。
ドキュメントを参照し、必要な設定を追加していきます。
主に以下の箇所を参照します。

- [Nablarchの提供する標準ハンドラ](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/handlers/index.html)
- [Nablarchが提供するライブラリ](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/index.html)


## 静的解析ツールの導入

[Javaスタイルガイド](https://github.com/nablarch-development-standards/nablarch-style-guide/tree/master/java)配下に以下のチェックツールの導入手順があります。

- Checkstyle
- SpotBugs
- 使用不許可APIチェックツール
- ArchUnit

それぞれ実行方法が記載されていますので、それに沿ってMavenで実行できるように設定をしておきます。
（プロジェクト構成によってはパスの読み替えが必要になります）


