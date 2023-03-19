package org.example;

import java.time.LocalDate;

public class Project {
    private int empID;
    private int projectID;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Project(int empID, int projectID, LocalDate dateFrom, LocalDate dateTo) {
        this.empID = empID;
        this.projectID = projectID;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getEmpID() {
        return empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

}
