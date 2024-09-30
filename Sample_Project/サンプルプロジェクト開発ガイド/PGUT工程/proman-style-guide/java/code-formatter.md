# Javaコードフォーマッター

プロジェクトでコードスタイルが統一されていなければ、コードの可読性が低下します。
コードスタイルを統一するためには、コードフォーマッターを使用することが効果的です。
機械的に対処することで、コーディング規約へ記載する必要が無くなり、コーディング規約では重要な事柄だけを記載できます。

IntelliJ IDEAやEclipse等のIDEでは、コードフォーマッターの機能が提供されています。
コードフォーマッターには、一般的に読みやすいとされているコードスタイルがデフォルトで設定されています。
もしカスタマイズが必要であれば、カスタマイズ後の設定をエクスポートやインポートすることで、プロジェクトの開発者間で共有できます。

また、使用するIDEが混在する場合でもコードスタイルを定義するツールを使用することで、最低限のコードスタイルを統一できます。

開発者がコードフォーマッターを設定し、コードを変更したらコードフォーマッターを実行することで、コードスタイルをプロジェクトで統一できます。

なお、開発者がコードフォーマッターを手動で実行する場合は、実行することを忘れてしまうといった状況も考えられます。
そのような状況を防ぐには、後述の自動的に実行する設定を行なったり、CheckStyleにインデントや空白有無などコードスタイルに関するルールを追加してチェックするといった方法があります。
CheckStyleでエラーとすることで開発者にコードフォーマッターの実行を強制するような運用についても検討してください。

## IntelliJ IDEA

### 設定方法

コードフォーマッターを設定する方法については、次のページを参考にしてください。

- [コードスタイルスキーム | IntelliJ IDEA ドキュメント](https://pleiades.io/help/idea/configuring-code-style.html)

#### 設定例

デフォルトの設定から以下の点を変更したプロファイルが[こちら](./assets/codestyle/intellij_formatter.xml)です。
（IntelliJ IDEA Community Edition `2023.1.4` の `Default` プロファイルをベースに作成しています）

- Imports
  - Class count to use import with '*'
    - `99`に変更
  - Names count to use static import with '*'
    - `99`に変更


### 実行方法

コードフォーマッターを実行する方法については、次のページを参考にしてください。
確実に実行されるよう、保存時に自動実行される設定としておくことを推奨します。

- [コードの整形 | IntelliJ IDEA ドキュメント](https://pleiades.io/help/idea/reformat-and-rearrange-code.html)

### 設定のインポートおよびエクスポート

コードスタイルの設定をエクスポートすることで、プロジェクトの開発者間で共有できます。
エクスポートおよびインポートの方法については、次のページを参考にしてください。

- [インポートおよびエクスポートスキーム | IntelliJ IDEA ドキュメント](https://pleiades.io/help/idea/configuring-code-style.html#import-export-schemes)

また、IntelliJ IDEAでは、Eclipseで設定したコードスタイル定義のインポートも可能です。
ただし、設定可能な内容が異なる部分もあるため、完全なマッピングでは無いことに注意してください。

## Eclipse

### 設定方法

Eclipseの設定から、`Java > Code Style > Formatter`を開いてください。
設定対象のプロファイルを選択し、`Edit`ボタンをクリックすることで、選択したプロファイルのコードスタイルを設定できます。

その他の詳細については、次のページを参考にしてください。

- [Code Formatter Preferences | Eclipse Platform](https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.user/reference/preferences/java/codestyle/ref-preferences-formatter.htm?cp=1_4_4_0_2_2)

#### 設定例

デフォルトの設定から以下の点を変更したプロファイルが[こちら](./assets/codestyle/eclipse_formatter.xml)です。
（Eclipse `4.28.0` の `Eclipse [built-in]` プロファイルをベースに作成しています）

- Indentation
    - Tab policy
        - `Spaces only`に変更
    - Indented elements
      - Statements within 'switch' body
          - チェック

### 実行方法

ファイルを選択して実行する場合は、エディタで対象のファイルを開いて、`Ctrl + Shift + F`を押すことで実行できます。

自動的に実行する場合は、Eclipseの設定から`Java > Editor > Save Actions`を開いて、`Perform the selected actions on save`および`Format source code`を選択することで、保存時にコードフォーマッターを実行するよう設定できます。 確実に実行されるよう、保存時に自動的に実行する設定にしておくことを推奨します。

その他の詳細については、次のページを参考にしてください。

- [Formatter | Eclipse Platform](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fref-java-editor-formatter.htm&cp%3D1_4_1_1)
- [Java Save Actions Preferences | Eclipse Platform](https://help.eclipse.org/latest/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2Freference%2Fpreferences%2Fjava%2Feditor%2Fref-preferences-save-actions.htm&cp%3D1_4_4_0_5_4)


### 設定のインポートおよびエクスポート

コードスタイルの設定をエクスポートすることで、プロジェクトの開発者間で共有できます。

Eclipseの設定から、`Java > Code Style > Formatter`を開いてください。
設定対象のプロファイルを選択し、`Export All`ボタンをクリックすることで、選択したプロファイルをエクスポートできます。
インポートする場合は、`Import`ボタンをクリックして、エクスポートしたファイルを選択してください。

その他の詳細については、次のページを参考にしてください。

- [Code Formatter Preferences | Eclipse Platform](https://help.eclipse.org/latest/topic/org.eclipse.jdt.doc.user/reference/preferences/java/codestyle/ref-preferences-formatter.htm?cp=1_4_4_0_2_2)

## EditorConfig

[EditorConfig](https://editorconfig.org/)は、コードスタイルを定義するためのツールです。
設定可能な項目は少ないですが、様々なエディタおよびIDEで使用でき、最低限のコードスタイルを統一できます。

### 設定方法

プロジェクトに`.editorconfig`ファイルを作成し、プロパティを定義します。
詳細については次のページを参考にしてください。

- [What is EditorConfig? | EditorConfig](https://editorconfig.org/#overview)

#### 設定例

次のコードスタイルを設定した`.editorconfig`ファイルの内容を記載します。

- フォーマット対象はJavaのソースファイル
- 文字コードが UTF-8
- 改行コードが LF
- インデントには空白を使用する
- インデントの深さは`4`
- 末尾の空白を除去
- ファイルが改行で終わる

```properties
root = true

[*.java]
charset = utf-8
end_of_line = lf
indent_style = space
indent_size = 4
tab_width = 4
trim_trailing_whitespace = true
insert_final_newline = true
```

### 実行方法

[EditorConfigに標準で対応しているエディタおよびIDE](https://editorconfig.org/#pre-installed)では、通常通りコードフォーマッターを実行します。

標準で対応していないエディタおよびIDEでは、[プラグイン](https://editorconfig.org/#download)を導入する必要があります。導入後は、通常通りコードフォーマッターを実行します。

なお、IntelliJ IDEAやEclipseではEditorConfigで設定したコードスタイルが優先されますが、IDEで設定したコードスタイルも適用されます。
そのため、EditorConfigで統一できるコードスタイルは、あくまでEditorConfigで設定可能な部分のみであることにご注意ください。
