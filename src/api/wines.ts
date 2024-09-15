import { Wine } from "types/Wine";
import { client } from "utils/fetchClient"

export const getWines = async (token: string): Promise<Wine[]> => {
  return client.get<Wine[]>('/wines', token);
};

