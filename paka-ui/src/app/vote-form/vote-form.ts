import {Component, inject} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RecaptchaModule, RecaptchaFormsModule} from 'ng-recaptcha-2';
import {MatButton} from '@angular/material/button';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {EventReadDto} from '../EventReadDto';
import {toSignal} from '@angular/core/rxjs-interop';
import {BarChart} from '../bar-chart/bar-chart';

@Component({
  selector: 'app-vote-form',
  imports: [
    FormsModule,
    RecaptchaModule,
    RecaptchaFormsModule,
    MatButton,
    BarChart,
  ],
  templateUrl: './vote-form.html',
  styleUrl: './vote-form.css',
})
export class VoteForm {

  protected isCaptchaCompleted: boolean = false;
  protected hasVoted: boolean = false;
  protected captchaToken: string = '';

  private http = inject(HttpClient);
  private route = inject(ActivatedRoute);

  private eventId = this.route.snapshot.paramMap.get('eventId') || 'default';

  eventData = toSignal(
    this.http.get<EventReadDto>(`http://localhost:8080/api/events/${this.eventId}`)
  );

  constructor() {
  }

  onSubmit(value: any) {
    console.log('Form submitted');
    const event = this.eventData();
    if (!event) {
      console.error('Event data is not available');
      return;
    }
    const finalData = {
      captcha: this.captchaToken,
      vote: value
    };
    this.http.post('http://localhost:8080/api/events/'+ event.eventId + '/vote?vote=' + value, finalData).subscribe(response => {
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

}
