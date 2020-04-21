var periodDataTable, period_id;
$(document).ready(function () {
  // defining data tabble
  periodDataTable = $('#periodsContainer').DataTable({
    ajax: {
      url: 'api/periods',
      dataSrc: '',
    },
    columns: [
      { data: 'id' },
      { data: 'name' },
      { data: 'startDate' },
      { data: 'endDate' },
      { data: 'rate' },
      {
        data: null,
        render: function (data, type, row) {
          return '<td><a href="#"><button class="btn btn-danger" periodid="' + data.id + '">Delete</button></a></td>';
        },
      },
      {
        data: null,
        render: function (data, type, row) {
          return '<td><a href="#"> <button class="btn btn-info" periodid="' + data.id + '">Update</button></a></td>';
        },
      },
    ],
  });
  getAllPeriods();

  // add event when you click on delete icon
  $('#periodsContainer').on('click', '.btn.btn-danger', function () {
    period_id = $(this).attr('periodid');
    $('.modal-title').html('');
    $('.modal-title').html('Error');
    $('.modal-header').css('background-color', 'red');
    $('#deleteconfirm').show();
  });

  // add event when you click on edit icon
  $('#periodsContainer').on('click', '.btn.btn-info', function () {
    period_id = $(this).attr('periodid');
    var name = event.target.parentNode.parentElement.parentNode.children[1].innerHTML;
    var startDate = event.target.parentNode.parentElement.parentNode.children[2].innerHTML;
    var endDate = event.target.parentNode.parentElement.parentNode.children[3].innerHTML;
    var rate = event.target.parentNode.parentElement.parentNode.children[4].innerHTML;

    $('#editname').val(name);
    $('#editStartDate').val(startDate);
    $('#editEndDate').val(endDate);
    $('#editrate').val(rate);
    $('#periodupdateModal').show();
  });

  // event on period update modal
  $('#editcloseSmallbtn').click(function (e) {
    $('#periodupdateModal').hide();
  });

  $('#updateperiodClose').click(function (e) {
    $('#periodupdateModal').hide();
  });

  $('#editperiodsave').click(function (e) {
    updatePeriod();
    $('#periodupdateModal').hide();
  });
  // event on delete confirm modal
  $('#deletecloseBtn').click(function (e) {
    $('#deleteconfirm').hide();
  });

  $('#closeConfirm').click(function (e) {
    $('#deleteconfirm').hide();
  });
  $('#yesBtn').click(function (e) {
    deletePeriod();
    $('#deleteconfirm').hide();
  });
  //event on add period
  $('#addperiodbtn').click(function () {
    if ($('#periodName').val() === '' || $('#startDate').val() === '' || $('#endDate').val() === '' || $('#rate').val() === '') {
      myAlert('Please fill in all the fields', 'error');
    } else {
      addPeriod();
    }
  });
});

// update period

// update guest data function
function updatePeriod() {
  console.log(period_id);
  var period = {
    id: period_id,
    name: $('#editname').val(),
    startDate: $('#editStartDate').val(),
    endDate: $('#editEndDate').val(),
    rate: $('#editrate').val(),
  };

  var jsonObject = JSON.stringify(period);

  $.ajax({
    url: 'api/periods/' + period_id,
    type: 'PUT',
    data: jsonObject,
    contentType: 'application/json',
    success: function () {
      myAlert('A record has been updated', 'success');
      clearFields();
      getAllPeriods();
    },
    error: function () {
      myAlert('Sorry, something went wrong!', 'error');
    },
  });
}
// get all periods function
function getAllPeriods() {
  periodDataTable.ajax.reload();
}

// function to add period
function addPeriod() {
  var period = {
    name: $('#periodName').val(),
    startDate: $('#startDate').val(),
    endDate: $('#editEndDate').val(),
    rate: $('#rate').val(),
  };

  var jsonObject = JSON.stringify(period);

  $.ajax({
    url: 'api/periods',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      myAlert('A period has been added', 'success');
      // call a function to clear fields
      clearFields();
      getAllPeriods();
    },
    error: function () {
      myAlert('Invalid input!', 'Error');
    },
  });
}

// delete period function
function deletePeriod() {
  $.ajax({
    url: 'api/periods/' + period_id,
    type: 'DELETE',
    success: function () {
      myAlert('A period has been deleted!', 'success');
      getAllPeriods();
    },
    error: function () {
      myAlert('Sorry, Something wrong went on!', 'error');
    },
  });
}

// clear field function
function clearFields() {
  // form fields
  $('#periodName').val('');
  $('#rate').val('');
  $('#editname').val('');
  $('#editrate').val('');
}

//my show Alert function
function myAlert(msg, className) {
  if (className === 'error') {
    $('.modal-title').html('');
    $('.modal-title').html('Error');
    $('.modal-header').css('background-color', 'red');
    const div = document.createElement('div');
    div.className = `alert ${className}`;
    div.appendChild(document.createTextNode(msg));
    $('#message').text('');
    $('#message').append(div);
    $('#error').show();
  } else {
    $('.modal-title').html('');
    $('.modal-title').html('Success');
    $('.modal-header').css('background-color', 'green');
    const div = document.createElement('div');
    div.className = `alert ${className}`;
    div.appendChild(document.createTextNode(msg));
    $('#message').text('');
    $('#message').append(div);
    $('#error').show();
  }
}
