# トランザクション制御ハンドラ

データベースやメッセージキューなどのトランザクションに対応したリソースを使用し、後続処理における透過的トランザクションを実現するハンドラ

**目的**: 後続処理のトランザクション境界を管理し、正常終了時のコミット、異常終了時のロールバックを自動的に行う


**責務**:

- トランザクションの開始

- トランザクションの終了(コミットやロールバック)

- トランザクションの終了時のコールバック



**モジュール**:
- `com.nablarch.framework:nablarch-core-transaction`
- `com.nablarch.framework:nablarch-core-jdbc` (データベースに対するトランザクションを制御する場合のみ)
- `com.nablarch.framework:nablarch-core` (トランザクション終了時に任意の処理を実行する場合のみ)

**class_name**: nablarch.common.handler.TransactionManagementHandler

**公式ドキュメント**:
- [トランザクション制御ハンドラ](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/handlers/common/transaction_management_handler.html)

---

## processing

**処理フロー**:

**リクエスト処理前**: transactionFactoryプロパティに設定されたファクトリクラス(TransactionFactory実装クラス)を使用してトランザクションの制御対象を取得し、トランザクションを開始する。トランザクションはスレッド上でtransactionName(デフォルトは'transaction')をキーとして管理される。

**後続ハンドラ呼び出し**: 次のハンドラに処理を委譲。後続ハンドラで実行される業務処理は、開始されたトランザクション内で実行される。

**リクエスト処理後（正常）**: 後続ハンドラが正常終了した場合、トランザクションをコミットする。コミット後、後続ハンドラの中でTransactionEventCallbackを実装しているハンドラに対してtransactionNormalEndをコールバックする。

**リクエスト処理後（異常）**: 後続ハンドラでエラーや例外が発生した場合、トランザクションをロールバックする。ロールバック後、新しいトランザクションを開始し、TransactionEventCallbackを実装しているハンドラに対してtransactionAbnormalEndをコールバックする。コールバックが正常終了するとコミットする。


**transaction_boundary**: 後続ハンドラの処理全体がトランザクション境界となる。コールバック処理は、正常終了時は同一トランザクション内で実行されないが、ロールバック時は新しいトランザクション内で実行される。

---

## setup

| プロパティ | 型 | 必須 | 説明 |
|-----------|-----|:----:|------|
| `transactionFactory` | `nablarch.core.transaction.TransactionFactory` | ✓ | トランザクション制御を行うファクトリクラス。データベースに対するトランザクション制御を行う場合はJdbcTransactionFactoryを設定する。 |
| `transactionName` | `String` |  | トランザクションを識別するための名前。複数のトランザクションを使用する場合は必須。DbConnectionManagementHandlerのconnectionNameに設定した値と同じ値を設定すること。 (デフォルト: `transaction`) |
| `transactionCommitExceptions` | `List<String>` |  | コミット対象の例外クラスのリスト(FQCN)。デフォルトでは全てのエラー及び例外がロールバック対象となるが、特定の例外の場合にトランザクションをコミットしたい場合に設定する。設定した例外クラスのサブクラスもコミット対象となる。 |

**xml_example**:

```xml
<!-- トランザクション制御ハンドラ -->
<component class="nablarch.common.handler.TransactionManagementHandler">
  <property name="transactionFactory" ref="databaseTransactionFactory" />
  <property name="transactionName" value="name" />
</component>

<!-- データベースに対するトランザクション制御を行う場合には、JdbcTransactionFactoryを設定する -->
<component name="databaseTransactionFactory"
    class="nablarch.core.db.transaction.JdbcTransactionFactory">
  <!-- プロパティの設定は省略 -->
</component>
```

**component_name**: TransactionManagementHandler

---

## commit_exceptions

デフォルト動作では、全てのエラー及び例外がロールバック対象となるが、発生した例外の内容によってはトランザクションをコミットしたい場合がある。

**xml_example**:

```xml
<component class="nablarch.common.handler.TransactionManagementHandler">
  <!-- transactionCommitExceptionsプロパティにコミット対象の例外クラスをFQCNで設定する。 -->
  <property name="transactionCommitExceptions">
    <list>
      <!-- example.TransactionCommitExceptionをコミット対象とする -->
      <value>example.TransactionCommitException</value>
    </list>
  </property>
</component>
```

