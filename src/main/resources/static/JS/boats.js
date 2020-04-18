var updated_boatId;
$(document).ready(function (e) {
  getAllBoats();

  // event on the body of the table for update button
  $('#rowingBoat-list').on('click', function (e) {
    if (e.target.className === 'btn btn-info') {
    }
      $('#updateModal').show();
      updated_boatId = e.target.parentNode.parentElement.parentElement.children[0].innerHTML;
      var boatNo = e.target.parentNode.parentElement.parentElement.children[1].innerHTML;
      var seats = e.target.parentNode.parentElement.parentElement.children[3].innerHTML;
      var type = e.target.parentNode.parentElement.parentElement.children[2].innerHTML;
      var min_price = e.target.parentNode.parentElement.parentElement.children[4].innerHTML;
      var acc_price = e.target.parentNode.parentElement.parentElement.children[5].innerHTML;
      var chargingTime = e.target.parentNode.parentElement.parentElement.children[6].innerHTML;

      $('#editboatNo').val(boatNo);
      $('#editseats').val(seats);
      $('#editboatType').val(type);
      $('#editchargingTime').val(chargingTime);
      $('#editminPrice').val(min_price);
      $('#editaccPrice').val(acc_price);
    }
  });
  // events for success and error modal
  $('#closeError').click(function (e) {
    $('#error').hide();
  });
  $('#closeOK').click(function (e) {
    $('#error').hide();
  });
  // event for the button of update model
  $('#updateClose').click(function (e) {
    $('#updateModal').hide();
  });
  $('#closeSmallbtn').click(function (e) {
    $('#updateModal').hide();
  });
  $('#save').click(function (e) {
    updateBoat(updated_boatId);
    $('#updateModal').hide();
  });
  $('#addBoat').click(function (e) {
    postBoat();
  });

  // event on the block for maintenance model
  $('#blockcloseError').click(function (e) {
    $('#blockmodel').hide();
    $('#unblockmodel').hide();
  });
  $('#blockOk').click(function (e) {
    if ($('#blockboatNo').val() === '') {
      myAlert('Please enter Boat number to block it!', 'error');
    } else {
      blockBoat($('#blockboatNo').val());
      $('#blockmodel').hide();
    }
  });
  // event on unblock modal buttons
  $('#unblockOk').click(function (e) {
    if ($('#unblockboatNo').val() === '') {
      myAlert('Please enter Boat number to unblock it!', 'error');
    } else {
      unblockBoat($('#unblockboatNo').val());
      $('#unblockmodel').hide();
    }
  });
  $('#unBlockbtn').click(function (e) {
    $('.modal-title').html('Info');
    $('.modal-header').css('background-color', 'orange');
    $('#unblockmodel').show();
  });
  // event on the button for block a boat
  $('#blockBoatbtn').click(function (e) {
    $('.modal-title').html('Info');
    $('.modal-header').css('background-color', 'orange');
    $('#blockmodel').show();
  });

  // event to enable the text of charging time input
  $('#boatType').on('change', function (e) {
    if ($('#boatType').val() === 'Electrical') {
      $('#chargingTime').removeAttr('disabled', false);
    } else {
      $('#chargingTime').attr('disabled', 'disabled');
    }
    if ($('#boatType').val() === 'Raft') {
      $('#chargingTime').attr('disabled', 'disabled');
      $('#minPrice').attr('disabled', 'disabled');
    } else {
      $('#minPrice').attr('disabled', false);
    }
  });

  // event to enable the text of edit charging time input - modal
  $('#editboatType').on('change', function (e) {
    if ($('#editboatType').val() === 'Electrical') {
      $('#editchargingTime').removeAttr('disabled', false);
    } else {
      $('#editchargingTime').attr('disabled', 'disabled');
    }
    if ($('#editboatType').val() === 'Raft') {
      $('#editchargingTime').attr('disabled', 'disabled');
      $('#editminPrice').attr('disabled', 'disabled');
    } else {
      $('#editminPrice').attr('disabled', false);
    }
  });

  // search box event
  $('#searchNo').on('keyup', function (e) {
    searchBoatNo();
  });
});

// get all boats
function getAllBoats() {
  $.get('api/boats', function (rowingboat) {
    $('#rowingBoat-list').empty();
    for (var i = 0; i < rowingboat.length; i++) {
      const list = document.getElementById('rowingBoat-list');
      const row = document.createElement('tr');
      row.innerHTML = `
          <td>${rowingboat[i].id}</td>
           <td>${rowingboat[i].no}</td>
           <td>${rowingboat[i].type}</td>
            <td>${rowingboat[i].noOfSeats}</td>
             <td>${rowingboat[i].minPrice}</td>
              <td>${rowingboat[i].accPrice}</td>
              <td>${rowingboat[i].chargingTime}</td>
              <td>${rowingboat[i].status}</td>
              <td><a href="#"> <button class="btn btn-info" boatid="' + ${rowingboat[i].id} + '">Update Boat</button></a></td>
              `;
      list.appendChild(row);
    }
  });
}

