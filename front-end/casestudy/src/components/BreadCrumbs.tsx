'use client'
import Link from 'next/link';
import styles from '@/styles/Breadcrumbs.module.css';
import { usePathname } from 'next/navigation';

const Breadcrumbs = () => {
    const pathname = usePathname();
    const pathnames = pathname.split('/').filter(x => x);

    return (
        <nav className={styles.breadcrumbs}>
            <Link href="/">Home</Link>
            {pathnames.map((pathname, index) => {
                const href = `/${pathnames.slice(0, index + 1).join('/')}`;
                return (
                    <span key={href}>
                        &gt; <Link href={href}>{pathname}</Link>
                    </span>
                );
            })}
        </nav>
    );
};

export default Breadcrumbs;
