const ctx = document.getElementById('myChart');

var pakaCounter = 0;
var srakaCounter = 0;
var altPakaCounter = 0;
var altSrakaCounter = 0;
var timestamp = 0;
var display_event = false;
var chart;
var event_title = "";
const title = document.getElementById('result_box');


async function fetchData() {
    const response = await fetch('./daily');
    const data = await response.json();
    const event = await fetch('./event').then(res => res.json());
    let start = new Date(event.time_start);
    let end = new Date(event.time_end);
 
    if (start < Date.now() && (end > Date.now() || end > Date.now() - event.display_time)) {
      console.log('Event is active');
      display_event = true;
        pakaCounter = event.votes.paka;
        srakaCounter = event.votes.sraka;
        altPakaCounter = data.data.paka;
        altSrakaCounter = data.data.sraka;
        event_title = event.name;
        title.innerHTML = event_title;
        document.getElementsByClassName('toggle')[0].style.display = "block";

    } else{
      console.log('Event is not active');
      pakaCounter = data.data.paka;
      srakaCounter = data.data.sraka;
      timestamp = data.timestamp;   
    }
  
        
}

function toggleChart(){
  console.log("Toggling chart");
  if (display_event){
    chart.data.datasets[0].data[0] = altPakaCounter;
    chart.data.datasets[0].data[1] = altSrakaCounter;
    chart.update();
    title.innerHTML = "Globalne wyniki";
    display_event = false;
  } else {
    chart.data.datasets[0].data[0] = pakaCounter;
    chart.data.datasets[0].data[1] = srakaCounter;
    chart.update();
    title.innerHTML = event_title;
    display_event = true;
  }
}




fetchData().then(() => {
  chart = new Chart(ctx, {
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