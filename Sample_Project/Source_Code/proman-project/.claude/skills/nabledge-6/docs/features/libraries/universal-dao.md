# ユニバーサルDAO

Jakarta Persistenceアノテーションを使った簡易的なO/Rマッパー。SQLを書かずに単純なCRUDを実行し、検索結果をBeanにマッピングできる

**目的**: 単純なCRUD操作とBean検索を簡潔に実現する


**位置づけ**: 簡易的なO/Rマッパーとして位置付け。全てのデータベースアクセスをカバーする設計ではない。実現できない場合はDatabaseを使用


**モジュール**:
- `com.nablarch.framework:nablarch-common-dao`

**classes**:

- nablarch.common.dao.UniversalDao



**annotations**:

- jakarta.persistence.*

**prerequisites**: 内部でDatabaseを使用するため、Databaseの設定が必要

**limitations**:

- 主キー以外の条件を指定した更新/削除は不可（Databaseを使用）
- 共通項目（登録ユーザ、更新ユーザ等）の自動設定機能は未提供
- CRUDでの@Tableスキーマ指定時、replace_schema機能は使用不可

**tips**:

- **title**: 共通項目の自動設定
- **description**: Domaアダプタのエンティティリスナー機能を推奨。ユニバーサルDAO使用時はアプリケーションで明示的に設定
- **title**: 基本方針
- **description**: ユニバーサルDAOで実現できない場合は、素直にDatabaseを使う

**公式ドキュメント**:
- [ユニバーサルDAO](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/database/universal_dao.html)

---

## crud

Jakarta PersistenceアノテーションをEntityに付けることで、SQLを書かずに単純なCRUDが可能。SQL文は実行時に自動構築

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `insert` | `UniversalDao.insert(T entity)` | エンティティを1件登録 |
| `batchInsert` | `UniversalDao.batchInsert(List<T> entities)` | エンティティを一括登録 |
| `update` | `UniversalDao.update(T entity)` | 主キーを指定して1件更新 |
| `batchUpdate` | `UniversalDao.batchUpdate(List<T> entities)` | 主キーを指定して一括更新（排他制御なし） ⚠️ 排他制御を行わない。バージョン不一致でも更新されず正常終了 |
| `delete` | `UniversalDao.delete(T entity)` | 主キーを指定して1件削除 |
| `batchDelete` | `UniversalDao.batchDelete(List<T> entities)` | 主キーを指定して一括削除 |
| `findById` | `UniversalDao.findById(Class<T> entityClass, Object... pk)` | 主キーを指定して1件検索 |
| `findAll` | `UniversalDao.findAll(Class<T> entityClass)` | エンティティを全件検索 |
| `findAllBySqlFile` | `UniversalDao.findAllBySqlFile(Class<T> entityClass, String sqlId)` | SQLファイルを使った全件検索 |
| `findAllBySqlFile` | `UniversalDao.findAllBySqlFile(Class<T> entityClass, String sqlId, Object condition)` | 条件を指定したSQLファイル検索 |
| `findBySqlFile` | `UniversalDao.findBySqlFile(Class<T> entityClass, String sqlId, Object condition)` | SQLファイルで1件検索（悲観的ロック用SELECT FOR UPDATEにも使用） |

**insert**:

パラメータ:
- `entity` (T): 登録するエンティティオブジェクト

戻り値: void

```java
UniversalDao.insert(user);
```

**batchInsert**:

パラメータ:
- `entities` (List<T>): 登録するエンティティリスト

戻り値: void

```java
UniversalDao.batchInsert(users);
```

**update**:

パラメータ:
- `entity` (T): 更新するエンティティオブジェクト（主キー指定必須）

戻り値: int（更新件数）

```java
UniversalDao.update(user);
```

**batchUpdate**:

パラメータ:
- `entities` (List<T>): 更新するエンティティリスト

戻り値: void

```java
UniversalDao.batchUpdate(users);
```

**delete**:

パラメータ:
- `entity` (T): 削除するエンティティオブジェクト（主キー指定必須）

戻り値: int（削除件数）

```java
UniversalDao.delete(user);
```

**batchDelete**:

パラメータ:
- `entities` (List<T>): 削除するエンティティリスト

戻り値: void

```java
UniversalDao.batchDelete(users);
```

**findById**:

パラメータ:
- `entityClass` (Class<T>): 検索結果をマッピングするエンティティクラス
- `pk` (Object...): 主キーの値（可変長引数）

