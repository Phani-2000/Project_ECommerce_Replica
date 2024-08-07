'use client'

import Link from "next/link";
import styles from "@/styles/Header.module.css";
import { useAuth } from "./AuthContext";

export const Header=()=>{

    const {isLoggedIn,logout} = useAuth();
    return(
        <nav className={styles.nav}>
            <ul className={styles.navList}>
              <li><Link href="/home">Home</Link></li>
              <li><Link href="/register">Register</Link></li>
              <li><Link href="/cart">Cart</Link></li>
              <li>{isLoggedIn?<Link href="/profile">Profile</Link>:<Link href="/login">Login</Link>}</li>
            </ul>
          </nav>
    );
}