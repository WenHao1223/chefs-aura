package com.chefsAura.api.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chefsAura.models.UserCollection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

// @WebServlet("/api/users/create")
public class UsersCreateServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("UsersCreateServlet initialized.");
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
        String username = jsonObject.get("username").getAsString();
        String email = jsonObject.get("email").getAsString();
        String password = jsonObject.get("password").getAsString();
        String nationality = jsonObject.get("nationality").getAsString();
        String firstName = jsonObject.get("firstName").getAsString();
        String lastName = jsonObject.get("lastName").getAsString();
        String phoneNo = jsonObject.get("phoneNo").getAsString();
        short gender = jsonObject.get("gender").getAsShort();
        String dob = jsonObject.get("dob").getAsString();
        boolean agreeToTerms = jsonObject.get("agreeToTerms").getAsBoolean();

        System.out.println("UsersCreateServlet POST request received with parameters: " + email + " = " + password);

        // Validate user login
        String status = UserCollection.registerUser(username, email, password, nationality, firstName, lastName,
                phoneNo, gender, dob, agreeToTerms);
        ;

        // Create JSON response
        JsonObject jsonResponse = new JsonObject();
        if (status.equals("Success")) {
            jsonResponse.addProperty("status", status);
        } else {
            jsonResponse.addProperty("status", "Error");
            jsonResponse.addProperty("message", status);
        }

        // Write the response
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(jsonResponse));
        out.flush();
    }
}
