import { fetchProductDetails } from "@/app/services/api"
import { ProductDetails } from "./ProductDetails"
import styles from "@/styles/ProductDetailsPage.module.css"


type ProductDetailsType={
    params:{
        id: string
    }
}

export default async function ProductDetailsPage({params} : ProductDetailsType){
    const {id}=params
    const product=await fetchProductDetails(id)
    return(
      <div className={styles.container}>
        <h1 className={styles.title}>Product Details Page</h1>
        {product ? (
                <ProductDetails product={product}/>
            ) : (
                <p>Loading product details...</p>
            )}
      </div>
    )
}