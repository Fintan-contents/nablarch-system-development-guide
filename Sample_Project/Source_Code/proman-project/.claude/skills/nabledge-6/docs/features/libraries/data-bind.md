# データバインド

CSVやTSV、固定長といったデータをJava BeansオブジェクトまたはMapオブジェクトとして扱う機能を提供する。データファイルとJavaオブジェクト間の双方向変換をサポートする。

**目的**: データファイルのデータをオブジェクト指向的に扱い、CSV/TSV/固定長ファイルの読み書きを簡潔に実装できるようにする。アノテーションまたはDataBindConfigでフォーマットを定義することで、様々な形式のファイルに対応可能。


**モジュール**:
- `com.nablarch.framework:nablarch-common-databind`

**機能**:

- データをJava Beansオブジェクトとして扱える（BeanUtilによる自動型変換）

- データをMapオブジェクトとして扱える（値は全てString型）

- フォーマット指定はアノテーションまたはDataBindConfigで定義

- CSV/TSV/固定長ファイルをサポート

- 複数フォーマットを持つ固定長ファイル（マルチレイアウト）に対応

- 論理行番号の取得が可能（@LineNumber）

- Bean Validationとの連携による入力値チェック

- ファイルダウンロード/アップロード機能との連携



**classes**:

- nablarch.common.databind.ObjectMapper

- nablarch.common.databind.ObjectMapperFactory

- nablarch.common.databind.DataBindConfig

- nablarch.core.beans.BeanUtil



**annotations**:

- @Csv
- @CsvFormat
- @FixedLength
- @Field
- @LineNumber
- @Record
- @Lpad
- @Rpad

**公式ドキュメント**:
- [データバインド](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/data_io/data_bind.html)

---

## modules

**依存関係**:

- `com.nablarch.framework:nablarch-common-databind` [必須] - データバインド機能のコアモジュール
- `com.nablarch.framework:nablarch-fw-web-extension` [任意] - ファイルダウンロード機能を使用する場合に必要

---

## usage

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `ObjectMapperFactory.create (Java Beans読み込み用)` | `public static <T> ObjectMapper<T> create(Class<T> entityClass, InputStream inputStream)` | Java Beansクラスにバインドしてデータを読み込むためのObjectMapperを生成する。Java Beansクラスに定義されたアノテーションをもとにフォーマットを決定する。 |
| `ObjectMapperFactory.create (Java Beans書き込み用)` | `public static <T> ObjectMapper<T> create(Class<T> entityClass, OutputStream outputStream)` | Java Beansオブジェクトをデータファイルに書き込むためのObjectMapperを生成する。Java Beansクラスに定義されたアノテーションをもとにフォーマットを決定する。 |
| `ObjectMapperFactory.create (Map読み込み用)` | `public static <T> ObjectMapper<T> create(Class<T> clazz, InputStream inputStream, DataBindConfig config)` | Mapオブジェクトにバインドしてデータを読み込むためのObjectMapperを生成する。DataBindConfigで指定したフォーマット設定をもとにデータを読み込む。 |
| `ObjectMapperFactory.create (Map書き込み用)` | `public static <T> ObjectMapper<T> create(Class<T> clazz, OutputStream outputStream, DataBindConfig config)` | Mapオブジェクトをデータファイルに書き込むためのObjectMapperを生成する。DataBindConfigで指定したフォーマット設定をもとにデータを書き込む。 |
| `ObjectMapper.read` | `public T read() throws IOException, InvalidDataFormatException` | データファイルから1データずつ読み込み、Java BeansまたはMapオブジェクトとして返却する。全データ読み込み後はnullを返す。 |
| `ObjectMapper.write` | `public void write(T object) throws IOException` | Java BeansまたはMapオブジェクトの内容をデータファイルに1データずつ書き込む。プロパティ値がnullの場合は空文字が出力される。 |
| `ObjectMapper.close` | `public void close() throws IOException` | ObjectMapperが使用しているリソースを解放する。全データの読み込み・書き込み完了後に必ず呼び出すこと。try-with-resourcesを使用することで自動的にクローズ処理が実行される。 |

**ObjectMapperFactory.create (Java Beans読み込み用)**:

パラメータ:
- `entityClass` (Class<T>): バインド対象のJava Beansクラス
- `inputStream` (InputStream): 読み込み元のストリーム

戻り値: ObjectMapper<T> - データ読み込み用のマッパー

```java
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    Person person;
    while ((person = mapper.read()) != null) {
        // 処理
    }
}
```

**ObjectMapperFactory.create (Java Beans書き込み用)**:

パラメータ:
- `entityClass` (Class<T>): バインド対象のJava Beansクラス
- `outputStream` (OutputStream): 書き込み先のストリーム

戻り値: ObjectMapper<T> - データ書き込み用のマッパー

```java
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, outputStream)) {
    for (Person person : personList) {
        mapper.write(person);
    }
}
```

**ObjectMapperFactory.create (Map読み込み用)**:

