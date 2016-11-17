package com.chubb.logparser.Entry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ichistruga on 11/7/2016.
 */
public class ReportEntry {

    private String threadName = "";
    private Date entryDate = new Date();
    private String stepName = "";
    private String level = "";
    private String message = "";
    private String parametersList = "";
    private long executionTime = 0;
    private List<ReportEntry> children = new ArrayList<ReportEntry>();

    public ReportEntry() {
    }

    public ReportEntry(ReportEntry entry) {
        this.setThreadName(entry.getThreadName());
        this.setEntryDate(entry.getEntryDate());
        this.setStepName(entry.getStepName());
        this.setLevel(entry.getLevel());
        this.setMessage(entry.getMessage());
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ReportEntry> getChildren() {
        return children;
    }

    public void setChildren(List<ReportEntry> children) {
        this.children = children;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public static ReportEntry groupByThreadName(ReportEntry entry) {

        Map<String, List<ReportEntry>> results =
                entry.getChildren().stream().collect(Collectors.groupingBy(data -> data.getThreadName()));

        ReportEntry root = new ReportEntry();
        for (Map.Entry<String, List<ReportEntry>> element : results.entrySet()) {
            ReportEntry reportElement = new ReportEntry();

            reportElement.setThreadName(element.getKey());

            List<ReportEntry> elemList = element.getValue();
            elemList.sort(Comparator.comparing(ReportEntry::getEntryDate));
            reportElement.setExecutionTime(
                    elemList
                            .stream()
                            .mapToLong(ReportEntry::getExecutionTime)
                            .sum()
            );
            reportElement.getChildren().addAll(elemList);
            if (elemList.stream().anyMatch(x -> x.getLevel().toUpperCase().equals("ERROR"))) {

                reportElement.setMessage("Failed");// at step " + failedRd.get().getStepName());
                // if details are needed - uncomment the line
                //Optional<ReportEntry> failedRd = elemList.stream()
                //        .sorted((x, y) -> x.getEntryDate().compareTo(y.getEntryDate()))
                //        .filter(x -> x.getLevel().toUpperCase().equals("ERROR"))
                //        .findFirst();
                //reportElement.setMessage("Failed at step " + failedRd.get().getStepName());
            } else {
                reportElement.setMessage("Ok");
            }
            root.getChildren().add(reportElement);
        }
        return root;

    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public String getParametersList() {
        return parametersList;
    }

    public void setParametersList(String parametersList) {
        this.parametersList = parametersList;
    }
}
