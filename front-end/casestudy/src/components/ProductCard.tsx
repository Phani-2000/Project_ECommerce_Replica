import { Product } from "@/app/services/ProductTypes"
import Link from "next/link"
import styles from '@/styles/ProductCard.module.css';

type ProductCartProps={
  product: Product
}
export const ProductCard=({product} :ProductCartProps) =>{
    return (
      <div className={styles.card}>
        <h2 className={styles.cardTitle}>{product.productName}</h2>
        <p className={styles.cardDescription}>{product.description}</p>
        <Link href={`/product/${product.productId}`} passHref><span className={styles.cardLink}>View Details</span></Link>
      </div>
    )
}