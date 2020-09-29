/*
 * Common JavaScript file for all projects
 */

/**
 * The search URL is saved to the session storage.
 */
function saveListUrl() {
  var url = decodeURI(window.location.href);

  // When the initial search screen is displayed
  if (url !== null && (!!url.match(/project\/initialDisplaySearch/) || !!url.match(/projectBulk\/initialDisplaySearch/))) {
    sessionStorage.setItem("url", url.split("?")[0]);
  }

  //When the search is executed
  if (url !== null && !!url.match(/search/)) {
      url = url.replace(/(%2F|%2f)/g, "/");
      sessionStorage.setItem("url", url.split("&nablarch_hidden=")[0]);
  }
}

/**
 * Set the URL of the search list in the link. <br />
 * The search URL saved in the session storage is set as the href attribute of the specified link.
 * If the search URL cannot be acquired from the session storage, set /action/projectSearch/initialDisplaySearch.
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
