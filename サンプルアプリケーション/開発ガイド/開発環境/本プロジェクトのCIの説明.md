# 本プロジェクトのCIの説明

## 使用ソフト

- Jenkins ver. 2.60.3

## Pipeline定義の所在

- [Jenkinsfile(developブランチ)](https://collaborage-ci.keel-dev.net/gitbucket/shaft/proman-project/blob/develop/Jenkinsfile)  

## パイプライン定義の使用構文

-  Declarative Pipeline  
   補足：JenkinsのPipelineの種類は[こちら](https://jenkins.io/doc/book/pipeline/)を参照してください。

## パイプライン処理概要

Jenkinsから変更を毎分ポーリングし、変更があったタイミングでビルドを行います。

- 全ブランチを対象にユニットテストと静的解析を行います
- developブランチを対象にデモ環境へのデプロイを行います

以下を順に行います。

１．コンパイルおよびユニットテスト
２．静的解析

以降はdevelopブランチのみ実施します。

３．デモサーバ配置用jarファイルのビルド
４．デモサーバへの配置


ビルド結果はRocket.Chatへ通知します。

