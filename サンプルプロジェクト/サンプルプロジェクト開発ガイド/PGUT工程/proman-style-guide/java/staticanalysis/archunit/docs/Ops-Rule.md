# ArchUnit運用ガイド

実際のプロジェクトでArchUnitを運用していく際のガイドです。

ここでは、アーキテクチャ「違反」という言葉を「ArchUnitを利用してテストしているアーキテクチャルールに違反している」という意味で使用します。

## 基本的な考え方

### 原則1: アーキテクチャ違反は全て解決します



### 原則2: アーキテクチャ違反を解消するために品質を下げてはいけません




### 原則3: 正当な理由があってアーキテクチャ違反となる場合、チェック対象から外します

正当な理由がある場合は、アーキテクチャ違反となっているソースコードをチェック対象から除外するようにします。

プロジェクトのアーキテクトは開発者からの申請を受けて妥当であると判断した場合、
該当箇所をチェック対象から除外するようにしてください。

この運用をすることによって、違反が放置されたり、開発者が無理に違反を回避したりすることを防げます。

## 例外登録ルール

### アーキテクチャ違反除外方法

アーキテクチャ違反となっている箇所をテスト対象から外すには、基本的にテストコードに除外する対象を直接記載する必要がある。
テスト対象によって記載が異なるため、注意してください。

### 除外対象クラス設定

テスト対象がクラス（レイヤーも含む）の場合、以下のように `and()`（または元々 `that()` がなければ `that()`）の引数に `JavaClass.Predicates.equivalentTo(除外対象クラス)` を使用して除外設定を行う。

``` java
@ArchTest
public static final ArchRule ActionクラスはBatchActionを継承していること =
        ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Action")
        .and(DescribedPredicate.not(JavaClass.Predicates.equivalentTo(PromanExampleAction.class)))  // #12345
        .should().beAssignableTo(BatchAction.class);
```

テスト対象がクラス内に含まれるもの（フィールドやメソッドなど）の場合、 `areNotDeclaredIn()` を使用して除外設定をおこなう。

``` java
@ArchTest
public static final ArchRule DaoContextを引数にとるメソッドはパッケージプライベートであること =
        ArchRuleDefinition.methods().that().haveRawParameterTypes(DaoContext.class)
            .and().areNotDeclaredIn(PromanExamAction.class)  // #1234
            .should().bePackagePrivate();
```

除外設定をする場合は次のようにしておくと、後で経緯を追跡できます。

- 課題管理システムの課題管理番号をコメントに記載する
- バージョン管理システムのコミットコメントに記載する

### 除外対象パッケージ設定

テスト対象がクラスの場合、以下のようにして特定のパッケージを除外できます。
以下の例では `common` を含むパッケージを除外しています。

```java
@ArchTest
public  static  final ArchRule 基盤以外のパッケージでNoDataExceptionを使用しているクラスがないこと =
        ArchRuleDefinition.noClasses().that().resideOutsideOfPackage("..common..")
        .should().dependOnClassesThat().areAssignableTo(NoDataException.class);
```

ただし、パッケージは特定の文字列にて対象を決定することになるため、あらかじめパッケージ構成について検討しておく必要があります。

### カスタムルールによる除外設定

カスタムルールを実装することで、より細かな除外設定を行うことができます。
内容については [ArchUnit User Guide](https://www.archunit.org/userguide/html/000_Index.html#_creating_custom_rules) を参照してください。