戻り値: T（エンティティオブジェクト）

```java
User user = UniversalDao.findById(User.class, 1L);
```

**findAll**:

パラメータ:
- `entityClass` (Class<T>): 検索結果をマッピングするエンティティクラス

戻り値: EntityList<T>

```java
EntityList<User> users = UniversalDao.findAll(User.class);
```

**findAllBySqlFile**:

パラメータ:
- `entityClass` (Class<T>): 検索結果をマッピングするBeanクラス
- `sqlId` (String): SQL ID

戻り値: EntityList<T>

```java
EntityList<User> users = UniversalDao.findAllBySqlFile(User.class, "FIND_BY_NAME");
```

**findAllBySqlFile**:

パラメータ:
- `entityClass` (Class<T>): 検索結果をマッピングするBeanクラス
- `sqlId` (String): SQL ID
- `condition` (Object): 検索条件オブジェクト

戻り値: EntityList<T>

```java
EntityList<Project> projects = UniversalDao.findAllBySqlFile(Project.class, "SEARCH_PROJECT", condition);
```

**findBySqlFile**:

パラメータ:
- `entityClass` (Class<T>): 検索結果をマッピングするBeanクラス
- `sqlId` (String): SQL ID
- `condition` (Object): 検索条件オブジェクト

戻り値: T

```java
User user = UniversalDao.findBySqlFile(User.class, "FIND_USER_FOR_UPDATE", condition);
```

**annotations_required**: @Entity、@Table、@Id、@Column等のJakarta Persistenceアノテーションを使用

**sql_generation**: アノテーション情報を元に実行時にSQL文を構築

---

## sql-file

任意のSQLで検索する場合、SQLファイルを作成しSQL IDを指定して検索

**method**: UniversalDao.findAllBySqlFile / findBySqlFile

**sql_file_path_derivation**: 検索結果をマッピングするBeanのクラスから導出。sample.entity.User → sample/entity/User.sql

**sql_id_with_hash**:

**description**: SQL IDに#を含めると「SQLファイルのパス#SQL ID」と解釈

**example**: UniversalDao.findAllBySqlFile(GoldUser.class, "sample.entity.Member#FIND_BY_NAME")

**sql_file_path**: sample/entity/Member.sql

**sql_id**: FIND_BY_NAME

**use_case**: 機能単位（Actionハンドラ単位）にSQLを集約したい場合

**recommendation**: 基本は#を付けない指定を使用（指定が煩雑になるため）

**bean_mapping**:

**description**: 検索結果をBean（Entity、Form、DTO）にマッピング

**mapping_rule**: Beanのプロパティ名とSELECT句の名前が一致する項目をマッピング

**typical_usage**: Database機能のuse_sql_fileと同様の使い方

---

## join

複数テーブルをJOINした結果を取得する場合の対応

**use_case**: 一覧検索などで複数テーブルをJOINした結果を取得

**recommendation**: 非効率なため個別検索せず、1回で検索できるSQLとJOIN結果をマッピングするBeanを作成

**implementation**:

- JOINした結果をマッピングするBean（DTO）を作成
- SQLファイルに複数テーブルをJOINするSQLを記述
- findAllBySqlFileでDTOにマッピング

---

## lazy-load

大量データでメモリ不足を防ぐための遅延ロード機能

**example**:

```java
try (DeferredEntityList<User> users = (DeferredEntityList<User>) UniversalDao.defer().findAllBySqlFile(User.class, "FIND_BY_NAME")) {
    for (User user : users) {
        // userを使った処理
    }
}
```

**use_cases**:

- ウェブで大量データをダウンロード
- バッチで大量データを処理

**method**:

**name**: defer

**signature**: UniversalDao.defer()

**description**: 遅延ロードを有効化するメソッド。検索メソッドの前に呼び出す

**returns**: UniversalDao（メソッドチェーン可能）

**return_type**: DeferredEntityList<T>

**requires_close**: True

**close_method**: DeferredEntityList.close()（try-with-resources推奨）

**mechanism**: 内部でサーバサイドカーソルを使用。JDBCのフェッチサイズでメモリ使用量が変わる

**fetch_size_note**: JDBCのフェッチサイズの詳細はデータベースベンダー提供のマニュアルを参照

