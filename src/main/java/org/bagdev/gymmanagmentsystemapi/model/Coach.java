package org.bagdev.gymmanagmentsystemapi.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "coaches")
public class Coach extends User {


    @Column(length = 1024)
    private String bio;


    private String certifications; // comma separated or normalized table


    private String specialties; // e.g. "strength,cardio,yoga"


    private Double hourlyRate;


    public Coach() { }


    // Getters and setters
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }
    public String getSpecialties() { return specialties; }
    public void setSpecialties(String specialties) { this.specialties = specialties; }
    public Double getHourlyRate() { return hourlyRate; }
    public void setHourlyRate(Double hourlyRate) { this.hourlyRate = hourlyRate; }
}