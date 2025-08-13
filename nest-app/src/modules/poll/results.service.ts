import { Injectable } from '@nestjs/common';

/*
here implement vote results handling

*/
@Injectable()
export class ResultsService {
  showResults() {
    // Logic to fetch and return poll results
    return {
      data: {
        paka: 0,
        sraka: 0,
      },
      timestamp: 0,
    };
  }
}
