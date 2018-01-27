package com.nik.roboland.domain;

public class Task {

  private String name;

  private long timeForTask;

  private long remainingTime;

  private Integer executionProcess = 0;

  public Task(String name, long timeForTask) {
    this.name = name;
    this.timeForTask = timeForTask;
    this.remainingTime = timeForTask;
  }

  synchronized public void setExecutionProcess(Integer process) {
    executionProcess = process;
  }

  synchronized public void setWorkTime(long workTime) {
    this.remainingTime = remainingTime - workTime > 0 ? remainingTime - workTime : 0;
  }

  synchronized public Integer getExecutionProcess() {
    return executionProcess;
  }

  synchronized public long getRemainingTime() {
    return remainingTime;
  }

  synchronized public long getTimeForTask() {
    return timeForTask;
  }

  synchronized public String getName() {
    return name;
  }
}
