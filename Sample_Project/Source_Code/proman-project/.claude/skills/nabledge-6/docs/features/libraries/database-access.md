# データベースアクセス（JDBCラッパー）

JDBCを使用してデータベースに対してSQL文を実行する機能を提供する。UniversalDao内部でも使用されており、データベースアクセスには必須の機能。

**目的**: JDBCをラップし、安全で簡潔なデータベースアクセスを実現する。SQLインジェクション対策、動的SQL構築、ページングなどの機能を提供。


**モジュール**:
- `com.nablarch.framework:nablarch-core-jdbc`

**classes**:

- nablarch.core.db.connection.AppDbConnection

- nablarch.core.db.statement.SqlPStatement

- nablarch.core.db.statement.ParameterizedSqlPStatement

- nablarch.core.db.statement.SqlCStatement

- nablarch.core.db.statement.SqlRow

- nablarch.core.db.statement.SqlResultSet

- nablarch.core.db.connection.DbConnectionContext



**key_features**:

- SQLファイルによるSQL管理でSQLインジェクション脆弱性を排除
- データベース製品の方言（Dialect）を意識せずに開発可能
- Beanオブジェクトを使用した名前付きバインド変数
- 動的な条件構築（$if、in句、order by）
- like検索の自動エスケープ処理
- 検索結果のキャッシュ機能

**recommendation**: SQLの実行にはUniversalDaoの使用を推奨。JDBCラッパーは設定が必須で、UniversalDao内部でも使用される。

**公式ドキュメント**:
- [データベースアクセス（JDBCラッパー）](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/database/database.html)

---

## dialect

データベース製品ごとの違い（方言）を吸収するためのDialectインタフェースを提供。製品に対応したDialectを設定することで、方言を意識せずにアプリケーション実装が可能。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `supportsIdentity` | `boolean supportsIdentity()` | identityカラム（自動採番）を使用できるか否かを返す |
| `supportsIdentityWithBatchInsert` | `boolean supportsIdentityWithBatchInsert()` | identity（自動採番）カラムを持つテーブルに対してbatch insertが可能か否かを返す |
| `supportsSequence` | `boolean supportsSequence()` | シーケンスオブジェクトを使用できるか否かを返す |
| `supportsOffset` | `boolean supportsOffset()` | 検索クエリーの範囲指定でoffset（またはoffsetと同等の機能）を使用できるか否かを返す |
| `isDuplicateException` | `boolean isDuplicateException(SQLException sqlException)` | 一意制約違反を表すSQLExceptionか否かを判定する |
| `isTransactionTimeoutError` | `boolean isTransactionTimeoutError(SQLException sqlException)` | トランザクションタイムアウト対象のSQLExceptionか否かを判定する |
| `buildSequenceGeneratorSql` | `String buildSequenceGeneratorSql(String sequenceName)` | シーケンスオブジェクトから次の値を取得するSQL文を生成する |
| `getResultSetConvertor` | `ResultSetConvertor getResultSetConvertor()` | ResultSetから値を取得するResultSetConvertorを返す |
| `convertPaginationSql` | `String convertPaginationSql(String sql, SelectOption selectOption)` | 検索クエリーを範囲指定（ページング用）SQLに変換する |
| `convertCountSql` | `String convertCountSql(String sql)` | 検索クエリーを件数取得SQLに変換する |
| `getPingSql` | `String getPingSql()` | Connectionがデータベースに接続されているかチェックを行うSQLを返す |

**supportsIdentity**:

戻り値: 使用可能な場合true

**supportsIdentityWithBatchInsert**:

戻り値: 可能な場合true

**supportsSequence**:

戻り値: 使用可能な場合true

**supportsOffset**:

戻り値: 使用可能な場合true

**isDuplicateException**:

パラメータ:
- `sqlException` (java.sql.SQLException): 判定対象の例外

戻り値: 一意制約違反の場合true

**isTransactionTimeoutError**:

パラメータ:
- `sqlException` (java.sql.SQLException): 判定対象の例外

戻り値: トランザクションタイムアウトの場合true

**buildSequenceGeneratorSql**:

パラメータ:
- `sequenceName` (String): シーケンス名

戻り値: シーケンス値取得SQL

**getResultSetConvertor**:

戻り値: ResultSetConvertor実装

**convertPaginationSql**:

パラメータ:
- `sql` (String): 元のSQL
- `selectOption` (nablarch.core.db.statement.SelectOption): 範囲指定オプション

戻り値: 範囲指定SQL

**convertCountSql**:

パラメータ:
- `sql` (String): 元のSQL

戻り値: 件数取得SQL

**getPingSql**:

戻り値: 接続確認SQL

**classes**:

- nablarch.core.db.dialect.Dialect

- nablarch.core.db.dialect.DefaultDialect

- nablarch.core.db.dialect.OracleDialect

- nablarch.core.db.dialect.PostgreSQLDialect

- nablarch.core.db.dialect.DB2Dialect

- nablarch.core.db.dialect.SqlServerDialect

- nablarch.core.db.dialect.H2Dialect



