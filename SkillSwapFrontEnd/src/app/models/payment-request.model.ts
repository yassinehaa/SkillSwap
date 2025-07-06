export interface PaymentRequest {
  id?: number;
  requesterId: number;
  providerId: number;
  skillId: number;
  status: string;
}
