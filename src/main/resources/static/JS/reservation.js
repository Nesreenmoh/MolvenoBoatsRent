var myboats, myguest, res_id, guest_name, boat_no, res_date, res_start, res_end;
$(document).ready(function (e) {
  loadAllBoatsByType($('#boatType').val());
  loadAllGuest();
  loadAllReservation();
  // setting events on the model buttons
  $('#closeError').click(function (e) {
    $('#error').hide();
  });

  $('#closeOK').click(function (e) {
    $('#error').hide();
  });

  // event on guest model data
  $('#GuestcloseError').click(function (e) {
    $('#addguestmodal').hide();
  });

  $('#GuestModeldateclose').click(function (e) {
    $('#addguestmodal').hide();
  });

  $('#GuestModeldatasave').click(function (e) {
    saveGuest();
    $('#addguestmodal').hide();
  });

  $('#guestdatabtn').click(function () {
    $('.modal-title').html('');
    $('.modal-title').html('Warning');
    $('.modal-header').css('background-color', 'rosybrown');
    $('#addguestmodal').show();
  });

  // function when boat's type change
  $('#boatType').change(function (e) {
    loadAllBoatsByType($('#boatType').val());
  });
  // event on making a reservation
  $('#reservationbtn').click(function (e) {
    checkfields();
    saveGuest();
    getBoatByNo();
  });

  // events on the buttons of the cancel reservation model
  $('#reservation-list').on('click', function (e) {
    if (e.target.className === 'btn btn-danger') {
      res_id = e.target.parentNode.parentElement.parentElement.children[0].innerHTML;
      res_date = e.target.parentNode.parentElement.parentElement.children[1].innerHTML;
      res_start = e.target.parentNode.parentElement.parentElement.children[2].innerHTML;
      res_end = e.target.parentNode.parentElement.parentElement.children[3].innerHTML;
      guest_name = e.target.parentNode.parentElement.parentElement.children[4].innerHTML;
      boat_no = e.target.parentNode.parentElement.parentElement.children[6].innerHTML;
      $('.modal-title').html('');
      $('.modal-title').html('Warning');
      $('.modal-header').css('background-color', 'rosybrown');
      $('#cancelModel').show();
    }
  });
  $('#cancelcloseError').click(function () {
    $('#cancelModel').hide();
  });

  $('#noCancelbtn').click(function () {
    $('#cancelModel').hide();
  });

  $('#yesCancelbtn').click(function (e) {
    cancelReservation();
    $('#cancelModel').hide();
  });

  $('#checkCancelreservationbtn').click(function () {
    loadCancelledReservation();
  });
  $('#checkreservationbtn').click(function () {
    loadAllReservation();
  });
});

// function add a guest
function saveGuest() {
  if ($('#guestName').val() === '' || $('#phone').val() === '') {
    myAlert('Please Enter the required guest data', 'error');
  } else {
    var guest = {
      name: $('#guestName').val(),
      phone: $('#phone').val(),
    };

    var jsonObject = JSON.stringify(guest);
    $.ajax({
      url: 'api/guests/',
      type: 'POST',
      contentType: 'application/json',
      data: jsonObject,
      success: function (return_guest) {
        loadAllGuest();
        myAlert('Added successfully', 'success');
        myguest = return_guest.id;
        console.log('guest id is ' + myguest);
      },
    });
  }
}

// function to load all guests to a select input
function loadAllGuest() {
  var returned_guests = '';
  $.get('api/guests', function (guests) {
    console.log(guests);
    if (guests.length > 0) {
      for (var i = 0; i < guests.length; i++) {
        returned_guests +=
          '<option value ="' + guests[i].id + '"> Name:  ' + guests[i].name + ' with ' + guests[i].phone + '  phone number</option>';
        $('#guestNames').html(returned_guests);
      }
    }
  });
}
// function to check the validation of the fields
function checkfields() {
  const valid = isValid($('#resDate').val());
  console.log(valid);
  if ($('#resTime').val() === '' || $('#duration').val() === '' || valid === false) {
    myAlert('Please fill in the required data!', 'error');
  }
}

// function to validate the date

