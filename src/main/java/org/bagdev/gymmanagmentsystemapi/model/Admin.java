package org.bagdev.gymmanagmentsystemapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "admins")
public class Admin extends User {
    // Additional admin-specific fields can go here (audit level, permissions map, etc.)
    public Admin() { }
}