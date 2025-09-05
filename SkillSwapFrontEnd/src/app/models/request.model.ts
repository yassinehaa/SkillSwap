
import { User } from "./user.model";
import { Skill } from "./skill.model";

export interface Request {
  id: number;
  requester: User;
  receiver: User;
  skill: Skill;
  status: string;
}
