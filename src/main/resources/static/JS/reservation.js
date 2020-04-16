$(document).ready(function (e) {
  loadAllBoatsByType($('#boatType').val());
  // setting events on the model buttons
  $('#closeError').click(function (e) {
    $('#error').hide();
  });

  $('#closeOK').click(function (e) {
    $('#error').hide();
  });

  // function when boat's type change
  $('#boatType').change(function (e) {
    loadAllBoatsByType($('#boatType').val());
  });
  // event on making a reservation
  $('#reservationbtn').click(function (e) {
    console.log(typeof $('#resDate').val());
    checkfields();
    addReservation();
  });
});

// function to check the validation of the fields
function checkfields() {
  const valid = isValid($('#resDate').val());
  console.log(valid);
  if (
    $('#resTime').val() === '' ||
    $('#duration').val() === '' ||
    $('#guestName').val() === '' ||
    $('#phone').val() === '' ||
    valid === false
  ) {
    myAlert('Please fill in the required data!', 'error');
  }
}

// function to validate the date

const isValid = (mydate) => {
  const today = new Date();
  console.log(mydate);
  // console.log(today.getDay)
  const parseMyDate = new Date(mydate);

  console.log(parseMyDate.getDate());
  console.log(parseMyDate.getMonth());
  console.log(parseMyDate.getFullYear());

  if (
    parseMyDate.getDate() >= today.getDate() &&
    parseMyDate.getMonth() >= today.getMonth() &&
    parseMyDate.getFullYear() >= today.getFullYear()
  ) {
    return true;
  } else {
    return false;
  }
};

// function to load all boats by type
function loadAllBoatsByType(mytype) {
  var returned_boats = '';
  $.get('api/boats/type/' + mytype, function (boats) {
    console.log(boats);
    if (boats.length > 0) {
      for (var i = 0; i < boats.length; i++) {
        returned_boats +=
          '<option value ="' + boats[i].no + '"> Boat No. ' + boats[i].no + ' with ' + boats[i].noOfSeats + '  seats</option>';
        $('#boatNo').html(returned_boats);
      }
    }
  });
}

// function to add a reservation

function addReservation() {
  var timeString = $('#resDate').val();
  var datetime = new Date(timeString);
  console.log('my date is' + timeString);
  // console.log(typeof datetime);
  var reservation = {
    resDate: timeString,
    duration: $('#duration').val(),
    status: 'Active',
  };

  var jsonObject = JSON.stringify(reservation);
  console.log(jsonObject);
  $.ajax({
    url: 'api/reservations/',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      myAlert('Reservation has created');

      //getAllBoats();
    },
    error: function () {
      myAlert('Invalid Input', 'error');
    },
  });
}
// function to show alert
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
  } else if (className === 'warning') {
    $('.modal-title').html('');
    $('.modal-title').html('Warning');
    $('.modal-header').css('background-color', 'orange');
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
