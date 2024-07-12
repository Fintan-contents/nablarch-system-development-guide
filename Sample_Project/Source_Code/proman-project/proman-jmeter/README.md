# proman-jmeter
本ツールは、JMeterを利用した取引単体テストを自動実行するためのものです。

初期状態では、サンプルプロジェクトのプロジェクト管理システムに対してテストを実行できる形で構成されています。  
プロジェクト管理システムに対してテストシナリオを実行する方法については、 [取引単体テストの自動実行方法（Web）](../../../サンプルプロジェクト開発ガイド/PGUT工程/ut/取引単体テストの自動実行方法（Web）.md) を参照してください。

ここでは、本ツールの仕組みや使い方について解説します。

## 動作環境
実行環境に以下のソフトウェアがインストールされている事を前提とします。
* Java Version : 21
* Maven 3.9.8
* PostgreSQL 16.2

### データベースのサポート状況について
現在、動作確認ができているデータベースがPostgreSQLのみとなっています。  
これ以外のデータベース製品については、データの登録で使用している単体テスト用データダンプツールが正常に動作しない可能性があります。

## 前提・制約
### 二重サブミットトークンのHIDDEN暗号は使用しない
Nablarchの機能に、二重サブミットトークンをHIDDEN暗号化する機能が存在します。  
この機能が有効になっていると、JMeterによるリクエストの実行が失敗します。

したがって、本ツールを使用する場合は二重サブミットトークンのHIDDEN暗号化機能は使用しないようにしてください。

