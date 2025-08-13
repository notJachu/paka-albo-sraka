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
    private daily_state = { data: { paka: 0, sraka: 0 } };
    getVotes() {
        // Logic to fetch and return votes
        return {
        data: this.daily_state.data,
        timestamp: Date.now(),
        };
    }

    submitVote(req: any) {
        const formData = req.body;
        //console.log('Form data received:', formData);
        if (formData.vote === 'PAKA') {
            this.daily_state.data.paka++;
        } else if (formData.vote === 'SRAKA') {
            this.daily_state.data.sraka++;
        }
    }
}
