var stop_trip_id,
  stop_boat_No,
  stop_type,
  stopped_guest,
  start_time,
  guestname,
  periodTime = 0;
$(document).ready(function () {
  // fetch the period data
  periodTime = $.ajax({
    url: 'api/periods',
    async: false,
    dataType: 'json',
  }).responseJSON;

  //  fetch all on going trips
  getAllOngoingTrips();
  // load all the guests
  loadAllGuest();

  // event on the check for suitable boats
  $('#checkbtn').on('click', function (e) {
    if ($('#noOfPersons').val() === '') {
      myAlert('Please Enter a number of persons', 'error');
    } else {
      checkforSuitableBoats();
    }
  });
  //event on the button of error/ success model
  $('#closeError').on('click', function () {
    $('#error').hide();
    $('#stopTripModel').hide();
  });
  $('#closeOK').on('click', function () {
    $('#error').hide();
  });
  // event on add trip
  $('#addTripbtn').on('click', function (e) {
    addTrip();
  });
  // event on the stop trip
  $('#trips-list').on('click', function (e) {
    if (e.target.className === 'btn btn-danger') {
      stop_trip_id = e.target.parentNode.parentElement.parentElement.children[0].innerHTML;
      start_time = e.target.parentNode.parentElement.parentElement.children[1].innerHTML;
      stop_boat_No = e.target.parentNode.parentElement.parentElement.children[2].innerHTML;
      stop_type = e.target.parentNode.parentElement.parentElement.children[3].innerHTML;
      guestname = e.target.parentNode.parentElement.parentElement.children[4].innerHTML;
      $('.modal-title').html('');
      $('.modal-title').html('Stop Trip');
      $('.modal-header').css('background-color', 'red');
      $('#stopTripModel').show();
    }
  });

  // event on the stop trip confirmation modal
  $('#cancelbtn').on('click', function (e) {
    $('#stopTripModel').hide();
  });
  $('#stopbtn').on('click', function (e) {
    // first, stop a boat
    stopTrip();
    $('#stopTripModel').hide();
    // get duration and price of the trip
  });
  $('#GuestcloseError').on('click', function () {
    $('#guestDataModel').hide();
  });
  $('#GuestModelcloseOK').on('click', function () {
    $('#guestDataModel').hide();
  });

  $('#guestdatabtn').click(function () {
    $('.modal-title').html('');
    $('.modal-title').html('Guest Data');
    $('.modal-header').css('background-color', 'rosybrown');
    $('#addguestmodal').show();
  });

  // events on the add new guest model
  $('#GuestModeldateclose').click(function () {
    $('#addguestmodal').hide();
  });
  $('#GuestcloseError').click(function (e) {
    $('#addguestmodal').hide();
  });

  $('#GuestModeldatasave').click(function () {
    saveGuest();
    $('#addguestmodal').hide();
  });

  // events on the modal of Reserved Boats/trips
  $('#reservedcloseError').click(function (e) {
    $('#reservedBoats').hide();
  });
  $('#reservedBoatsClose').click(function (e) {
    $('#reservedBoats').hide();
  });

  //event on the check Reserved Trips button
  $('#checkReservedbtn').click(function (e) {
    $('.modal-title').html('');
    $('.modal-title').html('Reserved Boats');
    $('.modal-header').css('background-color', 'orange');
    $('#reservedBoats').show();
  });

  // event on the check Reserved Trips modal
  $('#checkReservedTrips').click(function (e) {
    if ($('#reservationId').val() === '') {
      myAlert('Please Enter the reservation Id', 'error');
    }
    $('.modal-title').html('');
    $('.modal-title').html('Reserved Trip');
    $('.modal-header').css('background-color', 'orange');
    getReservedTrip();
  });

  $('#reservedBoatsSave').click(function () {
    addReservedTrip();
  });
});

// function to check for suitable boats
function checkforSuitableBoats() {
  var returned_boats = '';
  $.get('api/boats/' + Number($('#noOfPersons').val()) + '/' + $('#boatType').val(), function (suitableBoats) {
    console.log(suitableBoats);
    if (suitableBoats.length > 0) {
      for (var i = 0; i < suitableBoats.length; i++) {
        returned_boats +=
          '<option value ="' +
          suitableBoats[i].no +
          '"> Boat No. ' +
          suitableBoats[i].no +
          ' with ' +
          suitableBoats[i].noOfSeats +
          ' seats </option>';
        $('#Boats').html(returned_boats);
      }
    } else {
      myAlert('No suitable boats available at this time!', 'warning');
    }
  });
}

