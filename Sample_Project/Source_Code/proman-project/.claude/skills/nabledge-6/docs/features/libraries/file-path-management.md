# ファイルパス管理

システムで使用するファイルの入出力先のディレクトリや拡張子を管理するための機能を提供する

**目的**: ディレクトリや拡張子を論理名で管理し、ファイルの入出力を行う機能では論理名を指定するだけでそのディレクトリ配下のファイルに対する入出力を実現できる


**モジュール**:
- `com.nablarch.framework:nablarch-core`

**機能**:

- ディレクトリを論理名で管理できる

- 拡張子を論理名で管理できる

- 論理名を指定するだけでファイルの入出力が可能



**classes**:

- nablarch.core.util.FilePathSetting



**annotations**:


**公式ドキュメント**:
- [ファイルパス管理](https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/file_path_management.html)

---

## configuration

| プロパティ | 型 | 必須 | 説明 |
|-----------|-----|:----:|------|
| `basePathSettings` | `Map<String, String>` | ✓ | ディレクトリの論理名とパスのマッピング。キーは論理名、値はファイルパス（スキーム付き） |
| `fileExtensions` | `Map<String, String>` |  | 拡張子の論理名と拡張子のマッピング。キーは論理名、値は拡張子 |

**basePathSettingsの注記**:
- スキームは file と classpath が使用できる
- 省略した場合は classpath となる
- classpathスキームの場合、そのパスがディレクトリとして存在している必要がある（jarなどのアーカイブされたファイル内のパスは指定できない）
- パスにはスペースを含めない（スペースが含まれているパスは指定できない）

**fileExtensionsの注記**:
- 1つのディレクトリに対して複数の拡張子を設定する場合には、論理名を複数設定する
- 拡張子のないファイルの場合には、その論理名の拡張子設定を省略する

**xml_example**:

```xml
<component name="filePathSetting" class="nablarch.core.util.FilePathSetting">
  <!-- ディレクトリの設定 -->
  <property name="basePathSettings">
    <map>
      <entry key="csv-input" value="file:/var/nablarch/input" />
      <entry key="csv-output" value="file:/var/nablarch/output" />
      <entry key="dat-input" value="file:/var/nablarch/input" />
      <entry key="fixed-file-input" value="file:/var/nablarch/input" />
    </map>
  </property>

  <!-- 拡張子の設定 -->
  <property name="fileExtensions">
    <map>
      <entry key="csv-input" value="csv" />
      <entry key="csv-output" value="csv" />
      <entry key="dat-input" value="dat" />
      <!-- fixed-file-inputは拡張子が存在しないので、拡張子の設定は行わない -->
    </map>
  </property>
</component>
```

**component_name**: filePathSetting

**component_class**: nablarch.core.util.FilePathSetting

**configuration_points**:

- FilePathSettingのコンポーネント名は filePathSetting とすること（固定）
- basePathSettingsにディレクトリを設定する
- fileExtensionsに拡張子を設定する
- 1つのディレクトリに対して複数の拡張子を設定する場合には、論理名を複数設定する
- 拡張子のないファイルの場合には、その論理名の拡張子設定を省略する

---

## usage

| メソッド | シグネチャ | 説明 |
|----------|-----------|------|
| `getFileWithoutCreate` | `public File getFileWithoutCreate(String logicalPathName, String fileName)` | 論理名とファイル名から、ファイルパスを取得する。ファイルが存在しない場合でも、ファイルオブジェクトを生成して返す |
| `getBaseDirectory` | `public File getBaseDirectory(String logicalPathName)` | 論理名からベースディレクトリのパスを取得する |

**getFileWithoutCreate**:

パラメータ:
- `logicalPathName` (String): 論理名
- `fileName` (String): ファイル名（拡張子なし）

戻り値: Fileオブジェクト（ディレクトリパス + ファイル名 + 拡張子）

```java
// /var/nablarch/input/users.csv
File users = filePathSetting.getFileWithoutCreate("csv-input", "users");

// /var/nablarch/input/users (拡張子なし)
File users = filePathSetting.getFileWithoutCreate("fixed-file-input", "users");
```

**getBaseDirectory**:

パラメータ:
- `logicalPathName` (String): 論理名

戻り値: Fileオブジェクト（ディレクトリパス）

```java
// /var/nablarch/output
File csvOutputDir = filePathSetting.getBaseDirectory("csv-output");
```

**typical_usage**: 論理名を使ってファイルパスを取得し、ファイル入出力処理に渡す。環境ごとに異なるディレクトリパスをコンポーネント設定ファイルで切り替えることで、コードを変更せずに複数環境に対応できる

---

## anti-patterns

| パターン | 理由 | 正しい方法 |
|----------|------|------------|
| classpathスキームを使用してウェブアプリケーションサーバ（JBoss、Wildfly等）で実行する | 一部のウェブアプリケーションサーバでは本機能を使用できない。これは、ウェブアプリケーションサーバが独自のファイルシステム（例: JbossやWildflyのvfsというバーチャルファイルシステム）を使用して、クラスパス配下のリソースなどを管理していることに起因する | fileスキームを使用する（classpathスキームではなくfileスキームを使用することを推奨） |
| パスにスペースを含める | スペースが含まれているパスは指定できない（仕様上の制限） | スペースを含まないパスを使用する |
| jarなどのアーカイブされたファイル内のパスをclasspathスキームで指定する | classpathスキームの場合、そのパスがディレクトリとして存在している必要がある（アーカイブされたファイル内のパスは指定できない） | ディレクトリとして存在するパスを指定するか、fileスキームを使用する |

---

## tips

**title**: 拡張子のないファイルの扱い

**description**: 拡張子のないファイルの場合には、その論理名のfileExtensions設定を省略する。getFileWithoutCreateメソッドを呼び出すと、拡張子なしのファイルパスが取得できる

**title**: 1つのディレクトリに対する複数の拡張子の設定

**description**: 1つのディレクトリに対して複数の拡張子を設定する場合には、論理名を複数設定する。例えば、csv-inputとdat-inputで同じディレクトリを指定し、それぞれ異なる拡張子を設定する

**title**: コンポーネント名の固定

**description**: FilePathSettingのコンポーネント名は filePathSetting とすること（固定）。この名前でコンポーネントを登録することで、フレームワークが自動的に参照できる

**title**: スキームのデフォルト動作

**description**: スキームを省略した場合は classpath となる。ただし、classpathスキームには制限があるため、fileスキームの使用を推奨


---

## limitations

- classpathスキームを使用した場合、一部のウェブアプリケーションサーバ（JBoss、Wildfly等）では本機能を使用できない
- classpathスキームの場合、そのパスがディレクトリとして存在している必要がある（jarなどのアーカイブされたファイル内のパスは指定できない）
- パスにはスペースを含めない（スペースが含まれているパスは指定できない）

---