**configuration_example**: dialectプロパティにデータベース製品対応のDialect実装クラスを設定する。例: OracleDialect、PostgreSQLDialect等。

**notes**: 設定しなかった場合はDefaultDialectが使用されるが、原則全ての機能が無効化されるため、必ずデータベース製品に対応したDialectを設定すること。

---

## sql_file

SQLはロジックに記述せず、SQLファイルに定義する。SQLファイルに記述することで、必ずPreparedStatementを使用するため、SQLインジェクションの脆弱性が排除できる。

**example**:

```java
-- ＸＸＸＸＸ取得SQL
-- SQL_ID:GET_XXXX_INFO
GET_XXXX_INFO =
select
   col1,
   col2
from
   test_table
where
   col1 = :col1
```

**file_rules**:

- クラスパス配下に作成する
- 1つのSQLファイルに複数のSQLを記述できるが、SQLIDはファイル内で一意とする
- SQLIDとSQLIDとの間には空行を挿入する（スペースが存在する行は空行とはみなさない）
- SQLIDとSQLとの間には = を入れる
- コメントは -- で記述する（ブロックコメントはサポートしない）
- SQLは改行やスペース（tab）などで整形してもよい

**sql_id_format**: SQLIDの#までがSQLファイル名、#以降がSQLファイル内のSQLIDとなる。例: jp.co.tis.sample.action.SampleAction#findUser → ファイル名: jp.co.tis.sample.action.SampleAction.sql、SQLID: findUser

**configuration_class**: nablarch.core.db.statement.BasicSqlLoader

**configuration_properties**:

- 項目 1:
  **name**: fileEncoding

  **type**: String

  **required**: False

  **default**: utf-8

  **description**: SQLファイルのエンコーディング

- 項目 2:
  **name**: extension

  **type**: String

  **required**: False

  **default**: sql

  **description**: SQLファイルの拡張子


---

## execute_sql

SQLIDを指定してSQLを実行する基本的な方法。DbConnectionContextからデータベース接続を取得し、prepareStatementBySqlIdでステートメントを生成して実行する。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `prepareStatementBySqlId` | `SqlPStatement prepareStatementBySqlId(String sqlId)` | SQLIDを元にステートメントを生成する |
| `retrieve` | `SqlResultSet retrieve()` | 検索処理を実行し、結果を返す |
| `executeUpdate` | `int executeUpdate()` | 更新系SQL（INSERT、UPDATE、DELETE）を実行する |
| `setLong` | `void setLong(int parameterIndex, long x)` | 指定されたパラメータインデックスにlong値を設定する |
| `setString` | `void setString(int parameterIndex, String x)` | 指定されたパラメータインデックスにString値を設定する |
| `setBytes` | `void setBytes(int parameterIndex, byte[] x)` | 指定されたパラメータインデックスにbyte配列を設定する |

**prepareStatementBySqlId**:

パラメータ:
- `sqlId` (String): SQLID（形式: パッケージ名.クラス名#SQLID）

戻り値: SqlPStatementオブジェクト

```java
SqlPStatement statement = connection.prepareStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#findUser");
statement.setLong(1, userId);
SqlResultSet result = statement.retrieve();
```

**retrieve**:

戻り値: SqlResultSetオブジェクト（検索結果）

**executeUpdate**:

戻り値: 更新件数

**setLong**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス（1始まり）
- `x` (long): 設定する値

**setString**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス（1始まり）
- `x` (String): 設定する値

**setBytes**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス（1始まり）
- `x` (byte[]): 設定する値

**usage_pattern**: AppDbConnection connection = DbConnectionContext.getConnection();
SqlPStatement statement = connection.prepareStatementBySqlId(sqlId);
// バインド変数設定
SqlResultSet result = statement.retrieve();

---

## input_bean

Beanオブジェクトのプロパティ値をSQLのINパラメータに自動的にバインドする機能。名前付きバインド変数を使用することで、インデクスの管理が不要となり、INパラメータの増減に強い実装が可能。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `prepareParameterizedSqlStatementBySqlId` | `ParameterizedSqlPStatement prepareParameterizedSqlStatementBySqlId(String sqlId)` | SQLIDを元にパラメータ化されたステートメントを生成する |
| `executeUpdateByObject` | `int executeUpdateByObject(Object object)` | Beanオブジェクトのプロパティ値をバインド変数に設定してSQL（更新系）を実行する |
| `retrieve` | `SqlResultSet retrieve(Object object)` | Beanオブジェクトのプロパティ値をバインド変数に設定してSQL（検索系）を実行する |

**prepareParameterizedSqlStatementBySqlId**:

パラメータ:
- `sqlId` (String): SQLID

戻り値: ParameterizedSqlPStatementオブジェクト

**executeUpdateByObject**:

パラメータ:
- `object` (Object): BeanオブジェクトまたはMap

戻り値: 更新件数

```java
UserEntity entity = new UserEntity();
entity.setId(1);
entity.setUserName("なまえ");

ParameterizedSqlPStatement statement = connection.prepareParameterizedSqlStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#insertUser");
int result = statement.executeUpdateByObject(entity);
```

