import Vehicle_Enums.*;

import java.time.LocalDate;

public class Vehicle {
    private int ID;
    private String make;
    private String model;
    private String variant;
    private LocalDate registration;
    private FuelType fuelType;
    private Category category;
    private Drivetrain drivetrain;
    private int price;
    private int mileage;
    private int engineSize;
    private int enginePower;
    private EnginePosition enginePosition;
    private EngineType engineType;
    private Transmission transmission;
    private String colour;
    private String interiorColour;
    //Need to add notes variable when changed to longtext

    public Vehicle(int ID, String make, String model, String variant, LocalDate registration, FuelType fuelType, Category category, Drivetrain drivetrain, int price, int mileage, int engineSize, int enginePower, EnginePosition enginePosition, EngineType engineType, Transmission transmission, String colour, String interiorColour) {
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
}

