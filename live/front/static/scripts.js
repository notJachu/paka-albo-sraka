const ctx = document.getElementById('myChart');

var pakaCounter = 0;
var srakaCounter = 0;
var timestamp = 0;

async function fetchData() {
    const response = await fetch('./daily');
    const data = await response.json();
    const event = await fetch('./event').then(res => res.json());
    let start = new Date(event.time_start);
    let end = new Date(event.time_end);
 
    if (start < Date.now() && (end > Date.now() || end > Date.now() - event.display_time)) {
      console.log('Event is active');
         pakaCounter = event.votes.paka;
        srakaCounter = event.votes.sraka;
        const title = document.getElementById('result_box');
        title.innerHTML = event.name;
        return;
    }
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