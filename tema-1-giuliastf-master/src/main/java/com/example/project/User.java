package com.example.project;

public class User {
    public String username;
    private String password;
    //constructor
    public void setPassword(String new_psw){
        this.password = new_psw;
    }
    public String getPassword(){
        return this.password;
    }
    //-create-user -u 'uchii_cu_cel' -p unghii2022
    User (String name, String password){
        this.username = name;
        this.password = password;
    }
    public static void CreateUser(final String[] arg) {
        User user = new User(null, null);
        for(int i=0; i< arg.length; i++) {
            if (arg[i].split(" ")[0].equals("-u")) {
                user.username = arg[i].split(" ")[1].replace("'", "");
                if(UsersDatabase.findUser(user.username) == 1){
                    System.out.print("{'status':'error','message':'User already exists'}");
                    return;
                }
            }else if(arg[i].split(" ")[0].equals("-p"))
                user.setPassword(arg[i].split(" ")[1].replace("'", ""));
        }

        if(user.username == null)
            System.out.print("{'status':'error','message':'Please provide username'}");
        else if(user.password == null)
            System.out.print("{'status':'error','message':'Please provide password'}");
        else {
            UsersDatabase.addUser(user);
            System.out.print("{ 'status' : 'ok', 'message' : 'User created successfully'}");
        }
    }
}
