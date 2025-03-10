package com.example.recruit2.DTO;

public class OfferRequest {
    private String fullName;
    private String position;
    private String startDate;
    private String salary;
    private String probationPeriod;
    private String responsibilities;
    private String benefits;

    // Геттеры и сеттеры
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getProbationPeriod() { return probationPeriod; }
    public void setProbationPeriod(String probationPeriod) { this.probationPeriod = probationPeriod; }

    public String getResponsibilities() { return responsibilities; }
    public void setResponsibilities(String responsibilities) { this.responsibilities = responsibilities; }

    public String getBenefits() { return benefits; }
    public void setBenefits(String benefits) { this.benefits = benefits; }
}
