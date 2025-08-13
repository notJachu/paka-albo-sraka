import { Injectable } from '@nestjs/common';

/*
here implement vote handling
TODO:
- post logic
- vote validation
- save vote
- redirect to results
- vote dto
- 
*/
@Injectable()
export class VoteService {
    getVotes() {
        // Logic to fetch and return votes
        return {
        data: {
            paka: 4,
            sraka: 0,
        },
        timestamp: Date.now(),
        };
    }

    submitVote() {
        
    }
}
