var totalIncome = 0,
  used_boats = 0,
  total_duration = 0,
  average_duration = 0,
  str = '';
$(document).ready(function () {
  // fetching the data from database by ajax
  totalIncome = $.ajax({
    url: 'api/trips/totalincome',
    async: false,
    dataType: 'json',
  }).responseJSON;

  used_boats = $.ajax({
    url: 'api/trips/usedboats',
    async: false,
    dataType: 'json',
  }).responseJSON;

  ongoing_trips = $.ajax({
    url: 'api/trips/ongoing',
    async: false,
    dataType: 'json',
  }).responseJSON;

  ended_trips = $.ajax({
    url: 'api/trips/ended',
    async: false,
    dataType: 'json',
  }).responseJSON;

  for (var i = 0; i < ended_trips.length; i++) {
    total_duration += ended_trips[i].duration;
  }
  // calculate the average duration
  if (ended_trips.length != 0) {
    average_duration = total_duration / ended_trips.length;
  } else {
    average_duration = 0;
  }
  // console.length("duration "+ )
  console.log('Total income is  ' + totalIncome);
  console.log('Number of used boats is  ' + used_boats.length);
  console.log('Number of ongoing trips  ' + ongoing_trips.length);
  console.log('Number of ended trips  ' + ended_trips.length);
  console.log('The average duration is ' + average_duration);
  str +=
    '<b> Number of Trips Ended:  </b>' +
    ended_trips.length +
    '</br>' +
    '<b> Number of ongoing Trips:  </b>' +
    ongoing_trips.length +
    '</br>' +
    '<b> Average duration of the ended trips:  </b>' +
    average_duration +
    '</br>' +
    '<b> Number of used boats:  </b>' +
    used_boats.length +
    '</br>' +
    '<b> Total income: </b>' +
    totalIncome +
    '</br>';
  $('#total').val('');
  $('#total').append(str);
  getAllTripsOfToday();
});

function getAllTripsOfToday() {
  var income = 0;
  var endtime = '';
  $.get('/api/trips', function (trips) {
    for (var i = 0; i < trips.length; i++) {
      income = `${trips[i].boats.income}`;
      if (`${trips[i].status}` === 'ongoing') {
        endtime = 'Not Yet';
      } else {
        endtime = `${trips[i].endTime}`;
      }
      const list = document.getElementById('trips-list');
      const row = document.createElement('tr');
      row.innerHTML = `
           <td>${trips[i].startTime}</td>
           <td>${endtime}</td>
            <td>${trips[i].status}</td>
           <td>${trips[i].duration}</td>
             <td>${trips[i].boats.no}</td>
              `;
      list.appendChild(row);
    }
  });
  console.log(income);
}