**retrieve**:

パラメータ:
- `object` (Object): BeanオブジェクトまたはMap

戻り値: SqlResultSet（検索結果）

**bind_variable_format**: 名前付きバインド変数は :プロパティ名 の形式で記述する。例: :id、:userName

**sql_example**: insert into user (
  id,
  name
) values (
  :id,
  :userName
)

**notes**:

- BeanオブジェクトはBeanUtilを使用してMapに変換後に処理される
- Mapを指定した場合は、Mapのキー値と一致するINパラメータに対してMapの値が設定される
- BeanUtilで対応していない型がBeanのプロパティに存在した場合、そのプロパティは使用できない
- INパラメータをJDBC標準の?で記述した場合、Beanオブジェクトを入力としたSQL実行は動作しない

---

## paging

ウェブシステムの一覧検索画面などで使用するページング機能。検索結果の範囲を指定することで、特定の範囲のレコードのみを取得できる。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `SelectOption (constructor)` | `SelectOption(int offset, int limit)` | 検索範囲を指定するSelectOptionオブジェクトを生成する |
| `prepareStatementBySqlId (with SelectOption)` | `SqlPStatement prepareStatementBySqlId(String sqlId, SelectOption selectOption)` | SQLIDと検索範囲を指定してステートメントを生成する |

**SelectOption (constructor)**:

パラメータ:
- `offset` (int): 開始位置（1始まり）
- `limit` (int): 取得件数

**prepareStatementBySqlId (with SelectOption)**:

パラメータ:
- `sqlId` (String): SQLID
- `selectOption` (SelectOption): 検索範囲

戻り値: SqlPStatementオブジェクト

```java
SqlPStatement statement = connection.prepareStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#findUser", new SelectOption(11, 10));
SqlResultSet result = statement.retrieve();
```

**classes**:

- nablarch.core.db.statement.SelectOption



**notes**: 検索範囲が指定された場合、検索用のSQLを取得範囲指定のSQLに書き換えてから実行する。取得範囲指定のSQLはDialectにより生成される。

---

## like_search

like検索に対するescape句の挿入とワイルドカード文字のエスケープ処理を自動で行う機能。

**syntax_rules**:

- 前方一致: 名前付きパラメータの末尾に % を記述（例: name like :userName%）
- 後方一致: 名前付きパラメータの先頭に % を記述（例: name like :%userName）
- 途中一致: 名前付きパラメータの前後に % を記述（例: name like :%userName%）

**configuration_properties**:

- 項目 1:
  **name**: likeEscapeChar

  **type**: String

  **required**: False

  **default**: \

  **description**: like検索時のエスケープ文字

- 項目 2:
  **name**: likeEscapeTargetCharList

  **type**: String

  **required**: False

  **default**: %,_

  **description**: like検索時のエスケープ対象文字（カンマ区切り）


**example_sql**: select * from user where name like :userName%

**example_code**: UserEntity entity = new UserEntity();
entity.setUserName("な");

ParameterizedSqlPStatement statement = connection.prepareParameterizedSqlStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#findUserByName");
int result = statement.retrieve(bean);
// 実際の条件は name like 'な%' escape '\' となる

**notes**: エスケープ文字は自動的にエスケープ対象となるため、明示的にエスケープ対象文字に設定する必要はない。

---

## variable_condition

Beanオブジェクトの状態を元に、実行するSQL文を動的に組み立てる機能。条件の有無によって動的に条件を構築できる。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `prepareParameterizedSqlStatementBySqlId (with condition)` | `ParameterizedSqlPStatement prepareParameterizedSqlStatementBySqlId(String sqlId, Object condition)` | SQLIDと条件を持つBeanオブジェクトを指定してステートメントを生成する。Beanオブジェクトの状態を元にSQLの可変条件の組み立てが行われる。 |

**prepareParameterizedSqlStatementBySqlId (with condition)**:

パラメータ:
- `sqlId` (String): SQLID
- `condition` (Object): 条件を持つBeanオブジェクト

戻り値: ParameterizedSqlPStatementオブジェクト

**syntax**: $if(プロパティ名) {SQL文の条件}

**exclusion_rules**:

- 配列やCollectionの場合は、プロパティ値がnullやサイズ0の場合に条件が除外される
- 上記以外の型の場合は、プロパティ値がnullや空文字列（Stringオブジェクトの場合）の場合に条件が除外される

**constraints**:

- 使用できる箇所はwhere句のみ
- $if内に$ifを使用できない（ネスト不可）

**example_sql**: select
  user_id,
  user_name,
  user_kbn
from
  user
where
  $if (userName) {user_name like :userName%}
  and $if (userKbn) {user_kbn in ('1', '2')}
  and birthday = :birthday

**example_code**: UserEntity entity = new UserEntity();
entity.setUserName("なまえ");
// userKbnは設定しない（null）

ParameterizedSqlPStatement statement = connection.prepareParameterizedSqlStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#insertUser", entity);
// userKbnの条件は除外される
SqlResultSet result = statement.retrieve(entity);

