# NTFアサーション・期待値検証

テスト結果と期待値の自動比較機能を提供する。データベース更新内容の確認、検索結果の確認、メッセージの確認、オブジェクトプロパティの確認など、多様なアサーション機能を提供する。

**assertion_types**:

- DBアサーション（更新結果、検索結果）
- ファイルアサーション
- ログアサーション
- メッセージアサーション
- プロパティアサーション
- HTMLダンプ出力

**related_files**:

- ntf-overview.json
- ntf-test-data.json
- ntf-batch-request-test.json

**公式ドキュメント**:
- [NTFアサーション・期待値検証](https://nablarch.github.io/docs/LATEST/doc/development_tools/testing_framework/guide/development_guide/06_TestFWGuide/02_DbAccessTest.html)
- [NTFアサーション・期待値検証](https://nablarch.github.io/docs/LATEST/doc/development_tools/testing_framework/guide/development_guide/06_TestFWGuide/02_RequestUnitTest.html)
- [NTFアサーション・期待値検証](https://nablarch.github.io/docs/LATEST/doc/development_tools/testing_framework/guide/development_guide/06_TestFWGuide/03_Tips.html)

---

## db_assertion

データベースの更新結果や検索結果を期待値と比較する機能

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `assertTableEquals` | `assertTableEquals(String sheetName)` | 指定されたシート内のデータタイプ"EXPECTED_TABLE"であるデータを全て比較する。データベースの更新結果が期待値と一致することを確認する。 |
| `assertTableEquals (with groupId)` | `assertTableEquals(String message, String sheetName, String groupId)` | グループIDを指定して、そのグループIDのデータのみをassert対象にする。複数のテストケースのデータを1つのシートに混在させる場合に使用。 |
| `assertSqlResultSetEquals` | `assertSqlResultSetEquals(String sheetName, String id, SqlResultSet actual)` | Excelに記載した期待値（LIST_MAP形式）と実際の検索結果（SqlResultSet）が等しいことを確認する。 |

**assertTableEquals**:

パラメータ:
- `sheetName` (String): 期待値を記載したExcelシート名

使い方: 更新系テストで使用。テスト対象メソッド実行後、commitTransactions()を呼び出してから本メソッドを実行する。

注意事項: 更新日付のようなjava.sql.Timestamp型のフォーマットは"yyyy-mm-dd hh:mm:ss.fffffffff"である（fffffffffはナノ秒）。ナノ秒が設定されていない場合でも、フォーマット上は0ナノ秒として表示される（例：2010-01-01 12:34:56.0）。Excelシートに期待値を記載する場合は、末尾の小数点＋ゼロを付与しておく必要がある。

比較ルール:
- 期待値の記述で省略されたカラムは、比較対象外となる
- 比較実行時、レコードの順番が異なっていても主キーを突合して正しく比較ができる
- 1シート内に複数のテーブルを記述できる

**assertTableEquals (with groupId)**:

パラメータ:
- `message` (String): アサート失敗時に表示するメッセージ
- `sheetName` (String): 期待値を記載したExcelシート名
- `groupId` (String): グループID

使い方: 1つのシートに複数テストケースのデータを記載する場合に使用。EXPECTED_TABLE[groupId]=テーブル名の形式で記述する。

**assertSqlResultSetEquals**:

パラメータ:
- `sheetName` (String): 期待値を記載したExcelシート名
- `id` (String): 期待値のID（LIST_MAPのID）
- `actual` (SqlResultSet): 実際の検索結果

使い方: 参照系テストで使用。テスト対象メソッドが返すSqlResultSetを期待値と比較する。

注意事項: SELECT実行時はORDER BY指定がなされる場合がほとんどであり、順序についても厳密に比較する必要がある為、レコードの順序が異なる場合はアサート失敗となる。

比較ルール:
- SELECT文で指定された全てのカラム名（別名）が比較対象になる。ある特定のカラムを比較対象外にすることはできない
- レコードの順序が異なる場合は、等価でないとみなす（アサート失敗）

---

## db_setup

データベースに準備データを登録する機能

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `setUpDb` | `setUpDb(String sheetName)` | 指定されたシート内のデータタイプ"SETUP_TABLE"全てをデータベースに登録する。 |
| `setUpDb (with groupId)` | `setUpDb(String sheetName, String groupId)` | グループIDを指定して、そのグループIDのデータのみをデータベースに登録する。 |

**setUpDb**:

パラメータ:
- `sheetName` (String): 準備データを記載したExcelシート名

使い方: テスト対象メソッド実行前に呼び出す。

注意事項:
- Excelファイルには必ずしも全カラムを記述する必要はない。省略されたカラムには、デフォルト値が設定される
- Excelファイルの1シート内に複数のテーブルを記述できる。setUpDb(String sheetName)実行時、指定されたシート内のデータタイプ"SETUP_TABLE"全てが登録対象となる

**setUpDb (with groupId)**:

パラメータ:
- `sheetName` (String): 準備データを記載したExcelシート名
- `groupId` (String): グループID

使い方: 1つのシートに複数テストケースのデータを記載する場合に使用。SETUP_TABLE[groupId]=テーブル名の形式で記述する。

---

## transaction_control

トランザクション制御機能。Nablarch Application Frameworkでは複数種類のトランザクションを併用することが前提となっているため、テスト対象クラス実行後にデータベースの内容を確認する際には、トランザクションをコミットしなければならない。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `beginTransactions` | `beginTransactions()` | トランザクションを開始する。DbAccessTestSupportを継承している場合、@Beforeメソッドで自動的に呼び出される。 |
| `commitTransactions` | `commitTransactions()` | トランザクションをコミットする。 ⚠️ コミットしない場合、テスト結果の確認が正常に行われない。 |
| `endTransactions` | `endTransactions()` | トランザクションを終了する。DbAccessTestSupportを継承している場合、@Afterメソッドで自動的に呼び出される。 |

**beginTransactions**:

使い方: 通常は明示的に呼び出す必要はない。

**commitTransactions**:

使い方: 更新系テストで、テスト対象メソッド実行後、データベースの内容を確認する前に呼び出す。

**endTransactions**:

使い方: 通常は明示的に呼び出す必要はない。

**important**: 更新系テストの場合、テスト対象クラス実行後にcommitTransactions()を呼び出してからassertTableEquals()を実行する必要がある。参照系テストの場合はコミットを行う必要はない。

**automatic_control**: DbAccessTestSupportを継承している場合、テストメソッド実行前にトランザクション開始、テストメソッド終了後にトランザクション終了が自動的に行われる。

---

## message_assertion

アプリケーション例外に格納されたメッセージIDを検証する機能（ウェブアプリケーションのリクエスト単体テストで使用）

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `assertApplicationMessageId` | `assertApplicationMessageId(String expectedCommaSeparated, ExecutionContext actual)` | アプリケーション例外に格納されたメッセージが想定通りであることを確認する。 |

**assertApplicationMessageId**:

パラメータ:
- `expectedCommaSeparated` (String): 期待するメッセージID（複数ある場合はカンマ区切りで指定）
- `actual` (ExecutionContext): テスト実行時に使用したExecutionContext

使い方: リクエスト単体テストで、アプリケーション例外が発生した場合のメッセージIDを確認する。

動作:
- 例外が発生しなかった場合や、アプリケーション例外以外の例外が発生した場合は、アサート失敗となる
- メッセージIDの比較はIDをソートした状態で行うので、テストデータを記載する際に順序を気にする必要はない

---

## property_assertion

オブジェクトのプロパティを期待値と比較する機能

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `assertObjectPropertyEquals` | `assertObjectPropertyEquals(String message, String sheetName, String id, Object actual)` | オブジェクトのプロパティの値がExcelファイルに記載したデータとなっていることを検証する。 |
| `assertObjectArrayPropertyEquals` | `assertObjectArrayPropertyEquals(String message, String sheetName, String id, Object[] actual)` | オブジェクト配列の各要素のプロパティの値がExcelファイルに記載したデータとなっていることを検証する。 |
| `assertObjectListPropertyEquals` | `assertObjectListPropertyEquals(String message, String sheetName, String id, List<?> actual)` | オブジェクトリストの各要素のプロパティの値がExcelファイルに記載したデータとなっていることを検証する。 |

**assertObjectPropertyEquals**:

パラメータ:
- `message` (String): エラー時に表示するメッセージ
- `sheetName` (String): 期待値を記載したExcelシート名
- `id` (String): 期待値のID（LIST_MAPのID）
- `actual` (Object): 検証対象のオブジェクト

使い方: Formオブジェクト、Entityオブジェクトなどのプロパティを検証する。Excelには、2行目にプロパティ名、3行目以降にプロパティの期待値を記述する。

**assertObjectArrayPropertyEquals**:

パラメータ:
- `message` (String): エラー時に表示するメッセージ
- `sheetName` (String): 期待値を記載したExcelシート名
- `id` (String): 期待値のID（LIST_MAPのID）
- `actual` (Object[]): 検証対象のオブジェクト配列

使い方: 複数のオブジェクトを配列で受け取る場合に使用。Excelには、2行目にプロパティ名、3行目以降に各オブジェクトのプロパティの期待値を記述する。

**assertObjectListPropertyEquals**:

パラメータ:
- `message` (String): エラー時に表示するメッセージ
- `sheetName` (String): 期待値を記載したExcelシート名
- `id` (String): 期待値のID（LIST_MAPのID）
- `actual` (List<?>): 検証対象のオブジェクトリスト

使い方: 複数のオブジェクトをリストで受け取る場合に使用。Excelには、2行目にプロパティ名、3行目以降に各オブジェクトのプロパティの期待値を記述する。

**excel_format**:

**description**: プロパティアサーション用のExcelデータ記述方法

**format**: LIST_MAP=<ID>
プロパティ名1  プロパティ名2  プロパティ名3
期待値1        期待値2        期待値3

**example**: LIST_MAP=expectedUsers
kanjiName  kanaName      mailAddress
漢字氏名   カナシメイ    test@anydomain.com

**notes**: プロパティ名はJavaBeansの命名規則に従う。複数のオブジェクトを検証する場合は、3行目以降に複数行記述する。

---

## html_dump

ウェブアプリケーションのリクエスト単体テストで、HTMLレスポンスをファイル出力する機能

**目的**: 画面レイアウトの確認、レビュー時の証跡として使用する。


**output_directory**:

**default**: ./tmp/html_dump

**structure**: テストクラス毎に同名のディレクトリが作成され、そのテストクラスで実行されたテストケース説明と同名のHTMLダンプファイルが出力される

**backup**: html_dumpディレクトリが既に存在する場合は、html_dump_bkという名前でバックアップされる

**html_resources**: HTMLダンプファイルが参照するHTMLリソース（スタイルシートや画像などのリソース）についてもこのディレクトリに出力される

**automatic_execution**: リクエスト単体テストを実行すると、内蔵サーバが起動されHTMLレスポンスが自動的にファイル出力される。

**configuration**:

- **property**: htmlDumpDir
- **description**: HTMLダンプファイルを出力するディレクトリを指定する
- **default**: ./tmp/html_dump
- **property**: dumpFileExtension
- **description**: ダンプファイルの拡張子
- **default**: html
- **property**: htmlResourcesExtensionList
- **description**: ダンプディレクトリへコピーされるHTMLリソースの拡張子
- **default**: css, jpg, js
- **property**: htmlResourcesCharset
- **description**: CSSファイル（スタイルシート）の文字コード
- **default**: UTF-8
- **property**: backup
- **description**: ダンプディレクトリのバックアップOn/Off
- **default**: true
- **property**: dumpVariableItem
- **description**: HTMLダンプファイル出力時に可変項目（JSESSIONID、2重サブミット防止用のトークン）を出力するか否かを設定する。前回実行結果と差異がないことを確認したい場合等は、falseに設定する。
- **default**: false

**notes**: 1リクエスト1画面遷移のシンクライアント型ウェブアプリケーションを対象としている。Ajaxやリッチクライアントを利用したアプリケーションの場合、HTMLダンプによるレイアウト確認は使用できない。

---
