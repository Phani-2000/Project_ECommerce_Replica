'use client'
import { useRouter, useSearchParams } from "next/navigation"
import { useEffect, useState } from "react"
import { ItemType } from "../services/CartType"
import { useAuth } from "@/components/AuthContext"
import styles from "@/styles/OrderSummaryPage.module.css"

export default function OrderSummaryPage(){

    const [cartItems,setCartItems]=useState<ItemType[]>()
  const { isLoggedIn,username,token } = useAuth();
    const router=useRouter()
    

    useEffect(() => {
        if (!isLoggedIn) {
            router.push('/login?message=redirected');
        }
        else{
            const items=sessionStorage.getItem('cartItems');
            if(items){
                setCartItems(JSON.parse(items))
            }  
        }
    }, [isLoggedIn, router]);

    if (!isLoggedIn) {
        return <p>Redirecting to login Page. Please Wait </p>; // or a loading indicator
    }

    const handleConfirmOrder=async()=>{
        const response= await fetch('/api/order',{
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({username: username,token: token}),
        })
        const {success,message}=await response.json()
        alert(message)
        router.push('home')

    }
    return(
        <div className={styles.container}>
            <h2 className={styles.heading}>Welcome to Order Page</h2>
            <div className={styles.orderSummary}>
                <h4>Order Summary</h4>
                {cartItems?.map((cartItem) => (
                    <div key={cartItem.itemId} className={styles.orderItem}>
                        <p>{cartItem.productName} - Quantity: {cartItem.quantity} - ${cartItem.price}</p>
                    </div>
                ))}
                <p>We will send order confirmation mail to {username}</p>
            </div>
            <button className={styles.confirmButton} onClick={handleConfirmOrder}>Confirm Order</button>
        </div>
    )
}