---

## in_clause

in句の条件数が可変となるSQLを実行する機能。プロパティ値の要素数に応じてin句の条件が動的に構築される。

**syntax**: 条件の名前付きパラメータの末尾に [] を付加する。例: :userKbn[]

**property_type**: 配列またはjava.util.Collection（サブタイプ含む）

**example_sql**: select
  user_id,
  user_name,
  user_kbn
from
  user
where
  $if (userKbn) {user_kbn in (:userKbn[])}

**example_code**: UserSearchCondition condition = new UserSearchCondition();
condition.setUserKbn(Arrays.asList("1", "3"));

ParameterizedSqlPStatement statement = connection.prepareParameterizedSqlStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#searchUser", condition);
// 実行されるSQLの条件は userKbn in (?, ?) となる
SqlResultSet result = statement.retrieve(condition);

**notes**:

- in句の条件となるプロパティ値がnullやサイズ0となる場合には、該当条件は必ず可変条件（$if）として定義すること
- 可変条件としなかった場合でプロパティ値がnullの場合、条件が xxxx in (null) となるため、検索結果が正しく取得できない可能性がある
- in句は、条件式（カッコの中）を空にできないため、サイズ0の配列やnullが指定された場合には、条件式を in (null) とする仕様

---

## order_by

order byのソート項目を実行時に動的に切り替えてSQLを実行する機能。ソートIDに応じてorder by句が動的に構築される。

**syntax**: $sort(プロパティ名) {(ケース1)(ケース2)・・・(ケースn)}

**syntax_detail**:

- プロパティ名: BeanオブジェクトのソートIDを保持するプロパティ名
- ケース: order by句の切り替え候補。候補を一意に識別するソートIDとorder by句に指定する文字列（ケース本体）を記述
- デフォルトのケースには、ソートIDに default を指定する

**syntax_rules**:

- 各ケースは、ソートIDとケース本体を半角丸括弧で囲んで表現する
- ソートIDとケース本体は、半角スペースで区切る
- ソートIDには半角スペースを使用不可
- ケース本体には半角スペースを使用できる
- 括弧開き以降で最初に登場する文字列をソートIDとする
- ソートID以降で括弧閉じまでの間をケース本体とする
- ソートIDおよびケース本体はトリミングする

**example_sql**: select
  user_id,
  user_name
from
  user
where
  user_name = :userName
$sort(sortId) {
  (user_id_asc  user_id asc)
  (user_id_desc user_id desc)
  (name_asc     user_name asc)
  (name_desc    user_name desc)
  (default      user_id)
}

**example_code**: UserSearchCondition condition = new UserSearchCondition();
condition.setUserName("なまえ");
condition.setSortId("name_asc");

ParameterizedSqlPStatement statement = connection.prepareParameterizedSqlStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#searchUser", condition);
// order by句は order by user_name asc となる
SqlResultSet result = statement.retrieve(condition);

---

## auto_property

データ登録時や更新時に毎回設定する値をSQLの実行直前に自動的に設定する機能。登録日時や更新日時といった項目への自動設定に使用する。この機能はBeanオブジェクトを入力とする場合のみ有効。

**classes**:

- nablarch.core.db.statement.AutoPropertyHandler

- nablarch.core.db.statement.autoproperty.CurrentDateTime

- nablarch.core.db.statement.autoproperty.UserId

- nablarch.core.db.statement.autoproperty.RequestId



**annotations**:

- @CurrentDateTime
- @UserId
- @RequestId

**configuration_property**: updatePreHookObjectHandlerList

**configuration_class**: nablarch.core.db.statement.BasicStatementFactory

**example_entity**: public class UserEntity {
  private String id;

  @CurrentDateTime
  private Timestamp createdAt;  // 登録時に自動設定

  @CurrentDateTime
  private String updatedAt;  // 登録・更新時に自動設定
}

**example_sql**: insert into user (
  id,
  createdAt,
  updatedAt
) values (
  :id,
  :createdAt,
  :updatedAt
)

**example_code**: UserEntity entity = new UserEntity();
entity.setId(1);
// createdAtとupdatedAtには値を設定する必要はない

ParameterizedSqlPStatement statement = connection.prepareParameterizedSqlStatementBySqlId(
    "jp.co.tis.sample.action.SampleAction#insertUser");
int result = statement.executeUpdateByObject(entity);
// 自動設定項目に値が設定される

**notes**: 値を明示的に設定したとしても、SQL実行直前に値の自動設定機能により上書きされる。

---

## binary_column

blob（データベース製品によりバイナリ型の型は異なる）などのバイナリ型のカラムへのアクセス方法。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `getBytes` | `byte[] getBytes(String columnName)` | バイナリ型のカラムの値をbyte配列として取得する |
| `setBytes` | `void setBytes(int parameterIndex, byte[] x)` | サイズの小さいバイナリ値を登録・更新する |
| `setBinaryStream` | `void setBinaryStream(int parameterIndex, InputStream x, int length)` | サイズが大きいバイナリ値をストリームから登録更新する |

