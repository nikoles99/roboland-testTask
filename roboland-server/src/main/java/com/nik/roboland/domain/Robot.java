package com.nik.roboland.domain;

import com.nik.roboland.utils.ActivityLogger;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Robot {
  protected List<Task> tasks = new CopyOnWriteArrayList<>();
  protected String name;
  private Boolean alive = true;

  protected abstract CompletableFuture<Void> processTasks();

  public Robot(String name) {
    this.name = name;
    ActivityLogger.log("Robot with name " + name + " has been created");
  }

  public void addTaskInQueue(Task task) {
    if (alive) {
      tasks.add(task);
      ActivityLogger.log("Robot " + name + " get a new task - '" + task.getName() + "'");
      if (tasks.size() == 1) {
        new Thread(() -> processTasks()).start();
      }
    }
  }

  public Boolean isDied() {
    return !alive;
  }

  public String getName() {
    return name;
  }

  protected void killSelf() {
    alive = false;
    ActivityLogger.log("Robot " + name + " kill himself");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Robot robot = (Robot) o;
    return Objects.equals(name, robot.name) &&
      Objects.equals(alive, robot.alive);
  }

  @Override
  public int hashCode() {

    return Objects.hash(name, alive);
  }
}
