import axios, { AxiosRequestConfig } from 'axios';

const API_URL = 'https://wine-taster-app-production.up.railway.app/api';

const instance = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});


const getAuthConfig = (token: string | null) => ({
  headers: {
    ...(token ? { 'Authorization': `Bearer ${token}` } : {}),
  },
});

console.log(getAuthConfig('secretdbookdlineonvfcpwepovjpowevjcwopejhvc9'));

export const client = {
  async get<T>(url: string, token: string | null = null): Promise<T> {
    const config: AxiosRequestConfig = getAuthConfig(token);
    const response = await instance.get<T>(url, config);
    return response.data;
  },

  async post<T>(url: string, data: any, token: string | null = null): Promise<T> {
    const config: AxiosRequestConfig = getAuthConfig(token);
    const response = await instance.post<T>(url, data, config);
    return response.data;
  },

  async patch<T>(url: string, data: any, token: string | null = null): Promise<T> {
    const config: AxiosRequestConfig = getAuthConfig(token);
    const response = await instance.patch<T>(url, data, config);
    return response.data;
  },

  async delete(url: string, token: string | null = null): Promise<void> {
    const config: AxiosRequestConfig = getAuthConfig(token);
    await instance.delete(url, config);
  },
};
