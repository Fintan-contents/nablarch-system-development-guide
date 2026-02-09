# データリードハンドラ

データリーダを使用して、入力データの順次読み込みを行なうハンドラ。実行コンテキスト上のデータリーダを使用し、業務処理に対する入力データを1件ずつ読み込み、それを引数として後続ハンドラに処理を委譲する。

**目的**: バッチ処理における入力データの順次読み込みを制御し、データ終端の判定を行う


**責務**:

- データリーダを使用して入力データの読み込み

- 実行時IDの採番

- データ終端の判定(NoMoreRecordの返却)



**モジュール**:
- `com.nablarch.framework:nablarch-fw-standalone`

**class_name**: nablarch.fw.handler.DataReadHandler

**公式ドキュメント**:
- [データリードハンドラ](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/handlers/standalone/data_read_handler.html)

---

## processing

**処理フロー**:

**リクエスト処理前**: 実行コンテキスト(ExecutionContext)上のデータリーダ(DataReader)を取得する。データリーダが設定されていない場合、処理対象データ無しとしてNoMoreRecordを返却して処理を終了する。

**データ読み込みループ**: データリーダから入力データを1件読み込み、それを引数として後続ハンドラに処理を委譲する。最大処理件数(maxCount)が設定されている場合は、その件数に達するまで繰り返す。データリーダの終端に達した場合、またはmaxCountに達した場合はNoMoreRecordを返却する。

**実行時ID採番**: 各レコード処理時に実行時IDを採番する。


**data_reader**:

**interface**: nablarch.fw.DataReader

**source**: ExecutionContextに設定されたDataReaderを使用

**end_marker**: nablarch.fw.DataReader.NoMoreRecord

---

## setup

| プロパティ | 型 | 必須 | 説明 |
|-----------|-----|:----:|------|
| `maxCount` | `int` |  | 最大の処理件数。この件数分のデータを処理し終わると、本ハンドラは処理対象レコードなしを示すNoMoreRecordを返却する。大量データを処理するバッチ処理を数日に分けて処理させる場合などに指定する。例えば、最大100万件を処理するバッチを、日次で最大10万件だけ処理をさせ10日間かけて全件を処理させることが実現できる。 |

**xml_example**:

```xml
<component class="nablarch.fw.handler.DataReadHandler">
  <!-- 処理する件数は、最大1万レコード -->
  <property name="maxCount" value="10000" />
</component>
```

**component_name**: DataReadHandler

---

## max_count

本ハンドラには、最大の処理件数を設定することが出来る。最大処理件数分のデータを処理し終わると、本ハンドラは処理対象レコードなしを示すNoMoreRecordを返却する。

**example**:

```java
最大100万件を処理するバッチを、日次で最大10万件だけ処理をさせ10日間かけて全件を処理させることが実現できる。
```

**use_case**: 大量データを処理するバッチ処理を数日に分けて処理させる場合などに指定する。

---

## constraints

**handler_order**:

**before**:


**after**:


**reason**: 本ハンドラ自体に順序制約はないが、実行コンテキストにDataReaderが設定されている必要があるため、DataReaderを設定するハンドラより後に配置する必要がある。

**limitations**:


**notes**:

- 本ハンドラより手前のハンドラにて、ExecutionContextにDataReaderを設定する必要がある。
- 本ハンドラが呼び出されたタイミングでDataReaderが設定されていない場合、処理対象データ無しとして本ハンドラは処理を終了(NoMoreRecordを返却)する。

---
