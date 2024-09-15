import axios from 'axios';

export const instance = axios.create({
  baseURL: 'https://wine-taster-app-production.up.railway.app/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export const client = {
  async get<T>(url: string, token: string | null = null) {
    const config = {
      headers: {} as { [key: string]: string },
    };

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await instance.get<T>(url, config);
    return response.data;
  },

  async post<T>(url: string, data: any, token: string | null = null) {
    const config = {
      headers: {} as { [key: string]: string },
    };

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await instance.post<T>(url, data, config);
    return response.data;
  },

  async patch<T>(url: string, data: any, token: string | null = null) {
    const config = {
      headers: {} as { [key: string]: string },
    };

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    const response = await instance.patch<T>(url, data, config);
    return response.data;
  },

  async delete(url: string, token: string | null = null) {
    const config = {
      headers: {} as { [key: string]: string },
    };

    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }

    return instance.delete(url, config);
  },
};
