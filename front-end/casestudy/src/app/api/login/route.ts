import { loginUser } from "@/app/services/api"
import { NextResponse } from "next/server"

export async function POST(request: Request) {
    const {username,password}=await request.json()
    const response= await loginUser({username,password})
    if(response){
        const token=response.split(': ')[1]
        return NextResponse.json({success:true,token:token})
    }

    return NextResponse.json({success:false,message:"User not authenticated"})
    
}