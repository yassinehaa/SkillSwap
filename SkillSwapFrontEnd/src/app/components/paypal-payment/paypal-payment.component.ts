
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RequestService } from '../../services/request.service';
import { Request } from '../../models/request.model';
import { MessageService } from '../../services/message.service';
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-paypal-payment',
  templateUrl: './paypal-payment.component.html',
  imports: [
    NgIf
  ],
  styleUrls: ['./paypal-payment.component.css']
})
export class PaypalPaymentComponent implements OnInit {
  request: Request | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private requestService: RequestService,
    private messageService: MessageService
  ) { }

  ngOnInit(): void {
    const requestId = this.route.snapshot.paramMap.get('id');
    if (requestId) {
      this.requestService.getRequest(+requestId).subscribe((request: Request) => {
        this.request = request;
      });
    }
  }

  payWithPayPal(): void {
    // Add PayPal integration logic here
    if (this.request) {
      this.requestService.acceptRequestWithPayPal(this.request).subscribe(() => {
        const message = {
          senderId: this.request?.receiver.id,
          receiverId: this.request?.requester.id,
          content: `Your payment for the skill ${this.request?.skill.name} has been successful.`
        };
        this.messageService.sendHttpMessage(message).subscribe(() => {
          this.router.navigate(['/requests']);
        });
      });
    }
  }
}