// add trip function
function addTrip() {
  // get the guest data
  myguest = $.ajax({
    url: 'api/guests/' + Number($('#guestNames').val()),
    async: false,
    dataType: 'json',
  }).responseJSON;

  // get the boat  data
  myboat = $.ajax({
    url: 'api/boats/boat/' + $('#Boats').val(),
    async: false,
    dataType: 'json',
  }).responseJSON;

  // create a trip object
  var trip = {
    status: 'ongoing',
    boats: {
      id: myboat.id,
      no: myboat.no,
      noOfSeats: myboat.noOfSeats,
      minPrice: myboat.minPrice,
      type: myboat.type,
      available: false,
      chargingTime: myboat.chargingTime,
      accPrice: myboat.accPrice,
      status: myboat.status,
    },
    guest: {
      id: myguest.id,
      name: myguest.name,
      idNo: myguest.idNo,
      idType: myguest.idType,
      phone: myguest.phone,
    },
  };
  var jsonObject = JSON.stringify(trip);
  $.ajax({
    url: 'api/trips',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      getAllOngoingTrips();
      myAlert('A trip starts!', 'success');
      clearAllfields();
    },
    error: function () {
      myAlert('Invalid Input', 'error');
    },
  });
}

// add Reserved Trip
function addReservedTrip() {
  // get the guest data
  myguest = $.ajax({
    url: 'api/guests/name/' + $('#reservedGuestName').val(),
    async: false,
    dataType: 'json',
  }).responseJSON;

  // get the boat  data
  myboat = $.ajax({
    url: 'api/boats/boat/' + $('#reservedBoatNo').val(),
    async: false,
    dataType: 'json',
  }).responseJSON;

  // create a trip object
  var trip = {
    status: 'ongoing',
    boats: {
      id: myboat.id,
      no: myboat.no,
      noOfSeats: myboat.noOfSeats,
      minPrice: myboat.minPrice,
      type: myboat.type,
      available: false,
      chargingTime: myboat.chargingTime,
      accPrice: myboat.accPrice,
      status: myboat.status,
    },
    guest: {
      id: myguest.id,
      name: myguest.name,
      idNo: myguest.idNo,
      idType: myguest.idType,
      phone: myguest.phone,
    },
  };
  var jsonObject = JSON.stringify(trip);
  $.ajax({
    url: 'api/trips',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      $('#reservedBoats').hide();
      getAllOngoingTrips();
      myAlert('A trip starts!', 'success');
      clearAllfields();
    },
    error: function () {
      myAlert('Invalid Input', 'error');
    },
  });
}

