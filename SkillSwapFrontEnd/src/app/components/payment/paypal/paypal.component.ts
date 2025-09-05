import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

declare var paypal: any;

@Component({
  selector: 'app-paypal',
  standalone: true,
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements OnInit {

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    paypal.Buttons({
      createOrder: (data: any, actions: any) => {
        return this.http.post('/api/payments/create-order', { amount: 9.99 })
          .toPromise()
          .then((orderId: any) => {
            return orderId;
          })
          .catch(err => {
            console.error(err);
          });
      },
      onApprove: (data: any, actions: any) => {
        return this.http.post('/api/payments/capture-order', { orderId: data.orderID })
          .toPromise()
          .then(() => {
            alert('Payment successful!');
          })
          .catch(err => {
            console.error(err);
          });
      }
    }).render('#paypal-button-container');
  }
}
