package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "src\\Book1.csv";

        String[][] data = ReadFileIntoArray(filePath, 4);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < Objects.requireNonNull(data).length; i++) {
            String dateTo = data[i][3].trim();

            if (dateTo.equals("NULL")) {
                data[i][3] = dtf.format(now).trim();

            }
        }
        List<Project> listOfRecords = new ArrayList<>();
        for (int i = 0; i < Objects.requireNonNull(data).length; i++) {

            Project project = new Project(Integer.parseInt(data[i][0]), Integer.parseInt(data[i][1]), LocalDate.parse(data[i][2]), LocalDate.parse(data[i][3]));
            listOfRecords.add(project);
        }

        System.out.println(getPairOfWorkersWithLongestCommonProject(listOfRecords));
    }

    public static String[][] ReadFileIntoArray(String filePath, int amountOfFields) {

        List<String> recordList = new ArrayList<>();

        String delimiter = ",";
        String currentLine;


        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            while ((currentLine = br.readLine()) != null) {

                recordList.add(currentLine.trim());

            }

            int recordCount = recordList.size();
            String[][] arrayToReturn = new String[recordCount][amountOfFields];

            String[] data;

            for (int i = 0; i < recordCount; i++) {

                data = recordList.get(i).split(delimiter);

                for (int j = 0; j < data.length; j++) {

                    arrayToReturn[i][j] = data[j].trim();
                }
            }
            return arrayToReturn;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    private static int getDurationInDays(LocalDate dateFrom1, LocalDate dateTo1, LocalDate dateFrom2, LocalDate
            dateTo2) {
        LocalDate start;
        if (dateFrom1.isAfter(dateFrom2)) start = dateFrom1;
        else start = dateFrom2;
        LocalDate end = (dateTo1 == null || dateTo1.isAfter(LocalDate.now())) ? LocalDate.now() : dateTo1;
        if (dateTo2 == null || dateTo2.isBefore(end)) end = dateTo2;
        else end = end;

        if (start.isAfter(end)) {
            return 0;
        }

        return (int) ChronoUnit.DAYS.between(start, end);
    }

    public static String getPairOfWorkersWithLongestCommonProject(List<Project> projects) {
        int maxDurationInDays = 0;
        int emp1 = 0, emp2 = 0;
        for (int i = 0; i < projects.size() - 1; i++) {
            for (int j = i + 1; j < projects.size(); j++) {
                Project project1 = projects.get(i);
                Project project2 = projects.get(j);
                if (project1.getEmpID() != project2.getEmpID() && project1.getProjectID() == project2.getProjectID()) {
                    int durationInDays = getDurationInDays(project1.getDateFrom(), project1.getDateTo(),
                            project2.getDateFrom(), project2.getDateTo());
                    if (durationInDays > maxDurationInDays) {
                        maxDurationInDays = durationInDays;
                        emp1 = project1.getEmpID();
                        emp2 = project2.getEmpID();
                    }
                }
            }
        }
        return String.format("%d, %d, %d", emp1, emp2, maxDurationInDays);
    }
}