**important**: RDBMSによってはカーソルオープン中にトランザクション制御が行われるとカーソルがクローズされる。遅延ロード使用中のトランザクション制御でエラーの可能性。ページングで回避またはカーソル挙動を調整

---

## search-condition

検索画面のような条件指定検索

**example**:

```java
ProjectSearchForm condition = context.getRequestScopedVar("form");
List<Project> projects = UniversalDao.findAllBySqlFile(Project.class, "SEARCH_PROJECT", condition);
```

**method**: UniversalDao.findAllBySqlFile(Class<T>, String sqlId, Object condition)

**condition_object**: 検索条件を持つ専用のBean（Form等）。ただし1テーブルのみアクセスの場合はEntity指定も可

**important**: 検索条件はEntityではなく検索条件を持つ専用のBeanを指定。1テーブルのみの場合はEntity可

---

## type-conversion

データベース型とJava型の変換

**temporal_annotation**: @Temporalでjava.util.Date/java.util.Calendar型のDBマッピング方法を指定可能

**other_types**: 任意のマッピングは不可。DBの型とJDBCドライバ仕様に応じてEntityプロパティを定義

**auto_generated_sql**:

**description**: Entityから自動生成したSQL実行時

**output_to_db**: @Temporal設定プロパティは指定型へ変換。それ以外はDatabaseに委譲

**input_from_db**: @Temporal設定プロパティは指定型から変換。それ以外はEntity情報を元に変換

**custom_sql**:

**description**: 任意のSQLで検索する場合

**output_to_db**: Databaseに委譲して変換

**input_from_db**: 自動生成SQLと同様の処理

**important**:

- DB型とプロパティ型不一致で実行時型変換エラーの可能性
- SQL実行時の暗黙的型変換でindex未使用による性能劣化の可能性
- データベースとJavaのデータタイプマッピングはJDBCドライバマニュアルを参照

**type_examples**:

- **db_type**: date
- **java_type**: java.sql.Date
- **db_type**: 数値型（integer, bigint, number）
- **java_type**: int (Integer), long (Long)

---

## paging

検索結果のページング機能

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `per` | `UniversalDao.per(long perPage)` | 1ページあたりの件数を指定 |
| `page` | `UniversalDao.page(long pageNumber)` | ページ番号を指定 |

**per**:

パラメータ:
- `perPage` (long): 1ページあたりの件数

戻り値: UniversalDao（メソッドチェーン可能）

**page**:

パラメータ:
- `pageNumber` (long): ページ番号

戻り値: UniversalDao（メソッドチェーン可能）

**example**:

```java
EntityList<User> users = UniversalDao.per(3).page(1).findAllBySqlFile(User.class, "FIND_ALL_USERS");
```

**pagination_info**:

**class**: nablarch.common.dao.Pagination

**description**: ページング画面表示に必要な検索結果件数等の情報を保持

**retrieval**: Pagination pagination = users.getPagination();

**internal**: Databaseの範囲指定検索機能を使用して実装

**count_sql**:

**description**: 範囲指定レコード取得前に件数取得SQLが発行される

**default_behavior**: 元のSQLをSELECT COUNT(*) FROMで包んだSQL

**performance_note**: 件数取得SQLによる性能劣化時は拡張例を参照してカスタマイズ

---

## surrogate-key

サロゲートキーの自動採番機能

**annotations**:

- @GeneratedValue
- @SequenceGenerator
- @TableGenerator

**strategies**:

- 項目 1:
  **type**: GenerationType.AUTO

  **description**: Dialectを元に採番方法を自動選択

  **priority**: IDENTITY → SEQUENCE → TABLE

  **sequence_name_rule**: SEQUENCE選択時、シーケンスオブジェクト名は<テーブル名>_<カラム名>

  **generator_note**: generator属性に対応するGenerator設定がある場合、そのGeneratorを使用

  **example**: @Id
@Column(name = "USER_ID", length = 15)
@GeneratedValue(strategy = GenerationType.AUTO)
public Long getId() { return id; }

- **type**: GenerationType.IDENTITY
- **description**: DB自動採番機能（IDENTITY）を使用
- **example**: @Id
@Column(name = "USER_ID", length = 15)
@GeneratedValue(strategy = GenerationType.IDENTITY)
public Long getId() { return id; }
- 項目 3:
  **type**: GenerationType.SEQUENCE

  **description**: シーケンスオブジェクトで採番

  **sequence_generator_required**: True

  **sequence_name_config**: @SequenceGeneratorのsequenceName属性で指定。省略時は<テーブル名>_<カラム名>

  **example**: @Id
