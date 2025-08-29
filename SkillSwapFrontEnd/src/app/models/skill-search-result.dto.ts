import { UserDTO } from "./user.dto";

export interface SkillDTO {
  id: number;
  name: string;
  type: string;
  userId: number;
}

export interface SkillSearchResultDTO {
  skill: SkillDTO;
  user: UserDTO;
}