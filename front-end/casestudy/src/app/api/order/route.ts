import { placeOrder } from "@/app/services/api";
import { NextResponse } from "next/server";

export async function POST(request: Request){
    const {username,token} = await request.json()
    const message= await placeOrder(username,`Bearer ${token}`)
    return NextResponse.json({success:true, message: message})
}