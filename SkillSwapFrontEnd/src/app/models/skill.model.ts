export enum SkillType {
  OFFERED = 'OFFERED',
  SEARCHED = 'SEARCHED'
}

export interface Skill {
  id?: number; // Optional, assigned by backend
  name: string;
  type: SkillType;
  userId: number;
}
