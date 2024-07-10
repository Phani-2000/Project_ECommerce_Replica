'use client'

import { useAuth } from "@/components/AuthContext";
import { useRouter } from "next/navigation";
import styles from '@/styles/ProfilePage.module.css';

export default function ProfilePage() {
    const router = useRouter();
    const { isLoggedIn, logout, username } = useAuth();

    const handleLogin = () => {
        router.push('/login');
    };

    return (
        <div className={styles.container}>
            <h2 className={styles.heading}>Profile Page</h2>
            {isLoggedIn ? (
                <>
                    <div className={styles.userInfo}>
                        <h5>Username: {username}</h5>
                    </div>
                    <button className={styles.loginButton} onClick={logout}>Logout</button>
                </>
            ) : (
                <>
                    <p>Please login to continue</p>
                    <button className={styles.loginButton} onClick={handleLogin}>Login</button>
                </>
            )}
        </div>
    );
}
  