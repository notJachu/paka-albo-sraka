import { Injectable } from '@nestjs/common';


// here implement landing page logic
// TODO:
// - validation of past vote
// - optional redirect to thanks (or change view)
@Injectable()
export class AppService {
  getHello(): string {
    return 'Hello World!';
  }
  getViewName(): string {
    return 'index';
  }
}
