export interface Evaluation {
  id?: number;
  raterId: number;
  raterFirstName: string;
  raterLastName: string;
  ratedUserId: number;
  rating: number;
  comment: string;
  timestamp: string;
}
