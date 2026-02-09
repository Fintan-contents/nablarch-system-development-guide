# SLF4Jアダプタ

SLF4J経由でNablarchのログ出力機能を使用するためのアダプタ

**目的**: SLF4Jを使用するOSSライブラリのログをNablarchのログ出力機能で統一管理


**外部ライブラリ**:
- [SLF4J 2.0.11 (テスト済み)](https://www.slf4j.org/)

**nablarch_version**: 6u1以降

**対応Nablarchバージョン**: 6u1以降

**公式ドキュメント**:
- [SLF4Jアダプタ](https://nablarch.github.io/docs/LATEST/doc/application_framework/adaptors/slf4j_adaptor.html)

---

## setup

**依存関係**:

- `com.nablarch.integration:slf4j-nablarch-adaptor` (scope: runtime)

**maven_example**:

```java
<dependency>
  <groupId>com.nablarch.integration</groupId>
  <artifactId>slf4j-nablarch-adaptor</artifactId>
  <scope>runtime</scope>
</dependency>
```

**gradle_example**:

```gradle
runtimeOnly 'com.nablarch.integration:slf4j-nablarch-adaptor'
```

---

## configuration

依存関係を追加するだけで使用可能（追加設定不要）

**required_settings**:


**log_output**: Nablarchのログ設定（log.properties）に従う

---

## usage

SLF4Jが実行時に必要なクラスを自動で検出するため、プロジェクトの依存モジュールに追加するだけで使用できる

**SLF4Jを使用するOSSライブラリの例**:

HibernateなどSLF4Jでログ出力するライブラリを使用する場合、自動的にNablarchのログ機能経由で出力される

```java
// ライブラリ側のコード（変更不要）
Logger logger = LoggerFactory.getLogger(MyClass.class);
logger.info("message");
```

**best_practices**:

- 依存関係の追加のみで動作する（最もシンプルなアダプタ）

---

## notes

- SLF4Jのバージョン2.0.11を使用してテストを行っている
- バージョンを変更する場合は、プロジェクト側でテストを行い問題ないことを確認すること
- SLF4Jのバージョン2.0.0以降はロギング実装の検索方法が変わっている
- 互換性のない1.7系のバージョンが使用された場合、"Failed to load class org.slf4j.impl.StaticLoggerBinder"のログが出力され、以降のログ出力が行われないため注意

---

## limitations


---
