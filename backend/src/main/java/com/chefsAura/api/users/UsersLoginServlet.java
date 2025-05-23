package com.chefsAura.api.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chefsAura.models.User;
import com.chefsAura.models.UserCollection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

// @WebServlet("/api/users/login")
public class UsersLoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("UsersLoginServlet initialized.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");

        // Read request body
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // Parse JSON request body
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();

        System.out.println("UsersLoginServlet POST request received with parameters: " + email + " = " + password);

        // Validate user login
        boolean loginStatus = UserCollection.loginUser(email, password);

        // Create JSON response
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("loginStatus", loginStatus);

        if (loginStatus) {
            User currentUser = UserCollection.getUserByEmail(email);
            JsonObject userJson = new JsonObject();
            userJson.addProperty("username", currentUser.getUsername());
            userJson.addProperty("email", currentUser.getEmail());
            userJson.addProperty("nationality", currentUser.getNationality());
            userJson.addProperty("firstName", currentUser.getFirstName());
            userJson.addProperty("lastName", currentUser.getLastName());
            userJson.addProperty("phoneNo", currentUser.getPhoneNo());
            userJson.addProperty("gender", currentUser.getGender());
            userJson.addProperty("dob", currentUser.getDob());
            userJson.addProperty("role", currentUser.getRole());
            jsonResponse.addProperty("user", userJson.toString());
        }

        // Write the response
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(jsonResponse));
        out.flush();
    }
}