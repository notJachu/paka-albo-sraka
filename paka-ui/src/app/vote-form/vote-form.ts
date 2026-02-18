import { Component, inject } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RecaptchaModule, RecaptchaFormsModule} from 'ng-recaptcha-2';
import {MatButton} from '@angular/material/button';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {EventReadDto} from '../EventReadDto';

@Component({
  selector: 'app-vote-form',
  imports: [
    FormsModule,
    RecaptchaModule,
    RecaptchaFormsModule,
    MatButton
  ],
  templateUrl: './vote-form.html',
  styleUrl: './vote-form.css',
})
export class VoteForm {

  protected isCaptchaCompleted: boolean = false;
  protected hasVoted: boolean = false;
  private eventUrl: string = '';
  protected captchaToken: string = '';

  protected eventData: EventReadDto | null = null;

  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

  constructor() {
    const evetId = this.route.snapshot.paramMap.get('eventId');
    if (evetId) {
      this.eventUrl = `http://localhost:8080/api/events/${evetId}`;
      this.fetchEvent();
    } else {
      // Default event on the landing page
      this.eventUrl = 'http://localhost:8080/api/events/default';
      this.fetchEvent();
    }
  }

  // TODO: change url and vote value to be actual values and not hardcoded
  onSubmit(values: any) {
    console.log('Form submitted');
    this.http.post('http://localhost:8080/api/events/81386bff-8038-4687-8f58-60a6218b9605/vote?vote=paka', values).subscribe(response => {
      console.log('Vote submitted successfully:', response);
      this.hasVoted = true;
    }, error => {
      console.error('Error submitting vote:', error);
    });
  }

  handleCaptcha(event: any) {
    console.log('Captcha value:', event);
    this.isCaptchaCompleted = true;
    this.captchaToken = event;
  }

  fetchEvent() {
    console.log('Fetching event data...');
    this.http.get<EventReadDto>(this.eventUrl).subscribe(data => {
      this.eventData = data;
      console.log('Event data fetched:', this.eventData);
    }, error => {
      console.error('Error fetching event data:', error);
    });
  }

}
