var totalIncome = 0,
  used_boats = 0,
  total_duration = 0;
$(document).ready(function () {
  getAllTripsOfToday();
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
    console.log(ended_trips.duration);
  }
  // console.length("duration "+ )
  console.log('Total income is  ' + totalIncome);
  console.log('Number of used boats is  ' + used_boats.length);
  console.log('Number of ongoing trips  ' + ongoing_trips.length);
  console.log('Number of ended trips  ' + ended_trips.length);
  console.log('The average duration is ' + total_duration / ended_trips.length);
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
