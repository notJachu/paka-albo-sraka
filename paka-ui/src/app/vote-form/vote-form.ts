import { Component, inject } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RecaptchaModule} from 'ng-recaptcha-2';
import {MatButton} from '@angular/material/button';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-vote-form',
  imports: [
    FormsModule,
    RecaptchaModule,
    MatButton
  ],
  templateUrl: './vote-form.html',
  styleUrl: './vote-form.css',
})
export class VoteForm {

  protected isCaptchaCompleted: boolean = false;
  protected hasVoted: boolean = false;
  private eventUrl: string = '';

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
  onSubmit(values: any) {
    console.log('Form submitted');
  }

  handleCaptcha(event: any) {
    console.log('Captcha value:', event);
    this.isCaptchaCompleted = true;

  }

  fetchEvent() {
    console.log('Fetching event data...');
    this.http.get<any>(this.eventUrl).subscribe(data => {
      console.log('Event data received:', data);
    }, error => {
      console.error('Error fetching event data:', error);
    });
  }
}
