import { Module } from '@nestjs/common';
import { VoteService } from './vote.service';
import { VoteController } from './poll.controller';
import { ResultsController } from './poll.controller';
import { ResultsService } from './results.service';

@Module({
  controllers: [VoteController, ResultsController],
  providers: [VoteService, ResultsService],
})
export class PollModule {}
