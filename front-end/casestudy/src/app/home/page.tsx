import { ProductCard } from "@/components/ProductCard";
import { fetchProducts } from "../services/api";
import styles from '@/styles/HomePage.module.css';

export default async function HomePage() {
   const products=await fetchProducts();


    return (
      <>
        <div className={styles.homeContainer}>
        <h1>Welcome to Home Page</h1>
        <h4>List of Products Available</h4>
        <ol className={styles.productList}>
          {
            products.map((product) => (
              <li key={product.productId}><ProductCard product={product}/></li>
            
            ))
          }
          </ol>
        </div> 
      </>
    );
  }
  