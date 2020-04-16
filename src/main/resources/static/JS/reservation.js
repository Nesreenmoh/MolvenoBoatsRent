$(document).ready(function (e) {
  // setting events on the model buttons
  $('#closeError').click(function (e) {
    $('#error').hide();
  });

  $('#closeOK').click(function (e) {
    $('#error').hide();
  });

  $('reservationbtn').click(function (e) {
    checkfields();
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
    ($('#phone').val() === '' && valid === false)
  ) {
    myAlert('Please fill in the required data!', 'error');
  }
}
// function to validate the date

const isValid = (mydate) => {
  const today = new Date();
  return mydate.getDate() >= today.getDate() && mydate.getMonth() == today.getMonth() && someDate.getFullYear() == today.getFullYear();
};
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
