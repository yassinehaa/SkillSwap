export interface Skill {
  id?: number;
  name: string;
  type: SkillType;
}

export enum SkillType {
  OFFERED = 'OFFERED',
  SEARCHED = 'SEARCHED'
}