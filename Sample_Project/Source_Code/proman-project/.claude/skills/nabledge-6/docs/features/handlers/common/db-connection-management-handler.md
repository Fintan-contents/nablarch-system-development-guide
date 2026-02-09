# データベース接続管理ハンドラ

後続のハンドラ及びライブラリで使用するためのデータベース接続を、スレッド上で管理するハンドラ

**目的**: データベースアクセスに必要な接続オブジェクトをスレッド単位で管理し、後続処理で利用可能にする


**責務**:

- データベース接続の取得

- データベース接続の解放

- スレッド上での接続管理



**モジュール**:
- `com.nablarch.framework:nablarch-core-jdbc`
- `com.nablarch.framework:nablarch-common-jdbc`

**class_name**: nablarch.common.handler.DbConnectionManagementHandler

**公式ドキュメント**:
- [データベース接続管理ハンドラ](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/handlers/common/database_connection_management_handler.html)

---

## processing

**処理フロー**:

**リクエスト処理前**: connectionFactoryプロパティに設定されたファクトリクラス(ConnectionFactory実装クラス)を使用してデータベース接続を取得し、スレッド上で管理する。データベース接続名(connectionName)をキーとして管理する。

**後続ハンドラ呼び出し**: 次のハンドラに処理を委譲。後続ハンドラおよびライブラリはDbConnectionContext.getConnection()でスレッド上の接続を取得できる。

**リクエスト処理後**: スレッド上で管理しているデータベース接続を解放する。


---

## setup

| プロパティ | 型 | 必須 | 説明 |
|-----------|-----|:----:|------|
| `connectionFactory` | `nablarch.core.db.connection.ConnectionFactory` | ✓ | データベース接続オブジェクトを取得するファクトリクラス。BasicDbConnectionFactoryForDataSourceなどのConnectionFactory実装クラスを設定する。 |
| `connectionName` | `String` |  | データベース接続名。スレッド内で一意とする必要がある。省略した場合、その接続はデフォルトのデータベース接続となる。複数のデータベース接続を使用する場合に、最もよく使う接続をデフォルトとし、それ以外に任意の名前をつけると良い。 |

**xml_example**:

```xml
<!-- データベース接続管理ハンドラ -->
<component class="nablarch.common.handler.DbConnectionManagementHandler">
  <property name="connectionFactory" ref="connectionFactory" />
</component>

<!-- データベース接続オブジェクトを取得するファクトリクラスの設定 -->
<component name="connectionFactory"
    class="nablarch.core.db.connection.BasicDbConnectionFactoryForDataSource">
  <!-- プロパティの設定は省略 -->
</component>
```

**component_name**: DbConnectionManagementHandler

---

## multiple_connections

1つのアプリケーションで複数のデータベース接続が必要となる場合、このハンドラをハンドラキュー上に複数設定することで対応する。

**xml_example**:

```xml
<!-- デフォルトのデータベース接続を設定 -->
<component class="nablarch.common.handler.DbConnectionManagementHandler">
  <property name="connectionFactory" ref="connectionFactory" />
</component>

<!-- userAccessLogという名前でデータベース接続を登録 -->
<component class="nablarch.common.handler.DbConnectionManagementHandler">
  <property name="connectionFactory" ref="userAccessLogConnectionFactory" />
  <property name="connectionName" value="userAccessLog" />
</component>
```

**connection_naming**:

**default_connection**: connectionNameプロパティへの設定を省略した場合、その接続はデフォルトのデータベース接続となり簡易的に使用できる。DbConnectionContext.getConnection()を引数なしで呼び出すと、デフォルトの接続が戻される。

**named_connection**: connectionNameプロパティに任意の名前を設定することで、名前付き接続として管理できる。DbConnectionContext.getConnection(String)に接続名を指定して呼び出すことで、対応する接続が取得できる。

**recommendation**: 最もよく使うデータベース接続をデフォルトとし、それ以外のデータベース接続に対して任意の名前をつけると良い。

**usage_example**:

**default**: AppDbConnection connection = DbConnectionContext.getConnection(); // 引数なし

**named**: AppDbConnection connection = DbConnectionContext.getConnection("userAccessLog"); // 接続名を指定

---

## constraints

**handler_order**:

**before**:


**after**:


**reason**: このハンドラ自体には順序制約はない。ただし、データベースアクセスを行う全てのハンドラより前に配置する必要がある。

**limitations**:


**notes**:

- このハンドラを使用する場合は、TransactionManagementHandlerをセットで設定すること。トランザクション制御ハンドラが設定されていない場合、トランザクション制御が実施されないため後続で行ったデータベースへの変更は全て破棄される。
- データベース接続オブジェクトを取得するためのファクトリクラスの詳細は、データベースアクセス機能を参照すること。

---
