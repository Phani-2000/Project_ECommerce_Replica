'use client'
import { useRouter } from "next/navigation"
import { useAuth } from "@/components/AuthContext";
import { useEffect, useState } from "react";
import { getCart } from "../services/api";
import { ItemType } from "../services/CartType";
import styles from "@/styles/CartPage.module.css"


export default function CartPage() {
  
  const [cartItems,setCartItems]=useState<ItemType[]>()
  const { isLoggedIn,username,token } = useAuth();
    const router=useRouter()
    

    useEffect(() => {
        if (!isLoggedIn) {
            router.push('/login?message=redirected');
        }
        else{
          fetchCartOnServer(username,token)
        }
    }, [isLoggedIn, router]);

    if (!isLoggedIn) {
        return <p>Redirecting to login Page. Please Wait </p>; // or a loading indicator
    }

    const fetchCartOnServer=async(id: string,token: string)=>{
      const {cartItems}=await getCart(id,`Bearer ${token}`)
      console.log(cartItems)
      setCartItems(cartItems)

    }

    const handlePlaceOrder=()=>{
        sessionStorage.setItem('cartItems',JSON.stringify(cartItems))
        router.push('order-summary')
    }

    const totalPrice= cartItems?.reduce((total, cartItem) => total + cartItem.price, 0)
    return (
      <div className={styles.container}>
      <h2 className={styles.heading}>Welcome to Cart Page</h2>
      <h5>User: {username}</h5>
      <div className={styles.cartItems}>
        {cartItems?.map(cartItem => (
          <div key={cartItem.itemId} className={styles.cartItem}>
            <div className={styles.cartItemDetails}>
              <div className={styles.cartItemInfo}>
                <span>{cartItem.productName}</span>
                <span>Quantity: {cartItem.quantity}</span>
              </div>
              <span>${cartItem.price}</span>
            </div>
          </div>
        ))}
      </div>
      <h5 className={styles.totalPrice}>Total Price: ${totalPrice?.toFixed(2)}</h5>
      <button className={styles.placeOrderButton} onClick={handlePlaceOrder}>Place Order</button>
    </div>
    )
  
  }
  