var stop_trip_id, stop_boat_No, stop_type, stopped_guest, start_time;
$(document).ready(function () {
  // getAllOngoingTrips();
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
    addGuest();
  });
  // event on the stop trip
  $('#trips-list').on('click', function (e) {
    if (e.target.className === 'btn btn-danger') {
      stop_trip_id = e.target.parentNode.parentElement.parentElement.children[0].innerHTML;
      start_time = e.target.parentNode.parentElement.parentElement.children[1].innerHTML;
      stop_boat_No = e.target.parentNode.parentElement.parentElement.children[2].innerHTML;
      stop_type = e.target.parentNode.parentElement.parentElement.children[3].innerHTML;
      var guestname = e.target.parentNode.parentElement.parentElement.children[4].innerHTML;
      getAguest(guestname);
      $('#stopTripModel').show();
    }
  });

  // event on the stop trip confirmation modal
  $('#cancelbtn').on('click', function (e) {
    $('#stopTripModel').hide();
  });
  $('#stopbtn').on('click', function (e) {
    // first, check if the boat is electrical
    stopTrip();
    $('#stopTripModel').hide();
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
          returned_boats += '<option value ="' + suitableBoats[i].no + '">' + suitableBoats[i].no + '</option>';
          $('#Boats').html(returned_boats);
        }
      }
    } else {
      myAlert('No suitable boats available at this time!', 'warning');
    }
  });
}

// function to add guest
function addGuest() {
  if ($('#guestName').val() === '' || $('#idNumer').val() === '') {
    myAlert('Please Enter the required guest data', 'error');
  } else {
    var guest = {
      name: $('#guestName').val(),
      idType: $('#idType').val(),
      idNo: $('#idNumer').val(),
      phone: $('#phone').val(),
    };

    var jsonObject = JSON.stringify(guest);
    $.ajax({
      url: 'api/guests/',
      type: 'POST',
      contentType: 'application/json',
      data: jsonObject,
      success: function (return_guest) {
        // clearfields();
        console.log('guest id is ' + return_guest.id);
        addTrip(return_guest.id);
      },
    });
  }
}
// add trip function
function addTrip(guest_id) {
  var boatid;
  $.get('api/boats/boat/' + $('#Boats').val(), function (myboat) {
    boatid = myboat.id;
    console.log(boatid);
    var trip = {
      status: 'ongoing',
      guest: {
        id: guest_id,
      },
      boats: {
        id: boatid,
      },
    };
    console.log(trip);
    var jsonObject = JSON.stringify(trip);
    $.ajax({
      url: 'api/trips',
      type: 'POST',
      contentType: 'application/json',
      data: jsonObject,
      success: function () {
        // linkBoatsWithTrip(mytrip.id, myboats);
        getAllOngoingTrips();
        myAlert('A trip starts!', 'success');
      },
      error: function () {
        myAlert('Invalid Input', 'error');
      },
    });
  });
  //   console.log('Boat ID is' + boatid);
}
// get a guest function
function getAguest(name) {
  $.get('api/guests/name/' + name, function (guest) {
    stopped_guest = guest;
  });
}

// function to stop a trip
function stopTrip() {
  $.get('api/boats/boat/' + stop_boat_No, function (stopped_boat) {
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
        maintenance: false,
        minPrice: 100.0,
        accPrice: 200.0,
        chargingTime: 0,
        status: 'Active',
      },
    };
    var jsonObject = JSON.stringify(trip);
    $.ajax({
      url: 'api/trips/updatetrip/' + stop_trip_id,
      type: 'PUT',
      contentType: 'application/json',
      data: jsonObject,
      success: function () {
        myAlert('A trip is ended!', 'success');
        getAllOngoingTrips();
      },
      error: function () {
        myAlert('Invalid Input', 'error');
      },
    });
  });
}
// // link between boats and trip
// function linkBoatsWithTrip(mytrip_id, myboats) {
//   var jsonObject = JSON.stringify(myboats);
//   console.log(jsonObject);

//   $.ajax({
//     url: 'api/boats/linkBoat/' + mytrip_id,
//     type: 'PUT',
//     data: jsonObject,
//     contentType: 'application/json',
//     success: function () {
//       console.log('a link successfully built!');
//     },
//     error: function () {
//       myAlert('Sorry, something went wrong!', 'error');
//     },
//   });
// }

// function to retrieve all ongoing trips
function getAllOngoingTrips() {
  $.get('api/trips/ongoing', function (ongoing) {
    console.log(ongoing);
    //    count number of ongoingTrips
    var ongoingTripsCounter = ongoing.length;
    console.log(ongoingTripsCounter);
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