パラメータ:
- `clazz` (Class<T>): Map.classを指定
- `inputStream` (InputStream): 読み込み元のストリーム
- `config` (DataBindConfig): フォーマット設定（CsvDataBindConfigまたはFixedLengthDataBindConfig）

戻り値: ObjectMapper<Map> - Map形式でのデータ読み込み用マッパー

```java
DataBindConfig config = CsvDataBindConfig.DEFAULT.withHeaderTitles("年齢", "名前")
                                                 .withProperties("age", "name");
try (ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, inputStream, config)) {
    Map person;
    while ((person = mapper.read()) != null) {
        // 処理
    }
}
```

**ObjectMapperFactory.create (Map書き込み用)**:

パラメータ:
- `clazz` (Class<T>): Map.classを指定
- `outputStream` (OutputStream): 書き込み先のストリーム
- `config` (DataBindConfig): フォーマット設定（CsvDataBindConfigまたはFixedLengthDataBindConfig）

戻り値: ObjectMapper<Map> - Map形式でのデータ書き込み用マッパー

```java
DataBindConfig config = CsvDataBindConfig.DEFAULT.withHeaderTitles("年齢", "名前")
                                                 .withProperties("age", "name");
try (ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, outputStream, config)) {
    for (Map<String, Object> person : personList) {
        mapper.write(person);
    }
}
```

**ObjectMapper.read**:

戻り値: T - 読み込んだデータのオブジェクト（全データ読み込み後はnull）

例外:
- IOException - I/Oエラー発生時
- InvalidDataFormatException - データフォーマット不正時

```java
Person person;
while ((person = mapper.read()) != null) {
    // Java Beansオブジェクトごとの処理
}
```

**ObjectMapper.write**:

パラメータ:
- `object` (T): 書き込むオブジェクト（Java BeansまたはMap）

戻り値: void

例外:
- IOException - I/Oエラー発生時

```java
for (Person person : personList) {
    mapper.write(person);
}
```

**ObjectMapper.close**:

戻り値: void

例外:
- IOException - I/Oエラー発生時

```java
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    // 処理
} // 自動的にclose()が呼ばれる
```

**typical_usage**:

**file_to_bean**:

**description**: データファイルを先頭から1データずつ読み込み、Java Beansオブジェクトとして取得する。Java Beansクラスに定義されたアノテーションをもとにデータを読み込む。読み込み時にBeanUtilを使用して自動的に型変換が行われ、型変換に失敗した場合は例外が発生する。

**example**: try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    Person person;
    while ((person = mapper.read()) != null) {
        // Java Beansオブジェクトごとの処理を記述
    }
} catch (InvalidDataFormatException e) {
    // 読み込んだデータのフォーマットが不正な場合の処理を記述
}

**bean_to_file**:

**description**: Java Beansオブジェクトの内容をデータファイルに1データずつ書き込む。Java Beansクラスに定義されたアノテーションをもとにデータを書き込む。プロパティの値がnullの場合は未入力を表す値（CSVファイルの場合は空文字）が出力される。

**example**: try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, outputStream)) {
    for (Person person : personList) {
        mapper.write(person);
    }
}

**file_to_map**:

**description**: データファイルを先頭から1データずつ読み込み、Mapオブジェクトとして取得する。DataBindConfigの設定値をもとにデータを読み込む。Mapオブジェクトへの変換時、値は全てString型で格納される。

**example**: DataBindConfig config = CsvDataBindConfig.DEFAULT.withHeaderTitles("年齢", "名前")
                                                 .withProperties("age", "name");
try (ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, inputStream, config)) {
    Map person;
    while ((person = mapper.read()) != null) {
        // Mapオブジェクトごとの処理を記述
    }
} catch (InvalidDataFormatException e) {
    // 読み込んだデータのフォーマットが不正な場合の処理を記述
}

**map_to_file**:

**description**: Mapオブジェクトの内容をデータファイルに1データずつ書き込む。DataBindConfigの設定値をもとにデータを書き込む。Mapオブジェクトのvalue値がnullの場合は未入力を表す値（CSVファイルの場合は空文字）が出力される。

**example**: DataBindConfig config = CsvDataBindConfig.DEFAULT.withHeaderTitles("年齢", "名前")
                                                 .withProperties("age", "name");
try (ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, outputStream, config)) {
    for (Map<String, Object> person : personList) {
        mapper.write(person);
    }
}

**line_number**:

**description**: ファイルのデータをJava Beansオブジェクトとして取得する際、Java Beansクラスにプロパティを定義して@LineNumberアノテーションを使用することで、データの論理行番号も一緒に取得できる。入力値チェック時にバリデーションエラーが発生したデータの行番号をログに出力したい場合などに使用する。

**example**: // Java Beansクラスの定義
private Long lineNumber;

@LineNumber
public Long getLineNumber() {
    return lineNumber;
}

