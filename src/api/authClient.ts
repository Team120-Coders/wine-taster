import { instance } from "utils/axiosClient";

export const authClient = {
  // Регистрация нового пользователя
  async register(email: string, password: string) {
    const response = await instance.post('/register', { email, password });
    return response.data;
  },

  // Авторизация пользователя
  async login(email: string, password: string) {
    const response = await instance.post('/login', { email, password });
    return response.data;
  },
};