'use client'
import { useAuth } from "@/components/AuthContext";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import React, { Suspense, useEffect, useState } from "react";
import styles from "@/styles/Login.module.css"

function LoginPage() {

  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState('');
  const [redirectMessage,SetRedirectMessage]= useState('')
  const router = useRouter();
  const searchParams=useSearchParams()
  const {login}=useAuth()
  const message=searchParams.get('message')
  console.log(message)

  useEffect(()=>{
    if(message==="redirected"){
      SetRedirectMessage("Please login before continuing further")
    }
  },[searchParams])

  const handleLogin =async(e: React.FormEvent)=>{
    e.preventDefault()
    const loginForm = {
      username: email,
      password: password
    }
    try {
      const response = await fetch("/api/login", {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(loginForm),
      })
      const { success, token } = await response.json()
      if (success) {
        login(token,email)
        alert("User Login Successful")
        if(message?.includes('ProductDetails')){
          const id=message.split('@')[1]
          console.log(id)
          router.push(`/product/${id}`)
          return
        }
        router.push("/profile")
      } else {
        setError('Incorrect username or password');
      }
    } catch (error) {
      setError('Failed to login');
    }
    
  }
  return (
    <div className={styles.loginContainer}>
      <h1>Welcome to Login Page</h1>
      {redirectMessage && <p className={styles.redirectMessage}>{redirectMessage}</p>}
      <form onSubmit={handleLogin} className={styles.loginForm}>
        <div className={styles.formGroup}>
          <label>Email:</label>
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </div>
        <div className={styles.formGroup}>
          <label>Password:</label>
          <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
        </div>

        <button type="submit" className={styles.submitButton}>Login</button>

      </form>
      {error && <p className={styles.errorMessage}>{error}</p>}
      <p className={styles.registerLink}>For new user , register <Link href="register">Here</Link></p>
    </div>
  );
  }

  export default function LoginPageWrapper() {
    return (
      <Suspense fallback={<div>Loading...</div>}>
        <LoginPage />
      </Suspense>
    );
  }
  