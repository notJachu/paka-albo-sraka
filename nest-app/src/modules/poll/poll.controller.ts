import { Controller, Get, Post, Render, Redirect, Req, Res } from '@nestjs/common';
import { VoteService } from './vote.service';
import { ResultsService } from './results.service';

@Controller('vote')
export class VoteController {
  constructor(private readonly voteService: VoteService) {}

  @Get()
  vote() {
    return this.voteService.getVotes();
  }

  @Post()
  @Redirect('/results', 302)
  submitVote(@Req() req: Request, @Res() res: Response) {
    return this.voteService.submitVote(req);
  }
}

@Controller('results')
export class ResultsController {
  constructor(private readonly resulsService: ResultsService) {}

  @Get()
  @Render('results')
  results() {
    // Logic to fetch and render results
    // return { title: 'Poll Results' };
    return this.resulsService.showResults();
  }
  // Add methods to handle results display
  // e.g., @Get() to fetch and display results
}