**getBytes**:

パラメータ:
- `columnName` (String): カラム名

戻り値: byte配列

```java
SqlResultSet rows = statement.retrieve();
SqlRow row = rows.get(0);
byte[] encryptedPassword = row.getBytes("password");
```

**setBytes**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス
- `x` (byte[]): 設定する値

```java
SqlPStatement statement = getSqlPStatement("UPDATE_PASSWORD");
statement.setBytes(1, new byte[] {0x30, 0x31, 0x32});
int updateCount = statement.executeUpdate();
```

**setBinaryStream**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス
- `x` (java.io.InputStream): 入力ストリーム
- `length` (int): データサイズ

```java
final Path pdf = Paths.get("input.pdf");
try (InputStream input = Files.newInputStream(pdf)) {
    statement.setBinaryStream(1, input, (int) Files.size(pdf));
}
```

**notes**:

- getBytesを使用した場合、カラムの内容が全てJavaのヒープ上に展開される
- 非常に大きいサイズのデータを読み込んだ場合、ヒープ領域を圧迫し、システムダウンなどの障害の原因となる
- 大量データを読み込む場合には、Blobオブジェクトを使用して、ヒープを大量に消費しないようにすること

**large_data_example**: SqlResultSet rows = select.retrieve();
Blob pdf = (Blob) rows.get(0).get("PDF");
try (InputStream input = pdf.getBinaryStream()) {
  // InputStreamからデータを順次読み込み処理を行う
}

---

## clob_column

CLOBのような大きいサイズの文字列型カラムへのアクセス方法。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `getString` | `String getString(String columnName)` | CLOB型のカラムの値をString型として取得する |
| `setString` | `void setString(int parameterIndex, String x)` | サイズが小さい値を登録更新する |
| `setCharacterStream` | `void setCharacterStream(int parameterIndex, Reader reader, int length)` | サイズが大きい値をReaderから登録・更新する |

**getString**:

パラメータ:
- `columnName` (String): カラム名

戻り値: String値

```java
SqlResultSet rows = statement.retrieve();
SqlRow row = rows.get(0);
String mailBody = row.getString("mailBody");
```

**setString**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス
- `x` (String): 設定する値

```java
statement.setString(1, "値");
statement.executeUpdate();
```

**setCharacterStream**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス
- `reader` (java.io.Reader): Readerオブジェクト
- `length` (int): データサイズ

```java
Path path = Paths.get(filePath);
try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
  statement.setCharacterStream(1, reader, (int) Files.size(path));
}
```

**notes**:

- getStringを使用した場合、カラムの内容が全てJavaのヒープ上に展開される
- 非常に大きいサイズのデータを読み込んだ場合、ヒープ領域を圧迫し、システムダウンなどの障害の原因となる
- 大量データを読み込む場合には、Clobオブジェクトを使用して、ヒープを大量に消費しないようにすること

**large_data_example**: SqlResultSet rows = select.retrieve();
Clob mailBody = (Clob) rows.get(0).get("mailBody");
try (Reader reader = mailBody.getCharacterStream()) {
  // Readerからデータを順次読み込み処理を行う
}

---

## stored_procedure

ストアードプロシージャを実行する機能。基本的にはSQLを実行する場合と同じように実装するが、Beanオブジェクトを使用した実行（名前付きバインド変数）はサポートしない。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `prepareCallBySqlId` | `SqlCStatement prepareCallBySqlId(String sqlId)` | SQLIDを元にストアードプロシージャ実行用のステートメントを生成する |
| `registerOutParameter` | `void registerOutParameter(int parameterIndex, int sqlType)` | OUTパラメータを登録する |
| `execute` | `boolean execute()` | ストアードプロシージャを実行する |

**prepareCallBySqlId**:

パラメータ:
- `sqlId` (String): SQLID

戻り値: SqlCStatementオブジェクト

```java
SqlCStatement statement = connection.prepareCallBySqlId(
    "jp.co.tis.sample.action.SampleAction#execute_sp");
statement.registerOutParameter(1, Types.CHAR);
statement.execute();
String result = statement.getString(1);
```

**registerOutParameter**:

パラメータ:
- `parameterIndex` (int): パラメータインデックス
- `sqlType` (int): SQL型（java.sql.Types）

**execute**:

戻り値: 結果が存在する場合true

**classes**:

- nablarch.core.db.statement.SqlCStatement



**notes**: ストアードプロシージャを使用した場合、ロジックがJavaとストアードプロシージャに分散してしまい、保守性を著しく低下させるため原則使用すべきではない。ただし、既存の資産などでどうしても使用しなければならないケースが想定されるため、非常に簡易的ではあるがAPIを提供している。

---

## separate_transaction

データベース接続管理ハンドラ及びトランザクション制御ハンドラで開始したトランザクションではなく、個別のトランザクションを使用してデータベースアクセスを行う機能。業務処理が失敗した場合でも必ずデータベースへの変更を確定したい場合などに使用する。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `doTransaction` | `T doTransaction()` | トランザクション内で処理を実行する。SimpleDbTransactionExecutorを継承してexecuteメソッドを実装し、doTransactionメソッドを呼び出す。 |

