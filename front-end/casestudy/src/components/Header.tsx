import Link from "next/link";
import styles from "@/styles/Header.module.css";

export const Header=()=>{
    return(
        <nav className={styles.nav}>
            <ul className={styles.navList}>
              <li><Link href="/home">Home</Link></li>
              <li><Link href="/login">Login</Link></li>
              <li><Link href="/register">Register</Link></li>
              <li><Link href="/cart">Cart</Link></li>
              <li><Link href="/profile">Profile</Link></li>
            </ul>
          </nav>
    );
}