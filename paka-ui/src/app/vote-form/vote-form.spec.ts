import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteForm } from './vote-form';

describe('VoteForm', () => {
  let component: VoteForm;
  let fixture: ComponentFixture<VoteForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VoteForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VoteForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