const isValid = (mydate) => {
  const today = new Date();
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

// function to get all boats by no
function getBoatByNo() {
  $.get('api/boats/boat/' + $('#boatNo').val(), function (boats) {
    addReservation(boats);
  });
}
// function to add a reservation

function addReservation(boats) {
  var reservation = {
    resDate: $('#resDate').val(),
    duration: $('#duration').val(),
    status: 'Active',
    guest: {
      id: $('#guestNames').val(),
    },
    boat: {
      id: boats.id,
      no: boats.no,
      noOfSeats: boats.noOfSeats,
      type: boats.type,
      maintenance: boats.maintenance,
      minPrice: boats.minPrice,
      accPrice: boats.accPrice,
      chargingTime: boats.chargingTime,
      status: 'Reserved',
    },
  };
  console.log(reservation);
  var jsonObject = JSON.stringify(reservation);
  console.log(jsonObject);
  $.ajax({
    url: 'api/reservations/',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      myAlert('Reservation has created');
      loadAllReservation();
    },
    error: function () {
      myAlert('Invalid Input', 'error');
    },
  });
}

// function load all reservation
function loadAllReservation() {
  $.get('api/reservations', function (reservations) {
    console.log(reservations);
    $('.CancelledList').hide();
    $('.ReservationList').show();
    $('#reservation-list').empty();
    for (var i = 0; i < reservations.length; i++) {
      const list = document.getElementById('reservation-list');
      const row = document.createElement('tr');
      row.innerHTML = `
          <td>${reservations[i].id}</td>
           <td>${reservations[i].resDate}</td>
           <td>${reservations[i].res_start_time}</td>
            <td>${reservations[i].res_end_time}</td>
           <td>${reservations[i].guest.name}</td>
           <td>${reservations[i].boat.type}</td>
            <td>${reservations[i].boat.no}</td>
              <td><a href="#"> <button class="btn btn-danger"> Cancel </button></a></td>
              `;
      list.appendChild(row);
    }
  });
}

// function to cancel a reservation
function cancelReservation() {
  $.get('api/guests/name/' + guest_name, function (guests) {
    $.get('api/boats/boat/' + boat_no, function (boat) {
      $.get('api/reservations/' + res_id, function (reservations) {
        var reservation = {
          id: reservations.id,
          resDate: reservations.resDate,
          res_start_time: reservations.res_start_time,
          res_end_time: reservations.res_end_time,
          duration: reservations.duration,
          status: 'Cancelled',
          guest: {
            id: guests.id,
            name: guests.name,
            phone: guests.phone,
            idType: guests.idType,
            idNo: guests.idNo,
          },
          boat: {
            id: boat.id,
            no: boat.no,
            noOfSeats: boat.noOfSeats,
            type: boat.type,
            maintenance: boat.maintenance,
            minPrice: boat.minPrice,
            accPrice: boat.accPrice,
            chargingTime: boat.chargingTime,
            status: 'Active',
          },
        };
        var jsonObject = JSON.stringify(reservation);
        $.ajax({
          url: 'api/reservations/' + reservations.id,
          type: 'PUT',
          contentType: 'application/json',
          data: jsonObject,
          success: function () {
            myAlert('Reservation has been Cancelled');
            loadAllReservation();
          },
          error: function () {
            myAlert('Invalid Input', 'error');
          },
        });
      });
    });
  });
}

// function to load all cancelled reservation
function loadCancelledReservation() {
  $.get('api/reservations/cancelled', function (reservations) {
    const index = reservations.length;
    console.log('cancelled List is ' + index);
    if (reservations.length > 0) {
      $('.ReservationList').hide();
      $('.CancelledList').show();
      $('#cancelled-list').empty();
      for (var i = 0; i < reservations.length; i++) {
        const list = document.getElementById('cancelled-list');
        const row = document.createElement('tr');
        row.innerHTML = `
          <td>${reservations[i].id}</td>
           <td>${reservations[i].resDate}</td>
           <td>${reservations[i].res_start_time}</td>
            <td>${reservations[i].res_end_time}</td>
           <td>${reservations[i].guest.name}</td>
           <td>${reservations[i].boat.type}</td>
            <td>${reservations[i].boat.no}</td>
             <td>${reservations[i].status}</td>
              `;
        list.appendChild(row);
      }
    } else {
      myAlert('No cancelled Reservations', 'warning');
    }
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
