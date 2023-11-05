package com.example.project;

import java.io.*;

public class UsersDatabase {

    public static int addUser(User user) {
        try (FileWriter fw = new FileWriter("users.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.println(user.username + "," + user.getPassword());
        } catch (IOException e) { }
        return 0;
    }

    public static int findUser(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(("users.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].equals(username))
                    return 1;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return 0;
    }

    public static int login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(("users.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].equals(username) && data[1].equals(password))
                    return 1;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return 0;
    }
}
