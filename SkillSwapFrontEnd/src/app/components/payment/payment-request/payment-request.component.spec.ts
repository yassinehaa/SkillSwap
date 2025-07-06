import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentRequestComponent } from './payment-request.component';

describe('PaymentRequestComponent', () => {
  let component: PaymentRequestComponent;
  let fixture: ComponentFixture<PaymentRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaymentRequestComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PaymentRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