**doTransaction**:

戻り値: executeメソッドの戻り値

```java
SimpleDbTransactionManager dbTransactionManager =
    SystemRepository.get("update-login-failed-count-transaction");

SqlResultSet resultSet = new SimpleDbTransactionExecutor<SqlResultSet>(dbTransactionManager) {
  @Override
  public SqlResultSet execute(AppDbConnection connection) {
    SqlPStatement statement = connection.prepareStatementBySqlId(
        "jp.co.tis.sample.action.SampleAction#findUser");
    statement.setLong(1, userId);
    return statement.retrieve();
  }
}.doTransaction();
```

**classes**:

- nablarch.core.db.transaction.SimpleDbTransactionManager

- nablarch.core.db.transaction.SimpleDbTransactionExecutor



**configuration_properties**:

- 項目 1:
  **name**: connectionFactory

  **type**: nablarch.core.db.connection.ConnectionFactory

  **required**: True

  **description**: データベース接続を取得するConnectionFactory実装クラス

- 項目 2:
  **name**: transactionFactory

  **type**: nablarch.core.transaction.TransactionFactory

  **required**: True

  **description**: トランザクションを管理するTransactionFactory実装クラス

- 項目 3:
  **name**: dbTransactionName

  **type**: String

  **required**: True

  **description**: トランザクションを識別するための名前


**configuration_example**: <component name="update-login-failed-count-transaction" class="nablarch.core.db.transaction.SimpleDbTransactionManager">
  <property name="connectionFactory" ref="connectionFactory" />
  <property name="transactionFactory" ref="transactionFactory" />
  <property name="dbTransactionName" value="update-login-failed-count-transaction" />
</component>

---

## cache

実行したSQLと外部から取得した条件（バインド変数に設定した値）が等価である場合に、データベースにアクセスせずにキャッシュから検索結果を返却する機能。データベースの負荷を軽減させるために使用する。

**classes**:

- nablarch.core.db.cache.InMemoryResultSetCache

- nablarch.core.db.cache.statement.CacheableStatementFactory

- nablarch.core.cache.expirable.BasicExpirationSetting



**use_cases**:

- 売り上げランキングのように結果が厳密に最新である必要が無く大量に参照されるデータ
- データ更新タイミングが夜間のみで日中は更新されないデータ

**configuration_properties**:

- 項目 1:
  **name**: cacheSize

  **type**: int

  **required**: False

  **description**: キャッシュサイズ（InMemoryResultSetCache）

- 項目 2:
  **name**: expiration

  **type**: Map<String, String>

  **required**: True

  **description**: SQLID毎のキャッシュ有効期限。keyにSQLID、valueに有効期限を設定（BasicExpirationSetting）。単位: ms（ミリ秒）、sec（秒）、min（分）、h（時）

- 項目 3:
  **name**: expirationSetting

  **type**: nablarch.core.cache.expirable.ExpirationSetting

  **required**: True

  **description**: 有効期限設定（CacheableStatementFactory）

- 項目 4:
  **name**: resultSetCache

  **type**: nablarch.core.db.cache.ResultSetCache

  **required**: True

  **description**: キャッシュ実装（CacheableStatementFactory）


**configuration_example**: <component name="resultSetCache" class="nablarch.core.db.cache.InMemoryResultSetCache">
  <property name="cacheSize" value="100"/>
</component>

<component name="expirationSetting" class="nablarch.core.cache.expirable.BasicExpirationSetting">
  <property name="expiration">
    <map>
      <entry key="please.change.me.tutorial.ss11AA.W11AA01Action#SELECT" value="100ms"/>
      <entry key="please.change.me.tutorial.ss11AA.W11AA02Action#SELECT" value="30sec"/>
    </map>
  </property>
</component>

<component name="cacheableStatementFactory" class="nablarch.core.db.cache.CacheableStatementFactory">
  <property name="expirationSetting" ref="expirationSetting"/>
  <property name="resultSetCache" ref="resultSetCache"/>
</component>

**notes**:

- この機能は、参照系のデータベースアクセスを省略可能な場合に省略し、システム負荷を軽減することを目的としており、データベースアクセス（SQL）の高速化を目的としているものではない
- この機能は、データベースの値の更新を監視してキャッシュの最新化を行うことはない。常に最新のデータを表示する必要がある機能では使用しないこと

---

## schema_replacement

SQL文中のスキーマを環境毎に切り替える機能。環境によって参照したいスキーマ名が異なるケースで使用する。

**classes**:

- nablarch.core.db.statement.sqlloader.SchemaReplacer



**placeholder**: #SCHEMA#

**configuration_properties**:

- 項目 1:
  **name**: schemaName

  **type**: String

  **required**: True

  **description**: プレースホルダー #SCHEMA# を置き換える値


