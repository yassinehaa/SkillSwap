export interface Message {
  id?: number; // Optional, assigned by backend
  senderId: number;
  receiverId: number;
  content: string;
  timestamp?: string; // ISO 8601 string (e.g., "2025-07-06T17:57:28.283Z")
}
