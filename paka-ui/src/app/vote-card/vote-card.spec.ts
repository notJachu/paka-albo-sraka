import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteCard } from './vote-card';

describe('VoteCard', () => {
  let component: VoteCard;
  let fixture: ComponentFixture<VoteCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VoteCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoteCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
