import {Component, input, OnInit,} from '@angular/core';
import {EventReadDto} from '../EventReadDto';
import Chart from 'chart.js/auto';

@Component({
  selector: 'app-bar-chart',
  imports: [],
  templateUrl: './bar-chart.html',
  styleUrl: './bar-chart.css',
})
export class BarChart implements OnInit {

  event = input.required<EventReadDto>();

  chart: any = [];

  ngOnInit() {
    this.createChart();
  }
  constructor() {}

  createChart() {
    this.chart = new Chart('barChart', {
        type: 'bar',
        data: {
          labels: [
            'PAKA',
            'SRAKA'
          ],
          datasets: [{
            label: 'Votes',
            data: [this.event().votesPaka, this.event().votesSraka],
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
      }
    );
  }
}
