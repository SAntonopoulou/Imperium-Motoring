import java.security.SecureRandom;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.time.LocalDate;
//test2
public class Vehicle {
    private int ID;
    private String make;
    private String model;
    private String variant;
    private LocalDate registration;
    private String fuelType;
    private String category;
    private String drivetrain;
    private int price;
    private int mileage;
    private int engineSize;
    private int enginePower;
    private String enginePosition;
    private String engineType;
    private String transmission;
    private String colour;
    private String interiorColour;
    private String dbURL;
    private String dbUsername;
    private String dbPassword;

    //Need to add notes variable when changed to longtext
    public Vehicle(int ID,String dbURL, String dbUsername, String dbPassword) {
        this.ID = ID;
        this.dbURL = dbURL;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        queryDatabase(this.dbURL, this.dbUsername, this.dbPassword);
    }

    public Vehicle(int ID, String make, String model, String variant, LocalDate registration, String fuelType, String category, String drivetrain, int price, int mileage, int engineSize, int enginePower, String enginePosition, String engineType, String transmission, String colour, String interiorColour) {
        this.ID = ID;
        this.make = make;
        this.model = model;
        this.variant = variant;
        this.registration = registration;
        this.fuelType = fuelType;
        this.category = category;
        this.drivetrain = drivetrain;
        this.price = price;
        this.mileage = mileage;
        this.engineSize = engineSize;
        this.enginePower = enginePower;
        this.enginePosition = enginePosition;
        this.engineType = engineType;
        this.transmission = transmission;
        this.colour = colour;
        this.interiorColour = interiorColour;
    }
    private void queryDatabase(String dbURL, String dbUsername, String dbPassword){
        String query = "SELECT * FROM vehicles WHERE id =" + this.ID;

        // JDBC objects
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // 1. Establish a connection
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);

            // 2. Create a Statement object
            statement = connection.createStatement();

            // 3. Execute the SELECT query
            resultSet = statement.executeQuery(query);

            // 4. Process the ResultSet
            while (resultSet.next()) {
                // put all parameters for the vehicle into here using a
                // similar manner
                this.ID = resultSet.getInt("vehicle_id");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        } finally {
            // 5. Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

