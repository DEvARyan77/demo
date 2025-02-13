package com.example.demo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

@Document(collection = "training_centers")  // MongoDB collection name
public class TrainingCenter {

    @Id
    private String id;  // MongoDB will use this as the primary key if it's not set, or you can use `centerCode`

    @NotBlank(message = "Center name is required")
    @Size(max = 40, message = "Center name must be less than 40 characters")
    private String centerName;

    @NotBlank(message = "Center code is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{12}$", message = "Center code must be exactly 12 alphanumeric characters")
    @Indexed(unique = true)
    private String centerCode;

    @Valid
    @NotNull(message = "Address is required")
    @Indexed(unique = true)
    private Address address;

    @Min(value = 1, message = "Student capacity must be at least 1")
    private int studentCapacity;

    private List<@NotBlank(message = "Course name cannot be blank") String> coursesOffered;

    private long createdOn;

    @Email(message = "Invalid email format")
    private String contactEmail;

    @NotBlank(message = "Contact phone is required")
    @Pattern(regexp = "^\\+?[0-9]{10,10}$", message = "Invalid phone number format")
    private String contactPhone;

    // Getters and Setters (Added for completeness)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCenterCode() {
        return centerCode;
    }

    public void setCenterCode(String centerCode) {
        this.centerCode = centerCode;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getStudentCapacity() {
        return studentCapacity;
    }

    public void setStudentCapacity(int studentCapacity) {
        this.studentCapacity = studentCapacity;
    }

    public List<String> getCoursesOffered() {
        return coursesOffered;
    }

    public void setCoursesOffered(List<String> coursesOffered) {
        this.coursesOffered = coursesOffered;
    }

    public long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(long createdOn) {
        this.createdOn = createdOn;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
