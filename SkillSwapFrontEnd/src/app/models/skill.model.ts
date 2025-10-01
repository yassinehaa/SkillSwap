export interface Skill {
  id?: number;
  name: string;
  description: string;
  level: string;
  category: string;
  type: SkillType;
  status?: SkillStatus;
}

export enum SkillStatus {
  PENDING = 'PENDING',
  APPROVED = 'APPROVED',
  REJECTED = 'REJECTED'
}

export enum SkillType {
  OFFERED = 'OFFERED',
  SEARCHED = 'SEARCHED'
}