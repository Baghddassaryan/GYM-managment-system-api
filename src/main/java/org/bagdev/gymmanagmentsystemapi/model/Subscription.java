package org.bagdev.gymmanagmentsystemapi.model;

import jakarta.persistence.*;
import org.bagdev.gymmanagmentsystemapi.model.enums.SubscriptionStatus;

import java.time.LocalDate;


@Entity
@Table(name = "subscriptions")
public class Subscription {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String name; // e.g. "Monthly", "12-month"


    @Column(length = 1000)
    private String description;


    @Column(nullable = false)
    private Double price;


    // Duration in days
    @Column(nullable = false)
    private Integer durationDays;


    private LocalDate startDate;
    private LocalDate endDate;


    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;


    // Owner of the subscription
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    // Optionally assigned coach
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coach_id")
    private Coach coach;


    // QR token for subscription card / pass
    @Column(length = 128, unique = true)
    private String qrToken;


    public Subscription() { }
    // Getters/setters omitted for brevity
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public SubscriptionStatus getStatus() { return status; }
    public void setStatus(SubscriptionStatus status) { this.status = status; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Coach getCoach() { return coach; }
    public void setCoach(Coach coach) { this.coach = coach; }
    public String getQrToken() { return qrToken; }
    public void setQrToken(String qrToken) { this.qrToken = qrToken; }
}