package org.bagdev.gymmanagmentsystemapi.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.bagdev.gymmanagmentsystemapi.model.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email")
})
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;


    @NotBlank
    @Column(nullable = false)
    private String password; // store hashed password only


    private String firstName;
    private String lastName;


    @Column(length = 20)
    private String phone;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();


    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();


    private boolean active = true;


    // QR payload associated with the user (e.g. membership QR token)
    @Column(unique = true, length = 128)
    private String qrToken;


    // For future: reference to externally stored QR image or badge file path
    private String qrImagePath;


// Constructors, getters, setters omitted for brevity (generate via IDE)
public User() {}


// Getters and setters...
// (In production prefer Lombok to reduce boilerplate or generate methods)


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
    public Instant getCreatedAt() { return createdAt; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getQrToken() { return qrToken; }
    public void setQrToken(String qrToken) { this.qrToken = qrToken; }
    public String getQrImagePath() { return qrImagePath; }
    public void setQrImagePath(String qrImagePath) { this.qrImagePath = qrImagePath; }
}