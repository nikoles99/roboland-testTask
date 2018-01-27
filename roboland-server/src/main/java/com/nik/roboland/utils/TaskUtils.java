package com.nik.roboland.utils;

import com.nik.roboland.domain.Task;

import java.util.concurrent.ThreadLocalRandom;

public class TaskUtils {

  public static Task createTask(String name) {
    long timeForTask = TaskUtils.generateTimeForTask();
    return new Task(name + ": " + timeForTask, timeForTask);
  }

  private static Long generateTimeForTask() {
    return ThreadLocalRandom.current().nextLong(10, 100);
  }
}
