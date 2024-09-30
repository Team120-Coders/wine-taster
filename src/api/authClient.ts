const API_URL = 'https://wine-taster-app-production.up.railway.app/api';

// Загальна функція для обробки відповідей
const handleResponse = async (response: Response) => {
  const data = await response.json();
  if (!response.ok) {
    throw new Error(data.message || 'Помилка при обробці запиту');
  }
  return data;
};


export const registerUser = async (userData: {
  email: string;
  login: string;
  password: string;
  repeatPassword: string;
  firstName: string;
  lastName: string;
}) => {
  const response = await fetch(`${API_URL}/auth/registration`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(userData),
  });
  return handleResponse(response);  
};


export const loginUser = async (email: string, password: string) => {
  try {
    const response = await fetch(`${API_URL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ email, password }),
    });

    if (!response.ok) {
      const errorMessage = await response.text();
      throw new Error(`Ошибка ${response.status}: ${errorMessage}`);
    }

    const data = await response.json();
    return data.token; // Предполагается, что сервер возвращает токен
  } catch (error) {
    console.error('Ошибка при входе:', error);
    throw error;
  }
};

/*export const loginUser = async (loginData: any) => {
  const response = await fetch(`${API_URL}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(loginData),
  });
  const data = await handleResponse(response);

  if (data.token) {
    localStorage.setItem('jwtToken', data.token);
  } else {
    throw new Error('Токен не получен');
  }

  return data;
};*/


export const createCategory = async (categoryData: any, username: string | null, password: string | null) => {
  const response = await fetch(`${API_URL}/categories`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa(`${username}:${password}`),
    },
    body: JSON.stringify(categoryData),
  });
  return handleResponse(response);
};

export const getCategories = async () => {
  const response = await fetch(`${API_URL}/categories`, {
    method: 'GET',
  });
  return handleResponse(response);
};

export const getCategoryById = async (id: number) => {
  const response = await fetch(`${API_URL}/categories/${id}`, {
    method: 'GET',
  });
  return handleResponse(response);
};

export const updateCategory = async (id: number, categoryData: any, username: string | null, password: string | null) => {
  const response = await fetch(`${API_URL}/categories/${categoryData.id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa(`${username}:${password}`),
    },
    body: JSON.stringify(categoryData),
  });
  return handleResponse(response);
};

export const deleteCategory = async (id: number, token: string) => {
  const response = await fetch(`${API_URL}/categories/${id}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) throw new Error('Помилка видалення');
};

// Вина

export const createWine = async (wineData: any, token: string) => {
  const response = await fetch(`${API_URL}/wines`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(wineData),
  });
  return handleResponse(response);
};

export const getWines = async (username: string | null, password: string | null) => {
  try {
      const response = await fetch(`${API_URL}/wines`, {
          method: 'GET',
          headers: {
              'Authorization': 'Basic ' + btoa(`${username}:${password}`),
              'Content-Type': 'application/json',
          },
      });

      if (!response.ok) {
          const errorMessage = await response.text();
          throw new Error(`Ошибка ${response.status}: ${errorMessage}`);
      }

      return await response.json();
  } catch (error) {
      console.error('Ошибка при запросе вин:', error);
      throw error;
  }
};

export const getWiness = async (username: string | null, password: string | null) => {
  try {
      const response = await fetch(`${API_URL}/wines`, {
          method: 'GET',
          headers: {
              'Authorization': 'Basic ' + btoa(`${username}:${password}`),
              'Content-Type': 'application/json',
          },
      });

      if (!response.ok) {
          const errorMessage = await response.text();
          throw new Error(`Ошибка ${response.status}: ${errorMessage}`);
      }

      return await response.json();
  } catch (error) {
      console.error('Ошибка при запросе вин:', error);
      throw error;
  }
};

export const getWineById = async (id: number) => {
  const response = await fetch(`${API_URL}/wines/${id}`, {
    method: 'GET',
  });
  return handleResponse(response);
};

export const updateWine = async (id: number, wineData: any, token: string) => {
  const response = await fetch(`${API_URL}/wines/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(wineData),
  });
  return handleResponse(response);
};

export const deleteWine = async (id: number, token: string) => {
  const response = await fetch(`${API_URL}/wines/${id}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) throw new Error('Помилка видалення');
};

// Користувач

export const updateUserRole = async (userId: number, roleName: string, token: string) => {
  const response = await fetch(`${API_URL}/users/${userId}/role`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify({ roleName }),
  });
  return handleResponse(response);
};

export const getUserProfile = async (username: string | null, password: string | null) => {
  const response = await fetch(`${API_URL}/users/me`, {
    method: 'GET',
    headers: {
      'Authorization': 'Basic ' + btoa(`${username}:${password}`),    },
  });
  return handleResponse(response);
};

export const updateUserProfile = async (profileData: any, token: string) => {
  const response = await fetch(`${API_URL}/users/me`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(profileData),
  });
  return handleResponse(response);
};

// Кошик

export const getCart = async (token: string) => {
  const response = await fetch(`${API_URL}/cart`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  return handleResponse(response);
};

export const addToCart = async (cartData: any, token: string) => {
  const response = await fetch(`${API_URL}/cart`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(cartData),
  });
  return handleResponse(response);
};

export const updateCartItem = async (itemId: number, quantity: number, token: string) => {
  const response = await fetch(`${API_URL}/cart/items/${itemId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify({ quantity }),
  });
  return handleResponse(response);
};

export const deleteCartItem = async (itemId: number, token: string) => {
  const response = await fetch(`${API_URL}/cart/items/${itemId}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  if (!response.ok) throw new Error('Помилка видалення');
};

// Замовлення

export const createOrder = async (orderData: any, token: string) => {
  const response = await fetch(`${API_URL}/orders`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify(orderData),
  });
  return handleResponse(response);
};

export const getOrders = async (token: string) => {
  const response = await fetch(`${API_URL}/orders`, {
    method: 'GET',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });
  return handleResponse(response);
};

export const updateOrderStatus = async (orderId: number, status: string, token: string) => {
  const response = await fetch(`${API_URL}/orders/${orderId}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify({ status }),
  });
  return handleResponse(response);
};
