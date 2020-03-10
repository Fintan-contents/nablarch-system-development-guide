# ArchUnit解説

ArchUnitはアーキテクチャのテストを行うためのライブラリです。
PJのアーキテクチャ設計により、実装するテストは変わってきます。
ここではNablarchを使用した場合に、頻出するテストパターンをArchUnitでどのように記述するかを解説します。

ArchUnitでチェックできる内容については[ArchUnit User Guide -> What to Check](https://www.archunit.org/userguide/html/000_Index.html#_what_to_check)を参照してください。

## 基本的な内容について

### 基本となる形式

非常によく使用する形式として

```
classes that ${PREDICATE} should ${CONDITION}
```

のように記載します。

これは `${PREDICATE}` であるクラスが `${CONDITION}` となることをチェックします。

この形式に則ったテストコードを以下に示します。
チェック内容としては、「クラス名の末尾が `Action` であるクラスは `public` であること」です。

```java
ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action").should().bePublic();
```

このチェックを行ったときにクラス名の末尾が `Action` であるのにパッケージプライベートであるクラスが存在すると違反をおかしていることになります。

ちなみに、上記の内容をJUnit4で実行可能なテストとした場合、以下のようになります。

```java
 
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.nablarch.example.proman")
public class ActionRuleTest {

  @ArchTest
  public static final ArchRule Actionがpublicであること = ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action").should().bePublic();

}
```

#### 注意事項

このルールはクラス名の末尾が `Action` であるのものに対して、チェックを行います。

クラス名の末尾が `Action` でなければ、当然チェック対象になりません。

### チェック対象

上記の例ではクラスをチェック対象にしていますが、その他にメソッドやコンストラクター、フィールドなどをチェック対象にできます。

また、 `ArchRuleDefinition.noClasses()` などとすることで、「そのようなクラスが存在しないこと」をチェックできます。

### チェック範囲

ArchUnitはテストクラスに `@AnalyzeClasses` を指定することでテスト範囲を指定します。

以下はパッケージを対象にチェックを行う場合の例となります。

```java
@AnalyzeClasses(packages = "com.nablarch.example")
public class ExampleRuleTest {
    // 省略
}
```

これによりクラスパス・モジュールパス上にある `com.nablarch.example` パッケージを範囲としたチェックを行います。

## よく使用するチェック

よく使用することになる以下の3つのチェックについて、いくつかのコード例と共に解説します。

- 宣言チェック
- 依存チェック
- レイヤーチェック

### 宣言チェック

宣言チェックでは、クラスやメソッド、フィールドの修飾子、型などを確認することになります。

たとえば以下の例では、 `DaoContext` のフィールドはprivateであり、finalであるが、staticでないことをチェックします。

注意しなければならないのは、修飾子について宣言時に付与しないことが意味をもつ場合は「〇〇でないこと」のチェックを含むようにしてください。（以下の例ではstaticでないことのチェックをしています。）

```java
ArchRuleDefinition.fields().that().haveRawType(DaoContext.class)
                .should().bePrivate()
                .andShould().beFinal()
                .andShould().notBeStatic();
```

次の例では、クラス名の末尾がActionの場合、BatchActionを継承していることをチェックします。

```java
ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
                .should().beAssignableTo(BatchAction.class);
```

最後の例では `IOException` をスローするクラスがないことをチェックします。

ここで対象にできるのは `throws` が宣言されているメソッドのみです。
`throws` が宣言されていない実行時例外はチェックできません。

```java
ArchRuleDefinition.noMethods().should().declareThrowableOfType(IOException.class);
```

### 依存チェック

依存チェックでは、クラス間の依存をチェックします。

最初の例ではserviceパッケージにあるクラスは、formパッケージにあるクラスへ依存してはいけないというチェックを行います。

否定系なので`noClasses()`でそのようなクラスがないことをチェックしています。

※ パッケージ指定の記法については、[こちら](https://javadoc.io/doc/com.tngtech.archunit/archunit/latest/com/tngtech/archunit/base/PackageMatcher.html)を参照してください。

```java
ArchRuleDefinition.noClasses().that().resideInAPackage("..service..")
                .should().dependOnClassesThat().resideInAPackage("..form..");
```

### レイヤーチェック

レイヤーチェックは[基本となる形式](#基本となる形式)に当てはまらない特殊な形式でチェックを記述します。

パッケージなどによりレイヤー名を定義し、定義したレイヤー間の依存関係をチェックします。

たとえば

- コントローラーを配置するactionパッケージ
- コントローラーからのみ使用するロジックをまとめたserviceパッケージ
- 画面の入力内容をコントローラーへ移送するformパッケージ

という3つのレイヤーがあった際の例を示します。

actionパッケージのみ他のパッケージに依存できるものとし、自パッケージへの依存も禁止するものとします。

```java
Architectures.layeredArchitecture()
    .layer("Action").definedBy("..action..")
    .layer("Service").definedBy("..service..")
    .layer("Form").definedBy("..form..")
    .whereLayer("Action").mayNotBeAccessedByAnyLayer()
    .whereLayer("Service").mayOnlyBeAccessedByLayers("Action")
    .whereLayer("Form").mayOnlyBeAccessedByLayers("Action");
```

パッケージでそれぞれのレイヤー名を定義した後、それぞれがどこのレイヤーからアクセス可能かをチェックしています。

## ArchUnitで提供されているAPIで実現できない場合

カスタムルールを実装することで、提供されているAPIでは実現できないようなチェックを行うことができます。  
詳細については [ArchUnit User Guide](https://www.archunit.org/userguide/html/000_Index.html#_creating_custom_rules) を参照してください。
