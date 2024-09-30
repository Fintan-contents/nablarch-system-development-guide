# Checkstyleルール解説

用意された設定ファイルに記載されたCheckstyleのルールについて解説します。

ここでは以下の用語を使用することとします。

- OK: 設定ファイル記載のCheckyStyleルールに準拠していることを意味します。
- NG: 設定ファイル記載のCheckyStyleルールに違反していることを意味します。※ルールの重要度(error, warning等)は問いません。

以下、目次です。

<!-- START doctoc -->

- [AbstractClassName](#abstractclassname)
- [ArrayTypeStyle](#arraytypestyle)
- [AvoidStarImport](#avoidstarimport)
- [AvoidStaticImport](#avoidstaticimport)
- [CatchParameterName](#catchparametername)
- [ClassTypeParameterName](#classtypeparametername)
- [ConstantName](#constantname)
- [CyclomaticComplexity](#cyclomaticcomplexity)
- [EmptyCatchBlock](#emptycatchblock)
- [EqualsAvoidNull](#equalsavoidnull)
- [EqualsHashCode](#equalshashcode)
- [FallThrough](#fallthrough)
- [FileLength](#filelength)
- [Header](#header)
- [HiddenField](#hiddenfield)
- [HideUtilityClassConstructor](#hideutilityclassconstructor)
- [IllegalCatch](#illegalcatch)
- [IllegalThrows](#illegalthrows)
- [InnerAssignment](#innerassignment)
- [InterfaceTypeParameterName](#interfacetypeparametername)
- [MissingJavadocType](#missingjavadoctype)
- [MissingJavadocMethod](#missingjavadocmethod)
- [JavadocMethod](#javadocmethod)
- [JavadocType](#javadoctype)
- [JavadocVariable](#javadocvariable)
- [LambdaParameterName](#lambdaparametername)
- [LocalFinalVariableName](#localfinalvariablename)
- [LocalVariableName](#localvariablename)
- [MemberName](#membername)
- [MethodLength](#methodlength)
- [MethodName](#methodname)
- [MethodTypeParameterName](#methodtypeparametername)
- [MissingSwitchDefault](#missingswitchdefault)
- [ModifiedControlVariable](#modifiedcontrolvariable)
- [NeedBraces](#needbraces)
- [NoFinalizer](#nofinalizer)
- [PackageDeclaration](#packagedeclaration)
- [PackageName](#packagename)
- [ParameterName](#parametername)
- [RecordComponentName](#recordcomponentname)
- [RecordTypeParameterName](#recordtypeparametername)
- [RedundantImport](#redundantimport)
- [SimplifyBooleanExpression](#simplifybooleanexpression)
- [StaticVariableName](#staticvariablename)
- [StringLiteralEquality](#stringliteralequality)
- [TodoComment](#todocomment)
- [TypeName](#typename)
- [UnusedImports](#unusedimports)
- [UnusedLocalVariable](#unusedlocalvariable)
- [UpperEll](#upperell)
- [VisibilityModifier](#visibilitymodifier)

<!-- END doctoc -->

## AbstractClassName

```xml
<module name="AbstractClassName">
  <property name="format" value="^(Abstract.*|.*FormBase)$"/>
</module>
```

抽象クラスの名前をチェックします。

抽象クラスの名前は次の規則のうちどちらか片方を満たすようにしてください(OK)。

- `Abstract`から始まること
- `FormBase`で終わること

これらの条件を満たしていない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。


## ArrayTypeStyle

```xml
<module name="ArrayTypeStyle"/>
```

配列の宣言スタイルをチェックします。

配列の宣言はJavaスタイルを使用してください(OK)。
Cスタイルの宣言はNGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
// 次のように要素の型の後ろに括弧を付けるJavaスタイルの宣言をOKとします
String[] array = ...

// 次のように変数名の後ろに括弧を付けるCスタイルの宣言はNGです
String array[] = ...
```

## AvoidStarImport


```xml
    <module name="AvoidStarImport"/>
```

`import`文に`*`を使っていないことをチェックします。

`import`文には`*`を使用せず、1クラスずつimportするようにしてください(OK)。
`*`を使っている`import`文が存在する場合、NGとなります。

`import`文に`*`を使用すると、本来使用しないクラスまで`import`対象になります。
そのため`import`しているパッケージに変更や追加があった場合、名前が衝突しやすくなります。

```java
// 実際はListとArrayListしか使っていないのにjava.utilパッケージをまるごとimportしている（NG）。
import java.util.*;

    ///// 中略 /////
    
    public void example() {
        List<String> list = new ArrayList<>();
    }
}

```

## AvoidStaticImport

```xml
    <module name="AvoidStaticImport">
      <property name="severity" value="warning"/>
    </module>
```

static importが無いことをチェックします。

static importは使用せず、`<クラス名>.<メンバー名>`というように記載してください(OK)。
static import文が存在する場合、NGとなります。

static importを濫用すると、外部のメソッド呼び出しをローカルのメソッド呼び出しと勘違いしてしまうなど、 コードの読み手に誤解を与える恐れがあります。

```java
import static java.lang.System.out;

    ///// 中略 /////
    
    /**
     * AvoidStaticImportのコード例です。
     */
    public void example() {
        // static importを使用しています（NG）
        out.println("Hello World!");

        // static importを使用していません（OK）
        System.out.println("Hello World!");
    }
}
```

指定したクラスのstatic importをチェック対象外とする設定が可能です。詳細は以下を参照。

- http://checkstyle.sourceforge.net/config_imports.html#AvoidStaticImport

## CatchParameterName

```xml
<module name="CatchParameterName">
  <property name="format" value="^[a-z][a-zA-Z0-9]*$"/>
</module>
```

catchパラメータ名をチェックします。

catchパラメータの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

## ClassTypeParameterName

```xml
<module name="ClassTypeParameterName"/>
```

クラスにバインドされた型パラメーターの名前をチェックします。

型パラメーターの名前は以下の条件を満たすようにしてください(OK)。

- 大文字のアルファベット1文字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。


```java
/**
 * 型パラメーターが大文字アルファベット1文字（OK）。
 */
public class ClassTypeParameterNameExample<T> {
}

/**
 * 2文字以上の名前（NG）。
 */
class NgClassTypeParameterNameExample1<FOO> {
}

/**
 * 小文字アルファベットの名前（NG）。
 */
class NgClassTypeParameterNameExample2<t> {
}
```

## ConstantName

```xml
<module name="ConstantName">
  <property name="severity" value="error"/>
  <property name="format" value="^[A-Z](_?[A-Z0-9]+)*$"/>
</module>
```

`static final`なフィールドの名前をチェックします。


`static final`なフィールドの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が大文字のアルファベットで、それ以降はアンダースコア・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

## CyclomaticComplexity

```xml
<module name="CyclomaticComplexity">
  <property name="max" value="10"/>
</module>
```

メソッドの循環的複雑度を計測してチェックします。

循環的複雑度からコードの状態を以下のよう考えることができます。

| 値      | 説明                                     |
| ------- | ---------------------------------------- |
| 1 - 10  | シンプルな構造, リスクが少ない           |
| 11 - 20 | やや複雑, リスクが中程度                 |
| 21 - 50 | 複雑, リスクが高い                       |
| 50 -    | テスト不可能なコード, 非常にリスクが高い |

循環的複雑度が許容値を超えないようにしてください(OK)。
循環的複雑度が許容値を超えた場合NGとなります。

```java
public int example(int a, int b, int c, String str) {
    if (a == 1) {                       // +1 (1)
        return 1;
    } else if (a == b && a == c) {      // +2 (3)
        if (b < 1) {                    // +1 (4)
            return 2;
        }
    }
    try {
        int d = Integer.parseInt(str);
        if (a == d) {                   // +1 (5)
            return switch (d) {
                case 2 -> 20;           // +1 (6)
                case 3 -> 30;           // +1 (7)
                case 4 -> 40;           // +1 (8)
                default -> 99;          // +1 (9)
            };
        }
    } catch (NumberFormatException e) { // +1 (10)
        throw new IllegalArgumentException(e);
    }
    return a < 0 ? -1 : 1;              // +1 (11)
}
```

## EmptyCatchBlock


```xml
    <module name="EmptyCatchBlock"/>
```

空の`catch`節がないことをチェックします。

`catch`節に処理がない場合は、処理が必要ない理由をコメントで記載するようにしてください(OK)。
空の`catch`節が存在する場合、NGとなります。

空の`catch`節は、コンパイルエラーを解消するためだけの理由で書かれている可能性があります。
この場合、本来は適切に処理されるべき例外・エラーが揉み消されて、障害発生時の原因究明が困難になります。
処理がなくてもよい`catch`節とエラー回避のため`catch`節とを区別できるように`catch`節に処理が必要ない適切な理由を記載するようにしてください。

デフォルト設定では、以下のように`catch`節に何らかのコメントが記載されている場合はNGになりません。

```java
    public void example() {

        try {
            Files.readAllLines(new File("hoge.txt").toPath());
        // 空のcatch節（NG）。
        } catch (IOException e) {
        }


        try {
            Files.readAllLines(new File("hoge.txt").toPath());
        } catch (IOException e) {
            // catch節にコメントを入れた場合、Checkstyle違反になりません（OK）。
        }
    }
```

## EqualsAvoidNull

```xml
    <module name="EqualsAvoidNull">
      <property name="severity" value="error"/>
    </module>
```

変数とリテラルの`equals`メソッドによる比較方法をチェックします。

変数とリテラルを`equals`メソッドで比較する場合、リテラルを左辺に配置してください(OK)。
逆に、右辺が変数、左辺がリテラルの場合NGとなります。

変数が左辺にあると、もし変数が`null`だった場合に`NullPointerException`がスローされます。
リテラルを左辺にすると、このような`NullPointerException`は回避できます。

```java
//リテラルがequalsメソッドのレシーバなのでOK
if ("literal".equals(value1)) {
    System.out.println("equality");
}

//変数がequalsのレシーバなのでNG
if (value1.equals("literal")) {
    System.out.println("equality");
}

//変数同士ならOK
if (value1.equals(value2)) {
    System.out.println("equality");
}
```

## EqualsHashCode


```xml
    <module name="EqualsHashCode">
      <property name="severity" value="warning"/>
    </module>
```

`equals`メソッド、`hashCode`メソッドがペアになっていることをチェックします。

`equals`メソッド、`hashCode`メソッドは、どちらもオーバライドしないか、両方オーバーライドするかのいずれかとしてください(OK)。
片方だけオーバーライドされている場合NGとなります。

一般に、`equals`メソッドをオーバライドしているのに`hashCode`メソッドをオーバライドしていないというケースが多いです。
この場合、典型的にはコレクションから要素を取り出す際の性能劣化に繋がります。

`equals`メソッド、`hashCode`メソッドのオーバーライドについては以下を参照してください。

- https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/lang/Object.html#equals(java.lang.Object)
- https://docs.oracle.com/javase/jp/17/docs/api/java.base/java/lang/Object.html#hashCode()


## FallThrough

```xml
    <module name="FallThrough"/>
```

`switch`文にフォールスルーがないことをチェックします。

`case`内にステートメントが1文でもある場合、明示的にbreakをしてください(OK)。
ステートメントが1文でもある`case`をフォールスルーしている場合NGとなります。

このようなフォールスルーは、わかりにくいバグを埋め込みやすいので避けてください。

```java
    public void badExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
            // ゴールド会員の処理...
            bonus = 1000;
        case SILVER:
            // case GOLD:をフォールスルーをしている（break忘れのバグ）。
            // シルバー会員の場合の処理...
            bonus = 100;
            break;
        default:
            // その他場合の処理...
            bonus = 10;
        }
    }
```

`case`内にステートメントを含まないフォールスルーは許容されます(OK)。

```java
    public void notBadExample(MemberRank memberRank) {
        int bonus;
        switch (memberRank) {
        case GOLD:
        case SILVER:
            // ゴールド会員またはシルバー会員の場合の処理...
            bonus = 500;
            break;
        default:
            // その他場合の処理...
            bonus = 10;
        }
    }
```


## FileLength

```xml
  <module name="FileLength">
    <property name="severity" value="error"/>
  </module>
```

ファイルの行数をチェックします。

1つのJavaファイルの行数は2000行以下となるようにしてください(OK)。
2000行を超える場合、NGとなります。

大きすぎるJavaファイルには大量のフィールドや大量のメソッドを抱えているクラスが定義されていることが予想されます。
そういったクラスは扱いづらく、保守性も低くなります。

コーディングのときは、クラスをコンパクトに保つよう心がけてください。


## Header

```xml
  <module name="Header">
    <property name="headerFile" value="${config_loc}header.txt"/>
    <property name="severity" value="warning"/>
  </module>
```

ファイルの先頭に規定のヘッダーがあるかチェックします。

ヘッダーに書く内容は`headerFile`プロパティでファイルを指定して設定します。
これはシステム名や著作権など、共通のヘッダーをすべてのファイルへ漏れなく入れたい場合に使用するルールです。


## HiddenField

```xml
    <module name="HiddenField">
      <property name="severity" value="error"/>
      <property name="ignoreConstructorParameter" value="true"/>
      <property name="ignoreSetter" value="true"/>
    </module>
```

ローカル変数がフィールドを隠していないかチェックします。

ローカル変数の名前はフィールドと同名にならないよう注意してください。
ローカル変数がフィールドを隠していると、フィールドを参照しているつもりで書いたコードがローカル変数を参照していた、といった問題を起こすリスクがあります。

```java
public class HiddenFieldExample {

    private String foo;

    //コンストラクタ引数はフィールドと同名でもOK
    public HiddenFieldExample(String foo) {
        this.foo = foo;
    }

    //setterの引数はフィールドと同名でもOK
    public void setFoo(String foo) {
        this.foo = foo;
    }

    //setter以外のメソッドはフィールドと同名の引数はNG
    public void method1(String foo) {
    }

    public void method2() {
        //フィールドと同名のローカル変数はNG
        String foo = "NG";
    }
}
```


## HideUtilityClassConstructor

```xml
    <module name="HideUtilityClassConstructor">
      <property name="severity" value="error"/>
    </module>
```

ユーティリティクラスのコンストラクタが公開されていないかチェックします。

ユーティリティクラスとは`static`メソッドと`static`フィールドのみを持つクラスの事を指します。
ユーティリティクラスはインスタンス化する必要が無いため、コンストラクタは`private`にしてください。

```java
public class HideUtilityClassConstructorExample {

    /**
     * コンストラクタが同一パッケージのクラスに対して公開されている（NG）
     *
     */
    HideUtilityClassConstructorExample() {
        //nop
    }

    public static long count(final char c, final String s) {
        ...
    }
}
```
## IllegalCatch

```xml
<module name="IllegalCatch">
  <property name="severity" value="error"/>
  <property name="illegalClassNames" value="java.lang.Exception, java.lang.Throwable, java.lang.RuntimeException, java.lang.Error"/>
</module>
```

指定された例外・エラーを`catch`していないことをチェックします。

次に列挙する例外・エラーはアプリケーションで`catch`しないでください。

- `java.lang.Exception`
- `java.lang.Throwable`
- `java.lang.RuntimeException`
- `java.lang.Error`

これらを`catch`している場合NGとなります。

これらは汎用的なものであり、フレームワークが`catch`して処理をするべきものです。
アプリケーションで例外を`catch`する場合は、より具体的な型を`catch`してください(OK)。

## IllegalThrows

```xml
<module name="IllegalThrows">
  <property name="severity" value="error"/>
  <property name="illegalClassNames" value="java.lang.Exception, java.lang.Throwable, java.lang.RuntimeException, java.lang.Error"/>
</module>
```

指定された例外・エラーを`throws`で宣言していないことをチェックします。

次に列挙する例外・エラーはアプリケーションで`throw`しないでください。

- `java.lang.Exception`
- `java.lang.Throwable`
- `java.lang.RuntimeException`
- `java.lang.Error`

これらを`throws`で宣言している場合NGとなります。

これらは汎用的なものであり、例外・エラーの原因特定のための情報が不足しています。
アプリケーションで例外を`throw`する場合は、より具体的な型を選択するようにして、`throws`でも具体的な型を宣言してください(OK)。

## InnerAssignment

```xml
<module name="InnerAssignment">
  <property name="tokens" value="ASSIGN"/>
</module>
```

変数への代入がトップレベルで行われていることをチェックします。

変数への代入は式の途中ではなく、トップレベルで行ってください(OK)。
式途中での代入はNGとなります。

式の途中で代入した場合、どこで代入がされているかがわかりにくくなり可読性を低下させます。

```java
// 変数iへの代入がトップレベルで行われている(OK)
int i = 2;
String s = Integer.toString(i);

// 変数iへの代入が式の中で行われている(NG)
int i;
String s = Integer.toString(i = 2);
```

## InterfaceTypeParameterName

```xml
<module name="InterfaceTypeParameterName"/>
```

インターフェースにバインドされた型パラメーターの名前をチェックします。

型パラメーターの名前は以下の条件を満たすようにしてください(OK)。

- 大文字のアルファベット1文字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。


```java
/**
 * 型パラメーターが大文字アルファベット1文字（OK）。
 */
public interface InterfaceTypeParameterNameExample<T> {
}

/**
 * 2文字以上の名前（NG）。
 */
interface NgInterfaceTypeParameterNameExample1<FOO> {
}

/**
 * 小文字アルファベットの名前（NG）。
 */
interface NgInterfaceTypeParameterNameExample2<t> {
}
```

## MissingJavadocType

クラス、列挙型のJavadocコメント有無をチェックします。

Javadocコメントを記載するようにしてください(OK)。
Javadocコメントは、コードの読み手にとって重要な情報となります。

```xml
    <module name="MissingJavadocType">
      <property name="scope" value="private"/>
      <property name="severity" value="error"/>
    </module>
```

##  MissingJavadocMethod

メソッドのJavadocコメント有無をチェックします。

Javadocコメントを記載するようにしてください(OK)。
Javadocコメントは、コードの読み手にとって重要な情報となります。


```xml
    <module name="MissingJavadocMethod">
      <property name="scope" value="private"/>
      <property name="severity" value="error"/>
    </module>
```


## JavadocMethod

メソッドのJavadocコメント記載内容をチェックします。

Javadocコメントに@paramや@returnを記載するようにしてください(OK)。
書かれていない場合、NGとなります。



```java
    /**
     * 与えられた数を2倍にします。
     *
     * Javadocコメントはありますが、@param、@returnがありません（NG）。
     *
     * メソッドにthrows宣言がなくても非チェック例外の@paramsタグを記載できます
     * （Checkstyle NGとなりません）
     *
     * @throws IllegalArgumentException 引数が負数の場合
     */
    public int noParamAndReturnTag(int number) {
        if (number < 0) {
            throw new IllegalArgumentException();
        }
        return number * 2;
    }
```

## JavadocType

```xml
    <module name="JavadocType">
      <property name="severity" value="error"/>
    </module>
```

型（クラス・インターフェース・列挙型・アノテーション）のJavadocコメントをチェックします。

型にJavadocコメントを記載するようにしてください(OK)。
Javadocコメントが書かれていない場合、NGとなります。

型のJavadocコメントは、その型が持つ機能概要と仕様上の注意点を記載するようにしてください。
これはソースコードの読み手にとって重要な情報となります。

```java
/**
 * JavadocTypeのコード例です。
 *
 * @author example
 * @param <T> 型パラメーターの例です
 * @since 1.0.0
 */
public class JavadocTypeExample<T> {
}

class NgJavadocTypeExample1 { // javadocコメントがありません（NG）。
}

/**
 * Javadocコメントはありますが、型パラメーターTの@paramがありません（NG）。
 * 
 * @author example
 */
class NgJavadocTypeExample2<T> {
}
```


## JavadocVariable

```xml
    <module name="JavadocVariable">
      <property name="severity" value="info"/>
      <property name="scope" value="public"/>
    </module>
```

メンバー変数のJavadocコメントをチェックします。

`public`なメンバー変数にJavadocコメントを記載するようにしてください(OK)。
Javadocコメントが書かれていない場合、NGとなります。

Javadocコメントは、コードの読み手にとって重要な情報となります。

上記設定では、`public`スコープの変数がチェック対象となります。

```java

    public static final String HELLO = "こんにちは";    // javadocコメントがない（NG）。

    /** 別れの挨拶 */
    public static final String GOODBYE = "さようなら";  // javadocコメントがある（OK）。
```


## LambdaParameterName

```xml
<module name="LambdaParameterName"/>
```

ラムダ式の引数の名前をチェックします。

ラムダ式の引数の名前は以下の条件を満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
// 先頭が大文字のアルファベットになっています（NG）。
Function<String, String> ng1 = BadName -> "NG";

// アンダースコアを使用しています（NG）。
Function<String, String> ng2 = bad_name -> "NG";

// ルールに従った命名です（OK）。
Function<String, String> ok = goodName -> "OK";
```


## LocalFinalVariableName


```xml
    <module name="LocalFinalVariableName"/>
```

`final`修飾されたローカル変数名をチェックします。

`final`修飾されたローカル変数名はlowerCamelCaseで命名してください(OK)。
この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
    public void example() {
        // final修飾されたローカル変数をUPPER_SNAKE_CASEで命名しています（NG）。
        final String VERY_IMPORTANT_MESSAGE = "こんにちは";

        // final修飾されたローカル変数をlowerCamelCaseで命名しています（OK）。
        final String veryImportantMessage = "さようなら";
    }
```

## LocalVariableName


```xml
<module name="LocalVariableName"/>
```

ローカル変数の名前をチェックします。

ローカル変数の名前は以下の条件を満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。


```java
/**
 * 先頭が大文字のアルファベットなのでNG
 */
private static String BadName;

/**
 * アンダースコアがあるのでNG
 */
private static String bad_name;

/**
 * ルールに従った名前なのでOK
 */
private static String goodName;
```

## MemberName

```xml
<module name="MemberName"/>
```

インスタンスフィールドの名前をチェックします。

インスタンスフィールドの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
/**
 * 先頭が大文字のアルファベットなのでNG
 */
private String BadName;

/**
 * アンダースコアがあるのでNG
 */
private String bad_name;

/**
 * ルールに従った名前なのでOK
 */
private String goodName;
```

## MethodLength

```xml
    <module name="MethodLength">
      <property name="severity" value="error"/>
    </module>
```

メソッドの行数をチェックします。

1つのメソッドが150行以下となるようにしてください(OK)。
150行を超えるメソッドが存在する場合、NGとなります。

メソッドの行数だけで単純に良し悪しを測れるわけではありませんが、大きすぎるメソッドは得てして改善の余地があります。

コーディングのときは、メソッドをコンパクトに保つよう心がけてください。

なお、メソッドの行数は本体のブロックだけでなく、宣言も含んでカウントされます。

## MethodName

```xml
<module name="MethodName">
  <property name="severity" value="error"/>
</module>
```

メソッドの名前をチェックします。

メソッドの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

## MethodTypeParameterName

```xml
<module name="MethodTypeParameterName"/>
```

メソッドにバインドされた型パラメーターの名前をチェックします。

型パラメーターの名前は以下の条件を満たすようにしてください(OK)。

- 大文字のアルファベット1文字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。


```java
/**
 * MethodTypeParameterNameのOK例です。
 * 
 * @param <T> 型パラメーターが大文字アルファベット1文字（OK）
 */
public <T> void okMethod() {
}

/**
 * MethodTypeParameterNameのNG例です。
 * 
 * @param <FOO> 2文字以上の名前（NG）。
 * 
 */
public <FOO> void ngMethod1() {
}

/**
 * MethodTypeParameterNameのNG例です。
 * 
 * @param <t> 小文字アルファベットの名前（NG）。
 * 
 */
public <t> void ngMethod2() {
}
```

## MissingSwitchDefault

```xml
    <module name="MissingSwitchDefault"/>
```

`switch`文に`default`ラベルがあるかチェックします。

`switch`文には`default`ラベルを記載するようにしてください(OK)。
`default`ラベルが無い場合はNGとなります。

defaultの書き忘れによるバグや、defaultのケース考慮もれを防ぐ意図があります。

```java
//defaultがある（OK）
switch (value) {
case 0:
    System.out.println("0");
    break;
case 1:
    System.out.println("1");
    break;
default:
    System.out.println("other");
    break;
}

//defaultがない（NG）
switch (value) {
case 0:
    System.out.println("0");
    break;
case 1:
    System.out.println("1");
    break;
case 2:
    System.out.println("2");
    break;
}
```

## ModifiedControlVariable

```xml
<module name="ModifiedControlVariable"/>
```

`for`文のブロック内でループカウンタの値を変更していないことをチェックします。

レガシー`for`文でのループカウンタの変更は、ブロック内ではなく、`for`文内で行ってください(OK)。
レガシー`for`文で、ステートメントのブロックでループカウンタの値を変更している場合はNGとなります。

ステートメントのブロック内でのループカウンター変更は、処理の流れが追いにくくなり、可読性が低下します。


```java
// ステートメントのブロックでループカウンタの値を変更していない(OK)
for (int i = 0; i < size; i++) {
    System.out.println(i);
}

// ステートメントのブロックでループカウンタの値を変更している(NG)
for (int i = 0; i < size; i++) {
    i++;
    System.out.println(i);
}
```

## NeedBraces

```xml
<module name="NeedBraces">
  <property name="severity" value="error"/>
</module>
```

コードブロックを囲う中括弧があることをチェックします。

`if`文や`for`文ではステートメントを`{`と`}`で囲ってブロックにしてください(OK)。
ブロックにしていない場合、NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
// ステートメントをブロックにしているのでOK
if (foo) {
    doMethod();
}

//ステートメントをブロックにしていないのでNG
if (foo)
    doMethod();
```

## NoFinalizer


```xml
    <module name="NoFinalizer">
      <property name="severity" value="error"/>
    </module>
```

`finalize`メソッドをチェックします。

`finalize`メソッドをオーバーライドしないでください(OK)。
`finalize`メソッドをオーバーライドしている場合、NGとなります。


ファイナライザのオーバーライドには様々な問題が指摘されています。
- https://www.jpcert.or.jp/java-rules/met12-j.html

通常、ファイナライザをオーバライドする必要はありません。
何らかのリソースを解放する用途には、`java.lang.AutoCloseable`や`try-with-resources`を使用してください。

```java
    // finalizeメソッドをオーバライドしています（NG）。
    @Override
    protected void finalize() throws Throwable {
        super.finalize();

        // （リソース解放処理）
    }
```

## PackageDeclaration

```xml
<module name="PackageDeclaration">
  <property name="severity" value="error"/>
</module>
```

パッケージ宣言があるかチェックします。

クラス・インタフェースにはパッケージ宣言を含めてください(OK)。
パッケージ宣言がない、つまりデフォルトパッケージのクラス・インターフェースは定義しないようにしてください。デフォルトパッケージにクラス・インタフェースを定義した場合NGとなります。

デフォルトパッケージにあるクラスは他のパッケージにあるクラスからimportできません。
サンプルなど簡易的な利用方法を除いて、デフォルトパッケージを利用するメリットはないので使用しないでください。

## PackageName

```xml
<module name="PackageName">
  <property name="severity" value="error"/>
  <property name="format" value="^[a-z]+(\.[a-z_][a-z0-9_]*)*$"/>
</module>
```

パッケージの名前をチェックします。

パッケージの名前は以下のルールを全て満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降はピリオド・小文字のアルファベット・アンダースコア・アラビア数字で構成されていること
- ピリオドのすぐ後は小文字のアルファベットまたはアンダースコアであること

これらの条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

## ParameterName

```xml
<module name="ParameterName"/>
```

パラメータの名前をチェックします。

パラメータの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
 * @param BadName 先頭が大文字のアルファベットなのでNG
 * @param bad_name アンダースコアがあるのでNG
 * @param goodName ルールに従った名前なのでOK
 */
public void example(String BadName, String bad_name, String goodName) {
```

## RecordComponentName

```xml
<module name="RecordComponentName"/>
```

レコードコンポーネントの名前をチェックします。

レコードコンポーネントの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

## RecordTypeParameterName

```xml
<module name="RecordTypeParameterName"/>
```

レコードクラスにバインドされた型パラメーターの名前をチェックします。

型パラメーターの名前は以下の条件を満たすようにしてください(OK)。

- 大文字のアルファベット1文字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

## RedundantImport

```xml
<module name="RedundantImport"/>
```

冗長な`import`をチェックします。

冗長な`import`文は削除してください(OK)。
重複する2つ以上の`import`や`java.lang`パッケージにあるクラスの`import`、それから同じパッケージにあるクラスの`import`があるとNGとなります。

冗長な記述はコードの可読性を低下させます。


## SimplifyBooleanExpression

```xml
<module name="SimplifyBooleanExpression"/>
```

冗長な`boolean`の式をチェックします。

冗長な`boolean`式を使用しないでください。
以下に示すような冗長な式が存在する場合、NGとします。

```java
b == true
b || true
!false
```

これらの冗長な式は、次の簡単な式に置き換え可能です(OK)。

```java
b
true
true
```

## StaticVariableName

```xml
<module name="StaticVariableName"/>
```

`static`かつ、否`final`なフィールドの名前をチェックします。

`static`かつ、否`final`なフィールドの名前は以下のルールを満たすようにしてください(OK)。

- 先頭が小文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。

```java
/**
 * 先頭が大文字のアルファベットなのでNG
 */
private static String BadName;

/**
 * アンダースコアがあるのでNG
 */
private static String bad_name;

/**
 * ルールに従った名前なのでOK
 */
private static String goodName;
```

## StringLiteralEquality

文字列の比較方法をチェックします。

文字列の比較には`equals`メソッドを使用してください(OK)。

文字列を`==`または`!=`で比較しないようにしてください。
そのような文字列比較が存在する場合、NGとなります。


比較演算子によって文字列を比較した場合、文字列が等価かどうかではなくオブジェクトが同一かどうかで比較してしまうため、期待しない動作となる場合があります。

```xml
    <module name="StringLiteralEquality">
      <property name="severity" value="error"/>
    </module>
```

```java
        // 文字列を==で比較している（NG）。
        if (s == "something") {
            // matched!
        }

        // 文字列をequalsで比較している（OK）。
        if (s.equals("something")) {
            // matched!
        }
```


## TodoComment

```xml
    <module name="TodoComment">
      <property name="severity" value="info"/>
      <property name="format" value="TODO\s+"/>
    </module>
```

ソースコードコメントに、TODOコメントが無いことをチェックします。

TODOコメントの対処漏れや、対処済みコメントの削除漏れがないようにしてください(OK)。
TODOコメントが存在する場合に違反となります。

TODOコメントを禁止することが目的ではありません。TODOコメントの対処漏れを無くすことが本チェックの本来の目的です。

```java
    // ソースコード上のTODOコメントがあります。
    // TODO 処理が複雑なので後でリファクタリングが必要!
    doSomeComplexProcess();

```


## TypeName

```xml
<module name="TypeName"/>
```

クラス・インターフェース・列挙型・アノテーション・レコードの名前をチェックします。

名前は以下のルールを満たすようにしてください(OK)。

- 先頭が大文字のアルファベットで、それ以降は小文字のアルファベット・大文字のアルファベット・アラビア数字で構成されていること

この条件を満たさない場合NGとなります。

コーディングスタイル統一のため、ルールに準拠してください。


## UnusedImports

```xml
    <module name="UnusedImports"/>
```

使用されていない`import`文をチェックします。

未使用の`import`文は削除してください(OK)。
使用されていない`import`文が存在する場合、NGとなります。

使用されていない`import`文は動作には影響しませんが、コードの読み手に当該クラスの依存関係を誤解させる恐れがあるため、取り除くべきです。

```java
// 使用されないimport文（NG）。
import java.math.BigDecimal;

// 使用されているimport文
import java.util.regex.Pattern;

public class UnusedImportsExample {

    private static Pattern alphabet = Pattern.compile("^[a-zA-Z]+$");
```

## UnusedLocalVariable

```xml
    <module name="UnusedLocalVariable"/>
```

使用されていないローカル変数をチェックします。

未使用のローカル変数は削除してください(OK)。
使用されていないローカル変数が存在する場合、NGとなります。

使用されていないローカル変数は動作には影響しませんが、コードの読み手に余計な負荷を掛けてしまうため、取り除くべきです。

## UpperEll



```xml
    <module name="UpperEll"/>
```

整数リテラルの文字をチェックします。

整数リテラルの接尾語に大文字の`L`を使用してください(OK)。
小文字の`l`を使用している場合、NGとなります。

小文字の`l`は`1`と見分けがつきにくいため、大文字の`L`を使用すべきです。

```java
        // 整数リテラルの接尾語に小文字の'l'を使用している（NG）。
        long bad = 1l;

        // 整数リテラルの接尾語に大文字の'L'を使用している（OK）。
        long good = 1L;
```


## VisibilityModifier

```xml
<module name="VisibilityModifier">
  <property name="severity" value="info"/>
</module>
```

クラスメンバーの可視性をチェックします。

`static final`な定数を除き、すべてのフィールドは`private`にしてください(OK)。
`private`でないフィールドが存在する場合、NGとなります。

この規則は、カプセル化を遵守させることを意図しています。
