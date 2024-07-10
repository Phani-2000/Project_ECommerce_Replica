import axios from "axios";
import { headers } from "next/headers";
import { Product } from "./ProductTypes";
import { Cart } from "./CartType";
import { User } from "./UserType";

type CartRequestType={
  userId: string,
  cartItem: {
    productName: string,
    quantity: number
  }
}

const API_URL="https://api.mockapi.com/api/v1";

const AUTH_API_URL="http://localhost:8111/api/auth"
const PRODUCT_API_URL="http://localhost:8082/api/product"
const CART_API_URL="http://localhost:8081/api/shoppingcart"
const ORDER_API_URL="http://localhost:8088/api/orderservice"

export const fetchProducts=async (): Promise<Product[]> =>{
    let response= await axios.get(`${PRODUCT_API_URL}/getAllProducts`);
    return response.data.content;
}

export const fetchProductDetails= async(id : string): Promise<Product> =>{
  let response= await axios.get(`${PRODUCT_API_URL}/getById/${id}`);
   console.log(response)
   return response.data;
}

export const addToCart= async(cartRequest: CartRequestType,token: string): Promise<Cart> =>{
  let response= await axios.post(`${CART_API_URL}/addToCart`,cartRequest, {
    headers: {
      'Authorization': token
    }
   });
   console.log('product added to cart successfully')
   return response.data;
}

export const getCart= async(id : string,token: string): Promise<Cart> =>{
  let response= await axios.get(`${CART_API_URL}/getCart/${id}`, {
    headers: {
      'Authorization': token
    }
   });
   console.log(response.data)
   return response.data;
}

export const registerUser= async(user : User): Promise<String> =>{
  try {
    const response = await axios.post(`${AUTH_API_URL}/register`, user, {
      headers: {
        'x-api-key': '469be45cd6444b6b96c9360352f96821',
      },
    });
    console.log('User Registration successful');
    return response.data;
  } catch (error) {
    console.error('Error registering user:', error);
    throw new Error('Failed to register user');
  }
}

export const loginUser= async(user : User): Promise<String> =>{
  console.log(user)
  try {
    const response = await axios.post(`${AUTH_API_URL}/login`, user);
    console.log('User Log in successful');
    return response.data;
  } catch (error) {
    console.error('Error logging user:', error);
    throw new Error('Failed to login user');
  }
}

export const placeOrder=async(userId : string,token : string): Promise<String> =>{
  try {
    const response = await axios.post(`${ORDER_API_URL}/order`, {userId:userId},{
      headers: {
        'Authorization': token,
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error Placing Order:', error);
    throw new Error('Failed to Place Order');
  }
}