// 使用例
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    Person person;
    while ((person = mapper.read()) != null) {
        System.out.println("行番号: " + person.getLineNumber());
        // 処理
    }
}

**note**: Mapオブジェクトとして取得する場合は、データの行番号を取得できない点に注意すること。

**validation**:

**description**: データをJava Beansオブジェクトとして読み込むことができるため、Bean Validationによる入力値チェックを行うことができる。

**example**: try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    Person person;
    while ((person = mapper.read()) != null) {
        // 入力値チェックを実行
        ValidatorUtil.validate(person);
        // 後続の処理
    }
} catch (InvalidDataFormatException e) {
    // データファイルのフォーマット不正時の処理を記述
}

**file_download**:

**description**: ウェブアプリケーションで、Java Beansオブジェクトの内容をデータファイルとしてダウンロードする。データをメモリ上に展開すると大量データのダウンロード時などにメモリを圧迫する恐れがあるため、一時ファイルに出力する。FileResponseオブジェクト生成時にデータファイルを指定し、レスポンスにContent-Type及びContent-Dispositionを設定する。

**example**: public HttpResponse download(HttpRequest request, ExecutionContext context) {
    // 業務処理

    final Path path = Files.createTempFile(null, null);
    try (ObjectMapper<Person> mapper =
            ObjectMapperFactory.create(Person.class, Files.newOutputStream(path))) {
        for (Person person : persons) {
            mapper.write(BeanUtil.createAndCopy(PersonDto.class, person));
        }
    }

    // ファイルをボディに設定する。
    FileResponse response = new FileResponse(path.toFile(), true);

    // Content-Typeヘッダ、Content-Dispositionヘッダを設定する
    response.setContentType("text/csv; charset=Shift_JIS");
    response.setContentDisposition("person.csv");

    return response;
}

**points**:

- データをメモリ上に展開すると大量データのダウンロード時などにメモリを圧迫する恐れがあるため、一時ファイルに出力する
- FileResponseのコンストラクタの第二引数にtrueを指定すると、リクエスト処理の終了時に自動的にファイルを削除する
- レスポンスにContent-Type及びContent-Dispositionを設定する

**upload_file**:

**description**: ウェブアプリケーションで、画面からアップロードされたデータファイルをJava Beansオブジェクトとして読み込む。PartInfo#getInputStreamを使用してアップロードファイルのストリームを取得し、不正なデータが入力されている可能性があるため、Bean Validationを使用して入力チェックを行う。

**example**: List<PartInfo> partInfoList = request.getPart("uploadFile");
if (partInfoList.isEmpty()) {
    // アップロードファイルが見つからない場合の処理を記述
}

PartInfo partInfo = partInfoList.get(0);
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, partInfo.getInputStream())) {
    Person person;
    while ((person = mapper.read()) != null) {
        // 入力値チェックを実行
        ValidatorUtil.validate(person);
        // 後続の処理は省略
    }
} catch (InvalidDataFormatException e) {
    // データファイルのフォーマット不正時の処理を記述
}

**points**:

- PartInfo#getInputStreamを使用して、アップロードファイルのストリームを取得する
- 不正なデータが入力されている可能性があるため、Bean Validationを使用して入力チェックを行う

---

## csv_format_beans

Java BeansクラスにバインドしてCSVファイルを扱う場合、@Csvおよび@CsvFormatアノテーションを使用してフォーマットを指定する。CSVファイルのフォーマットは予め用意したフォーマットセットの中から選択できる。フォーマットセットのいずれにも当てはまらない場合は、@CsvFormatを使用して個別にフォーマットを指定できる。

**annotations**:

- 項目 1:
  **name**: @Csv

  **class**: nablarch.common.databind.csv.Csv

  **attributes**:

  - 項目 1:
    **name**: type

    **type**: Csv.CsvType

    **required**: True

    **description**: CSVフォーマットのタイプ（DEFAULT, RFC4180, EXCEL, TSV, CUSTOM）

  - 項目 2:
    **name**: properties

    **type**: String[]

    **required**: True

    **description**: バインドするプロパティ名の配列（CSV項目順）

  - 項目 3:
    **name**: headers

    **type**: String[]

    **required**: False

    **description**: ヘッダタイトルの配列（CSV項目順）


  **example**: @Csv(type = Csv.CsvType.DEFAULT, properties = {"age", "name"}, headers = {"年齢", "氏名"})
public class Person {
    private Integer age;
    private String name;
    // getter、setterは省略
}