@Column(name = "USER_ID", length = 15)
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
@SequenceGenerator(name = "seq", sequenceName = "USER_ID_SEQ")
public Long getId() { return id; }

- 項目 4:
  **type**: GenerationType.TABLE

  **description**: 採番テーブルで採番

  **table_generator_required**: True

  **pk_value_config**: @TableGeneratorのpkColumnValue属性で指定。省略時は<テーブル名>_<カラム名>

  **example**: @Id
@Column(name = "USER_ID", length = 15)
@GeneratedValue(strategy = GenerationType.TABLE, generator = "table")
@TableGenerator(name = "table", pkColumnValue = "USER_ID")
public Long getId() { return id; }


**generator_configuration**: シーケンス/テーブル採番はGenerator機能を使用。別途設定が必要（generator参照）

---

## batch-execute

大量データの一括登録/更新/削除でバッチ実行

**目的**: アプリケーションサーバとDBサーバ間のラウンドトリップ回数削減によるパフォーマンス向上


| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `batchInsert` | `UniversalDao.batchInsert(List<T> entities)` | エンティティを一括登録 |
| `batchUpdate` | `UniversalDao.batchUpdate(List<T> entities)` | エンティティを一括更新 ⚠️ 排他制御を行わない。更新対象EntityとDBのバージョン不一致でも、そのレコードは更新されず処理が正常終了 |
| `batchDelete` | `UniversalDao.batchDelete(List<T> entities)` | エンティティを一括削除 |

**batchInsert**:

パラメータ:
- `entities` (List<T>): 登録するエンティティリスト

戻り値: void

**batchUpdate**:

パラメータ:
- `entities` (List<T>): 更新するエンティティリスト

戻り値: void

**batchDelete**:

パラメータ:
- `entities` (List<T>): 削除するエンティティリスト

戻り値: void

**important**: batchUpdateは排他制御を行わない。排他制御が必要な更新は1レコード毎の更新処理を使用

---

## optimistic-lock

@Version付きEntity更新時に自動で楽観的ロック実行

**annotation**: @Version

**mechanism**: 更新処理時にバージョンカラムが条件に自動追加され楽観ロックが行われる

**exception**:

**class**: jakarta.persistence.OptimisticLockException

**cause**: 排他エラー発生（バージョン不一致）

**version_annotation_constraints**:

- 数値型のプロパティのみ指定可（文字列型不可）
- Entity内に1つのみ指定可能

**error_handling**:

**annotation**: @OnError

**description**: 排他エラー時の画面遷移制御

**example**: @OnError(type = OptimisticLockException.class, path = "/WEB-INF/view/common/errorPages/userError.jsp")
public HttpResponse update(HttpRequest request, ExecutionContext context) {
    UniversalDao.update(user);
}

**batch_update_note**: batchUpdateでは楽観的ロックは使用できない

---

## pessimistic-lock

悲観的ロック機能は特に提供していない

**example**:

```java
User user = UniversalDao.findBySqlFile(User.class, "FIND_USER_FOR_UPDATE", condition);
```

**implementation**: データベースの行ロック（SELECT FOR UPDATE）を使用

**method**:

**name**: findBySqlFile

**signature**: UniversalDao.findBySqlFile(Class<T> entityClass, String sqlId, Object condition)

**description**: SELECT FOR UPDATEを記載したSQLファイルを実行

---

## exclusive-control

排他制御の設計指針

**example**:

```java
「ユーザ」単位でロックが業務的に許容されるなら、ユーザテーブルにバージョン番号を定義
```

**principle**: バージョンカラムは排他制御を行う単位ごとに定義し、競合が許容される最大の単位で定義

**trade_off**: 単位を大きくすると競合可能性が高まり、更新失敗（楽観的ロック）や処理遅延（悲観的ロック）を招く

**design_consideration**: 業務的観点で排他制御単位を決定する必要がある

---

## binary-data

OracleのBLOBのようなデータサイズの大きいバイナリデータの登録/更新

**limitation**: ユニバーサルDAOは全データをメモリに展開するため不向き

**recommendation**: データベース提供機能を使ってファイルから直接登録/更新

**reference**: database-binary_column参照

---

## text-data

