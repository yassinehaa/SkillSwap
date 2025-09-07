export interface Report {
  id?: number;
  reporterId: number;
  reportedUserId: number;
  reason: string;
  timestamp?: string;
}
