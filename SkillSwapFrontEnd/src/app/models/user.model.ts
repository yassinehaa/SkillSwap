import { Skill } from './skill.model';
import { Evaluation } from './evaluation.model';

export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  password?: string;
  isPremium: boolean;
  isAdmin: boolean;
  proposedSkills?: Skill[];
  searchedSkills?: Skill[];
  evaluations?: Evaluation[];
}
