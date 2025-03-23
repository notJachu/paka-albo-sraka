const ctx = document.getElementById('myChart');

var pakaCounter = 0;
var srakaCounter = 0;
var timestamp = 0;

async function fetchData() {
    const response = await fetch('./daily');
    const data = await response.json();
    pakaCounter = data.data.paka;
    srakaCounter = data.data.sraka;
    timestamp = data.timestamp;
}

fetchData().then(() => {
  new Chart(ctx, {
    type: 'bar',
    data: {
      labels: [
        'PAKA',
        'SRAKA'
      ],
      datasets: [{
        label: 'Votes',
        data: [pakaCounter, srakaCounter],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
});