import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {RecaptchaModule} from 'ng-recaptcha-2';

@Component({
  selector: 'app-vote-form',
  imports: [
    FormsModule,
    RecaptchaModule
  ],
  templateUrl: './vote-form.html',
  styleUrl: './vote-form.css',
})
export class VoteForm {

  onSubmit(values: any) {
    console.log('Form submitted');
  }

  handleCaptcha(event: any) {
    console.log('Captcha value:', event);
  }
}
