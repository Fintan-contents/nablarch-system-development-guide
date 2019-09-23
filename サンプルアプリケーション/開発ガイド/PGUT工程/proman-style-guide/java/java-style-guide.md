# Javaコーディング規約

<!-- START doctoc -->

- [1.はじめに](#no1)
  - [1.1.前提](#no1-1)
  - [1.2.指標](#no1-2)
  - [1.3.Javaバージョン](#no1-3)
  - [1.4.表記ルール](#no1-4)
- [2.パッケージ構成](#no2)
  - [2.1.方式設計上のステレオタイプに従ってパッケージを構成してください](#no2-1)
- [3.命名](#no3)
  - [3.1.クラス名は基本的には名詞とし、必要に応じて方式設計上のステレオタイプに従って命名してください](#no3-1)
  - [3.2.メソッド名は基本的には動詞から始まるように命名をしてください](#no3-2)
  - [3.3.変数は基本的には名詞で命名してください](#no3-3)
  - [3.4.データモデルの用語辞書を活用して命名してください](#no3-4)
- [4.コメント](#no4)
  - [4.1.クラスのJavadocコメントを書くようにしてください](#no4-1)
  - [4.2.フィールドのJavadocコメントを書くようにしてください](#no4-2)
  - [4.3.メソッドのJavadocコメントを書くようにしてください](#no4-3)
  - [4.4.コードの理解を助けるため、必要に応じて行コメントを記載してください](#no4-4)
- [5.禁止事項](#no5)
  - [5.1.計算式の途中でインクリメント・デクリメントをしないでください](#no5-1)
  - [5.2.フィールドを一時変数として使用しないでください](#no5-2)
  - [5.3.スーパークラスと同名のフィールドをサブクラスで定義しないでください](#no5-3)
  - [5.4.戻り値がコレクションや配列の場合は null を返さないでください](#no5-4)
  - [5.5.コンストラクタ内で自分自身のインスタンスメソッドを呼び出さないようにしてください](#no5-5)
  - [5.6.インスタンスが格納された変数をレシーバにして static メソッドを呼び出さないでください](#no5-6)
  - [5.7.インスタンスが格納された変数をレシーバにして static 変数を参照しないでください](#no5-7)
  - [5.8.アプリケーションプログラマーの独断で例外クラスは作らないでください](#no5-8)
  - [5.9.java.lang.Exceptionクラスのインスタンスを生成してスローしないでください](#no5-9)
  - [5.10.try-catch 文を条件分岐のために使用しないでください](#no5-10)
  - [5.11.秘匿情報はログ出力やシリアライズされないように注意してください](#no5-11)
  - [5.12.Java標準ライブラリにあるレガシーなAPIは使用しないでください](#no5-12)
  - [5.13.リフレクションを直接使用しないでください](#no5-13)
  - [5.14.クラスを不整合な状態にしないでください](#no5-14)
  - [5.15.アプリケーションプログラマーの独断でスレッドを作って非同期処理をしないでください](#no5-15)
  - [5.16.名前と値が同じ定数は作らないでください](#no5-16)
- [6.注意事項](#no6)
  - [6.1.メソッドには名前から想像できない処理を実装しないでください](#no6-1)
  - [6.2.単一のメソッドに複数の要素を詰め込まないでください](#no6-2)
  - [6.3.クラス外に公開されるメソッドの引数や戻り値・フィールドの型は実装クラスではなくインターフェースで宣言してください](#no6-3)
  - [6.4.メソッドのオーバーロードはオプションの省略用途のみに使用してください](#no6-4)
  - [6.5.未使用コードは残したままにしないでください](#no6-5)
  - [6.6.クラスは大きくなりすぎないようにしてください](#no6-6)
  - [6.7.メソッドは大きくなりすぎないようにしてください](#no6-7)
  - [6.8.インナークラスやstaticにネストしたクラス、匿名クラスは作りすぎないようにしてください](#no6-8)
  - [6.9.可能な限りキャストは使用しないでください](#no6-9)
  - [6.10.ラッパークラスの変数とプリミティブ値を演算する際は、アンボクシングに注意してください](#no6-10)
  - [6.11.計算の誤差が許されない場合はBigDecimalを使用してください](#no6-11)
  - [6.12.ソートと集計はJava側ではなく、SQLで行うようにしてください](#no6-12)
  - [6.13.ループ処理の中では極力データベースアクセスしないようにしてください](#no6-13)
  - [6.14.外部からの入力値は共通部品を用いてチェックしてください](#no6-14)
  - [6.15.ファイル入出力は共通部品のクラスを使用してください](#no6-15)
  - [6.16.リソースをクローズする必要がある場合はtry-with-resources構文を使用してください](#no6-16)
  - [6.17.例外処理はプロジェクトの方式設計に従って統一的にコーディングしてください](#no6-17)
  - [6.18.ループのネストはできれば二重までにしてください](#no6-18)
- [7.推奨事項](#no7)
  - [7.1.クラスやメソッドなどには適切なアクセス修飾子を付与してください](#no7-1)
  - [7.2.インスタンス変数は原則 private にしてください](#no7-2)
  - [7.3.ローカル変数はできるだけ狭いスコープで使用してください](#no7-3)
  - [7.4.なるべく再代入は避けて、そのためにfinalを活用してください](#no7-4)
  - [7.5.なるべく引数の状態を変えないでください](#no7-5)
  - [7.6.戻り値として null を使いたい場合、Optional の使用を検討してください](#no7-6)
  - [7.7.コレクションのように型パラメーターを取るクラスを使用する場合は適切な型をバインドしてください](#no7-7)
  - [7.8.コレクションの処理はStream APIを使用して簡潔に書くことを検討してください](#no7-8)
  - [7.9.チェック例外のスローを伴う処理はStream APIではなく拡張for文を使用して実装してください](#no7-9)
  - [7.10.レガシーfor文はなるべく使用せず、Stream APIか拡張for文の使用を検討してください](#no7-10)
  - [7.11.配列全体をコピーする場合はcloneメソッドを使用してください](#no7-11)
  - [7.12.コレクションを配列に変換する場合はtoArrayメソッドを使用してください](#no7-12)
  - [7.13.配列をコレクションに変換する場合はArrays.asList、またはList.ofを使用してください](#no7-13)
  - [7.14.メソッドをオーバーライドしたり、抽象メソッドを実装する場合はメソッドに@Overrideを付けてください](#no7-14)
- [8.使用可能なAPI](#no8)
  - [8.1.使用可能な標準APIを使用して実装してください](#no8-1)
- [9.Nablarchライブラリ](#no9)
  - [9.1.使用可能なNablarchのAPIを使用して実装してください](#no9-1)

<!-- END doctoc -->

## <a name="no1">1.はじめに</a>

本規約はJavaを使用してアプリケーションを開発するプロジェクトにおいて、アプリケーションプログラマーが守るべきルールやより良いコードを書くための指針を解説しています。

一部、[Nablarch Application Framework](https://nablarch.github.io/docs/LATEST/doc/index.html)を前提とした項目も含まれますが、多くの項目は特定のフレームワークに限らず汎用的に使用できるようにしてあります。

### <a name="no1-1">1.1.前提</a>

本規約が対象とするコードは、次の3つが実施されていることを前提としています。

- コードフォーマッターでフォーマットしていること（[Javaコードフォーマッター](./code-formatter.md)）
- Checkstyleで機械的に検出できる規約違反は解消していること（[Checkstyleガイド](./staticanalysis/checkstyle/README.md)）
- SpotBugsで明らかに問題のあるコードや、後々問題が発生しそうなコードを排除していること（[SpotBugsガイド](./staticanalysis/spotbugs/README.md)）

機械的に対処できることは予め実施し、本規約ではより良いコードを書くためのガイド、あるいはコードレビューの指針となるように作成しました。

なお著名な静的解析ツールは他にもSonarQubeがありますが、サーバーへインストールする形式のSonarQubeよりもMavenの実行だけでチェックが簡潔するCheckstyleとSpotBugsの方が導入の敷居が低いため、本規約ではCheckstyleとSpotBugsを前提にしています。

プロジェクトによってはCheckstyle・SpotBugs以外の手段で静的解析をする場合もあるでしょう。
必要に応じて、規約の前提条件をCheckstyle・SpotBugsからSonarQubeやその他の静的解析ツールに読み換えて頂いて構いません。

### <a name="no1-2">1.2.指標</a>

本規約を書くにあたって、次の3つを指標としました。

- ランタイムエラーよりもコンパイルエラー
- 変更可能な状態を減らす
- 状態の変更箇所を局所化する

ランタイムエラーよりもコンパイルエラー：アプリケーションを実際に動かさないと発見できないバグよりも、コンパイル時にエラーとなって発見できるバグの方が素早く修正できます。
ランタイムエラーに頼らず、なるべくコンパイルエラーでバグを発見できるようなコーディングを心がけることで安心感をもって開発を進められます。

変更可能な状態を減らす：変更可能な状態とは、`setter`などのメソッドによって再代入できるフィールドや、`java.lang.StringBuilder`のように値を変更できるクラスで宣言されたフィールドを指します。

```java
//setterで変更できるフィールド（変更可能な状態）
private String mutableState;

public void setMutableState(final String mutableState) {
    this.mutableState = mutableState;
}
```

```java
//フィールドは再代入できないが、値は変更できる（変更可能な状態）
private final StringBuilder mutableState = new StringBuilder();

public void appendHello(final String yourName) {
    //値を変更している
    mutableState.append("Hello ").append(yourName);
}
```

変更可能な状態がある場合、現在の状態がどうなっているか頭の中で覚えながらコードを読み進めなくてはいけません。
人それぞれですが、一時的に頭の中で覚えておける情報量はそれほど多くありません。
なるべく変更可能な状態を減らすことで、読み手に負担をかけず保守しやすいコードになります。

状態の変更箇所を局所化する：`setter`などで状態を変更することがありますが、状態の変更前後で頭の中で覚えている状態も変更しないといけません。
状態を変更するコードをなるべく近い場所にまとめることで、読み手に負担をかけず保守しやすいコードになります。

本規約はこの指標に則ったコードを書くためにはどのような事に気を配れば良いのかを意識して作成しました。
本規約を読み進める際も、また実際にコードを書く際も、この指標を意識しておくとより効果的でしょう。

### <a name="no1-3">1.3.Javaバージョン</a>

本規約はJava 8をベースに作成しており、一部の項目ではJava 9やJava 10で使用できる新機能にも言及しています。

本規約はJava 8以降をお使いのプロジェクトでご利用頂けます。

### <a name="no1-4">1.4.表記ルール</a>

本規約ではJavaのバージョンをJ2SE 5やJava SE 8とは書かずに、単にJava 5やJava 8と記載しています。

規約の解説中にコード例を掲載することがあります。
やってはいけない「禁止コード」の例は先頭に`//NG`と一行コメントの形式で記載しています。
禁止コードとの対比として挙げている、やってもよい・ぜひやるべき「推奨コード」の例は先頭に`//OK`と記載しています。
`//NG`と`//OK`のどちらも記載されていないコード例は、「推奨コード」か、もしくは単なる解説の補助として掲載しています。

コード例の中で、特に見せたい箇所以外は省略する場合があります。
その際は `...` と書いて省略を表しています。

```java
//省略の例
public void someMethod(...) { //引数の記載を省略
    ... //メソッド本体の記載を省略
}
```

また、縦に伸びているコード例を中略する場合は `.` を縦に3つ書いて表していることがあります。

```java
//中略の例
final String text = message.getText();
.
.
.
return text;
```

本規約ではループインデックスを伴う昔ながらの`for`文を「レガシー`for`文」と表現しています。

```java
//レガシーfor文の例
for (int i = 0; i < length; i++) {
    ...
}
```

コード例は本規約、特に「7.推奨事項」で挙げられている規約に従っているため、次のような特徴があります。

- フィールドには`private`を付けている
- 変数には可能な限り`final`を付けている

他にも従っている規約はありますが、詳細は「7.推奨事項」を参照してください。
なお「禁止コード」の例を示すため、一部のコード例では規約に従っていないものもあります。

---

## <a name="no2">2.パッケージ構成</a>

アプリケーションのパッケージ構成についての規約です。

### <a name="no2-1">2.1.方式設計上のステレオタイプに従ってパッケージを構成してください</a>

業務アプリケーションでは方式設計上のステレオタイプに従ってパッケージを構成してください。

パッケージ名の簡単な例を示します。

|役割|命名例|
|---|---|
|アクション|`com.example.action`|
|エンティティ|`com.example.entity`|
|業務フォーム|`com.example.form`|
|バリデーター|`com.example.validation`|
|DTO|`com.example.dto`|

---

## <a name="no3">3.命名</a>

クラスやメソッド、変数の命名についての規約です。

### <a name="no3-1">3.1.クラス名は基本的には名詞とし、必要に応じて方式設計上のステレオタイプに従って命名してください</a>

クラス名は基本的には名詞にします。

慣習的に「特定の能力を付与することを表すインターフェース」は、末尾に`able`を付ける事があります。
Java標準APIにもそのような名前のインターフェースがいくつも含まれています。

- `java.lang.Appendable`
- `java.lang.AutoCloseable`
- `java.lang.Comparable`
- `java.lang.Iterable`
- `java.lang.Runnable`

業務アプリケーションで作成するクラスは方式設計上のステレオタイプに従った役割を持っていることが多いです。
そのため、ステレオタイプに対応していることが分かるような接尾語を付けるなどして命名をしてください。

|役割|命名ルール|
|---|---|
|アクション|機能を表す名前 + `Action`|
|エンティティ|テーブルの物理名をアッパーキャメルケースにしたもの|
|業務ベースフォーム|機能を表す名前 + `FormBase`|
|業務フォーム|機能を表す名前 + `Form`|
|バリデーター|機能を表す名前 + `Validator`|
|DTO|データの種類を表す名前 + `Dto`|
|排他制御の制御クラス|テーブルの物理名をアッパーキャメルケースにしたもの + `ExclusiveControl`|
|テスト|テスト対象クラス名 + `Test`|
|リクエスト単体テスト|`Action`クラス名 + `RequestTest`|

### <a name="no3-2">3.2.メソッド名は基本的には動詞から始まるように命名をしてください</a>

メソッド名は動詞から始まるように命名をするのが基本になりますが、次に示す「機能別の推奨命名ルール」には動詞から始まらないものも含んでいます。

|機能|命名ルール|例|
|---|---|---|
|インスタンスを生成する|`create` + 対象|`createItemCode`|
|異なる型に変換する|`to` + 変換先の型|`toString` `toItemCode`|
|異なる型として扱う|`as` + 扱う型|`asReadLock` `asList`|
|含んでいるかどうかを返す|`contains` + 対象|`containsKey`|
|可能かどうかを返す|`can` + 動作|`canRead` `canEncode`|
|その状態かどうかを返す|`is` + 状態|`isClosed` `isEmpty`|

「異なる型に変換する」と「異なる型として扱う」は同じように見えますが、少し異なります。
前者は「異なる型に変換してしまって元の型の性質は期待しない」のに対して、後者は「異なる型に変換するが元の型の性質と関連する」ものです。
Java標準APIで言うと、前者にあたるのは`Integer.toString`です。整数が文字列に変換されて、整数の性質は期待されません。
後者にあたるのは`Arrays.asList`です。配列がリストに変換されますが、配列とリストは性質が似ていますし、元の配列を変更するとリストも変更されます。

Java標準APIを見ても`String.length`や`Optional.of`のように動詞から始まらない名前のメソッドがいくつも存在します。
Java標準APIも参考にして、適切な命名を行うようにしてください。

### <a name="no3-3">3.3.変数は基本的には名詞で命名してください</a>

フィールド名やローカル変数名は基本的には名詞にします。

Javaの命名規則の慣習上、決まった名前を付ける場合があります。

`java.io.InputStream`や`java.io.OutputStream`は、それぞれ `in` `is` や `out` `os`と命名しても構いません。

```java
try (final InputStream in = openInputStream(file)) {
    ...
}
```

レガシー`for`文で使用するカウンターは`i`と命名することが一般的です。

```java
for (int i = 0; i < length; i++) {
    ...
}
```

`catch`ブロックで例外を受け取る変数は`e`と命名することが一般的です。

また、値を組み立てるために一時的に使用されるスコープの狭い変数は意味の無い名前でも構いません。

```java
//メッセージを組み立てるための一時変数
//ここではbufという無意味な名前を付けている
//sbやtempといった名前でも良い
final StringBuilder buf = new StringBuilder();
buf.append("Hello, ");
buf.append(yourName);
buf.append("!");
final String message = buf.toString();
```

### <a name="no3-4">3.4.データモデルの用語辞書を活用して命名してください</a>

整理された用語辞書をもとにすると、統一感のある命名が出来ます。

是非、用語辞書を活用してください。
用語辞書が整理されていない場合は、まず用語辞書の整理をすることを検討してみてください。

---

## <a name="no4">4.コメント</a>

Javadocコメントや処理の説明をするためのコメントに関する規約です。

### <a name="no4-1">4.1.クラスのJavadocコメントを書くようにしてください</a>

クラスのJavadocコメントには、そのクラスの役割を記載してください。

プロジェクトのルールに応じて`@author`や`@since`なども記載してください。

```java
/**
 * 商品情報を表すクラスです。
 * 
 * @author TIS
 */
public class Item {
```

### <a name="no4-2">4.2.フィールドのJavadocコメントを書くようにしてください</a>

フィールドのJavadocコメントには、それがどのような属性を表すものなのか、記載してください。

```java
/**
 * 商品コード
 */
private String code;

/**
 * 商品の名称
 */
private String name;

/**
 * 更新日時
 */
private LocalDateTime updatedAt;
```

### <a name="no4-3">4.3.メソッドのJavadocコメントを書くようにしてください</a>

メソッドのJavadocには、そのメソッドが行う処理の概要を記載してください。
また、そのメソッドを呼び出す上で前提条件があれば、それも記載してください。

`@param`で引数の説明を、`@return`で戻り値の説明を、`@throws`でスローされる可能性がある例外の説明を記載してください。

`@throws`では例外クラスの日本語名だけを書いて済ませてはいけません。
該当の例外がスローされるのはどのような場合かを記載してください。

```java
//NG 例外クラスの名前を記載しているだけなのでNG。
 ...
 * @throws ItemNotFoundException 商品Not Found例外
 */
```

```java
//OK 例外がスローされるのはどのような場合か記載している。
 ...
 * @throws ItemNotFoundException 更新対象の商品がデータベース内に見つからない場合
 */
```

次に示す引数と戻り値における`null`の扱いは、アプリケーション全体のデフォルトがどちらなのかをプロジェクトで決めてください。

- 引数に`null`を許可する、もしくは許可しない
- 戻り値として`null`を返される場合がある、もしくは必ず`null`でない値が返る

そしてデフォルトから外れる場合のみ、その旨をJavadocへ記載するようにしてください。

例えば、「デフォルトでは引数に`null`を許可しない」とルールを決めた場合、`null`を受け取れる引数には`null`を受け取るとどのように振舞うのかを記載してください。

```java
/**
 * 商品情報を更新します。
 *
 * <p>
 * このメソッドでは既に登録済みの商品に対して更新を行います。
 * まだ商品を登録していない場合は、先に登録処理を行ってください。
 * </p>
 *
 * @param code 商品コード
 * @param name 商品の名称
 * @param updatedAt 更新日時。nullの場合はデフォルト値としてシステム日時が使用される
 * @throws ItemNotFoundException 更新対象の商品がデータベース内に見つからない場合
 */
public void updateItem(final String code, final String name, final LocalDateTime updatedAt)
        throws ItemNotFoundException {
    ...
}
```

### <a name="no4-4">4.4.コードの理解を助けるため、必要に応じて行コメントを記載してください</a>

コードだけを読んで処理の内容を理解できるのが理想的ですが、複雑なロジックや、パフォーマンスのためにあえて特殊な実装をした場合は説明のコメントを記載してください。
コメントは`//`から始まる一行コメントの形式で記載してください。

---

## <a name="no5">5.禁止事項</a>

バグを発生させそうな危ういコードを減らすため、禁止事項を定めています。

### <a name="no5-1">5.1.計算式の途中でインクリメント・デクリメントをしないでください</a>

計算式の途中でインクリメント・デクリメントをすると、該当の変数の値が分かりにくくなります。

計算式は計算式で済ませてしまって、そのあとインクリメント・デクリメントをするようにしてください。

```java
//NG
int x = ...
int y = (x++ * height) / width;
```

```java
//OK
int x = ...
int y = (x * height) / width;
x++;
```

### <a name="no5-2">5.2.フィールドを一時変数として使用しないでください</a>

コレクションの要素を順次処理して必要な値を構築するメソッドなどで中間状態を保持するために一時変数を導入することがありますが、その場合にフィールドを使用しないでください。

フィールドを使用するとクラスが持つ状態を増やすことになります。
なるべく変更可能な状態は持たないようにするためにも、一時変数の用途でフィールドは使用しないでください。

一時変数の用途にはフィールドではなく、ローカル変数を使用してください。

```java
//NG
//フィールドを一時変数として使用している
private List<String> itemNames;

public String collectNames(final List<Item> items) {
    this.itemNames = new ArrayList<>();
    for (Item item : items) {
        collectName(item);
    }
    return this.itemNames.stream().collect(Collectors.joining(", "));
}

private void collectName(final Item item) {
    this.itemNames.add(item.getName());
}
```

```java
//OK
public String collectNames(final List<Item> items) {
    //一時変数はローカル変数を使用して、メソッドに渡して引き回している
    final List<String> itemNames = new ArrayList<>();
    for (Item item : items) {
        collectName(itemNames, item);
    }
    return itemNames.stream().collect(Collectors.joining(", "));
}

private void collectName(final List<String> itemNames, final Item item) {
    itemNames.add(item.getName());
}
```


### <a name="no5-3">5.3.スーパークラスと同名のフィールドをサブクラスで定義しないでください</a>

スーパークラスに定義されているフィールドと同名のフィールドをサブクラスにも定義すると、スーパークラスに定義されているフィールドを名前だけでアクセスできなくなります。

フィールドはメソッドと異なりオーバーライドできません。

混乱の元となるので、スーパークラスと同名のフィールドをサブクラスで定義しないでください。

```java
//NG
public class SuperClass {

    //サブクラスで使用されることが想定されているフィールド
    protected final String var;

    ...
}

public class SubClass extends SuperClass {

    private final String var;

    public String getVar() {
        return var; //SubClass.var が返される
    }

    ...
}
```

### <a name="no5-4">5.4.戻り値がコレクションや配列の場合は null を返さないでください</a>

値がない状態を表すために`null`を使うことがありますが、戻り値が次に示すようなコレクションや配列の場合は値がない状態を表すためであっても`null`を返さないでください。

- `java.util.Collection`
- `java.util.Set`
- `java.util.List`
- `java.util.Map`

コレクションの値がない状態というのは、多くの場合はコレクションが空であることを指します。
コレクションには空であることを示す`isEmpty`というメソッドがあるので、わざわざ`null`を返す必要はありません。
また、配列の場合は`length`フィールドが`0`なら空であると判断できます。

`null`を返す可能性があると、呼び出し元で`null`チェックをしなくてはならずコードが複雑になります。

```java
//NG
public List<Item> findItems(final ItemCategory category) {
    List<Item> items = dao.findItems(category);
    if (items.isEmpty()) {
        return null;
    }
    return items;
}
```

```java
//OK
public List<Item> findItems(final ItemCategory category) {
    return dao.findItems(category);
}
```

明示的に空のコレクションを作成して返したい場合は`java.util.Collections`クラスにある`empty<コレクション>`メソッドを使用してください。

例えば条件に応じて空のリストを返したい場合は`java.util.ArrayList`をインスタンス化するのではなく、`java.util.Collections`クラスの`emptyList`メソッドを使用してください。

```java
//NG
if (empty) {
    return new ArrayList<>();
}
```

```java
//OK
if (empty) {
    return Collections.emptyList();
}
```

`java.util.Collections`には`emptyList`以外にもコレクションの種類に応じて`emptySet`や`emptyMap`が用意されています。

### <a name="no5-5">5.5.コンストラクタ内で自分自身のインスタンスメソッドを呼び出さないようにしてください</a>

コンストラクタ内では、たとえ`final`が付いているフィールドであっても初期化前の`null`値を参照してしまう場合があります。

そのため、コンストラクタからフィールドを参照するインスタンスメソッドを呼び出す場合、フィールドの初期化とメソッドの呼び出しの順番に注意する必要が出てきてしまいます。

```java
//NG
public class Foo {

    private final String text;
    private final int length;

    public Foo(final String text) {

        //textが初期化されていないのでこの位置でcalculateLengthを呼び出すと
        //NullPointerExceptionがスローされる。
        this.length = calculateLength();

        this.text = text;

        //textが初期化された後のこの位置で呼び出すべき。
        //this.length = calculateLength();
    }

    protected int calculateLength() {
        return text.length();
    }
}
```

継承を伴うと話は更に複雑化します。

次に示すNG例を見てください。
`Foo`と`Bar`という2つのクラスが定義されています。
`Bar`は`Foo`を継承しています。

`Bar`をインスタンス化すると、`Bar`のコンストラクタの先頭で`Foo`のコンストラクタが呼び出されます。
`Foo`のコンストラクタでは`calculateLength`を呼び出そうとしますが、`Bar`でオーバーライドされているので実際には`Bar`の`calculateLength`が呼び出されます。
`Bar`の`calculateLength`では`text`を参照していますが、この時点ではまだ初期化されていないので`NullPointerException`となってしまいます。

```java
//NG
public class Foo {

    private final int length;

    public Foo() {
        //コンストラクタで自分自分のメソッドを呼び出している
        this.length = calculateLength();
    }

    protected int calculateLength() {
        return 0;
    }
}

class Bar extends Foo {

    protected final String text;

    public Bar(final String text) {
        this.text = text;
    }

    @Override
    protected int calculateLength() {
        //ここでNullPointerExceptionがスローされる
        return text.length();
    }
}
```

このような複雑さを持ち込んでしまうため、コンストラクタ内から自分自身のインスタンスメソッドを呼び出さないようにしてください。

コンストラクタ内では基本的には引数をフィールドにセットするだけにしてください。
何かしらの処理を行いたい場合は、コンストラクタ内で処理を完結させるようにしてください。

```java
//OK
public class Foo {

    private final String text;
    private final int length;

    public Foo(final String text) {
        this.text = text;
        // コンストラクタ内で処理が完結している
        this.length = text.length();
    }
}
```

ただし、コンストラクタ内で長々と処理を書く必要が出てきた場合は、次に挙げるように自クラス以外で処理する方法も含めて検討してください。

- コンストラクタを呼び出す前にあらかじめ処理を行い、その結果をコンストラクタの引数に渡す
- コンストラクタ内で別のクラスに処理を委譲し、その結果を使用する

### <a name="no5-6">5.6.インスタンスが格納された変数をレシーバにして static メソッドを呼び出さないでください</a>

`static`メソッドは通常、クラス名をレシーバにして呼び出すコードを書きます。

インスタンスが格納された変数をレシーバにして呼び出すコードも文法的には許容されていますが、一般的ではないため行わないでください。

```java
//NG
final String text = ...
final int value = ...
return text.valueOf(value);
```

```java
//OK
final int value = ...
return String.valueOf(value);
```

### <a name="no5-7">5.7.インスタンスが格納された変数をレシーバにして static 変数を参照しないでください</a>

`static`変数は通常、クラス名をレシーバにして参照するコードを書きます。

インスタンスが格納された変数をレシーバにして参照コードも文法的には許容されていますが、一般的ではないため行わないでください。

```java
//NG
final Integer length = ...
return length.MAX_VALUE;
```

```java
//OK
return Integer.MAX_VALUE;
```

### <a name="no5-8">5.8.アプリケーションプログラマーの独断で例外クラスは作らないでください</a>

例外はメソッドの処理を中断して大域脱出ができる仕組みです。
適切な管理の元、統一的に扱うべきだと考えます。

必要な例外クラスはフレームワークが提供するものとし、アプリケーションプログラマーは提供されたクラスを使用してください。

もし例外クラスの作成が必要になった場合は、アーキテクトへ相談してください。

### <a name="no5-9">5.9.java.lang.Exceptionクラスのインスタンスを生成してスローしないでください</a>

明示的に例外をスローする場合に単なる`java.lang.Exception`クラスのインスタンスを生成してスローしないでください。
業務アプリケーションの方式設計に沿った例外クラスのインスタンスを生成してスローしてください。

`java.lang.Exception`はすべての例外の基底クラスなので、`catch`する側が業務アプリケーションの例外なのかネットワークの例外なのかといった判別ができません。

また、`java.lang.Exception`をスローすると`throws Exception`をメソッドに付与しなければいけませんが、呼び出し元のメソッドにも`throws Exception`を強要することになり、扱いづらくなってしまいます。

```java
//NG
if (items.isEmpty()) {
    throw new Exception("Items searched by " + code + " are not found.");
}
```

```java
//OK
if (items.isEmpty()) {
    throw new ItemsNotFoundException(code);
}
```

### <a name="no5-10">5.10.try-catch 文を条件分岐のために使用しないでください</a>

`try-catch`文は例外を扱うためのものです。
条件分岐をしたい場合は`if`文を使用してください。

また例外がスローされた場合、`catch`できるかどうかをチェックしますが、このチェックは高コストです。
特にループ内で`try-catch`を条件分岐のために使用していた場合は、性能劣化が顕著に起る可能性があります。

```java
//NG
try {
    //codeに対するitemが無い場合に例外をスローするAPI
    service.findItem(code);
    return "Items exist";
} catch (ItemsNotFoundException e) {
    return "No items";
}
```

```java
//OK
//codeに対するitemが存在するかチェックするAPI
if (service.exists(code)) {
    return "Items exist";
} else {
    return "No items";
}
```

### <a name="no5-11">5.11.秘匿情報はログ出力やシリアライズされないように注意してください</a>

パスワードのような秘匿情報はログに含まれないようにマスクするなど、注意をしてください。

また、インスタンスをシリアライズする場合も、秘匿情報が含まれないように注意してください。
なお、ここでは「シリアライズ」という言葉の意味として、JavaのシリアライズのみならずJSONやXMLなどのフォーマットに変換することも含めています。

例えばJavaのシリアライズの場合、シリアライズ対象外にしたいフィールドには`transient`キーワードを付与します。

```java
public class LoginForm implements Serializable {

    private String username;
    private transient String password;

    ...
}
```

JSONやXMLへのシリアライズでも、通常はライブラリがシリアライズ対象外にする方法を用意してありますので、適切に対応してください。

### <a name="no5-12">5.12.Java標準ライブラリにあるレガシーなAPIは使用しないでください</a>

Java標準ライブラリには過去のバージョンでは使われていましたが、今となってはレガシーであり使うべきではないAPIがあります。
次に列挙するレガシーAPIは使わないようにしてください。

|レガシーAPI|代替となる使っても良いAPI|
|---|---|
|`java.lang.StringBuffer`|`java.lang.StringBuilder`|
|`java.util.Dictionary`|`java.util.Map`|
|`java.util.Enumeration`|`java.util.Iterator`|
|`java.util.Hashtable`|`java.util.HashMap`|
|`java.util.Stack`|`java.util.Deque`|
|`java.util.StringTokenizer`|`String`の`split`メソッド<br>または`java.util.regex`パッケージのAPI|
|`java.util.Vector`|`java.util.ArrayList`|

これらのレガシーAPIを使用しているかどうかは使用不許可APIチェックツールによって検出できますが、知識として知っておくためにも本規約に明記しています。

### <a name="no5-13">5.13.リフレクションを直接使用しないでください</a>

`java.lang.reflect`パッケージにあるクラスを使ってできる操作をリフレクションと言いますが、リフレクションを直接使用しないでください。
リフレクションを使用すると動的なオブジェクト操作ができますが「コンパイルは通っているけれど、実行時にエラーが出る」といった状況を引き起こす原因になります。

フレームワークやアーキテクトから提供される共通部品が内部でリフレクションを使用していることはありますが、アプリケーションで直接使用することは禁止します。

リフレクションを使用しているかどうかは使用不許可APIチェックツールによって検出できますが、危険なコードを書かないためにも本規約に明記しています。

### <a name="no5-14">5.14.クラスを不整合な状態にしないでください</a>

クラス内に関連性のある複数のフィールドを定義した場合、それらのフィールド間で値の整合性を保つようにしてください。

```java
//NG
public class ItemList {

    private final List<Item> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void add(final Item item) {
        //この時点で合計値が変わるためtotalPriceを更新しなければいけない
        items.add(item);
    }

    public void save(final ItemDao dao) {
        //保存時についでに合計値の計算をしている
        totalPrice = BigDecimal.ZERO;
        for (final Item item : items) {
            dao.save(item);
            totalPrice = totalPrice.add(item.getPrice());
        }
    }

    public BigDecimal getTotalPrice() {
        //addの後にsaveを呼ばずにこのメソッドを呼ぶとItem追加前の合計値が返されてしまう
        return totalPrice;
    }
}
```

上記の例では`items`を変更した際、同時に`totalPrice`を変更すると良いでしょう。

```
//OK
public class ItemList {

    private final List<Item> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

    public void add(final Item item) {
        //itemを追加してすぐに合計値の計算をしているので状態の整合性が保たれている
        items.add(item);
        totalPrice = totalPrice.add(item.getPrice());
    }

    public void save(final ItemDao dao) {
        for (final Item item : items) {
            dao.save(item);
        }
    }

    public BigDecimal getTotalPrice() {
        //どのタイミングで呼び出しても正しい合計値を返す
        return totalPrice;
    }
}
```

もしくは合計値は状態として持たずに`getTotalPrice`内で都度計算するようにしても良いでしょう。

```
//OK
public class ItemList {

    private final List<Item> items = new ArrayList<>();

    public void add(final Item item) {
        items.add(item);
    }

    public void save(final ItemDao dao) {
        for (final Item item : items) {
            dao.save(item);
        }
    }

    public BigDecimal getTotalPrice() {
        return items.stream()
                .map(item -> item.getPrice())
                .reduce(BigDecimal.ZERO, (price1, price2) -> price1.add(price2));
    }
}
```

### <a name="no5-15">5.15.アプリケーションプログラマーの独断でスレッドを作って非同期処理をしないでください</a>

フレームワークはスレッドに紐付けてデータベースコネクションやトランザクションを管理していることがあります。
アプリケーションプログラマーが自分でスレッドを作って非同期処理を行うことは想定されていません。

また、フレームワークの制限が無いとしても非同期処理は高難度です。
パフォーマンスなどの観点から非同期処理が必要になった場合は、アーキテクトに相談してください。

### <a name="no5-16">5.16.名前と値が同じ定数は作らないでください</a>

定数の名前と値が同じになっているものは、もっと良い名前がある場合と、そもそも定数にしなくて良い場合があります。

次に示すのは、もっと良い名前がある例です。
定数名が改行コードの値そのものになっています。

```java
//NG
//この名前だともし改行コードがLFに変更となった場合に定数名も変更しなくてはいけない
private static final String CRLF = "\r\n";
```

この場合は定数名を変えると良いでしょう。

```java
//OK
//この名前だともし改行コードがLFに変更となった場合でも定数名はこのままで良い
private static final String LINE_BREAK = "\r\n";
```

次に示すのは、そもそも定数にしなくて良い例です。
あるテーブルのカラム名を定数にしたものです。

```java
//NG
private static final String ITEM_CODE = "ITEM_CODE";
```

もしカラム名が変わるとそれに追随して定数名も変えることになり、定数化している意味が薄まります。
別の名前を付けるとしたら`COLUMN_01`といったものしか考えられず、意味ある名前にはなりません。

この場合は定数にしなくて良いでしょう。

---

## <a name="no6">6.注意事項</a>

禁止とまではいきませんが、バグを防いだり保守性を損なわないために、注意事項を定めています。

### <a name="no6-1">6.1.メソッドには名前から想像できない処理を実装しないでください</a>

メソッド名から想像できない処理が実装されていると、そのメソッドの使用者は混乱します。

```java
//NG
//メソッド名からは「未読通知を取得する」処理だと想像できるが、
//実際には「既読状態への更新」も行っている
private List<Notification> findUnreadNotifications() {
    final List<Notification> notifications = dao.findUnreadNotifications();

    //未読から既読へ更新している
    for (Notification notification : notifications) {
        notification.setStatus(Status.ALREADY_READ);
        dao.update(notification);
    }

    return notifications;
}
```

```java
//OK
//メソッド名から想像できる通り「未読通知を取得する」処理を行っている
private List<Notification> findUnreadNotifications() {
    return dao.findUnreadNotifications();
}

//「既読状態への更新」は別メソッドとしている
private void updateToAlreadyRead(List<Notification> notifications) {
    for (Notification notification : notifications) {
        notification.setStatus(Status.ALREADY_READ);
        dao.update(notification);
    }
}
```

実装中はメソッドを使うのは自分だけなので大丈夫だと思っていても、コードレビューや保守など実装以降のフェーズになると他人の目に触れる機会があります。

誰が見ても自然に映るよう、「名は体を表す」ようなメソッドを実装してください。

### <a name="no6-2">6.2.単一のメソッドに複数の要素を詰め込まないでください</a>

単一のメソッドに「取得」「更新」「チェック」といった複数の要素を詰め込まないでください。
複数の要素を詰め込んだメソッドの名前は、`execute`や`update`など抽象的なものになりがちです。

アプリケーションのコードは「プレゼンテーション」や「ビジネスロジック」、「データアクセス」といったレイヤーに分かれています。
それぞれのレイヤーによって抽象度は異なるので一概には言えませんが、アプリケーションプログラマーが書くビジネスロジックでは単一のメソッドには複数の要素を詰め込まず具体的な名前を付けるよう努めてください。

### <a name="no6-3">6.3.クラス外に公開されるメソッドの引数や戻り値・フィールドの型は実装クラスではなくインターフェースで宣言してください</a>

クラス外に公開されるメソッドの引数や戻り値、フィールドの宣言には実装クラスではなくインターフェースを使用してください。

引数を実装クラスで宣言していると、引数の型を変更した場合に呼び出し元のコードも変更しなくてはいけません。

```java
//NG
//Listではなく、実装クラスであるArrayListで宣言している
public void saveItems(final ArrayList<Item> items) {
    ...
}
```

```java
//saveItemsメソッドを呼び出しているコード
//もしsaveItemsの引数がArrayListからLinkedListに変更された場合、
//こちらのコードも変更する必要がある（NG）
final ArrayList<Item> items = ...
dao.saveItems(items);
```

引数をインターフェースで宣言していると、そもそも実装クラスは呼び出し元で用意するものなので、上記のような問題は起きません。

```java
//OK
//Listインターフェースで宣言している
public void saveItems(final List<Item> items) {
    ...
}
```

戻り値を実装クラスで宣言している場合も、呼び出し元のコードで戻り値を受け取っている変数の型が実装クラスになっていると、やはり戻り値の型を変更した場合に呼び出し元のコードも変更しなくてはいけません。

```java
//NG
//Listではなく、実装クラスであるArrayListで宣言している
public ArrayList<Item> findAllItems() {
    ...
}
```

```java
//findAllItemsメソッドを呼び出しているコード
//もしfindAllItemsの戻り値がArrayListからLinkedListに変更された場合、
//こちらのコードも変更する必要がある（NG）
final ArrayList<Item> allitems = dao.findAllItems();
```

引数とは違って、呼び出し元コードの方で宣言している変数の型をインターフェースにしておけば実害はありません。
しかし、変数の型を実装クラスで宣言してしまう余地をなくすためにも、戻り値の型もインターフェースで宣言するようにしてください。

```java
//OK
//Listインターフェースで宣言している
public List<Item> findAllItems() {
    ...
}
```

`private`メソッドや`private`フィールドはクラス内に閉じており影響範囲が狭いので実装クラスで宣言しても構いませんが、そうする強い理由が無ければインターフェースで宣言しておいてください。

また、ローカル変数はメソッド内に閉じており影響範囲がかなり狭いので実装クラスで宣言しても構いません。
なお、Java 10から導入された`var`を使用してローカル変数を宣言すると、右辺の式を評価した結果の型として扱われます。
これはローカル変数の型を実装クラスで宣言していることと同義です。

```java
//OK
public void someMethod() {

    //ローカル変数なので実装クラスで宣言してもOK
    ArrayList<String> localVariable = new ArrayList<>();

    //Java 10から導入されたvarでローカル変数を宣言してもOK
    //この場合は右辺の値がArrayListなので、ArrayListで宣言したものとして扱われる
    //※ダイヤモンドオペレーターが使えず、右辺で型パラメーターをバインドしなくてはいけない点に注意
    var useVarKeyword = new ArrayList<String>();

    ...
}
```

### <a name="no6-4">6.4.メソッドのオーバーロードはオプションの省略用途のみに使用してください</a>

引数の順番が入れ替わっただけのメソッドをオーバーロードしたり、全く異なる型の引数を取るメソッドをオーバーロードするとコードが複雑になります。

メソッドのオーバーロードはオプション扱いの引数を省略したメソッドを定義する場合にのみ使用してください。
その際も、引数の順番に注意をして後ろの引数から省略するようにメソッドを設計してください。

```java
//NG
//引数の順番が変わっただけのオーバーロード
public void updateItem(final ItemCode code, final String name, final int version) {
    ...
}

public void updateItem(final ItemCode code, final int version, final String name) {
    ...
}

public void updateItem(final String name, final int version, final ItemCode code) {
    ...
}
```

```java
//NG
//全く異なる型の引数を取るオーバーロード
public void updateItem(final ItemCode code, final String name, final int version) {
    ...
}

public void updateItem(final ItemCode code, final LocalDateTime updatedAt) {
    ...
}
```

```java
//OK
//オプションの引数を省略するオーバーロード
public void updateItem(final ItemCode code, final String name, final int version) {
    LocalDateTime defaultUpdatedAt = ...
    updateItem(code, name, defaultUpdatedAt);
}

public void updateItem(final ItemCode code, final String name, final int version, final LocalDateTime updatedAt) {
    ...
}
```

### <a name="no6-5">6.5.未使用コードは残したままにしないでください</a>

試行錯誤や性能改善の過程で使用されなくなったメソッドや変数、デバッグ用のコードなどは残したままにせず削除してください。

ただし、次のような未使用コードは例外として扱います。
これらは削除せずに残しておいてください。

- フレームワークの制約で、必ず定義しないといけないメソッドや変数
- 自動生成されたコードに含まれる未使用メソッド・変数

### <a name="no6-6">6.6.クラスは大きくなりすぎないようにしてください</a>

クラスが大きすぎると内容の把握が難しくなり、保守性が低くなります。
そのような場合は、クラスを分割することを検討してください。

フィールドはクラスの状態を表すものですが、中でも変更可能なフィールドが多いとクラス全体の把握が難しくなります。
なるべく変更可能なフィールドを減らすか、分割して別のクラスに移動してください。

ただし次に示すようなデータの入れ物として機能するクラスは、変更可能なフィールドが多くなる傾向にありますが意味のある単位でクラスが作られているので分割しないでください。

- データベースのテーブルをマッピングするエンティティクラス
- HTMLのフォームをマッピングするフォームクラス

### <a name="no6-7">6.7.メソッドは大きくなりすぎないようにしてください</a>

メソッドが大きすぎると処理の把握が難しくなり、保守性が低くなります。
そのような場合は、メソッドを分割することを検討してください。

最も簡単なのはある程度の塊ごとに`private`メソッドへ切り出すことです。

また、メソッドの引数の個数も多くなりすぎないようにしてください。

### <a name="no6-8">6.8.インナークラスやstaticにネストしたクラス、匿名クラスは作りすぎないようにしてください</a>

インナークラスや`static`にネストしたクラス、匿名クラスの使用を特に禁止しませんが、これらのクラスを作りすぎてしまうとコードの可読性が低くなってしまいます。

もし大量のインナークラスや`static`にネストしたクラス、匿名クラスが必要な場合は、別の独立したクラスに切り出せないか検討してみてください。

### <a name="no6-9">6.9.可能な限りキャストは使用しないでください</a>

キャストはある型として扱っている値を強制的に異なる型として扱うようにする仕組みで、「コンパイルは通っているけれど、実行時にエラーが出る」といった状況を引き起こす原因になります。

Java 5からジェネリクスが導入されて、キャストを使用しなくてもほとんど困る事はなくなっているはずです。

### <a name="no6-10">6.10.ラッパークラスの変数とプリミティブ値を演算する際は、アンボクシングに注意してください</a>

`java.lang.Integer`のようなラッパークラスを`int`のようなプリミティブ値へ変換することをアンボクシングと言います。

ラッパークラスの変数はプリミティブ値を伴う演算を行う場合、コンパイラーによって自動でアンボクシング処理が差し込まれます。

```java
final Integer a = ...
final int b = ...

//コンパイラーによって変数aに対してintValueメソッド呼び出しが差し込まれる
//つまり実際の処理は final int c = a.intValue() + b; となる
final int c = a + b;
```

自動でアンボクシングが差し込まれるのは便利ですが、ラッパークラスの変数が`null`の場合にアンボクシングすると`java.lang.NullPointerException`がスローされてしまいます。

```java
final Integer a = null;
final int b = ...

//変数aがnullの場合、コンパイラーによって差し込まれたintValueメソッド呼び出しでNullPointerExceptionがスローされてしまう
final int c = a + b;
```

このため、ラッパークラスの変数とプリミティブ値を演算する場合は`null`チェックを行うなど、注意をしてコーディングしてください。

```java
final Integer a = ...
final int b = ...

if (a != null) {
    final int c = a + b;
    .
    .
    .
}
```

### <a name="no6-11">6.11.計算の誤差が許されない場合はBigDecimalを使用してください</a>

`float`と`double`は浮動小数点数と呼ばれる方法で表現される数値型ですが、浮動小数点数の計算はいくつかのケースで誤差を生じることがあります。

GUIの座標計算のように多少の誤差を無視できる場合は浮動小数点数を使用しますが、金利や値引きなどのように誤差が許されない場合は`java.math.BigDecimal`を使用してください。

```java
//NG
final double discountRage = 0.07;
return 1 - discountRate; //0.9299999999999999
```

```java
//OK
final BigDecimal discountRate = new BigDecimal("0.07");
return BigDecimal.ONE.subtract(discountRate); //0.93
```

なお、`BigDecimal`のコンストラクタには`double`の値を受け取るものがありますが、浮動小数点数で発生する誤差と同じような誤差が発生する場合もあるので使用しないでください。
`double`の値から`BigDecimal`インスタンスを得たい場合は`valueOf`メソッドを使用してください。

```java
//NG
final BigDecimal discountRate = new BigDecimal(0.07); //doubleの値を渡してインスタンス化
return BigDecimal.ONE.subtract(discountRate); //0.929999999999999993338661852249060757458209991455078125
```

```java
//OK
final BigDecimal discountRate = BigDecimal.valueOf(0.07); //doubleの値をvalueOfに渡してインスタンス化
return BigDecimal.ONE.subtract(discountRate); //0.93
```

また、`BigDecimal`の`equals`メソッドは値とスケールが同じである場合に等しいと見なします。
ですので、2つの`BigDecimal`の値を比較する場合は`compareTo`メソッドを使用してください。

```java
//NG
final BigDecimal value = new BigDecimal("10.0"); //スケールは1
if (value.equals(BigDecimal.TEN)) { //BigDecimal.TENのスケールは0なのでequalsはfalseを返す
    ...
}
```

```java
//OK
final BigDecimal value = new BigDecimal("10.0"); //スケールは1
if (value.compareTo(BigDecimal.TEN) == 0) { //comareToはスケールが異なっても値が等しい場合は0を返す
    ...
}
```

`BigDecimal`は誤差の無い計算ができる他、`java.math.RoundingMode`で多数の丸めモードをサポートしています。

### <a name="no6-12">6.12.ソートと集計はJava側ではなく、SQLで行うようにしてください</a>

データベース内のデータのソートや集計はJava側ではなくSQLで行ってください。

データが少量であればJava側でソートを行っても問題はありませんが、データが大量だと性能劣化の原因となります。
また、データベースから少量ずつフェッチして処理を行う場合はそもそもJava側でソートができません。

集計は通常、大量データに対して行うものですので、これもJava側で行うと性能劣化の原因となります。
それにソートと異なり集計前のデータは一時的に使用するもので、本当に必要なのは最終的な集計結果です。
このことからも`SUM`や`AVR`などの集計関数を使用してSQLで集計を行ってください。

### <a name="no6-13">6.13.ループ処理の中では極力データベースアクセスしないようにしてください</a>

業務アプリケーションでは、データベースから取得したデータをループで1件ずつ処理することがよくあります。

このとき、ループの中で追加の情報を取得するためにデータベースへアクセスしないようにしてください。
データベースアクセスの回数が多くなり、性能劣化の原因となります。

可能な限り、最初のデータ取得時にテーブル結合を使用して1回のデータベースアクセスで必要な情報を取得するようにしてください。

### <a name="no6-14">6.14.外部からの入力値は共通部品を用いてチェックしてください</a>

アプリケーション外部からの入力値にはいくつかの種類があります。
次に3つの例を挙げます。

- ブラウザから送信されたフォームの値
- 外部システムから連携されたファイルの内容
- MQで連携されたメッセージ

これらの値は必ず入力仕様に沿ってチェックをしてください。
例えばブラウザから送信されたフォームの値であればBean Validationを使用してバリデーションを行ってください。
他のものも同様で、フレームワークが提供している機能を使用して入力値チェックを行ってください。

なお「アプリケーション外部からの入力値」というとデータベースから取得したデータも該当しそうですが、自分たちのコントロール下にあるデータベースには既にチェック済みの信頼できる値が格納されているはずです。
このことから、データベースから取得したデータは入力値チェック対象外です。

### <a name="no6-15">6.15.ファイル入出力は共通部品のクラスを使用してください</a>

アプリケーションプログラマーが思い思いにファイル入出力を行うと文字コードや改行コードの取り扱いを統一させるのに労力がかかります。

アーキテクトはプロジェクトで定めるファイルフォーマット仕様に基づいて、ファイル入出力の共通部品を作成してください。
そしてアプリケーションプログラマーはファイル入出力を行う際は、共通部品のクラスを使用してください。

不足機能は共通部品に追加する方向でアーキテクトへ依頼・相談してください。

共通部品を使ってファイル入出力を行うことで文字コードや改行コードの取り扱いを統一できます。
また、共通部品内で要求されたファイルパスのチェックを行うことでディレクトリトラバーサルを防げます。

### <a name="no6-16">6.16.リソースをクローズする必要がある場合はtry-with-resources構文を使用してください</a>

`java.io.InputStream`や`java.io.OutputStream`など、外部リソースを使用するオブジェクトは`try-with-resources`構文を使用してクローズ漏れが無いようにしてください。

`try-with-resources`構文はJava 7で導入されました。

```java
//NG
//Java 6までのレガシーな書き方
final InputStream in = openStream();
try {

    final String content = readAsString(in);

} finally {
    in.close();
}
```

```java
//OK
//Java 7以降のtry-with-resources
try (final InputStream in = openStream()) {

    final String content = readAsString(in);

}
```

### <a name="no6-17">6.17.例外処理はプロジェクトの方式設計に従って統一的にコーディングしてください</a>

例外処理方式はプロジェクトで統一されていることが重要になります。
プロジェクトの方式設計に従って統一的にコーディングしてください。

### <a name="no6-18">6.18.ループのネストはできれば二重までにしてください</a>

ループのネストが深くなるとコードの可読性が低下します。
可読性は主観によるものなのでこの指標は絶対ではないですが、本規約ではループのネストは二重までと定めます。

---

## <a name="no7">7.推奨事項</a>

より良いコードを書くために、推奨事項を定めています。

### <a name="no7-1">7.1.クラスやメソッドなどには適切なアクセス修飾子を付与してください</a>

クラスやメソッドなど、アクセス修飾子を付与できる場所では適切なアクセス修飾子を選択してください。

アクセス修飾子の種類と公開範囲を次に示します。

|アクセス修飾子|公開範囲|
|---|---|
|`public`|全てのクラスからアクセス可能|
|`protected`|自分自身、同一パッケージのクラス、サブクラスからアクセス可能|
|（なし）|自分自身、同一パッケージのクラスからアクセス可能|
|`private`|自分自身のみアクセス可能|

みだりに`public`で宣言せず、必要がなければ狭い範囲になるようアクセス修飾子を付与してください。

### <a name="no7-2">7.2.インスタンス変数は原則 private にしてください</a>

インスタンス変数はクラス外に露出するべきではありません。
原則`private`としてください。

例外的に、フレームワークの制約で`private`以外にしなくてはならない場合は適切なアクセス修飾子を付与してください。

また、抽象クラスを作成してサブクラスで参照させたいインスタンス変数がある場合は`protected`にしてください。
ただし、その場合でもみだりにインスタンス変数を公開するのではなく、メソッドを併用することでインスタンス変数を`private`にできないか検討してください。

### <a name="no7-3">7.3.ローカル変数はできるだけ狭いスコープで使用してください</a>

ローカル変数のスコープができるだけ狭くなるように利用する場所と近い位置で宣言をしてください。

```java
//NG
final String text = ...

callMethod1();
callMethod2();
callMethod3();
.
.
.
callMethodN();

//textを使わない処理が延々と続いた後に初めてtextを使う処理が登場
useText(text);
```

```
//OK
callMethod1();
callMethod2();
callMethod3();
.
.
.
callMethodN();

//textを宣言してすぐに使用している
final String text = ...
useText(text);
```

また、ローカル変数のスコープはブロック単位です。
特定のブロック内でしか使用しないローカル変数は該当のブロック内で宣言してください。

```java
//NG
//if文のブロック内でしか使用されないのにブロック外で宣言されている
final String text = ...
if (isSuccess(result)) {
    useText(text);
}
callOtherMethod();
return;
```

```java
//OK
//if文のブロック内で宣言されている
if (isSuccess(result)) {
    final String text = ...
    useText(text);
}
callOtherMethod();
return;
```

### <a name="no7-4">7.4.なるべく再代入は避けて、そのためにfinalを活用してください</a>

コードは「変更可能な状態」が少なければ少ないほど、全体の把握がしやすく理解しやすい傾向にあります。

変数を再代入するということは、知らないうちに「変更可能な状態」を作っていることになります。

```java
String value = "hello";

...

//変数の再代入をすると「変更可能な状態」となり、コードを読んでいる途中で
//「今この変数の値は何か？」を常に気にする必要が出てくる
value = "world";
```

コードを読むときに把握すべきことを減らしましょう。
そのために変数の再代入は避けましょう。

変数の再代入を避けるために`final`を活用してください。
変数を宣言する際に`final`を付けると、その変数は再代入不可になります。

```java
final String value1 = "hello";

...

//再代入不可なので次のコメントアウトしているコードはコンパイルエラーになる
//value1 = "world";

//既存の変数に再代入せずに、新しい変数を導入する
final String value2 = "world";
```

ローカル変数だけではなく、フィールドにも`final`を付けて再代入不可に出来ないか検討してみてください。
フレームワークの制約などで必ず`setter`を定義しないといけない場合もありますが、そうでない場合はなるべくフィールドも再代入不可にしてください。

### <a name="no7-5">7.5.なるべく引数の状態を変えないでください</a>

コードは「状態を変更する箇所」が局所的であればあるほど、全体の把握がしやすく理解しやすい傾向にあります。

引数の状態を変更してしまうと、状態を変更する箇所が広がってしまうことになります。

```java
//※これはやらない方が良い非推奨コードの例
//
//消費税計算をした後に引数自身に税をセットしている
//なるべくなら消費税計算をするだけにとどめて、itemに税をセットするのは呼び出し元が行った方が良い
public BigDecimal calculateTax(final Item item) {
    BigDecimal tax = item.getPrice().multiply(taxRate);
    item.setTax(tax);
    return tax;
}
```

`private`メソッドであればクラス内に閉じているので影響範囲が限られていますが、`public`や`protected`などのクラス外に公開されるメソッドではなるべく引数の状態を変更しないことを推奨します。


### <a name="no7-6">7.6.戻り値として null を使いたい場合、Optional の使用を検討してください</a>

値が「無い」状態を表す手段として`null`を使う方法と、Java 8から導入された`java.util.Optional`を使う方法があります。

戻り値を`java.util.Optional`にすると、値を返さない場合があるメソッドだということをシグネチャで表現できます。

```java
//値を返さない場合があることがメソッドのシグネチャから読み取れる
public Optional<String> maybeReturnValue() {
    ...
    if (...) {
        return Optional.of(value);
    }
    return Optional.empty();
}

//（Optionalではないので）必ず値を返すことがメソッドのシグネチャから読み取れる
public String mustReturnValue() {
    ...
}
```

`null`は値がある場合と同じ型で表現できるので、値を返さない場合があるメソッドだということをシグネチャで表現できず、Javadocへの記載などで対応しなくてはいけません。

```java
//値を返さない場合があることがメソッドのシグネチャから読み取れない
public String maybeReturnValue() {
    ...
    if (...) {
        return value;
    }
    return null;
}

//必ず値を返すことがメソッドのシグネチャから読み取れない
public String mustReturnValue() {
    ...
}
```

`java.util.Optional`を使用すると、メソッドの呼び出し元で戻り値の存在チェックをするべきかどうかを型で表現できるので、コンパイラーのサポートを受けられて安全です。
値を返さない場合があるメソッドでは戻り値を`java.util.Optional`にすることを検討してみてください。

```java
//Optionalを使用したメソッドの場合
//戻り値が無い場合を考慮しないといけないことが型を見れば分かる
final Optional<String> optional = maybeReturnValue();
final String value = optional.orElse(defaultValue);
```

```java
//nullを使用したメソッドの場合
//戻り値が無い場合を考慮しないといけないことが型を見ても分からない
//※nullチェックが漏れていてもコンパイルエラーにならず発見が遅れる
String value = maybeReturnValue();
if (value == null) {
    value = defaultValue;
}
```

### <a name="no7-7">7.7.コレクションのように型パラメーターを取るクラスを使用する場合は適切な型をバインドしてください</a>

`java.util.List<E>`や`java.util.Map<K, V>`のようなコレクションや`java.util.Optional<T>`は、型パラメーターが定義されています。
このようなクラスを使用する場合は適切な型をバインドしてください。

適切な型をバインドすることでコンパイラーによる型チェックを活かせます。

```java
//NG
//型パラメーターがバインドされていないためNG
final List raw = new ArrayList();
//バインドされた型が抽象的すぎるためNG
final List<Object> objects = new ArrayList<>();
```

```java
//OK
//具体的な型をバインドしているためOK
final List<Item> items = new ArrayList<>();

final String item = ...
items.add(item); //バインドされた型と不一致なのでコンパイルエラーになる

final Item item = ...
items.add(item); //バインドされた型と一致するのでコンパイルが通る
```

### <a name="no7-8">7.8.コレクションの処理はStream APIを使用して簡潔に書くことを検討してください</a>

Java 8から導入されたStream APIを使用すれば`java.util.List`や`java.util.Set`などのコレクションを簡潔なコードで処理できる場合があります。

Stream APIは`filter`や`map`、`collect`といったメソッドを使用して各要素に対する操作を小さく設定できます。
そのため、各要素に対してどのような処理をするのかが分かりやすくなる傾向にあります。

|メソッド|説明|コード例|
|---|---|---|
|`filter`|条件に合う要素だけに絞り込む|`stream.filter(x -> x % 2 == 0) //偶数だけに絞り込む`|
|`map`|要素を変換する|`stream.map(x -> x.getClass()) //Classに変換する`|
|`collect`|`Collector`によって`Stream`を変換する|`stream.collect(Collectors.joining(", ")) //要素をカンマ区切りの文字列に変換する`|

その他のメソッドは[`java.util.stream.Stream`のJavadoc](https://docs.oracle.com/javase/jp/10/docs/api/java/util/stream/Stream.html)で確認してください。

Stream APIを使用したコード例と拡張for文を使用したコード例を次に示します。
どちらも従業員のリストから職種がプログラマーの従業員だけに絞り込んで平均年齢を算出しています。

Stream APIを使用したコード例の方が、どのような処理を積み重ねて結果を得ているのかが分かりやすく感じないでしょうか。

```java
//Stream APIを使用したコード例
final List<Employee> employees = ...
final IntSummaryStatistics statistics = employees.stream()
        //職種がプログラマーだけに絞る
        .filter(emp -> emp.getJobCategory().equals(programmer))
        //年齢を抽出
        .mapToInt(emp -> emp.getAge())
        //集計する
        .summaryStatistics();
//平均を算出
final double average = statistics.getAverage();
```

```java
//拡張for文を使用したコード例
final List<Employee> employees = ...
double tempAge = 0;
int tempSize = 0;
for (final Employee employee : employees) {
    //職種がプログラマーだけに絞る
    if (employee.getJobCategory().equals(programmer)) {
        //年齢を抽出して一時変数へ足し込む
        tempAge += employee.getAge();
        //平均を求めるため分母となる数をインクリメント
        tempSize++;
    }
}
//平均を算出
final double average = tempAge / tempSize;
```

必ず簡潔になるとは限りませんし、コードの読みやすさ・分かりやすさは主観によるものなので強制ではありませんが、コレクションを処理する際はStream APIを使用することを検討してみてください。

### <a name="no7-9">7.9.チェック例外のスローを伴う処理はStream APIではなく拡張for文を使用して実装してください</a>

Stream APIはコレクションの操作を簡潔に書けますが、チェック例外のスローが宣言されているメソッドを呼び出す場合はラムダ式の中で`try-catch`を書かなくてはいけません。
せっかく簡潔に書けるはずのStream APIが`try-catch`によって煩雑なコードになってしまいます。
そのため、チェック例外のスローを伴う操作が含まれる場合は拡張`for`文を使用してください。

ファイルの入出力では多くの場合に`java.io.IOException`がスローされます。
コレクション操作に伴ってファイルを処理する場合は拡張`for`文を使用すると良いでしょう。

```java
//NG
final List<String> heads = files.stream()
        .map(file -> {
            //readLineでIOExceptionがスローされる可能性がある
            try (BufferedReader in = openReader(file)) {
                return in.readLine();
            } catch (final IOException e) {
                throw new UncheckedIOException(e);
            }
        })
        .filter(head -> head != null)
        .collect(Collectors.toList());
```

```java
//OK
final List<Path> files = ...
final List<String> heads = new ArrayList<>();
for (Path file : files) {
    //readLineでIOExceptionがスローされる可能性がある
    try (BufferedReader in = openReader(file)) {
        final String head = in.readLine();
        if (head != null) {
            heads.add(head);
        }
    }
}
```

### <a name="no7-10">7.10.レガシーfor文はなるべく使用せず、Stream APIか拡張for文の使用を検討してください</a>

コレクションの要素を順次処理するにはStream APIと拡張`for`文が使えるので、レガシー`for`文を使用する場面は通常ありません。
レガシー`for`文はなるべく使用しないようにしてください。

### <a name="no7-11">7.11.配列全体をコピーする場合はcloneメソッドを使用してください</a>

配列全体をコピーする場合は`clone`メソッドを使用するのが最もシンプルです。

```java
//OK
final Item[] values = ...
final Item[] copied = values.clone();
```

`java.util.Arrays`クラスの`copyOf`メソッドを使用しても良いです。
このメソッドは第2引数でコピーする長さを指定できます。

```java
//OK
final Item[] values = ...
final Item[] copied = Arrays.copyOf(values, values.length);
```

`clone`と`Arrays.copyOf`はどちらもシャローコピーを行います。
ディープコピーをしたい場合はループしながら各要素に対してもコピー処理を行う必要があります。

```java
//OK
final Item[] values = ...
final List<Item> temp = new ArrayList<>(values.length);
for (Item item : values) {
    temp.add(copyItem(item));
}
final Item[] copied = temp.toArray(new Item[0]);
```

例に示したケースではJava 8から導入されたStream APIを使用すると、より簡潔なコードになります。

```java
//OK
final Item[] values = ...
final Item[] copied = Arrays.stream(values)
        .map(item -> copyItem(item))
        .toArray(Item[]::new);
```

### <a name="no7-12">7.12.コレクションを配列に変換する場合はtoArrayメソッドを使用してください</a>

コレクションには配列に変換する`toArray`メソッドが用意されています。
各要素をループして配列を作ったりせず、`toArray`メソッドを使用してください。

```java
//NG
final List<Item> items = ...
final Item[] itemArray = new Item[items.size()];
int index = 0;
for (final Item item : items) {
    itemArray[index++] = item;
}
```

```java
//OK
final List<Item> items = ...
final Item[] itemArray = items.toArray(new Item[0]);

//Stream APIにもtoArrayメソッドが用意されている
//Streamを配列に変換したい場合はこのメソッドを使用する
final Item[] itemArray = items.stream().toArray(Item[]::new);
```

このコード例では`toArray`メソッドに渡す配列を長さ`0`で初期化しています。
元となるコレクションの`size`メソッドを長さに指定して初期化することも可能ですが、パフォーマンスの差はほぼありません。
そのため、どちらの初期化方法を選択しても良いですが、本規約のコード例では見やすさを考慮して長さ`0`で初期化しています。

### <a name="no7-13">7.13.配列をコレクションに変換する場合はArrays.asList、またはList.ofを使用してください</a>

配列のユーティリティである`java.util.Arrays`クラスにはリストに変換する`asList`メソッドが用意されています。
各要素をループしてリストを作ったりせず、`java.util.Arrays`クラスの`asList`メソッドを使用してください。

```java
//NG
final Item[] itemArray = ...
final List<Item> items = new ArrayList<>(itemArray.length);
for (final Item item : itemArray) {
    items.add(item);
}
```

```java
//OK
final Item[] itemArray = ...
final List<Item> items = Arrays.asList(itemArray);
```

なお、Java 9からは`java.util.List`に`of`メソッドが追加されたので、こちらを使用しても構いません。

```
//OK
final Item[] itemArray = ...
final List<Item> items = List.of(itemArray);
```

Java 9からは`java.util.Set`にも`of`メソッドが追加されました。
これまで配列から`java.util.Set`に変換しようとすると、一旦`java.util.List`に変換してから`java.util.Set`を生成したり、Java 8からはStream APIを使用して変換していました。
Java 9からは簡潔なコードで変換できるようになりました。

```java
//Java 7までの変換方法
final Set<Item> items =  new HashSet<>(Arrays.asList(itemArray));

//Java 8からはStream APIで変換できる
final Set<Item> items =  Arrays.stream(itemArray).collect(Collectors.toSet());

//Java 9からはより簡潔に変換できる
final Set<Item> items = Set.of(itemArray);
```

### <a name="no7-14">7.14.メソッドをオーバーライドしたり、抽象メソッドを実装する場合はメソッドに@Overrideを付けてください</a>

サブクラスでスーパークラスのメソッドをオーバーライドする場合、サブクラス側のメソッドに`@Override`を付けてください。
`@Override`を付けておくと、コンパイラーが本当にオーバーライドされたメソッドであるかチェックしてくれます。

```java
//NG
public class SuperClass {

    public void someMethod() {
        ...
    }
}

public class SubClass extends SuperClass {

    //メソッド名が間違っておりオーバーライドになっていない。
    //コンパイルは通るのでミスに気付きにくい。
    public void sameMethod() {
        ...
    }
}
```


```java
//OK
public class SuperClass {

    public void someMethod() {
        ...
    }
}

public class SubClass extends SuperClass {

    //@Overrideを付けているとオーバーライドになっていない場合はコンパイルエラーになる。
    //コンパイル時にミスに気づくことができる。
    @Override
    public void sameMethod() {
        ...
    }
}
```

Java 6からはインターフェースで宣言された抽象メソッドを実装する際にも`@Override`が使用できるようになりました。
スーパークラスのメソッドをオーバーライドする際と同様に`@Override`を付けるようにしてください。

```java
//OK
public class SomeAction implements Runnable {

    @Override
    public void run() {
        ...
    }
}
```

---

## <a name="no8">8.使用可能なAPI</a>

業務アプリケーションを開発するのに十分なだけのAPIを厳選することで、品質を確保するための規約です。

### <a name="no8-1">8.1.使用可能な標準APIを使用して実装してください</a>

Java標準ライブラリのうち使用可能なAPIについては、[Java標準ライブラリ使用可能API](./staticanalysis/spotbugs/spotbugs-example/spotbugs/published-config/production/JavaOpenApi.config)を参照してください。

また、[使用不許可APIチェックツール](./staticanalysis/unpublished-api/README.md)を使用してチェックを行えますので活用してください。

---

## <a name="no9">9.Nablarchライブラリ</a>

Nablarchではアプリケーションプログラマー向けに公開するAPIを厳選しています。
それらのAPIに絞って開発することで、品質を確保するための規約です。

### <a name="no9-1">9.1.使用可能なNablarchのAPIを使用して実装してください</a>

Nablarchのライブラリのうち使用可能なAPIについては、アプリケーションプログラマー向けのJavadocを参照してください。

業務要件上Javadocに記載のないAPIを使用する必要がある場合は、アーキテクトへ相談するようにしてください。

また、[使用不許可APIチェックツール](./staticanalysis/unpublished-api/README.md)を使用してチェックを行えますので活用してください。
