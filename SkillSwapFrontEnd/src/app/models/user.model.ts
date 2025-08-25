import { Skill } from './skill.model';

export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  password?: string;
  isPremium: boolean;
  proposedSkills?: Skill[];
  searchedSkills?: Skill[];
}
