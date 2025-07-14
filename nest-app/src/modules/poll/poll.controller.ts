import { Controller, Get, Render } from '@nestjs/common';
import { VoteService } from './vote.service';
import { ResultsService } from './results.service';

@Controller('vote')
export class VoteController {
  constructor(private readonly voteService: VoteService) {}
}

@Controller('results')
export class ResultsController {
  constructor(private readonly resulsService: ResultsService) {}

  @Get()
  @Render('results')
  results() {
    // Logic to fetch and render results
    return { title: 'Poll Results' };
  }
  // Add methods to handle results display
  // e.g., @Get() to fetch and display results
}