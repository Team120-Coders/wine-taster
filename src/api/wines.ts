import axios from "axios";

export const getWines = async (token: string) => {
  const response = await axios.get('https://wine-taster-app-production.up.railway.app/api/wines', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  return response.data;
};