**configuration**: transactionCommitExceptionsプロパティに対して、コミット対象の例外クラスを設定することで対応する。設定した例外クラスのサブクラスもコミット対象となる。

---

## callback

トランザクション終了(コミットやロールバック)時に、コールバック処理を行う機能を提供する。

**xml_example**:

```xml
<list name="handlerQueue">
  <!-- トランザクション制御ハンドラ -->
  <component class="nablarch.common.handler.TransactionManagementHandler">
    <!-- プロパティへの設定は省略 -->
  </component>

  <!-- コールバック処理を実装したハンドラ -->
  <component class="sample.SampleHandler" />
</list>
```

**callback_interface**: nablarch.fw.TransactionEventCallback

**callback_methods**:

- **method**: transactionNormalEnd
- **signature**: void transactionNormalEnd(TData data, ExecutionContext context)
- **description**: トランザクションコミット時のコールバック処理。正常終了時のコールバックは、トランザクションコミット後に実行される。
- **method**: transactionAbnormalEnd
- **signature**: void transactionAbnormalEnd(Throwable e, TData data, ExecutionContext context)
- **description**: トランザクションロールバック時のコールバック処理。ロールバック後に新しいトランザクションで実行され、コールバックが正常に終了するとコミットされる。

**callback_target**: このハンドラより後続に設定されたハンドラの中で、TransactionEventCallbackを実装しているものがコールバック対象となる。複数のハンドラが実装している場合は、より手前に設定されているハンドラから順次コールバック処理を実行する。

**callback_error_handling**: 複数のハンドラがコールバック処理を実装していた場合で、コールバック処理中にエラーや例外が発生した場合は、残りのハンドラに対するコールバック処理は実行しない。

---

## multiple_transactions

1つのアプリケーションで複数のトランザクション制御が必要となる場合、このハンドラをハンドラキュー上に複数設定することで対応する。

**xml_example**:

```xml
<!-- デフォルトのデータベース接続を設定 -->
<component name="defaultDatabaseHandler"
    class="nablarch.common.handler.DbConnectionManagementHandler">
  <property name="connectionFactory" ref="connectionFactory" />
</component>

<!-- userAccessLogという名前でデータベース接続を登録 -->
<component name="userAccessLogDatabaseHandler"
    class="nablarch.common.handler.DbConnectionManagementHandler">
  <property name="connectionFactory" ref="userAccessLogConnectionFactory" />
  <property name="connectionName" value="userAccessLog" />
</component>

<!-- デフォルトのデータベース接続に対するトランザクション制御の設定 -->
<component name="defaultTransactionHandler"
    class="nablarch.common.handler.TransactionManagementHandler">
  <property name="transactionFactory" ref="databaseTransactionFactory" />
</component>

<!-- userAccessLogというデータベース接続に対するトランザクション制御の設定 -->
<component name="userAccessLogTransactionHandler"
    class="nablarch.common.handler.TransactionManagementHandler">
  <property name="transactionFactory" ref="databaseTransactionFactory" />
  <property name="transactionName" value="userAccessLog" />
</component>

<!-- ハンドラキューへの設定 -->
<list name="handlerQueue">
  <!-- デフォルトのデータベースに対する接続とトランザクション制御 -->
  <component-ref name="defaultDatabaseHandler" />
  <component-ref name="defaultTransactionHandler" />

  <!-- userAccessLogのデータベースに対する接続とトランザクション制御 -->
  <component-ref name="userAccessLogDatabaseHandler" />
  <component-ref name="userAccessLogTransactionHandler" />
</list>
```

**configuration_rule**: 複数のトランザクションを使用する場合、transactionNameプロパティへの値の設定が必須となる。DbConnectionManagementHandlerで設定したデータベースに対するトランザクションを制御する場合は、DbConnectionManagementHandler#connectionNameに設定した値と同じ値をtransactionNameプロパティに設定すること。

---

## constraints

**handler_order**:

**before**:


**after**:

- DbConnectionManagementHandler

**reason**: データベースに対するトランザクションを制御する場合には、トランザクション管理対象のデータベース接続がスレッド上に存在している必要がある。このため、本ハンドラはDbConnectionManagementHandlerより後ろに配置する必要がある。

**limitations**:


**notes**:

- DbConnectionManagementHandlerのconnectionNameに設定した値と同じ値をtransactionNameプロパティに設定すること。
- connectionNameに値を設定していない場合は、transactionNameへの設定は省略して良い。

---
