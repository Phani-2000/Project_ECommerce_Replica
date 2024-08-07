'use client'
import Link from 'next/link';
import styles from '@/styles/Breadcrumbs.module.css';
import { usePathname } from 'next/navigation';

const Breadcrumbs = () => {
    const pathname = usePathname();
    const pathnames = pathname.split('/').filter(x => x);

    return (
        <nav className={styles.breadcrumbs}>
          <ul className={styles.breadcrumbList}>
            <li>
              <Link href="/">Home</Link>
              <span> | </span>
            </li>
            {pathnames.map((name, index) => {
              const routeTo = `/${pathnames.slice(0, index + 1).join('/')}`;
              const isLast = index === pathnames.length - 1;
              return (
                <li key={name}>
                  {isLast ? (
                    name
                  ) : ( <><Link href={routeTo}>{name}</Link>
                  <span> |</span></>
                  
                  )}
                </li>
              );
            })}
          </ul>
        </nav>
      );
    };

export default Breadcrumbs;
