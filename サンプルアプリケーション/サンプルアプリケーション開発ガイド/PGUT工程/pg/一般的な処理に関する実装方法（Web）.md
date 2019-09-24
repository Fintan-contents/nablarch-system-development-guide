# 一般的な処理に関する実装方法（Web）

## 画面間のデータの移送

### 取引間

- 取引間は疎結合にする。SessionUtil経由での受け渡しは行わないでください。
  例えば、プロジェクト詳細画面から検索/削除画面への遷移は、プロジェクトIDをGET/POSTして、DBからプロジェクト情報を再検索します。
- パラメータはFormを使用して受け渡します。  
  パラメータが1個しかなかったとしてもFormクラスを作成し、HttpRequest#getParamMap()から直接値を取り出さないでください。
- ユーザが直接入力しない値であっても必ずバリデーションを経由して値を取得してください。  
  ブラウザから送信される値を改変することは容易であるためです。

### 取引内

- SessionUtilを使用して値を画面間で共有します。
- SessionにはFormを格納せず、DTOまたはEntityを格納します。  
  Formは特定の画面と紐づいており、画面間での共有には向かないためです。

## セッション

### セッションストアの選定基準

アーキテクトから指定がない限りデフォルト(DBストア)を使用します。

### 削除のタイミング

以下のタイミングで削除を行います。
- 取引開始時の初期表示時
- 確定等、後続処理でセッションに設定したオブジェクトが不要になったタイミング

### セッションのオブジェクトが見つからなかった場合

ブラウザの戻るボタンを使用した結果、存在を仮定しているオブジェクトがセッションに存在しない可能性があります。  例えば、確定画面で戻るボタンをクリックした場合等です。  
この場合、 `SessionUtil#get(ExecutionContext, java.lang.String)` 呼び出し時に、SessionKeyNotFoundExceptionが送出されますがアプリ実装では何もしないでください。基盤側で例外をキャッチしてユーザーに400のレスポンスを返します。

## 画面遷移

### ページングによる遷移時に表示するデータ
- ページングボタン押したときに都度DBから検索します。  
  つまり、DBから取得した検索結果をセッションに格納し**ない**でください。

### 戻るボタンによる遷移

#### 画面表示に使用するFormクラスのデータ

セッションに格納したDTOまたはEntityから復元します。

#### 画面表示に使用するマスタ系データ

戻るたびにDBから再検索します。  
つまり、DBから取得したマスタデータをセッションに格納し**ない**でください。


### データの確定を行う画面の遷移

#### 本項の対象となる遷移パターン

以下の遷移パターンを対象とします。
```
入力確認画面
↓
(確定処理 画面は無し)
↓
完了画面
```

この場合、仕様に以下を含めます。
- 完了画面の表示にはリダイレクトを使用します
- 確定ボタンには二重サブミット防止を実装します

##### 完了画面表示の実装方法

実装はActionクラスで行います。  
`確定処理→(リダイレクト)→完了画面表示` 部分の実装方法について以下に示します。

````java

    @OnDoubleSubmission
    public HttpResponse create(HttpRequest request, ExecutionContext context) {
        //中略 登録処理

        //ステータスコード303を指定してリダイレクト(ステータスコードを指定しない場合302になる)
        return new HttpResponse(303, "redirect://completionOfCreation");
    }

    public HttpResponse completionOfCreation(HttpRequest request, ExecutionContext context) {
        //完了画面の表示
        return new HttpResponse("/WEB-INF/view/project/completionOfCreation.jsp");
    }
````


##### 二重サブミット防止の実装方法

実装はJSPとActionクラスのメソッドに行う必要があります。

###### JSP側の実装方法

ボタンのカスタムタグのallowDoubleSubmission属性と、formタグのuseToken属性を使用して実装します。  
実装方法は以下を参照してください。  
https://nablarch.github.io/docs/LATEST/doc/application_framework/application_framework/libraries/tag.html#tag-double-submission

###### Actionクラス側の実装方法

OnDoubleSubmissionアノテーションを付与します。
````java
@OnDoubleSubmission
public HttpResponse register(HttpRequest req, ExecutionContext ctx) {
    // 省略。
}
````

二重サブミット発生時は基盤側で特定のページへ遷移させるため、@OnDoubleSubmissionのpath属性は付けなくて良いです。
