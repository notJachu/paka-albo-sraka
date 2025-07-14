import { Module } from '@nestjs/common';
import { AppController } from './app.controller';
import { AppService } from './app.service';
import { PollModule } from './modules/poll/poll.module';
import { EventsModule } from './modules/events/events.module';

@Module({
  imports: [PollModule, EventsModule],
  controllers: [AppController],
  providers: [AppService],
})
export class AppModule {}
