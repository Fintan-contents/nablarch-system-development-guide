# 業務日付の管理

アプリケーションで使用するシステム日時(OS日時)と業務日付を一元的に管理する機能を提供する。コンポーネント定義で指定されたクラスを使用して、システム日時(OS日時)や業務日付を取得する。

**目的**: コンポーネント定義で指定するクラスを差し替えるだけで、アプリケーションで使用するシステム日時(OS日時)と業務日付の取得方法を切り替えることができる。この切り替えは、テストなどで一時的にシステム日時(OS日時)や業務日付を切り替えたい場合に使用できる。


**機能**:

- システム日時(OS日時)の一元管理

- 業務日付の一元管理(データベース使用)

- テスト時のシステム日時・業務日付の切り替え

- 複数の業務日付の管理(区分単位)

- 業務日付の上書き(プロセス単位)

- 業務日付の更新



**classes**:

- nablarch.core.date.SystemTimeProvider

- nablarch.core.date.BasicSystemTimeProvider

- nablarch.core.date.SystemTimeUtil

- nablarch.core.date.BusinessDateProvider

- nablarch.core.date.BasicBusinessDateProvider

- nablarch.core.date.BusinessDateUtil



**公式ドキュメント**:
- [業務日付の管理](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/date.html)

---

## modules

**依存関係**:

- `com.nablarch.framework:nablarch-core` [必須] - システム日時管理機能を使用する場合に必要
- `com.nablarch.framework:nablarch-common-jdbc` [任意] - 業務日付管理機能を使用する場合のみ必要

---

## system_time_configuration

システム日時の管理機能を使うためには、BasicSystemTimeProviderの設定をコンポーネント定義に追加する。

**xml_example**:

```xml
<component name="systemTimeProvider" class="nablarch.core.date.BasicSystemTimeProvider" />
```

**component_name**: systemTimeProvider

**class**: nablarch.core.date.BasicSystemTimeProvider

**properties**:


---

## system_time_usage

システム日時の取得は、SystemTimeUtilを使用する。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `getDate` | `public static Date getDate()` | 現在のシステム日時を取得する |
| `getTimestamp` | `public static Timestamp getTimestamp()` | 現在のシステム日時をTimestamp型で取得する |

**getDate**:

戻り値: 現在のシステム日時

```java
Date systemDate = SystemTimeUtil.getDate();
```

**getTimestamp**:

戻り値: 現在のシステム日時(Timestamp型)

```java
Timestamp systemTimestamp = SystemTimeUtil.getTimestamp();
```

**class**: nablarch.core.date.SystemTimeUtil

---

## business_date_configuration

業務日付管理機能では、データベースを使用して複数の業務日付を管理する。BasicBusinessDateProviderの設定をコンポーネント定義に追加し、初期化対象のリストに設定する。

| プロパティ | 型 | 必須 | 説明 |
|-----------|-----|:----:|------|
| `tableName` | `String` | ✓ | 業務日付を管理するテーブル名 |
| `segmentColumnName` | `String` | ✓ | 区分のカラム名 |
| `dateColumnName` | `String` | ✓ | 日付のカラム名 |
| `defaultSegment` | `String` | ✓ | 区分を省略して業務日付を取得した場合に使用される区分 |
| `transactionManager` | `TransactionManagerの参照` | ✓ | データベースアクセスに使用するトランザクションマネージャ |

**tableNameの例**: `BUSINESS_DATE`

**segmentColumnNameの例**: `SEGMENT`

**dateColumnNameの例**: `BIZ_DATE`

**defaultSegmentの例**: `00`

**xml_example**:

