import { Controller } from '@nestjs/common';
import { VoteService } from './vote.service';
import { ResultsService } from './results.service';

@Controller('vote')
export class VoteController {
  constructor(private readonly voteService: VoteService) {}
}

@Controller('results')
export class ResultsController {
  constructor(private readonly resulsService: ResultsService) {}

  // Add methods to handle results display
  // e.g., @Get() to fetch and display results
}