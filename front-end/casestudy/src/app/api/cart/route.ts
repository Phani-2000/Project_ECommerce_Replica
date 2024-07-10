
import { addToCart } from "@/app/services/api";
import { NextResponse } from "next/server";

export async function POST(request: Request){

    const {cartRequest,token}=await request.json();

    const message=await addToCart(cartRequest,token);

    return NextResponse.json({ success: true, message: message });

}