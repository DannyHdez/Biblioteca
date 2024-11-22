import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DisableAccountComponent } from './disable-account.component';

describe('DisableAccountComponent', () => {
  let component: DisableAccountComponent;
  let fixture: ComponentFixture<DisableAccountComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DisableAccountComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DisableAccountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