HIDDEN暗号化機能を無効化する方法は、解説書の[クライアントに保持するデータを暗号化する(hidden暗号化)](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/tag.html#tag-hidden-encryption)を参照してください。

### 実行都度変化する要素について
実行するたびに結果が変化するような要素が存在すると、アサーションが失敗してしまいます。  
これには例えば、次のようなケースが該当します。

- 画面上に実行日や時刻を出力している
- データベースのテーブルでシーケンス番号や更新日時を記録しているカラム

画面上に出力しているケースでは、テストのときだけその部分をモック化する（固定値を出力する）などの対応が必要となります。  
アプリケーションで対応が可能な場合はモック化してください。  
モック化が難しい場合は、[レスポンスファイル作成時に特定の項目を検証から除外する](#レスポンスファイル作成時に特定の項目を検証から除外する)を参照して対応してください。

データベースのカラムが該当する場合は、そのカラムを検証対象から除外する必要があります。  
特定のカラムを検証から除外する方法については、[特定のカラムを検証対象から除外する](#特定のカラムを検証対象から除外する)を参照してください。


## 仕組み
### 全体像
![ツールの全体像](image/tool-overview.jpg)

1. 本ツールはJUnitで作られており、Mavenの `test` フェーズを指定して実行します
2. まず、単体テスト用データダンプツールを使ってデータベースに初期データを登録します
3. 次に、JMeterを使ってテストシナリオを実行します
4. アプリケーションが返却したHTMLなどのファイルが期待通りか検証します
5. 最後に、DBUnitを使ってデータベースの状態が期待通りか検証します

### ディレクトリ構成
```
proman-jmeter/
 |-log/
 |-target/
 | |-test-classes/
 | :
 `-src/test/
   |-java/
   `-resources/
```

| ディレクトリ          | 説明                                                                           |
| --------------------- | ------------------------------------------------------------------------------ |
| `log`                 | 本ツールのログが出力されます。                                                 |
| `target/test-classes` | テスト実行時のレスポンスはこの下の、各シナリオディレクトリの下に出力されます。 |
| `src/test/java`       | 本ツールのソースが格納されています。この下のソースを触ることはありません。     |
| `src/test/resources`  | テストシナリオや設定ファイルが格納されています。                               |

### テストシナリオ
JMeterで実行する１回分のテストを**テストシナリオ**と呼びます。  
テストシナリオは、１つの jmx ファイル（JMeterのテスト計画ファイル）と、いくつかのデータファイルで構成されます。

テストシナリオは、 `src/test/resources` の下に次のようにして配置してください。

```
src/test/resources
 |-[取引ID]
 : |-[シナリオID]   * このディレクトリ１つ１つが、１つのテストシナリオ
   |-[シナリオID]
   : 
```

`[取引ID]` と `[シナリオID]` は、取引単体テスト仕様書に合わせて設定してください。

そして、各テストシナリオのディレクトリは、次のようなディレクトリ構成にします。

```
[シナリオID]
 |-scenario/
 | `-[シナリオ名].jmx
 |
 |-db_dump/
 | |-INSERT.xlsm
 | `-EXPECTED.xlsx
 |
 |-response/
 |-input/
 |-output/
 |-log/
 `-hard_copy/
```

#### scenario/[シナリオ名].jmx
JMeterのテスト計画ファイルです。  
`[シナリオ名]`には任意の分かりやすい名前を設定してください。

#### db_dump/INSERT.xlsm
単体テスト用データダンプツールのExcelデータファイルです。  
JMeter実行前にデータベースに登録するデータを設定しておきます。

#### db_dump/EXPECTED.xlsx
JMeter実行後にDBUnitで検証するデータを設定しておくExcelファイルです。

DBUnitによる検証は、このファイルが配置されているときだけ実施されます。

#### response/
初回の打鍵テストで取得したレスポンスのHTMLやJS, CSSファイルを格納します。  
JMeter実行後に、ここのファイルと実際のレスポンスが比較検証されます。

#### input/
テスト対象の処理のインプットとなるデータ(データベース以外)を格納します。  
本ツールでは使用しません。

#### output/
ファイル・メール・帳票等、テスト対象の取引でアウトプットされるものを格納します。  
本ツールでは使用しません。

#### log/
打鍵テストをしたときの、アプリ等のログを保存しておくディレクトリです。  
本ツールでは使用しません。

#### hard_copy/
打鍵テストをしたときの、画面ハードコピーを保存しておくディレクトリです。  
本ツールでは使用しません。

### 設定ファイル
`src/test/resources/env.properties` が、本ツールの設定ファイルになります。  
この設定ファイルで、テスト対象のアプリケーションサーバーや、データベースの接続情報などを設定します。

以下で、各設定項目について説明します。

#### jmeter.home
本ツールを動かす環境にインストールしているJMeterのインストールディレクトリを設定します。  
ここで設定したディレクトリの下に、JMeterの `bin` ディレクトリなどがある状態にしてください。

**設定例**

```properties
jmeter.home=C:\\tools\\apache-jmeter-5.6.3
```

#### server.host
JMeterで接続するサーバーのホスト情報（ホスト名またはIPアドレス）を設定します。

JMeterの接続先サーバー情報は、jmxファイル内のユーザ定義変数にも設定されています。  
しかし、本ツールからjmxファイルを実行した場合は、 `env.properties` の `server.host` の値でユーザ定義変数が上書きされるようになっています。  
これにより、jmxファイルを変更することなく、 `env.properties` のみの変更で接続先を変更できます。

**設定例**

```properties
server.host=demo-server
```

#### server.port
JMeterで接続するサーバーのポート番号を設定します。

`server.host` 同様、jmxファイルのユーザ定義変数はここで設定した値で上書きされます。

**設定例**

```properties
server.port=80
```

#### response.encoding
レスポンスファイルの内容を読み込むときにしようする文字コードを設定します。

**設定例**

```properties
response.encoding=UTF-8
```

#### database.driver
データベース接続で使用するJDBCドライバの完全修飾名を設定します。

この設定は、DBUnitと単体テスト用データダンプツールの両方で使用されます。

単体テスト用データダンプツールはExcelシート上にDBの接続設定を記述できるようになっていますが、本ツールから実行した場合はこの設定ファイルの値が使用されます。  
これにより、シナリオごとのExcelシートを編集することなく、 `env.properties` だけの修正で接続先データベースを切り替えられます。

**設定例**

```properties
database.driver=org.postgresql.Driver
```

#### database.url
データベースの接続URLを設定します。

`database.driver` 同様、この設定はDBUnitと単体テスト用データダンプツールで使用されます。

**設定例**

```properties
database.url=jdbc:postgresql://localhost:5432/postgres
```

#### database.username
データベースの接続ユーザ名を設定します。

`database.driver` 同様、この設定はDBUnitと単体テスト用データダンプツールで使用されます。

**設定例**

```properties
database.username=postgres
```

#### database.password
データベースの接続パスワードを設定します。

`database.driver` 同様、この設定はDBUnitと単体テスト用データダンプツールで使用されます。

**設定例**

```properties
database.password=password
```

## テストシナリオの作り方
ここでは、テストシナリオを新規に作成する場合の手順について説明します。

### jmxを作成する
各テストシナリオで実行するJMeterのテスト計画ファイルを作成します。

具体的な手順は、[取引単体テストのテスト方法（Web）](../../../サンプルプロジェクト開発ガイド/PGUT工程/ut/取引単体テストのテスト方法（Web）.md)の「JMeterでリクエストを記録する」を参照してください。

ここで作成したjmxファイルを、各シナリオディレクトリの `scenario` ディレクトリの下に格納してください。

### INSERT.xlsmを作成する
JMeter実行前にデータベースに登録するデータを作成します。

具体的な手順は、[取引単体テストのテスト方法（Web）](../../../サンプルプロジェクト開発ガイド/PGUT工程/ut/取引単体テストのテスト方法（Web）.md) の「テストデータを準備する」を参照してください。  
作成した `DUMP.xlsm` ファイルを `INSERT.xlsm` にリネームして、各シナリオディレクトリの `db_dump` ディレクトリの下に格納してください。

### EXPECTED.xlsxを作成する
DBUnitで検証するための期待値となるデータを作成します。

このファイルは、データベースを更新するテストの場合でのみ必要になります。  
データベースを更新しない検索系のテストでは不要なので、次の「レスポンスファイルを作成する」に進んでください。

このデータは、初回に手動で打鍵テストを行った後のデータベースを、単体テスト用データダンプツールを使ってダンプすることで取得します。  
単体テスト用データダンプツールを使ってデータベースのダンプを取得する方法については、[エビデンスの取得方法（ログとDBダンプ）](../../../サンプルプロジェクト開発ガイド/PGUT工程/ut/エビデンスの取得方法（ログとDBダンプ）.md) の「エビデンスの取得方法」を参照してください。

単体テスト用データダンプツールで取得したダンプファイルは、そのままではDBUnitで使用できません。  
DBUnitで使用できるようにするため、次の編集を行う必要があります。

#### テーブルをシートごとに分ける
単体テスト用データダンプツールのダンプファイルでは、１シートに抽出対象のテーブルのデータが全て出力されます。  
一方で、DBUnitで使用するExcelファイルでは、１シートにつき１テーブルのデータを記述します。

したがって、単体テスト用データダンプツールで取得したダンプファイルを元に、１シート１テーブルとなるようにファイルを加工する必要があります。  
このとき、各シートは次のようなルールで記述してください。

- シート名はテーブルの物理名と一致させる
- １行目はカラムの物理名とする
- ２行目以降にデータを列挙していく
- `null` の項目は空セルにする（一括置換などを活用してください）

また、セルの書式設定などが残っていると、見た目が空でもDBUnitがデータ行と誤認識することがあるので、下の余白行を選択して明示的に削除しておくことをおすすめします。

#### 特定のカラムを検証対象から除外する
カラムによっては、実行するたびに格納される値が変わるものがあります（シーケンスで採番するカラムや、実行日時を保存するようなカラムなど）。  
このようなカラムを検証対象に含めていると、再実行時に必ずテストが失敗してしまいます。  
このため、そのようなカラムは検証対象から外す必要があります。  
DBUnitの検証から特定のカラムを除外するには、そのカラムの列を削除してください。

以上の修正を行ったExcelファイルを、 `EXPECTED.xlsx` という名前で保存してください。  
保存先は、各シナリオディレクトリの `db_dump` ディレクトリの下です。

### レスポンスファイルを作成する
レスポンスを比較検証するために、期待値となるファイルを作成します。

期待値となるファイルは、初回に行った打鍵テストの結果を使用します。  
具体的な取得方法は、[取引単体テストのテスト方法（Web）](../../../サンプルプロジェクト開発ガイド/PGUT工程/ut/取引単体テストのテスト方法（Web）.md)の「レスポンスを記録する」を参照してください。

ここで取得したファイルを、そのまま各シナリオディレクトリの `response` ディレクトリの下に格納してください。

#### レスポンスファイル作成時に特定の項目を検証から除外する

レスポンスファイルの保存は[JSR223 PostProcessor](https://jmeter.apache.org/usermanual/component_reference.html#JSR223_PostProcessor)というJMeterのコンポーネントを利用しています。
[JSR223 PostProcessor](https://jmeter.apache.org/usermanual/component_reference.html#JSR223_PostProcessor)の機能を利用して、特定の項目を固定値に上書きするか削除することで検証対象外にできます。

[テスト計画-テンプレート.jmx](../../../サンプルプロジェクト開発ガイド/PGUT工程/ut/取引単体テストツール/テスト計画-テンプレート.jmx)では二重サブミットトークン、CSRFトークン、JSESSIONIDを書き換えています。
書き換えを行っているのは以下の部分です。

```groovy
// エビデンスとして使用するファイルには実行ごとに変更される値（二重サブミットトークン、CSRFトークン）は書き出さない。
def responseWithoutNablarchToken = prev.getResponseDataAsString().replaceAll(/(input type="hidden" name="nablarch_hidden" value=")(.*?)(nablarch_token=[^"\|]+)(\|?)(.*?")/, "\$1\$2nablarch_token=TMP_VALUE_FOR_EVIDENCE\$4\$5").replaceAll(/(input type="hidden" name="nablarch_hidden" value=")(.*?)(csrf-token=[^"\|]+)(\|?)(.*?")/, "\$1\$2csrf-token=TMP_VALUE_FOR_EVIDENCE\$4\$5");
// jsessionid を削除
def responseWithoutJsessionId = responseWithoutNablarchToken.replaceAll(/;jsessionid=[a-zA-Z0-9.]+/, "");
```

`String#replaceAll(String regex, String replacement)` で、取得したレスポンスに対して正規表現での置換を行っています。

上記の方法を利用することで、特定の項目を検証から除外できます。