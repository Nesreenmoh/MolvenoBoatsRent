var totalIncome = 0,
  used_boats = 0,
  total_duration = 0,
  average_duration = 0,
  str = '';
var tripssDataTable;
$(document).ready(function () {
  // defining the data table
  tripssDataTable = $('#trips-list').DataTable({
    ajax: {
      url: 'api/trips',
      dataSrc: '',
    },
    columns: [{ data: 'startTime' }, { data: 'endTime' }, { data: 'status' }, { data: 'duration' }, { data: 'boats.no' }],
  });

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
  tripssDataTable.ajax.reload();
}
