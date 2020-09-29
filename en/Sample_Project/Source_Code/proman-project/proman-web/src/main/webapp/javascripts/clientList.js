$(function () {
  var $clientSearchButton = $('#client-search');
  var $modal = $('#client-search-dialog');
  var $clientName = $('#client-name');
  var $clientId = $('#client-id');
  var $searchClientName = $('#search-client-name');
  var $searchIndustryCode = $('#search-industry-code');
  var $searchResult = $('#search-result').find('tbody');
  var $messageAreaTemplate = $('#message-area');
  
  var contextPath = (function () {
    var path = $('#context-path').val();
    if (path) {
      return path;
    } else {
      return '';
    }
  })();

  /**
   * Set the selected client information
   */
  function setClientInfo() {
    var $td = $(this).parent();
    $clientId.val($td.children('span.id').first().text());
    $clientName.val($td.children('span.name').first().text());
    $modal.modal('hide');
  }

  /**
   * Event when dialog is displayed
   */
  $modal.on('shown.bs.modal', function () {
    $searchClientName.val('');
    $searchIndustryCode.val('');
    searchClientList();
  });

  /**
   * Event when dialog is closed
   */
  $modal.on('hidden.bs.modal', function () {
    $('div.alert-area').remove();
    $searchClientName.val('');
    $searchIndustryCode.val('');
    $searchResult.empty();
  });
  
  /**
   * Search for clients.
   */
  function searchClientList() {
    $('div.alert-area').remove();
    $searchResult.empty();
    $.ajax({
      url : contextPath + '/api/clients',
      data: {
        clientName  : $searchClientName.val(),
        industryCode: $searchIndustryCode.val()
      }
    }).done(function (data) {
      $.each(data, function (i, item) {
        $searchResult
            .append($('<tr>')
                .append($('<td>').addClass("client-id").append(
                    $('<a>', {
                      href: 'javascript:void(0)',
                      text: item.clientId
                    }).click(setClientInfo))
                    .append($('<span>').text(item.clientId).addClass('id').hide())
                    .append($('<span>').text(item.clientName).addClass('name').hide()))
                .append($('<td>').text(item.clientName))
                .append($('<td>').text(item.industryName))
            )
        ;
      })
    }).fail(function (xhr, textStatus, errorThrown) {
      var $messageArea = $messageAreaTemplate.clone().addClass('alert-area');
      var $messageText = $messageArea.children('div:first');
      $messageAreaTemplate.after($messageArea);

      if (xhr.status == 400) {
        $messageArea.addClass('alert-warning');
        var $ul = $('<ul>');
        $.each(xhr.responseJSON, function (i, message) {
          $ul.append($('<li>', {
            text: message.message
          }));
        });
        $messageText.append($ul);
      } else {
        $messageArea.addClass('alert-danger');
        $messageText.append('Search process failed.')
      }
      $messageArea.show();
    });
  }

  /**
   * Client search process
   */
  $clientSearchButton.click(searchClientList);
  
  /**
   * Acquires industry
   */
  function findIndustry() {
    $searchIndustryCode.empty();
    $searchIndustryCode.append('<option>', {
      value: ''
    });
    $.ajax({
      url   : contextPath + '/api/industries',
      method: 'get'
    }).done(function (data) {
      $.each(data, function (i, industry) {
        $searchIndustryCode.append(
            $('<option>', {
              value: industry.industryCode,
              text : industry.industryName
            })
        );
      });
    });
  }
  findIndustry();
});

