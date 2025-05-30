package com.chefsAura.models;

import java.util.ArrayList;

public class UserCollection {
    static ArrayList<User> users;
    static User currentUser;

    // Static initializer block to initialize the users list and currentUser
    static {
        users = new ArrayList<>();
        currentUser = new User();
    }

    // empty constructor
    public UserCollection() {
    }

    // add user from file
    public static void addUser(User user) {
        users.add(user);
        // System.out.println("User " + user.getFirstName() + " added successfully");
    }

    // set all users
    public static void setAllUsers(ArrayList<User> users) {
        UserCollection.users = users;
    }

    // get all users
    public static ArrayList<User> getAllUsers() {
        return users;
    }

    // get all orders
    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        for (User user : users) {
            orders.addAll(user.getOrders());
        }
        return orders;
    }

    // register user
    public static String registerUser(String username, String email, String password,
            String nationality, String firstName, String lastName,
            String phoneNo, short gender, String dob, Boolean agreeToTerms) {
        try {
            if(username == "" || email == "" || password == "" ||
                    nationality == "" || firstName == "" || lastName == "" ||
                    phoneNo == "" || gender == 0 || dob == "" || agreeToTerms == false) {
                System.err.println("All fields are required");
                return "All fields are required";
            }
            for (User user : users) {
                if (user.getEmail().equals(email)) {
                    System.err.println("User with email " + email + " already exists");
                    return ("User with email " + email + " already exists");
                }
                if (user.getUsername().equals(username)) {
                    System.err.println("User with username " + username + " already exists");
                    return ("User with username " + username + " already exists");
                }
            }
            User user = new User(username, email, password,
                    nationality, firstName, lastName, phoneNo,
                    gender, dob, agreeToTerms);
            users.add(user);
            currentUser = user;
            System.out.println(currentUser.getFirstName() + " registered successfully");
            return "Success";
        } catch (Exception e) {
            System.err.println("Error registering user: " + e.getMessage());
            return "Error registering user: " + e.getMessage();
        }
    }

    // login user
    public static boolean loginUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && user.validatePassword(password)) {
                currentUser = user;
                System.out.println(currentUser.getFirstName() + " logged in successfully");
                return true;
            }
        }
        System.err.println("Invalid email or password");
        return false;
    }

    // get user by email
    public static User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }
}