OracleのCLOBのようなデータサイズの大きいテキストデータの登録/更新

**limitation**: ユニバーサルDAOは全データをメモリに展開するため不向き

**recommendation**: データベース提供機能を使ってファイルから直接登録/更新

**reference**: database-clob_column参照

---

## transaction

現在のトランザクションとは異なるトランザクションでDAO実行

**example**:

```java
private static final class FindPersonsTransaction extends UniversalDao.Transaction {
    private EntityList<Person> persons;

    FindPersonsTransaction() {
        super("find-persons-transaction");
    }

    @Override
    protected void execute() {
        persons = UniversalDao.findAllBySqlFile(Person.class, "FIND_PERSONS");
    }

    public EntityList<Person> getPersons() {
        return persons;
    }
}

FindPersonsTransaction tx = new FindPersonsTransaction();
EntityList<Person> persons = tx.getPersons();
```

**use_case**: Databaseのdatabase-new_transactionと同じことをユニバーサルDAOで実行

**steps**:

- コンポーネント設定ファイルにSimpleDbTransactionManagerを定義
- SimpleDbTransactionManagerを使用して新たなトランザクションでDAO実行

**component_configuration**:

**component_name**: 任意の名前（例: find-persons-transaction）

**class**: nablarch.core.db.transaction.SimpleDbTransactionManager

**properties**:

- 項目 1:
  **name**: connectionFactory

  **type**: nablarch.core.db.connection.ConnectionFactory

  **required**: True

  **description**: ConnectionFactory実装クラス

- 項目 2:
  **name**: transactionFactory

  **type**: nablarch.core.transaction.TransactionFactory

  **required**: True

  **description**: TransactionFactory実装クラス

- 項目 3:
  **name**: dbTransactionName

  **type**: String

  **required**: True

  **description**: トランザクションを識別するための名前


**xml_example**: <component name="find-persons-transaction" class="nablarch.core.db.transaction.SimpleDbTransactionManager">
  <property name="connectionFactory" ref="connectionFactory" />
  <property name="transactionFactory" ref="transactionFactory" />
  <property name="dbTransactionName" value="update-login-failed-count-transaction" />
</component>

**implementation**:

**parent_class**: nablarch.common.dao.UniversalDao.Transaction

**description**: UniversalDao.Transactionを継承したクラスを作成

**constructor**: super("transaction-name")でSimpleDbTransactionManagerの名前またはオブジェクトを指定

**execute_method**:

**description**: executeメソッドにDAO処理を実装

**behavior**: 正常終了でコミット、例外/エラーでロールバック

---

## configuration

ユニバーサルDAO使用のための設定

**required_component**:

**component_name**: daoContextFactory

**class**: nablarch.common.dao.BasicDaoContextFactory

**description**: コンポーネント定義に追加が必要

**xml_example**: <component name="daoContextFactory" class="nablarch.common.dao.BasicDaoContextFactory" />

**prerequisites**: Databaseの設定が必要（内部でDatabaseを使用）

---

## extensions

**metadata_extractor**:

**description**: DatabaseMetaDataから主キー情報を取得できない場合の対応

**cause**: シノニム使用や権限問題

**impact**: 主キー指定検索が正しく動作しない

**solution**: DatabaseMetaDataExtractorを継承したクラスを作成

**parent_class**: nablarch.common.dao.DatabaseMetaDataExtractor

**configuration**:

**component_name**: databaseMetaDataExtractor

**example**: <component name="databaseMetaDataExtractor" class="sample.dao.CustomDatabaseMetaDataExtractor" />

**count_sql_customization**:

**description**: ページング処理の件数取得SQL変更

**use_case**: ORDER BY句等で処理負荷が大きい場合に負荷軽減（ORDER BY句を外す等）

**default_behavior**: 元のSQLをSELECT COUNT(*) FROMで包んだSQL

**implementation**:

**method**: Dialect.convertCountSql(String sqlId, Object params, StatementFactory statementFactory)をオーバーライド

**approach**: 使用中のDialectを継承し、元SQLと件数取得SQLのマッピングをコンポーネント設定

**important**: 件数取得SQLは元SQLと同一の検索条件が必要。検索条件に差分が発生しないよう注意

**example_class**: CustomH2Dialect extends H2Dialect

