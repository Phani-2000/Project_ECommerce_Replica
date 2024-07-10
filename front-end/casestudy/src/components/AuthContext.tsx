'use client'

import { AuthContextType } from "@/app/services/AuthContextType"
import { createContext, useContext, useState, ReactNode } from 'react';


const AuthContext= createContext<AuthContextType | undefined>(undefined)

export const AuthProvider = ({ children }: { children: ReactNode }) => {
    const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false);
    const [token,setToken]= useState<string>("");
    const [username,setUsername]=useState<string>("");
    const login = (token: string,email: string) => {
      setToken(token);
      setUsername(email);
      setIsLoggedIn(true);
    };
  
    const logout = () => {
      localStorage.removeItem('token');
      setIsLoggedIn(false);
    };
  
    return (
      <AuthContext.Provider value={{ isLoggedIn, login, logout,token,username }}>
        {children}
      </AuthContext.Provider>
    );
  };
  
  export const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
      throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
  };