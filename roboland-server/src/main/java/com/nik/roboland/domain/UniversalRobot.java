package com.nik.roboland.domain;

import com.nik.roboland.utils.ActivityLogger;
import org.springframework.scheduling.annotation.Async;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class UniversalRobot extends Robot {

  public UniversalRobot(String name) {
    super(name);
  }

  @Override
  public CompletableFuture<Void> processTasks() {
    ActivityLogger.log("Robot " + name + " start working");
    Random random = new Random();
    int i = random.nextInt(10);
    synchronized (tasks) {
      Iterator<Task> iterator = tasks.iterator();
      while (iterator.hasNext()) {
        Task task = iterator.next();
        // imitate random  robot death
        if (i == tasks.indexOf(task)) {
          killSelf();
          return null;
        }
        process(task);
        iterator.remove();
      }
    }
    ActivityLogger.log("Robot " + name + " finished all tasks");
    return null;
  }

  // imitate robot working
  private void process(Task task) {
    try {
      while (task.getRemainingTime() != 0) {
        long workTime = ThreadLocalRandom.current().nextLong(5, 15);
        Thread.currentThread().sleep(workTime * 1000);
        task.setWorkTime(workTime);
        task.setExecutionProcess((int) (workTime * 100 / task.getTimeForTask()));
      }
      ActivityLogger.log("Robot " + name + " finished task: " + task.getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