// add a boat function

function postBoat() {
  var min_price = Number($('#minPrice').val());
  var actual_price = Number($('#accPrice').val());
  if (actual_price === 0) {
    actual_price = min_price;
  }
  console.log('Actual price is= ' + actual_price);
  var rowingboat = {
    no: $('#boatNo').val(),
    noOfSeats: $('#seats').val(),
    type: $('#boatType').val(),
    minPrice: Number($('#minPrice').val()),
    chargingTime: Number($('#chargingTime').val()),
    accPrice: actual_price,
  };

  var jsonObject = JSON.stringify(rowingboat);
  $.ajax({
    url: 'api/boats/',
    type: 'POST',
    contentType: 'application/json',
    data: jsonObject,
    success: function () {
      myAlert('Rowing Boat is added!', 'success');
      clearfields();
      getAllBoats();
    },
    error: function () {
      myAlert('Invalid Input', 'error');
    },
  });
}

// function to update a boat
function updateBoat(updated_boatId) {
  var min_price = Number($('#editminPrice').val());
  var actual_price = Number($('#editaccPrice').val());
  if (actual_price === 0) {
    actual_price = min_price;
  }
  var boat = {
    id: updated_boatId,
    no: $('#editboatNo').val(),
    noOfSeats: $('#editseats').val(),
    minPrice: Number($('#editminPrice').val()),
    type: $('#editboatType').val(),
    chargingTime: Number($('#editchargingTime').val()),
    accPrice: actual_price,
  };

  var jsonObject = JSON.stringify(boat);

  $.ajax({
    url: 'api/boats/' + updated_boatId,
    type: 'PUT',
    data: jsonObject,
    contentType: 'application/json',
    success: function () {
      myAlert('A boat has been updated!', 'success');
      getAllBoats();
    },
    error: function () {
      myAlert('Sorry, something went wrong!', 'error');
    },
  });
}

// function to block a boat
function blockBoat(boatno) {
  $.get('api/boats/boat/' + boatno, function (myboat) {
    if (myboat) {
      var boat = {
        id: myboat.id,
        no: myboat.no,
        noOfSeats: myboat.noOfSeats,
        minPrice: myboat.minPrice,
        chargingTime: myboat.chargingTime,
        accPrice: myboat.accPrice,
        status: 'Blocked',
        available: false,
        maintenance: true,
      };

      var jsonObject = JSON.stringify(boat);

      $.ajax({
        url: 'api/boats/' + myboat.id,
        type: 'PUT',
        data: jsonObject,
        contentType: 'application/json',
        success: function () {
          myAlert('A boat has been blocked!', 'success');
          getAllBoats();
        },
        error: function () {
          myAlert('Sorry, something went wrong!', 'error');
        },
      });
    } else {
      myAlert('No such Boat', 'error');
    }
  });
}

// function to unblock a boat
function unblockBoat(blockedId) {
  $.get('api/boats/boat/' + blockedId, function (myboat) {
    if (myboat) {
      var boat = {
        id: myboat.id,
        no: myboat.no,
        noOfSeats: myboat.noOfSeats,
        minPrice: myboat.minPrice,
        chargingTime: myboat.chargingTime,
        accPrice: myboat.accPrice,
        status: 'Active',
        available: true,
        maintenance: false,
      };

      var jsonObject = JSON.stringify(boat);

      $.ajax({
        url: 'api/boats/' + myboat.id,
        type: 'PUT',
        data: jsonObject,
        contentType: 'application/json',
        success: function () {
          myAlert('A boat has been unblocked successfully!', 'success');
          getAllBoats();
        },
        error: function () {
          myAlert('Sorry, something went wrong!', 'error');
        },
      });
    } else {
      myAlert('No such Boat', 'error');
    }
  });
}
// function to clear the controls
function clearfields() {
  $('#boatNo').val('');
  $('#seats').val('');
  $('#minPrice').val('');
  $('#accPrice').val('');
  $('#chargingTime').val('');
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

// search box event
function searchBoatNo() {
  var input, filter, table, tr, td, i, txtValue;
  input = document.getElementById('searchNo');
  filter = input.value.toUpperCase();
  table = document.getElementById('rowingBoat-list');
  tr = table.getElementsByTagName('tr');

  // Loop through all table rows, and hide those who don't match the search query

  for (i = 0; i < tr.length; i++) {
    tds = tr[i].getElementsByTagName('td');
    var flag = false;
    for (var j = 0; j < tds.length; j++) {
      var td = tds[j];
      if (td.innerText.toUpperCase().indexOf(filter) > -1) {
        flag = true;
      }
    }
    if (flag) {
      tr[i].style.display = '';
    } else {
      tr[i].style.display = 'none';
    }
  }
}
