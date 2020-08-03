$(function() {
  //Elements generated automatically by pagination tags are deleted
  $('.true').remove();
});

$(function() {
  // Sorting conditions
  $("#sortKey,#sortDir").change(function() {
      $(this).parents('form').submit();
  });
});

$(function () {
  var $clientId = $('#client-id');
  var $clientName = $('#client-name');
  $('#client-remove').click(function () {
    $clientId.val('');
    $clientName.val('');
  })
});