- 項目 2:
  **name**: @CsvFormat

  **class**: nablarch.common.databind.csv.CsvFormat

  **description**: CSVフォーマットが予め用意したフォーマットセットのいずれにも当てはまらない場合に、個別にフォーマットを指定する。@CsvのtypeにCUSTOMを指定する必要がある。

  **attributes**:

  - 項目 1:
    **name**: fieldSeparator

    **type**: char

    **required**: False

    **default**: ,

    **description**: 列区切り文字

  - 項目 2:
    **name**: lineSeparator

    **type**: String

    **required**: False

    **default**: \r\n

    **description**: 行区切り文字

  - 項目 3:
    **name**: quote

    **type**: char

    **required**: False

    **default**: "

    **description**: フィールド囲み文字

  - 項目 4:
    **name**: ignoreEmptyLine

    **type**: boolean

    **required**: False

    **default**: true

    **description**: 空行を無視するか

  - 項目 5:
    **name**: requiredHeader

    **type**: boolean

    **required**: False

    **default**: true

    **description**: ヘッダ行が必須か

  - 項目 6:
    **name**: charset

    **type**: String

    **required**: False

    **default**: UTF-8

    **description**: 文字コード

  - 項目 7:
    **name**: quoteMode

    **type**: CsvDataBindConfig.QuoteMode

    **required**: False

    **default**: NORMAL

    **description**: クォートモード（NORMAL, ALL）

  - 項目 8:
    **name**: emptyToNull

    **type**: boolean

    **required**: False

    **default**: false

    **description**: 空文字をnullとして扱うか


  **example**: @Csv(type = Csv.CsvType.CUSTOM, properties = {"age", "name"})
@CsvFormat(
        fieldSeparator = '\t',
        lineSeparator = "\r\n",
        quote = '\'',
        ignoreEmptyLine = false,
        requiredHeader = false,
        charset = "UTF-8",
        quoteMode = CsvDataBindConfig.QuoteMode.ALL,
        emptyToNull = true)
public class Person {
    private Integer age;
    private String name;
    // getter、setterは省略
}


**note**: Java Beansクラスにバインドする場合、フォーマット指定はアノテーションで行うため、ObjectMapperの生成時にDataBindConfigを使用したフォーマットの指定はできない。

---

## csv_format_map

MapクラスにバインドしてCSVファイルを扱う場合、ObjectMapperの生成時にCsvDataBindConfigを使用してフォーマットを指定する。CsvDataBindConfig#withPropertiesで設定したプロパティ名がMapオブジェクトのキーとして使用される。CSVにヘッダ行が存在する場合は、プロパティ名の設定を省略することでヘッダタイトルをキーとして使用できる。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `withProperties` | `public CsvDataBindConfig withProperties(String... properties)` | プロパティ名を設定する。設定したプロパティ名がMapオブジェクトのキーとして使用される。 |
| `withHeaderTitles` | `public CsvDataBindConfig withHeaderTitles(String... headerTitles)` | ヘッダタイトルを設定する。 |

**withProperties**:

パラメータ:
- `properties` (String...): プロパティ名（CSV項目順）

戻り値: CsvDataBindConfig

**withHeaderTitles**:

パラメータ:
- `headerTitles` (String...): ヘッダタイトル（CSV項目順）

戻り値: CsvDataBindConfig

**example**:

```java
// ヘッダタイトル、プロパティ名はCSVの項目順と一致するように定義する
DataBindConfig config = CsvDataBindConfig.DEFAULT.withHeaderTitles("年齢", "名前")
                                                 .withProperties("age", "name");
ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, outputStream, config);
```

**class**: nablarch.common.databind.csv.CsvDataBindConfig

**point**: ヘッダタイトル、プロパティ名はCSVの項目順と一致するように定義すること

---

## fixed_length_format_beans

Java Beansクラスにバインドして固定長ファイルを扱う場合、@FixedLengthおよび@Fieldアノテーションを使用してフォーマットを指定する。各フィールドに対し、パディングやトリム等を変換するコンバータ（@Lpad, @Rpad等）を指定できる。未使用領域が存在するフォーマットの場合、固定長ファイルへの書き込み時にFixedLength#fillCharに設定した文字で自動的にパディングされる。

**example**:

```java
@FixedLength(length = 24, charset = "MS932", lineSeparator = "\r\n", fillChar = '0')
public class Person {
    @Field(offset = 1, length = 3)
    @Lpad
    private Integer age;

    @Field(offset = 9, length = 16)
    @Rpad
    private String name;
    // getter、setterは省略
}
```

**annotations**:

- 項目 1:
  **name**: @FixedLength

  **class**: nablarch.common.databind.fixedlength.FixedLength

  **attributes**:

  - 項目 1:
    **name**: length

    **type**: int

    **required**: True

    **description**: 1レコードの長さ（バイト数）

  - 項目 2:
    **name**: charset

    **type**: String

    **required**: False

    **default**: UTF-8

    **description**: 文字コード

  - 項目 3:
    **name**: lineSeparator

    **type**: String

    **required**: False

    **default**: \r\n

    **description**: 行区切り文字

  - 項目 4:
    **name**: fillChar

    **type**: char

    **required**: False

    **default**:  (半角スペース)

    **description**: 未使用領域のパディング文字

  - 項目 5:
    **name**: multiLayout

    **type**: boolean

    **required**: False

    **default**: false

    **description**: 複数フォーマットを持つファイルか


  **example**: @FixedLength(length = 19, charset = "MS932", lineSeparator = "\r\n")
