var stop_trip_id, stop_boat_No, stop_type, stopped_guest, start_time, guestname;
$(document).ready(function () {
  getAllOngoingTrips();
  loadAllGuest();
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
});

// function to check for suitable boats
function checkforSuitableBoats() {
  var max = 4;
  var returned_boats = '';
  $.get('api/boats/' + Number($('#noOfPersons').val()) + '/' + $('#boatType').val(), function (suitableBoats) {
    console.log(suitableBoats);
    if (suitableBoats.length > 0) {
      if (suitableBoats.length < max) {
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
      }
    } else {
      myAlert('No suitable boats available at this time!', 'warning');
    }
  });
}

// add trip function
function addTrip() {
  console.log('guest id is ' + $('#guestNames').val());
  // get the guest data
  myguest = $.ajax({
    url: 'api/guests/' + Number($('#guestNames').val()),
    async: false,
    dataType: 'json',
  }).responseJSON;

  // console.log('my guest ' + myguest.id);
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
// // get a guest function
// function getAguest(name) {
//   $.get('api/guests/name/' + name, function (guest) {
//     stopped_guest = guest;
//   });
// }

// // update boat function

// function updateBoat(boats) {
//   var boat = {
//     id: boats.id,
//     no: boats.no,
//     noOfSeats: boats.noOfSeats,
//     minPrice: boats.minPrice,
//     type: boats.type,
//     available: false,
//     chargingTime: boats.chargingTime,
//     accPrice: boats.accPrice,
//   };

//   console.log('Type is' + boat.type.toString());
//   var jsonObject = JSON.stringify(boat);

//   $.ajax({
//     url: 'api/boats/' + boat.id,
//     type: 'PUT',
//     data: jsonObject,
//     contentType: 'application/json',
//     success: function () {
//       console.log('a boat has been updated');
//     },
//     error: function () {
//       console.log('Sorry, something went wrong!');
//     },
//   });
// }

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
      accPrice: stopped_boat.accPrice,
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
