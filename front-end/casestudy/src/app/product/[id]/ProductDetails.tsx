'use client'

import { Product } from "@/app/services/ProductTypes";
import { useAuth } from "@/components/AuthContext";
import { useRouter } from "next/navigation";
import { useState, useEffect } from "react";
import styles from "@/styles/ProductDetails.module.css"

type ProductDetailsType={
    product: Product
}

export function ProductDetails({product}: ProductDetailsType ){
    const { isLoggedIn,token,username } = useAuth();
    const [quantity,setQuantity]=useState(1)
    const router = useRouter();

    const handleOnClick= async()=>{
        if (!isLoggedIn) {
            alert("Please Login to add product to your cart")
            router.push(`/login?message=ProductDetails@${product.productId}`);
            return
        }
        const cartRequest={
            userId: username,
            cartItem: {
                productName: product?.productName,
                quantity : quantity
            }
        }
        const response=await fetch("/api/cart",{
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({ cartRequest: cartRequest,token: `Bearer ${token}` }),
          });
          const {message}=await response.json()
        alert(message)
        router.push("/home")
    }
    return(
      <div className={styles.productDetails}>
                    <h3 className={styles.productName}>Product Name: {product.productName}</h3>
                    <p className={styles.description}>{product.description}</p>
                    <label className={styles.label}>
                        Quantity:
                        <select
                            value={quantity}
                            onChange={(e) => setQuantity(Number(e.target.value))}
                            className={styles.select}
                        >
                            {Array.from({ length: 10 }, (_, i) => i + 1).map((n) => (
                                <option key={n} value={n}>
                                    {n}
                                </option>
                            ))}
                        </select>
                    </label>
                    <h5 className={styles.price}>Per Unit Price: ${product.pricePerUnit}</h5>
                    <button onClick={handleOnClick} className={styles.button}>Add To Cart</button>
                
      </div>
    )
}