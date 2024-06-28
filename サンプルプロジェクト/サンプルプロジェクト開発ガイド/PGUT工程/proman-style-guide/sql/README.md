# SQLスタイルガイド

ここではSQLをコーディングする際に役立つコーディング規約を提供しています。
このコーディング規約は[Nablarch Application Frameworkのデータベースアクセス](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/database_management.html)を前提としています。
ただし規約の大部分はNablarchに依存していません。Nablarchに依存する箇所は以下に限定されます。

- 2.1.1. Nablarchアプリケーションフレームワークで拡張された機能を使用する場合の例外
- 2.2. SQL文のフォーマット
    - 「SQL_ID～「=」までは、単独行に記述する。」の一文のみNablarchを前提としている。それを除くとNablarch以外にも流用可能
- 2.2.1. SQLファイルの構成
- 4.7. 条件値や更新値に指定する値が固定の場合
    - 規約自体はNablarchに依存しないが例示したSQLでNablarch独自の名前付きバインドパラメータを利用している。例を修正することで流用可能
- 4.8. $if構文の注意点

他のフレームワークを利用される場合は上記項目を見直し利用するフレームワークに合わせたコーディング規約を作成してください。

- [SQLコーディング規約.docx](./SQLコーディング規約.docx?raw=true)