```xml
<component name="businessDateProvider" class="nablarch.core.date.BasicBusinessDateProvider">
  <!-- テーブル名 -->
  <property name="tableName" value="BUSINESS_DATE" />
  <!-- 区分のカラム名 -->
  <property name="segmentColumnName" value="SEGMENT"/>
  <!-- 日付のカラム名 -->
  <property name="dateColumnName" value="BIZ_DATE"/>
  <!-- 区分を省略して業務日付を取得した場合に使用される区分 -->
  <property name="defaultSegment" value="00"/>
  <!-- データベースアクセスに使用するトランザクションマネージャ -->
  <property name="transactionManager" ref="transactionManager" />
</component>

<component name="initializer"
    class="nablarch.core.repository.initialization.BasicApplicationInitializer">
  <property name="initializeList">
    <list>
      <!-- 他のコンポーネントは省略 -->
      <component-ref name="businessDateProvider" />
    </list>
  </property>
</component>
```

**component_name**: businessDateProvider

**class**: nablarch.core.date.BasicBusinessDateProvider

**initialization_required**: True

**database_table**:

**description**: 業務日付を管理するためのテーブル

**columns**:

- **name**: 区分(PK)
- **type**: 文字列型
- **description**: 業務日付を識別するための値
- 項目 2:
  **name**: 日付

  **type**: 文字列型

  **format**: yyyyMMdd

  **description**: 業務日付


---

## business_date_usage

業務日付の取得は、BusinessDateUtilを使用する。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `getDate` | `public static String getDate()` | デフォルト区分の業務日付を取得する |
| `getDate` | `public static String getDate(String segment)` | 指定した区分の業務日付を取得する |

**getDate**:

戻り値: 業務日付(yyyyMMdd形式の文字列)

```java
String bizDate = BusinessDateUtil.getDate();
```

**getDate**:

パラメータ:
- `segment` (String): 区分

戻り値: 業務日付(yyyyMMdd形式の文字列)

```java
String bizDate = BusinessDateUtil.getDate("batch");
```

**class**: nablarch.core.date.BusinessDateUtil

---

## business_date_override

バッチ処理で障害時の再実行時に、過去日付をバッチ実行時の業務日付としたい場合、再実行するプロセスのみ任意の日付を業務日付として実行できる。業務日付の上書きは、環境設定の上書き機能を使用して行う。

区分が"batch"の日付を"2016/03/17"に上書きしたい場合

**system_property**: -DBasicBusinessDateProvider.batch=20160317

**use_case**: バッチ処理の障害時の再実行で、過去日付を業務日付として実行したい場合

**method**: システムプロパティで指定

**format**: BasicBusinessDateProvider.<区分>=日付(yyyyMMdd形式)

---

## business_date_update

業務日付の更新は、BasicBusinessDateProviderを使用して行う。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `setDate` | `public void setDate(String segment, String date)` | 指定した区分の業務日付を更新する |

**setDate**:

パラメータ:
- `segment` (String): 区分
- `date` (String): 更新する日付(yyyyMMdd形式)

```java
// システムリポジトリからBasicBusinessDateProviderを取得する
BusinessDateProvider provider = SystemRepository.get("businessDateProvider");

// setDateメソッドを呼び出し、更新する
provider.setDate(segment, date);
```

**class**: nablarch.core.date.BasicBusinessDateProvider

---

## customization

ユニットテストの実行時など、システム日時や業務日付を切り替えたい場合、それぞれのProviderインターフェースを実装したクラスを作成し、コンポーネント定義で差し替える。

**system_time_customization**:

**description**: システム日時を切り替える場合

**steps**:

- SystemTimeProviderを実装したクラスを作成する
- システム日時の管理機能を使うための設定に従い、作成したクラスをコンポーネント定義に設定する

**interface**: nablarch.core.date.SystemTimeProvider

**business_date_customization**:

**description**: 業務日付を切り替える場合

**steps**:

- BusinessDateProviderを実装したクラスを作成する
- 業務日付管理機能を使うための設定に従い、作成したクラスをコンポーネント定義に設定する

**interface**: nablarch.core.date.BusinessDateProvider

---

## tips

**title**: ウェブアプリケーションでの業務日付の上書き

**description**: ウェブアプリケーションのように、全ての機能が１プロセス内で実行される場合は、単純にデータベースで管理されている日付を変更すればよい。業務日付の上書き機能は、バッチ処理のように複数プロセスで実行される場合に有用。


---
