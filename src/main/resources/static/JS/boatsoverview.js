var total_time = 0,
  total_income = 0,
  str = '';
$(document).ready(function () {
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

  console.log('Total Time  ' + total_time);
  console.log('Total income  ' + total_income);

  str += '<b> Total time:  </b>' + total_time + '</br>' + '<b> Total income:  </b>' + total_income + '</br>';
  $('#total').val('');
  $('#total').append(str);
  getAllBoatsOf();
});

function getAllBoatsOf() {
  $.get('/api/boats', function (boats) {
    for (var i = 0; i < boats.length; i++) {
      const list = document.getElementById('boats-list');
      const row = document.createElement('tr');
      row.innerHTML = `
           <td>${boats[i].no}</td>
            <td>${boats[i].noOfSeats}</td>
             <td>${boats[i].type}</td>
           <td>${boats[i].totalTime}</td>
             <td>${boats[i].income}</td>
              `;
      list.appendChild(row);
    }
  });
}