**example_method**: @Override
public String convertCountSql(String sqlId, Object params, StatementFactory statementFactory) {
    if (sqlMap.containsKey(sqlId)) {
        return statementFactory.getVariableConditionSqlBySqlId(sqlMap.get(sqlId), params);
    }
    return convertCountSql(statementFactory.getVariableConditionSqlBySqlId(sqlId, params));
}

**configuration**:

**component_name**: dialect

**example**: <component name="dialect" class="com.nablarch.example.app.db.dialect.CustomH2Dialect">
  <property name="sqlMap">
    <map>
      <entry key="com.nablarch.example.app.entity.Project#SEARCH_PROJECT" value="com.nablarch.example.app.entity.Project#SEARCH_PROJECT_FORCOUNT"/>
    </map>
  </property>
</component>

---

## jpa-annotations

Entityに使用できるJakarta Persistenceアノテーション

**important**: 記載のないアノテーション/属性は機能しない

**access_rule**: @Accessで明示的にフィールド指定した場合のみフィールドのアノテーションを参照

**getter_setter_required**: フィールドにアノテーション設定でもgetter/setter必須（値の取得/設定はプロパティ経由）

**naming_rule**: フィールド名とプロパティ名（get〇〇/set〇〇の〇〇）は同一にすること

**lombok_tip**: Lombokのようなボイラープレートコード生成ライブラリ使用時、フィールドにアノテーション設定でgetter自動生成の利点を活用可能

**class_annotations**:

- 項目 1:
  **name**: @Entity

  **package**: jakarta.persistence.Entity

  **description**: データベースのテーブルに対応したEntityクラスに設定

  **table_name_derivation**: クラス名（パスカルケース）→スネークケース（大文字）

  **examples**:

  - **class**: Book
  - **table**: BOOK
  - **class**: BookAuthor
  - **table**: BOOK_AUTHOR

  **tip**: クラス名からテーブル名を導出できない場合は@Tableで明示指定

- 項目 2:
  **name**: @Table

  **package**: jakarta.persistence.Table

  **description**: テーブル名を明示指定するアノテーション

  **attributes**:

  **name**:

  **type**: String

  **required**: False

  **description**: テーブル名。指定した値がテーブル名として使用される

  **schema**:

  **type**: String

  **required**: False

  **description**: スキーマ名。指定されたスキーマ名を修飾子としてテーブルにアクセス。例: schema="work" → work.users_work

- 項目 3:
  **name**: @Access

  **package**: jakarta.persistence.Access

  **description**: アノテーション設定場所を指定するアノテーション

  **behavior**: 明示的にフィールド指定した場合のみフィールドのアノテーションを参照


**property_annotations**:

- 項目 1:
  **name**: @Column

  **package**: jakarta.persistence.Column

  **description**: カラム名を指定するアノテーション

  **attributes**:

  **name**:

  **type**: String

  **required**: False

  **description**: カラム名。指定した値がカラム名として使用される

  **default_derivation**: 未設定時はプロパティ名からカラム名を導出（テーブル名導出と同じ方法）

- 項目 2:
  **name**: @Id

  **package**: jakarta.persistence.Id

  **description**: 主キーに設定するアノテーション

  **composite_key**: 複合主キーの場合は複数のgetterまたはフィールドに設定

- 項目 3:
  **name**: @Version

  **package**: jakarta.persistence.Version

  **description**: 排他制御用バージョンカラムに設定するアノテーション

  **constraints**:

  - 数値型のプロパティのみ指定可（文字列型不可）
  - Entity内に1つのみ指定可能

  **behavior**: 更新処理時にバージョンカラムが条件に自動追加され楽観ロック実行

- 項目 4:
  **name**: @Temporal

  **package**: jakarta.persistence.Temporal

  **description**: java.util.Date/java.util.Calendar型のDBマッピング方法を指定

  **attributes**:

  **value**:

  **type**: TemporalType

  **required**: True

  **description**: データベース型（DATE, TIME, TIMESTAMP）

  **behavior**: value属性に指定されたDB型へJavaオブジェクトの値を変換してDB登録

- 項目 5:
  **name**: @GeneratedValue

  **package**: jakarta.persistence.GeneratedValue

  **description**: 自動採番された値を登録することを示すアノテーション

  **attributes**:

  **strategy**:

  **type**: GenerationType

  **required**: False

  **default**: AUTO

  **description**: 採番方法（AUTO, IDENTITY, SEQUENCE, TABLE）

  **generator**:

  **type**: String

  **required**: False

  **description**: Generator設定名

  **auto_behavior**:

  - generator属性に対応するGenerator設定がある場合、そのGeneratorを使用
  - generatorが未設定または対応設定がない場合、Dialectを元に選択（IDENTITY→SEQUENCE→TABLE）

  **default_name_rule**: シーケンス名/レコード識別値を取得できない場合、<テーブル名>_<カラム名>から導出

