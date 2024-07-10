export type AuthContextType={
    isLoggedIn: boolean,
    token: string,
    username: string,
    login: (token: string,email: string)=> void,
    logout: ()=>void
}