// get a boat by No
function getDurationandPrice(trip) {
  $('#guestDatabody').text('');
  var str = '';
  $.get('api/boats/boat/' + stop_boat_No, function (updatedboat) {
    console.log(updatedboat);
    str +=
      'Guest Name:  ' +
      stopped_guest.name +
      '</br>' +
      'Guest ID:  ' +
      stopped_guest.idNo +
      '</br>' +
      'ID Type:  ' +
      stopped_guest.idType +
      '</br>' +
      'Guest Phone:  ' +
      stopped_guest.phone +
      '</br>' +
      'Trip Duration:  ' +
      trip.duration +
      ' hours ' +
      '</b>' +
      'Price:  ' +
      updatedboat.income +
      '</br>';
    $('.modal-title').html('');
    $('.modal-title').html('Guest Details ');
    $('.modal-header').css('background-color', 'rosybrown');
    $('#guestDatabody').append(str);
  });
}
// function to stop a trip
function stopTrip() {
  var rate = 0;
  // check the date that the trip start so we calculate the price according to the seasons rate
  // get today date
  const date = new Date();
  let D3 = `${date.getMonth()}\/${date.getDate()}\/${date.getFullYear()}`;

  for (var i = 0; i < periodTime.length; i++) {
    // Format - MM/DD/YYYY
    var D1 = periodTime[i].startDate;
    var D2 = periodTime[i].endDate;

    D1 = new Date(D1);
    D2 = new Date(D2);
    D3 = new Date(D3);
    if (D3.getTime() <= D2.getTime() && D3.getTime() >= D1.getTime()) {
      rate = periodTime[i].rate / 100;
      break;
    } else {
      rate = 0;
    }
  }

  // get the boat  data
  stopped_boat = $.ajax({
    url: 'api/boats/boat/' + stop_boat_No,
    async: false,
    dataType: 'json',
  }).responseJSON;

  //get guest data
  stopped_guest = $.ajax({
    url: 'api/guests/name/' + guestname,
    async: false,
    dataType: 'json',
  }).responseJSON;

  // create trip object
  console.log('start time is ' + start_time);
  var trip = {
    id: stop_trip_id,
    startTime: start_time,
    status: 'Ended',
    guest: {
      id: stopped_guest.id,
      name: stopped_guest.name,
      idType: stopped_guest.idType,
      idNo: stopped_guest.idNo,
      phone: stopped_guest.phone,
    },
    boats: {
      id: stopped_boat.id,
      no: stopped_boat.no,
      noOfSeats: stopped_boat.noOfSeats,
      type: stopped_boat.type,
      maintenance: stopped_boat.maintenance,
      minPrice: stopped_boat.minPrice,
      accPrice: stopped_boat.accPrice + stopped_boat.accPrice * rate, // adding the rate of the season to the acctual price of the trip
      chargingTime: stopped_boat.chargingTime,
      status: stopped_boat.status,
    },
  };
  var jsonObject = JSON.stringify(trip);
  $.ajax({
    url: 'api/trips/updatetrip/' + stop_trip_id,
    type: 'PUT',
    contentType: 'application/json',
    data: jsonObject,
    success: function (trip) {
      getDurationandPrice(trip);
      $('#guestDataModel').show();
      getAllOngoingTrips();
    },
    error: function () {
      myAlert('Invalid Input', 'error');
    },
  });
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

// function to clear the text input
function clearAllfields() {
  $('#noOfPersons').val('');
  $('#idNumer').val('');
  $('#guestName').val('');
  $('#phone').val('');
  $('#reservedGuestName').val('');
  $('#reservedBoatsDuration').val('');
  $('#reservedStartTime').val('');
  $('#reservedBoatType').val('');
  $('#reservedBoatNo').val('');
}
// function to retrieve all ongoing trips
function getAllOngoingTrips() {
  $.get('api/trips/ongoing', function (ongoing) {
    $('#trips-list').empty();
    for (var i = 0; i < ongoing.length; i++) {
      const list = document.getElementById('trips-list');
      const row = document.createElement('tr');
      row.innerHTML = `
           <td>${ongoing[i].id}</td>
           <td>${ongoing[i].startTime}</td>
           <td>${ongoing[i].boats.no}</td>
           <td>${ongoing[i].boats.type}</td>
           <td>${ongoing[i].guest.name}</td>
            <td>${ongoing[i].status}</td>
            <td><a href="#"> <button class="btn btn-danger"> Stop Trip</button></a></td>
              `;
      list.appendChild(row);
    }
  });
}

// function add a guest
function saveGuest() {
  if ($('#guestName').val() === '' || $('#phone').val() === '' || $('#idNumer').val() === '') {
    myAlert('Please Enter the required guest data', 'error');
  } else {
    var guest = {
      name: $('#guestName').val(),
      idNo: $('#idNumer').val(),
      idType: $('#idType').val(),
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
      },
    });
  }
}

// function to get reserved boats

function getReservedTrip() {
  $.get('api/reservations/' + Number($('#reservationId').val()), function (reserved_trip) {
    if (reserved_trip) {
      $('#reservedBoatNo').val(reserved_trip.boat.no);
      $('#reservedBoatType').val(reserved_trip.boat.type);
      $('#reservedStartTime').val(reserved_trip.resDate);
      $('#reservedBoatsDuration').val(reserved_trip.duration);
      $('#reservedGuestName').val(reserved_trip.guest.name);
    } else {
      $('#reservedBoats').hide();
      myAlert('Nothing found!', 'error');
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