**configuration_example**: <component name="statementFactory" class="nablarch.core.db.statement.BasicStatementFactory">
  <property name="sqlLoader">
    <component name="sqlLoader" class="nablarch.core.db.statement.BasicSqlLoader">
      <property name="sqlLoaderCallback">
        <list>
          <component class="nablarch.core.db.statement.sqlloader.SchemaReplacer">
            <property name="schemaName" value="${nablarch.schemaReplacer.schemaName}"/>
          </component>
        </list>
      </property>
    </component>
  </property>
</component>

**sql_example**: -- スキーマ名を指定してSELECT
SELECT * FROM #SCHEMA#.TABLE1

**notes**: 本機能によるSQL文中のスキーマ置き換えは単純な文字列置換処理であり、スキーマが存在するか、スキーマ置き換え後のSQLが妥当であるかといったチェックは行われない（SQL文実行時にエラーとなる）。

---

## configuration

**classes**:

- nablarch.core.db.connection.BasicDbConnectionFactoryForDataSource

- nablarch.core.db.connection.BasicDbConnectionFactoryForJndi

- nablarch.core.db.statement.BasicStatementFactory

- nablarch.core.db.statement.BasicSqlLoader



**connection_methods**:

- javax.sql.DataSourceを使ったデータベース接続の生成（BasicDbConnectionFactoryForDataSource）
- アプリケーションサーバなどに登録されたデータソースを使ったデータベース接続の生成（BasicDbConnectionFactoryForJndi）

**configuration_example_datasource**: <component class="nablarch.core.db.connection.BasicDbConnectionFactoryForDataSource">
  <!-- 設定値の詳細はJavadocを参照すること -->
</component>

**configuration_example_jndi**: <component class="nablarch.core.db.connection.BasicDbConnectionFactoryForJndi">
  <!-- 設定値の詳細はJavadocを参照すること -->
</component>

**statement_factory_example**: <component name="statementFactory" class="nablarch.core.db.statement.BasicStatementFactory">
  <property name="sqlLoader">
    <component class="nablarch.core.db.statement.BasicSqlLoader">
      <property name="fileEncoding" value="utf-8"/>
      <property name="extension" value="sql"/>
    </component>
  </property>
</component>

**notes**:

- 上記に設定したクラスを直接使用することは基本的にない。データベースアクセスを必要とする場合には、データベース接続管理ハンドラを使用すること
- データベースを使用する場合はトランザクション管理も必要となる

---

## exceptions

**exception_types**:

- **exception**: nablarch.core.db.DbAccessException
- **cause**: データベースアクセス時に発生する例外
- **description**: データベースアクセス時の一般的なエラー
- 項目 2:
  **exception**: nablarch.core.db.connection.exception.DbConnectionException

  **cause**: データベース接続エラーを示す例外

  **description**: データベースアクセスエラー時の例外がデータベース接続エラーを示す場合に送出される。retry_handlerにより処理される。

  **solution**: retry_handler未適用の場合には、実行時例外として扱われる

- **exception**: nablarch.core.db.statement.exception.SqlStatementException
- **cause**: SQLの実行に失敗した時に発生する例外
- **description**: SQL実行時の一般的なエラー
- 項目 4:
  **exception**: nablarch.core.db.statement.exception.DuplicateStatementException

  **cause**: 一意制約違反を示す例外

  **description**: SQL実行時の例外が一意制約違反を示す場合に送出される。一意制約違反の判定にはDialectが使用される。

  **solution**: try-catchで補足して処理する。データベース製品によってはSQL実行時に例外が発生した場合に、ロールバックを行うまで一切のSQLを受け付けないものがあるので注意。


**notes**:

- これらの例外は全て非チェック例外のため、SQLExceptionのようにtry-catchで補足する必要はない
- データベース接続エラーの判定には、Dialectが使用される
- 一意制約違反の判定には、Dialectが使用される

---

## anti-patterns

