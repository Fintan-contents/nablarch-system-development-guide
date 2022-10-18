/*
 * プロジェクト共通JavaScriptファイル
 */

/**
 * 検索URLをセッションストレージへ保存する。
 */
function saveListUrl() {
  var url = decodeURI(window.location.href);

  // 検索初期画面の表示時
  if (url !== null && (!!url.match(/project\/initialDisplaySearch/) || !!url.match(/projectBulk\/initialDisplaySearch/))) {
    sessionStorage.setItem("url", url.split("?")[0]);
  }

  //検索実行時
  if (url !== null && !!url.match(/search/)) {
      url = url.replace(/(%2F|%2f)/g, "/");
      sessionStorage.setItem("url", url.split("&nablarch_hidden=")[0]);
  }
}

/**
 * リンクに検索一覧へのURLを設定する。<br />
 * セッションストレージに保存されている検索URLを指定されたリンクのhref属性に設定する。
 * セッションストレージから検索URLを取得できない場合は、/action/projectSearch/initialDisplaySearchを設定する。
 */
function setListUrlTo(linkTagId) {
  var url = sessionStorage.getItem("url")
    , $link = null;

  if (url === null) {
    url = "/action/projectSearch/initialDisplaySearch";
  }

  $link = window.document.getElementById(linkTagId);
  $link.href = encodeURI(url);
}
