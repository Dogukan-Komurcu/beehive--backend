package com.example.beehiveproject.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
// Marks this class as a JPA entity, meaning it will be mapped to a table in the database
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private String id;

    private String username;
    private String name;      // Kısa ad
    private String fullName;  // Tam ad
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    public enum Status {
        ACTIVE,
        PASSIVE
    }

    private java.time.LocalDateTime lastLogin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    private boolean blocked = false;

    public User() {}

    public User(String email, String password, String name, String fullName, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.fullName = fullName;
        this.role = role;
    }

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public java.time.LocalDateTime getLastLogin() { return lastLogin; }

    public void setLastLogin(java.time.LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public boolean isBlocked() { return blocked; }

    public void setBlocked(boolean blocked) { this.blocked = blocked; }
}
