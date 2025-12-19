package org.bagdev.gymmanagmentsystemapi.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.bagdev.gymmanagmentsystemapi.model.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_users_email", columnList = "email")
})
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
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



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email; // We use email as the username
    }

    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return active; }

// Constructors, getters, setters omitted for brevity (generate via IDE)
public User() {}


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

}