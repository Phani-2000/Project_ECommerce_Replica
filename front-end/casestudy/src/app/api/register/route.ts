import { registerUser } from "@/app/services/api";
import { NextResponse } from "next/server";

export async function POST(request: Request){
    const {username,password}= await request.json()
    console.log({username,password})
    try {
        await registerUser({ username, password });
        return NextResponse.json({ success: true, message: 'User Registered Successfully' });
    } catch (error) {
        console.error('Error registering user:', error);
        return NextResponse.json({ success: false, message: 'Failed to register user' }, { status: 500 });
    }
}