- 項目 6:
  **name**: @SequenceGenerator

  **package**: jakarta.persistence.SequenceGenerator

  **description**: シーケンス採番を使用する場合に設定

  **attributes**:

  **name**:

  **type**: String

  **required**: True

  **description**: @GeneratedValueのgenerator属性と同じ値

  **sequenceName**:

  **type**: String

  **required**: False

  **default**: <テーブル名>_<カラム名>

  **description**: データベース上に作成されているシーケンスオブジェクト名

  **note**: シーケンス採番はGenerator機能を使用。採番用の設定を別途行う必要がある

- 項目 7:
  **name**: @TableGenerator

  **package**: jakarta.persistence.TableGenerator

  **description**: テーブル採番を使用する場合に設定

  **attributes**:

  **name**:

  **type**: String

  **required**: True

  **description**: @GeneratedValueのgenerator属性と同じ値

  **pkColumnValue**:

  **type**: String

  **required**: False

  **default**: <テーブル名>_<カラム名>

  **description**: 採番テーブルのレコードを識別するための値

  **note**: テーブル採番はGenerator機能を使用。採番用の設定を別途行う必要がある


---

## bean-data-types

検索結果をマッピングするBeanに使用可能なデータタイプ

**important**: 記載のないデータタイプへのマッピングは実行時例外

**types**:

- **type**: java.lang.String
- **note**:
- **type**: java.lang.Short
- **primitive**: True
- **note**: プリミティブ型も指定可。プリミティブ型でnullは0として扱う
- **type**: java.lang.Integer
- **primitive**: True
- **note**: プリミティブ型も指定可。プリミティブ型でnullは0として扱う
- **type**: java.lang.Long
- **primitive**: True
- **note**: プリミティブ型も指定可。プリミティブ型でnullは0として扱う
- **type**: java.math.BigDecimal
- **note**:
- **type**: java.lang.Boolean
- **primitive**: True
- **note**: プリミティブ型も指定可。プリミティブ型でnullはfalseとして扱う。ラッパー型のリードメソッド名はgetから開始必須。プリミティブ型はisで開始可
- **type**: java.util.Date
- **note**: @Temporalでデータベース上のデータ型を指定する必要がある
- **type**: java.sql.Date
- **note**:
- **type**: java.sql.Timestamp
- **note**:
- **type**: java.time.LocalDate
- **note**:
- **type**: java.time.LocalDateTime
- **note**:
- **type**: byte[]
- **note**: BLOB等の非常に大きいサイズのデータ型の値は、本機能でヒープ上に展開しないよう注意。非常に大きいサイズのバイナリデータを扱う場合は、Databaseを直接使用しStream経由でデータを参照

---

## anti-patterns