public class Person {
    @Field(offset = 1, length = 3)
    @Lpad
    private Integer age;

    @Field(offset = 4, length = 16)
    @Rpad
    private String name;
    // getter、setterは省略
}

- **name**: @Field
- **class**: nablarch.common.databind.fixedlength.Field
- **attributes**:
  - 項目 1:
    **name**: offset

    **type**: int

    **required**: True

    **description**: フィールドの開始位置（1始まり）

  - 項目 2:
    **name**: length

    **type**: int

    **required**: True

    **description**: フィールドの長さ（バイト数）


- **name**: @Lpad
- **class**: nablarch.common.databind.fixedlength.converter.Lpad
- **description**: 左詰めでパディング（読み込み時はトリム）を行うコンバータ
- **name**: @Rpad
- **class**: nablarch.common.databind.fixedlength.converter.Rpad
- **description**: 右詰めでパディング（読み込み時はトリム）を行うコンバータ

**converters**:

- nablarch.common.databind.fixedlength.converter.Lpad - 左詰めパディング
- nablarch.common.databind.fixedlength.converter.Rpad - 右詰めパディング
- その他のコンバータはnablarch.common.databind.fixedlength.converterパッケージ配下を参照

**note**: 未使用領域（offset 4-8）は、fillCharに設定した文字（この例では'0'）で自動的にパディングされる。

---

## fixed_length_format_map

Mapクラスにバインドして固定長ファイルを扱う場合、ObjectMapperの生成時にFixedLengthDataBindConfigを使用してフォーマットを指定する。FixedLengthDataBindConfigは、FixedLengthDataBindConfigBuilderを使用して生成できる。

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `newBuilder` | `public static FixedLengthDataBindConfigBuilder newBuilder()` | ビルダーのインスタンスを生成する |
| `length` | `public FixedLengthDataBindConfigBuilder length(int length)` | 1レコードの長さを設定する |
| `charset` | `public FixedLengthDataBindConfigBuilder charset(Charset charset)` | 文字コードを設定する |
| `lineSeparator` | `public FixedLengthDataBindConfigBuilder lineSeparator(String lineSeparator)` | 行区切り文字を設定する |
| `singleLayout` | `public SingleLayoutBuilder singleLayout()` | シングルレイアウト（単一フォーマット）の設定を開始する |
| `field` | `public SingleLayoutBuilder field(String name, int offset, int length, FieldConverter converter)` | フィールドを定義する |
| `build` | `public DataBindConfig build()` | FixedLengthDataBindConfigを生成する |

**newBuilder**:

戻り値: FixedLengthDataBindConfigBuilder

**length**:

パラメータ:
- `length` (int): 1レコードの長さ（バイト数）

戻り値: FixedLengthDataBindConfigBuilder

**charset**:

パラメータ:
- `charset` (Charset): 文字コード

戻り値: FixedLengthDataBindConfigBuilder

**lineSeparator**:

パラメータ:
- `lineSeparator` (String): 行区切り文字

戻り値: FixedLengthDataBindConfigBuilder

**singleLayout**:

戻り値: SingleLayoutBuilder

**field**:

パラメータ:
- `name` (String): フィールド名（Mapのキーとして使用）
- `offset` (int): 開始位置（1始まり）
- `length` (int): 長さ（バイト数）
- `converter` (FieldConverter): コンバータ（Lpad.Converter, Rpad.RpadConverter等）

戻り値: SingleLayoutBuilder

**build**:

戻り値: DataBindConfig

**example**:

```java
final DataBindConfig config = FixedLengthDataBindConfigBuilder
        .newBuilder()
        .length(19)
        .charset(Charset.forName("MS932"))
        .lineSeparator("\r\n")
        .singleLayout()
        .field("age", 1, 3, new Lpad.Converter('0'))
        .field("name", 4, 16, new Rpad.RpadConverter(' '))
        .build();

final ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, outputStream, config);
```

**class**: nablarch.common.databind.fixedlength.FixedLengthDataBindConfig

**builder_class**: nablarch.common.databind.fixedlength.FixedLengthDataBindConfigBuilder

---

## multi_layout

複数のフォーマットを持つ固定長ファイルに対応する。Java BeansクラスまたはMapクラスにバインドできる。

**beans_approach**:

**description**: フォーマットごとにJava Beansクラスを定義し、それらのJava Beansクラスをプロパティとして持つMultiLayoutの継承クラスを作成する。

**class**: nablarch.common.databind.fixedlength.MultiLayout

**steps**:

- フォーマットごとにJava Beansクラスを定義する
- 上記のフォーマットを定義したJava Beansクラスをプロパティとして持つMultiLayoutの継承クラスを定義する
- MultiLayoutの継承クラスに@FixedLengthアノテーションを設定し、multiLayout属性にtrueを設定する
- MultiLayout#getRecordIdentifierメソッドをオーバーライドして、対象のデータがどのフォーマットに紐づくかを識別するRecordIdentifierの実装クラスを返却する

