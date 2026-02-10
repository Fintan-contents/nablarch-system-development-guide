# プロジェクト管理システム（Proman）

これは「プロジェクト管理システム」のソースコードです。
（**Pro**ject **Man**agement Systemを略して、**Proman**と名付けています）

## セットアップ（WSL/Ubuntu環境）

### 1. 前提ツールのインストール

```bash
# GitHub CLI
sudo apt update
sudo apt install gh -y

# Java 21、Maven、PostgreSQLなど（必要に応じて）
```

### 2. 環境変数の設定

```bash
# .env.exampleから.envを作成
cp .env.example .env

# .envを編集して必要な値を設定
# - AWS_ACCESS_KEY_ID
# - AWS_SECRET_ACCESS_KEY
# - GH_TOKEN
```

### 3. 環境変数の読み込み

```bash
source .env
```

以下の3つのモジュールにより構成されます。

| モジュール名     | 役割                                           |
| ---------------- | ---------------------------------------------- |
| proman-common    | 基盤部品、Entity等のプロジェクト共有モジュール |
| proman-web       | Webモジュール                                  |
| proman-batch     | バッチモジュール                               |

`proman-jmeter`は回帰テストの自動実行ツールです。詳細は[単体テストの考え方（Web）](../../サンプルプロジェクト開発ガイド/PGUT工程/ut/単体テストの考え方（Web）.md)を参照してください。