| パターン | 理由 | 正しい方法 |
|----------|------|------------|
| 主キー以外の条件で更新/削除しようとする | ユニバーサルDAOは主キー指定の更新/削除のみ対応 | 主キー以外の条件が必要な場合はDatabaseを直接使用 |
| 検索条件にEntityを無条件に使用する | 複数テーブル検索時にEntityを使うと設計が不明瞭になる | 検索条件は専用のBean（Form等）を指定。ただし1テーブルのみアクセスの場合はEntity指定も可 |
| フィールドにアノテーション設定してgetter/setterを省略する | UniversalDaoは値の取得/設定をプロパティ経由で行うため、フィールドアノテーション設定でもgetter/setterが必要 | フィールドにアノテーションを設定する場合でもgetter/setterを必ず作成する |
| 共通項目（登録ユーザ、更新ユーザ等）の自動設定を期待する | 自動設定機能は未提供 | Domaアダプタのエンティティリスナー使用、またはアプリケーションで明示的に設定 |
| @Tableのスキーマ指定でreplace_schema機能を使用しようとする | ユニバーサルDAOのCRUD機能ではreplace_schema未対応 | 環境毎のスキーマ切替はDatabaseを使用 |
| batchUpdateで排他制御を期待する | batchUpdateは排他制御を行わない。バージョン不一致でも更新されず正常終了し、更新失敗に気付けない | 排他制御が必要な場合は1レコード毎の更新処理（update）を使用 |
| @Versionを文字列型プロパティに設定する | 数値型のみ対応。文字列型は正しく動作しない | @Versionは数値型プロパティに設定 |
| 大きいBLOB/CLOBデータをユニバーサルDAOで登録/更新する | 全データをメモリに展開するため、大容量データでメモリ不足になる | データベース提供機能でファイルから直接登録/更新 |
| 遅延ロード中にトランザクション制御を行う | RDBMSによってはカーソルオープン中のトランザクション制御でカーソルがクローズされエラーになる | ページングで回避、またはDBベンダマニュアルに沿ってカーソル挙動を調整 |
| JOIN対象のデータを個別に検索する | 複数回のクエリで非効率 | 1回で検索できるSQLとJOIN結果をマッピングするBeanを作成 |
| DeferredEntityListをcloseせずに放置する | 内部でサーバサイドカーソルを使用しており、リソースリークの原因になる | try-with-resourcesでclose呼び出し |
| フィールドとプロパティを異なる名前にする（@Accessでフィールド指定時） | フィールド名とプロパティ名で紐づいているため、異なるとフィールドのアノテーションをプロパティで参照できなくなる | フィールド名とプロパティ名（get〇〇/set〇〇の〇〇）は同一にする |
| 記載のないアノテーション/属性を使用する | Jakarta Persistenceの全機能には対応していない | 公式ドキュメント記載のアノテーション/属性のみ使用 |
| サポートされていないデータタイプにマッピングする | 実行時例外が発生する | bean-data-typesに記載のデータタイプを使用 |
| DB型とプロパティ型を不一致にする | 実行時型変換エラーや暗黙的型変換によるindex未使用で性能劣化 | JDBCドライバマニュアルを参照し適切な型でプロパティを定義 |

---

## errors

| 例外 | 原因 | 対処 |
|------|------|------|
| `jakarta.persistence.OptimisticLockException` | 楽観的ロックで排他エラー発生（@Version付きEntity更新時にバージョン不一致） | @OnErrorで画面遷移を制御。例: @OnError(type = OptimisticLockException.class, path = "/WEB-INF/view/common/errorPages/userError.jsp") |
| `型変換エラー（実行時例外）` | データベースの型とプロパティの型が不一致 | JDBCドライバのマニュアルを参照し、データベースとJavaのデータタイプマッピングに従って適切な型でプロパティを定義 |
| `実行時例外（マッピングエラー）` | サポートされていないデータタイプへのマッピング | bean-data-typesに記載のデータタイプを使用 |
| `主キー検索が正しく動作しない` | DatabaseMetaDataから主キー情報を取得できない（シノニム使用、権限問題） | DatabaseMetaDataExtractorを継承したクラスを作成し、databaseMetaDataExtractorコンポーネントとして設定 |

---

## tips

**title**: ユニバーサルDAOの位置付け

**description**: 簡易的なO/Rマッパー。全てのDBアクセスをカバーする設計ではない。実現できない場合は素直にDatabaseを使用

**title**: 共通項目の自動設定

**description**: Domaアダプタのエンティティリスナー機能を推奨。ユニバーサルDAO使用時はアプリケーションで明示的に設定

**title**: SQLファイルのパス指定

**description**: #を含めた指定は機能単位にSQL集約に使えるが、基本は#なしを推奨（指定が煩雑になるため）

**title**: ページングの内部実装

**description**: Databaseの範囲指定検索機能を使用。範囲指定レコード取得前に件数取得SQLが発行される

**title**: シーケンス/テーブル採番の設定

**description**: Generator機能を使用するため、別途採番用の設定が必要

**title**: Lombokとの相性

**description**: フィールドにアノテーション設定でgetter自動生成の利点を活用可能


---

## limitations

- 主キー以外の条件を指定した更新/削除は不可
- 共通項目の自動設定機能は未提供
- CRUDでの@Tableスキーマ指定時、replace_schema機能は使用不可
- batchUpdateでは排他制御不可
- @Versionは数値型のみ対応（文字列型不可）
- 大容量BLOB/CLOBデータは全データをメモリ展開するため不向き
- Jakarta Persistenceの全機能には対応していない（記載のないアノテーション/属性は機能しない）

---