| パターン | 理由 | 正しい方法 |
|----------|------|------------|
| SQL文字列を直接連結してクエリを構築する | SQLインジェクションの脆弱性を生む。PreparedStatementを使用せず、文字列連結でSQLを組み立てると、ユーザー入力値が直接SQL文に埋め込まれ、悪意ある入力により意図しないSQL文が実行される危険性がある。 | SQLファイルに定義し、名前付きバインド変数を使用する。どうしてもSQLファイルに定義できない場合でも、必ずPreparedStatementとバインド変数を使用する。 |
| SQLを複数機能で流用する | 複数機能で流用した場合、意図しない使われ方やSQLが変更されることにより思わぬ不具合が発生する原因となる。例えば、複数機能で使用していたSQL文に排他ロック用の for update が追加された場合、排他ロックが不要な機能でロックが取得され処理遅延の原因となる。 | SQLを複数機能で流用せずに、かならず機能毎に作成すること。 |
| 可変条件を使ってSQLを共通化する | 可変条件機能は、ウェブアプリケーションの検索画面のようにユーザの入力内容によって検索条件が変わるような場合に使うものである。条件だけが異なる複数のSQLを共通化するために使用するものではない。安易に共通化した場合、SQLを変更した場合に思わぬ不具合を埋め込む原因にもなる。 | 条件が異なる場合は必ずSQLを複数定義すること。 |
| ストアードプロシージャを多用する | ストアードプロシージャを使用した場合、ロジックがJavaとストアードプロシージャに分散してしまい、保守性を著しく低下させるため原則使用すべきではない。 | ロジックはJavaで実装する。既存の資産などでどうしても使用しなければならないケースのみ、ストアードプロシージャ実行APIを使用する。 |
| getBytesやgetStringでLOB型の大容量データを一括取得する | カラムの内容が全てJavaのヒープ上に展開されるため、非常に大きいサイズのデータを読み込んだ場合、ヒープ領域を圧迫し、システムダウンなどの障害の原因となる。 | 大量データを読み込む場合には、BlobオブジェクトやClobオブジェクトを使用して、InputStreamやReader経由で順次読み込み処理を行う。 |
| 検索結果のキャッシュをSQLの高速化目的で使用する | この機能は、参照系のデータベースアクセスを省略可能な場合に省略し、システム負荷を軽減することを目的としており、データベースアクセス（SQL）の高速化を目的としているものではない。 | SQLの高速化を目的とする場合には、SQLのチューニングを実施すること。 |
| java.sql.Connectionを直接使用する | java.sql.Connectionを使用した場合、チェック例外であるjava.sql.SQLExceptionをハンドリングして例外を制御する必要がある。この例外制御は実装を誤ると、障害が検知されなかったり障害時の調査ができないなどの問題が発生することがある。 | どうしてもjava.sql.Connectionを使わないと満たせない要件がない限り、この機能は使用しないこと。 |

---

## tips

**title**: 型変換の取扱い

**description**: データベースアクセス（JDBCラッパー）は、データベースとの入出力に使用する変数の型変換をJDBCドライバに委譲する。よって、入出力に使用する変数の型は、データベースの型及び使用するJDBCドライバの仕様に応じて定義する必要がある。任意の型変換が必要な場合は、アプリケーション側で型変換する。

**title**: java.util.Mapも入力として使用可能

**description**: Beanの代わりにjava.util.Mapの実装クラスも指定できる。Mapを指定した場合は、Mapのキー値と一致するINパラメータに対して、Mapの値が設定される。

**title**: フィールドアクセスへの変更

**description**: Beanへのアクセス方法をプロパティからフィールドに変更できる。propertiesファイルに nablarch.dbAccess.isFieldAccess=true を設定する。ただし、本フレームワークのその他の機能ではプロパティアクセスで統一されているため、フィールドアクセスは推奨しない。

**title**: java.sql.Connectionの取得

**description**: JDBCのネイティブなデータベース接続（java.sql.Connection）を扱いたい場合は、DbConnectionContextから取得したTransactionManagerConnectionからjava.sql.Connectionを取得できる。ただし、どうしてもjava.sql.Connectionを使わないと満たせない要件がない限り使用しないこと。

**title**: 一意制約違反のハンドリング

**description**: 一意制約違反時に何か処理を行う必要がある場合には、DuplicateStatementExceptionをtry-catchで補足し処理をする。ただし、データベース製品によってはSQL実行時に例外が発生した場合に、ロールバックを行うまで一切のSQLを受け付けないものがあるので注意。例えば、登録処理で一意制約違反が発生した場合に更新処理をしたい場合は、例外ハンドリングを行うのではなくmerge文を使用することでこの問題を回避できる。


---

## limitations

- この機能は、JDBC 3.0に依存しているため、使用するJDBCドライバがJDBC 3.0以上を実装している必要がある
- LOB型（BLOB型やCLOB型）のカラムを取得した場合、実際にDBに格納されたデータではなくLOBロケータが取得される。このLOBロケータの有効期間は、RDBMS毎の実装に依存しており、通常、ResultSetやConnectionがクローズされた時点でアクセスできなくなる。このため、ResultSetやConnectionよりも生存期間が長いキャッシュにはBLOB、CLOB型を含めることができない
- デフォルトで提供するキャッシュを保持するコンポーネントはJVMのヒープ上にキャッシュを保持する。このため、アプリケーションを冗長化構成とした場合、アプリケーションごとに検索結果がキャッシュされることになり、それぞれのアプリケーションで異なるキャッシュを保持する可能性がある
- ストアードプロシージャの実行では、Beanオブジェクトを使用した名前付きバインド変数はサポートしない

---

## extensions

**title**: データベースへの接続法を追加する

**description**: OSSのコネクションプールライブラリを使用する場合など、データベースの接続方法を追加する場合は、ConnectionFactorySupportを継承し、データベース接続を生成するクラスを作成する。

**title**: ダイアレクトを追加する

**description**: 使用するデータベース製品に対応したダイアレクトがない場合や、特定機能の使用可否を切り替えたい場合は、DefaultDialectを継承し、データベース製品に対応したダイアレクトを作成する。

**title**: データベースアクセス時の例外クラスを切り替える

**description**: デッドロックエラーの例外クラスを変更したい場合など、DbAccessExceptionFactoryとSqlStatementExceptionFactoryの実装クラスを作成して、コンポーネント設定ファイルに定義する。


---
