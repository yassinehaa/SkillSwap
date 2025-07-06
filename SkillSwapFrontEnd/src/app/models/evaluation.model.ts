export interface Evaluation {
  id?: number;
  raterId: number;
  ratedUserId: number;
  rating: number;
  comment?: string;
}
