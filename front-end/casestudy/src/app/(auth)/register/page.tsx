'use client'
import Link from "next/link";
import { useRouter } from "next/navigation";
import styles from "@/styles/Register.module.css";
import { useState } from "react";

export default function RegisterPage() {
    const [email,setEmail]=useState("");
    const [password,setPassword]=useState("");
    const [confPwd,setConfPwd]=useState("")
    const [error, setError] = useState('');
    const router=useRouter();

    const handleRegister=async (e :React.FormEvent) =>{
        e.preventDefault();
        if(password!==confPwd){
            setError('Passwords do not match');
            return;
        }
        setError("")
        const registerForm={
            username: email,
            password: password
        }
        console.log(registerForm)
        const response= await fetch("/api/register", {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(registerForm),
        })
        const {message}= await response.json()
        console.log('Response -',message)
        alert(message)
        router.push("/login")
    }
    return (
        <div className={styles.registerContainer}>
            <h1>Registration Page</h1>
            <form onSubmit={handleRegister} className={styles.registerForm}>
                <div className={styles.formGroup}>
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e)=>setEmail(e.target.value)} required />
                </div>
                <div className={styles.formGroup}>
                    <label>Password:</label>
                    <input type="password" value={password} onChange={(e)=>setPassword(e.target.value)} required />
                </div>
                <div className={styles.formGroup}>
                    <label>Confirm Password:</label>
                    <input type="text" value={confPwd} onChange={(e)=>setConfPwd(e.target.value)} required />
                </div>

                {error && <p className={styles.errorMessage}>{error}</p>}
                <button type="submit" className={styles.submitButton}>Register</button>
                
            </form>
            <p className={styles.registerLink}>For existing user login <Link href="login">Here</Link></p>
        </div>
    );
}