**annotations**:

- **name**: @Record
- **class**: nablarch.common.databind.fixedlength.Record
- **description**: フォーマットを表すJava Beansクラスのプロパティに付与する

**example**: @FixedLength(length = 20, charset = "MS932", lineSeparator = "\r\n", multiLayout = true)
public class Person extends MultiLayout {
    @Record
    private Header header;

    @Record
    private Data data;

    @Override
    public RecordIdentifier getRecordIdentifier() {
        return new RecordIdentifier() {
            @Override
            public RecordName identifyRecordName(byte[] record) {
                return record[0] == 0x31 ? RecordType.HEADER : RecordType.DATA;
            }
        };
    }
    // getter、setterは省略
}

public class Header {
    @Field(offset = 1, length = 1)
    private Long id;

    @Rpad
    @Field(offset = 2, length = 19)
    private String field;
    // getter、setterは省略
}

public class Data {
    @Field(offset = 1, length = 1)
    private Long id;

    @Lpad
    @Field(offset = 2, length = 3)
    private Long age;

    @Rpad
    @Field(offset = 5, length = 16)
    private String name;
    // getter、setterは省略
}

enum RecordType implements MultiLayoutConfig.RecordName {
    HEADER {
        @Override
        public String getRecordName() {
            return "header";
        }
    },
    DATA {
        @Override
        public String getRecordName() {
            return "data";
        }
    }
}

**usage_read**: try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    final Person person = mapper.read();
    if (RecordType.HEADER == person.getRecordName()) {
        final Header header = person.getHeader();
        // 後続の処理は省略
    }
}

**usage_write**: try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, outputStream)) {
    final Person person = new Person();
    person.setHeader(new Header("1", "test"));
    mapper.write(person);
}

**map_approach**:

**description**: FixedLengthDataBindConfigBuilderのmultiLayoutメソッドを使用して複数フォーマットを定義する。

**methods**:

- 項目 1:
  **name**: multiLayout

  **signature**: public MultiLayoutBuilder multiLayout()

  **description**: マルチレイアウト用のDataBindConfigを生成する

  **returns**: MultiLayoutBuilder

- 項目 2:
  **name**: record

  **signature**: public RecordBuilder record(String recordName)

  **description**: レコードタイプを定義する

  **parameters**:

  - **name**: recordName
  - **type**: String
  - **description**: レコード名

  **returns**: RecordBuilder

- 項目 3:
  **name**: recordIdentifier

  **signature**: public MultiLayoutBuilder recordIdentifier(RecordIdentifier recordIdentifier)

  **description**: 対象のデータがどのフォーマットに紐づくかを識別するRecordIdentifierを設定する

  **parameters**:

  - **name**: recordIdentifier
  - **type**: RecordIdentifier
  - **description**: レコード識別子の実装

  **returns**: MultiLayoutBuilder


**example**: final DataBindConfig config = FixedLengthDataBindConfigBuilder
        .newBuilder()
        .length(20)
        .charset(Charset.forName("MS932"))
        .lineSeparator("\r\n")
        .multiLayout()
        .record("header")
        .field("id", 1, 1, new DefaultConverter())
        .field("field", 2, 19, new Rpad.RpadConverter(' '))
        .record("data")
        .field("id", 1, 1, new DefaultConverter())
        .field("age", 2, 3, new Lpad.LpadConverter('0'))
        .field("name", 5, 16, new Rpad.RpadConverter(' '))
        .recordIdentifier(new RecordIdentifier() {
            @Override
            public RecordName identifyRecordName(byte[] record) {
                return record[0] == 0x31 ? RecordType.HEADER : RecordType.DATA;
            }
        })
        .build();

**usage_read**: try (ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, inputStream, config)) {
    final Map<String, ?> map = mapper.read();
    if (RecordType.HEADER == map.get("recordName")) {
        final Map<String, ?> header = map.get("header");
        // 後続の処理は省略
    }
}

**usage_write**: try (ObjectMapper<Map> mapper = ObjectMapperFactory.create(Map.class, outputStream, config)) {
    final Map<String, ?> header = new HashMap<>();
    header.put("id", "1");
    header.put("field", "test");

    final Map<String, ?> map = new HashMap<>();
    map.put("recordName", RecordType.HEADER);
    map.put("header", header);

    mapper.write(map);
}

---

## formatter

データを出力する際に、format機能を使用することで日付や数値などのデータの表示形式をフォーマットできる。

**reference**: 詳細はformat機能のドキュメントを参照すること。

---

## extension

Java Beansクラスにバインドできるファイル形式を追加する方法

**steps**:

- **step**: 1
- **description**: 指定した形式のファイルとJava Beansクラスをバインドさせるため、ObjectMapperの実装クラスを作成する
- **step**: 2
- **description**: ObjectMapperFactoryを継承したクラスを作成し、先ほど作成したObjectMapperの実装クラスを生成する処理を追加する
- **step**: 3
- **description**: ObjectMapperFactoryの継承クラスをコンポーネント設定ファイルに設定する。コンポーネント名はobjectMapperFactoryとすること。
- **example**: <component name="objectMapperFactory" class="sample.SampleObjectMapperFactory" />

