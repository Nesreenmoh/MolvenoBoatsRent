var total_time = 0,
  total_income = 0,
  str = '';
var boatsDataTable;
$(document).ready(function () {
  // datatable of the boats
  boatsDataTable = $('#boats-list').DataTable({
    ajax: {
      url: 'api/boats',
      dataSrc: '',
    },
    columns: [{ data: 'no' }, { data: 'noOfSeats' }, { data: 'type' }, { data: 'totalTime' }, { data: 'income' }],
  });

  // fetching the data from database by ajax
  total_income = $.ajax({
    url: 'api/trips/totalincome',
    async: false,
    dataType: 'json',
  }).responseJSON;

  total_time = $.ajax({
    url: 'api/boats/totalTime',
    async: false,
    dataType: 'json',
  }).responseJSON;

  // add the details of the boats to a paragraph

  str += '<b> Total time:  </b>' + total_time + '</br>' + '<b> Total income:  </b>' + total_income + '</br>';
  $('#total').val('');
  $('#total').append(str);
  getAllBoatsOf();
});

// fetch all boats
function getAllBoatsOf() {
  boatsDataTable.ajax.reload();
}
