import { Personne } from './personne.model';
import { Skill } from './skill.model';
import { Evaluation } from './evaluation.model';

export interface User extends Personne {
  proposedSkills?: Skill[];
  searchedSkills?: Skill[];
  evaluations?: Evaluation[];
}