---

## csv_format_sets

デフォルトで提供しているCSVファイルのフォーマットセット及び設定値

**format_sets**:

- 項目 1:
  **name**: DEFAULT

  **fieldSeparator**: カンマ(,)

  **lineSeparator**: 改行(\r\n)

  **quote**: ダブルクォート(")

  **ignoreEmptyLine**: True

  **requiredHeader**: True

  **charset**: UTF-8

  **quoteMode**: NORMAL

- 項目 2:
  **name**: RFC4180

  **fieldSeparator**: カンマ(,)

  **lineSeparator**: 改行(\r\n)

  **quote**: ダブルクォート(")

  **ignoreEmptyLine**: False

  **requiredHeader**: False

  **charset**: UTF-8

  **quoteMode**: NORMAL

- 項目 3:
  **name**: EXCEL

  **fieldSeparator**: カンマ(,)

  **lineSeparator**: 改行(\r\n)

  **quote**: ダブルクォート(")

  **ignoreEmptyLine**: False

  **requiredHeader**: False

  **charset**: UTF-8

  **quoteMode**: NORMAL

- 項目 4:
  **name**: TSV

  **fieldSeparator**: タブ(\t)

  **lineSeparator**: 改行(\r\n)

  **quote**: ダブルクォート(")

  **ignoreEmptyLine**: False

  **requiredHeader**: False

  **charset**: UTF-8

  **quoteMode**: NORMAL


**quote_modes**:

- **name**: NORMAL
- **description**: フィールド囲み文字、列区切り文字、改行のいずれかを含むフィールドのみフィールド囲み文字で囲む
- **name**: ALL
- **description**: 全てのフィールドをフィールド囲み文字で囲む

**note**: CSVファイルの読み込み時は、クォートモードは使用せずに自動的にフィールド囲み文字の有無を判定して読み込みを行う。

---

## anti-patterns

| パターン | 理由 | 正しい方法 |
|----------|------|------------|
| 外部から受け付けたアップロードファイルのデータをJava Beansとして読み込む際、Java BeansクラスのプロパティをIntegerやDate等の型で定義する | アップロードファイルなどの外部から受け付けたデータには不正なデータが含まれる可能性がある。型変換に失敗すると例外が発生しJava Beansオブジェクトが生成されないため、不正な値を業務エラーとして通知できず異常終了となってしまう。 | 外部から受け付けたデータを読み込む場合は、Java BeansクラスのプロパティをすべてString型で定義し、Bean Validationで入力値チェックを行うこと。 |
| ObjectMapperのclose()を呼び出さずにリソースを解放しない | ObjectMapperは内部でストリームなどのリソースを保持しているため、close()を呼び出さないとリソースリークが発生する。 | try-with-resourcesを使用してObjectMapperを生成することで、自動的にclose()が呼ばれるようにする。 |
| ObjectMapperのインスタンスを複数スレッドで共有する | ObjectMapperの読み込み及び書き込みはスレッドアンセーフであるため、複数スレッドから同時に呼び出された場合の動作は保証されない。 | ObjectMapperのインスタンスを複数スレッドで共有するような場合には、呼び出し元にて同期処理を行うこと。または、スレッドごとにObjectMapperのインスタンスを生成すること。 |
| Java Beansクラスにバインドする際に、ObjectMapperの生成時にDataBindConfigを指定する | Java Beansクラスにバインドする場合、フォーマット指定はアノテーションで行うため、DataBindConfigを使用したフォーマットの指定はできない。DataBindConfigはMapクラスにバインドする場合にのみ使用する。 | Java Beansクラスにバインドする場合は、@Csvや@FixedLengthなどのアノテーションでフォーマットを指定すること。 |
| ファイルダウンロード時にデータをメモリ上に全て展開してからレスポンスに設定する | 大量データのダウンロード時にメモリを圧迫する恐れがある。 | 一時ファイルに出力し、FileResponseでファイルを指定する。FileResponseのコンストラクタの第二引数にtrueを指定すると、リクエスト処理の終了時に自動的にファイルを削除する。 |
| CsvDataBindConfigやFixedLengthDataBindConfigで定義したプロパティ名やフィールド名の順序がファイルの項目順と一致していない | ヘッダタイトル、プロパティ名、フィールド定義はファイルの項目順と一致するように定義する必要がある。順序が一致していないと、データが正しくバインドされない。 | ヘッダタイトル、プロパティ名、フィールド定義はファイルの項目順と一致するように定義すること。 |

**外部から受け付けたアップロードファイルのデータをJava Beansとして読み込む際、Java BeansクラスのプロパティをIntegerやDate等の型で定義するの正しい例**:

```java
@Csv(type = Csv.CsvType.DEFAULT, properties = {"age", "name"})
public class Person {
    @NumberRange(min = 0, max = 150)
    private String age;  // String型で定義

    @Required
    private String name; // String型で定義
    // getter、setterは省略
}
```

**ObjectMapperのclose()を呼び出さずにリソースを解放しないの正しい例**:

```java
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    // 処理
} // 自動的にclose()が呼ばれる
```

**ファイルダウンロード時にデータをメモリ上に全て展開してからレスポンスに設定するの正しい例**:

```java
final Path path = Files.createTempFile(null, null);
try (ObjectMapper<Person> mapper =
        ObjectMapperFactory.create(Person.class, Files.newOutputStream(path))) {
    for (Person person : persons) {
        mapper.write(person);
    }
}
FileResponse response = new FileResponse(path.toFile(), true);
```

---

## errors

| 例外 | 原因 | 対処 |
|------|------|------|
| `nablarch.common.databind.InvalidDataFormatException` | 読み込んだデータのフォーマットが不正な場合に発生する。例えば、CSVファイルで囲み文字が閉じられていない、固定長ファイルでレコード長が不正、型変換に失敗した場合などに発生する。 | データファイルのフォーマットを確認し、正しいフォーマットで作成されているか検証する。外部から受け付けるファイルの場合は、try-catchでInvalidDataFormatExceptionを捕捉し、ユーザーに適切なエラーメッセージを表示する。 |
| `型変換エラー（InvalidDataFormatExceptionの一種）` | Java Beansクラスへの変換時、Java Beansクラスに定義されたプロパティの型に自動的に型変換するが、型変換に失敗した場合に発生する。例えば、Integerプロパティにアルファベットがバインドされようとした場合など。 | 外部から受け付けたデータを読み込む場合は、Java BeansクラスのプロパティはすべてString型で定義し、Bean Validationで入力値チェックを行うこと。 |

**nablarch.common.databind.InvalidDataFormatException**:

```java
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    Person person;
    while ((person = mapper.read()) != null) {
        // 処理
    }
} catch (InvalidDataFormatException e) {
    // データファイルのフォーマット不正時の処理を記述
    log.error("データフォーマットが不正です: " + e.getMessage());
}
```

**型変換エラー（InvalidDataFormatExceptionの一種）**:

```java
// Java Beansクラスの定義
@Csv(type = Csv.CsvType.DEFAULT, properties = {"age", "name"})
public class Person {
    @NumberRange(min = 0, max = 150)
    private String age;  // String型で定義

    @Required
    private String name; // String型で定義
}

// 使用例
try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    Person person;
    while ((person = mapper.read()) != null) {
        ValidatorUtil.validate(person); // Bean Validationで検証
    }
}
```

---

## tips

**title**: try-with-resourcesの使用

**description**: 全データの読み込みが完了したら、ObjectMapper#closeでリソースを解放すること。try-with-resourcesを使用することでクローズ処理を省略可能。

**example**: try (ObjectMapper<Person> mapper = ObjectMapperFactory.create(Person.class, inputStream)) {
    // 処理
} // 自動的にclose()が呼ばれる

**title**: null値の出力

**description**: プロパティの値がnullの場合は、未入力を表す値が出力される。例えば、CSVファイルに書き込む場合は空文字が出力される。Mapオブジェクトの場合も同様に、value値がnullの場合は空文字が出力される。

**title**: Mapオブジェクトでは行番号取得不可

**description**: 論理行番号の取得は@LineNumberアノテーションを使用するが、これはJava Beansクラスにのみ適用可能。Mapオブジェクトとして取得する場合は、データの行番号を取得できない点に注意すること。

**title**: CSVファイル読み込み時のクォートモード

**description**: CSVファイルの読み込み時は、クォートモードは使用せずに自動的にフィールド囲み文字の有無を判定して読み込みを行う。クォートモードはCSVファイル書き込み時にのみ使用される。

**title**: ヘッダタイトルをMapのキーとして使用

**description**: MapクラスにバインドしてCSVを読み込む場合、CSVにヘッダ行が存在する場合は、CsvDataBindConfig#withPropertiesの設定を省略することでヘッダタイトルをMapのキーとして使用できる。


---

## limitations

- ObjectMapperの読み込み及び書き込みはスレッドアンセーフであるため、複数スレッドから同時に呼び出された場合の動作は保証しない。ObjectMapperのインスタンスを複数スレッドで共有する場合には、呼び出し元にて同期処理を行うこと。
- Java Beansクラスにバインドする場合、フォーマット指定はアノテーションで行うため、ObjectMapperの生成時にDataBindConfigを使用したフォーマットの指定はできない。
- Mapオブジェクトとして取得する場合は、データの論理行番号を取得できない。行番号が必要な場合はJava Beansクラスを使用すること。
- Mapオブジェクトへの変換時、値は全てString型で格納される。型変換が必要な場合は別途実装する必要がある。

---
