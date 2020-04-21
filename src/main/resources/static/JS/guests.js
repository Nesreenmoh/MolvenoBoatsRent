// define a global variable
var guest_id;
var guestDataTable;
$(document).ready(function () {
  guestDataTable = $('#guestContainer').DataTable({
    ajax: {
      url: 'api/guests',
      dataSrc: '',
    },
    columns: [
      { data: 'id' },
      { data: 'name' },
      { data: 'idType' },
      { data: 'idNo' },
      { data: 'phone' },
      {
        data: null,
        render: function (data, type, row) {
          return '<td><a href="#"><button class="btn btn-danger" guestid="' + data.id + '">Delete</button></a></td>';
        },
      },
      {
        data: null,
        render: function (data, type, row) {
          return '<td><a href="#"> <button class="btn btn-info" guestid="' + data.id + '">Update</button></a></td>';
        },
      },
    ],
  });

  getGuests();

  $('#addGuest').click(function () {
    var guestName = $('#guestName').val();
    var idType = $('#idType').val();
    var idNo = $('#idNumer').val();
    var phone = $('#phone').val();
    if (guestName === '' || phone === '') {
      myAlert('Please fill in the data.', 'error');
    } else {
      addGuest();
    }
  });

  // add event when you click on delete icon
  $('#guestContainer').on('click', '.btn.btn-danger', function () {
    guest_id = $(this).attr('guestid');
    $('.modal-title').html('');
    $('.modal-title').html('Error');
    $('.modal-header').css('background-color', 'red');
    $('#confirm').show();
  });

  // add event when you click on edit icon
  $('#guestContainer').on('click', '.btn.btn-info', function () {
    guest_id = $(this).attr('guestid');
    var name = event.target.parentNode.parentElement.parentNode.children[1].innerHTML;
    var idType = event.target.parentNode.parentElement.parentNode.children[2].innerHTML;
    var idnumber = event.target.parentNode.parentElement.parentNode.children[3].innerHTML;
    var phone = event.target.parentNode.parentElement.parentNode.children[3].innerHTML;
    $('#editName').val(name);
    $('#editidNumer').val(idnumber);
    $('#editidType').val(idType);
    $('#editphone').val(phone);
    $('#guestupdateModal').show();
  });

  $('#closeOK').click(function () {
    $('#error').hide();
  });

  $('#updateClose').click(function () {
    $('#guestupdateModal').hide();
  });

  $('#closeSmallbtn').click(function () {
    $('#guestupdateModal').hide();
  });

  $('#yesBtn').click(function () {
    deleteGuest();
    $('#confirm').hide();
  });

  $('#save').click(function () {
    updateGuest();
    $('#guestupdateModal').hide();
  });

  $('#closeError').click(function () {
    $('#error').hide();
  });

  $('#closeConfirm').click(function () {
    $('#confirm').hide();
  });
  $('.closeBtn').click(function () {
    $('#confirm').hide();
  });
});

// Adding guest function

function addGuest() {
  var guest = {
    name: $('#guestName').val(),
    idType: $('#idType').val(),
    idNo: $('#idNumer').val(),
    phone: $('#phone').val(),
  };

  var jsonObject = JSON.stringify(guest);

  $.ajax({
    url: 'api/guests',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      myAlert('A Guest has been added', 'success');
      // call a function to clear fields
      clearFields();
      getGuests();
    },
    error: function () {
      myAlert('Invalid input!', 'Error');
    },
  });
}

// get all guests from database function
function getGuests() {
  guestDataTable.ajax.reload();
}

// update guest data function
function updateGuest() {
  var guest = {
    id: guest_id,
    name: $('#editName').val(),
    idNo: $('#editidNumer').val(),
    idType: $('#editidType').val(),
    phone: $('#editphone').val(),
  };

  var jsonObject = JSON.stringify(guest);

  $.ajax({
    url: 'api/guests/' + guest_id,
    type: 'PUT',
    data: jsonObject,
    contentType: 'application/json',
    success: function () {
      myAlert('A record has been updated', 'success');
      clearFields();
      getGuests();
    },
    error: function () {
      showAlert('Sorry, something went wrong!', 'error');
    },
  });
}
// clear field function
function clearFields() {
  // form fields
  $('#guestName').val('');
  $('#idNumer').val('');
  $('#phone').val('');

  // modal fields
  $('#editName').val('');
  $('#editidNumer').val('');
  $('#editphone').val('');
}

// delete guest function
function deleteGuest() {
  $.ajax({
    url: 'api/guests/' + guest_id,
    type: 'DELETE',
    success: function () {
      myAlert('A guest is deleted!', 'success');
      getGuests();
    },
    error: function () {
      myAlert('Sorry, Something wrong went on!', 'error');
    },
  });
}

// show alert function
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
