package com.redhat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.*;

@Path("/testConnection")
public class JdbcTestResource {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        StringBuilder retval = new StringBuilder();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/example", "admin", "testing")) {
            System.out.println("Connected to PostgreSQL database.\n");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM testtable");
            while (resultSet.next()) {
                retval.append("Found row: " + resultSet.getString(1));
            }

        } catch (SQLException e) {
            retval.append("Connection failure: " + e.getMessage());
        }
        return retval.toString();
    }
}
