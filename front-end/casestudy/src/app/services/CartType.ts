

export type ItemType={
    itemId: string,
    productName: string,
    quantity: number,
    price: number
  }

export type Cart={
    userId: string,
    cartItems: ItemType[]
}