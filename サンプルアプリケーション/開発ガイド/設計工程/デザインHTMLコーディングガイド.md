# デザインHTMLコーディングガイド

## はじめに
本書は、デザインHTMLのコーディングに必要な情報について記載します。  

デザインHTMLは以下の用途で使用します。

- お客様との画面イメージのすり合わせ
- システム機能設計の画面イメージ
- PG工程でのJSP作成のインプット

## デザインHTMLの管理場所
GitBucketで管理します。  
https://collaborage-ci.keel-dev.net/gitbucket/shaft/proman-project-html

## フォルダ構成
WEB-INF/view/project,login等の機能名

## HTMLのテンプレート
デザインHTMLを作成するときは既にあるHTMLをコピーして作成してください。  

## コーディングルール
### レイアウトについて
既にあるHTMLを参考にレイアウトしてください。

部品のレイアウトには、Semantic UIの標準である16 columnsグリッドシステムを使用します。

参考： https://semantic-ui.com/collections/grid.html


### 使用するCSSクラス
Semantic UIで規定されているCSSクラスまたは、既にあるHTMLに定義されているCSSを使用してください。

- [Semantic UI Elements](https://semantic-ui.com/elements/button.html) ※
- [Semantic UI Collections](https://semantic-ui.com/collections/form.html) ※

前述のCSSで実装できない場合は、アーキテクトに相談してください。  
相談の結果、画面に固有のCSSクラスが必要であると判断された場合、個々のHTMLのhead要素内にCSSクラスを定義してください。

※ サイトに適切なindexページが存在しないため、特定の部品のページへのリンクにしています。  
　 ページを表示すると左側にメニューが表示されますので、そのメニューから部品を探してください。

### フォーマット
- VSCodeのフォーマッタに従います。  
  コミット前にフォーマッタを掛けてください。  
  参考：https://qiita.com/rubytomato@github/items/d4716c41a2d15c64f791

## その他ルール
- HTMLタグのみを使用してください。JSPのカスタムタグは使用しないでください。
- HTML→JSPタグへの変換が可能な部品を使用してください。
  例えば、aタグやform系タグでは無いボタン部品は使用しないでください。

- 不要なCSSクラスは付与しないでください。  
  HTMLをもとにJSPを作成しますので、JSPに不要なスタイル指定が行われる原因となります。
