package com.nik.roboland.domain;

import com.nik.roboland.utils.ActivityLogger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public abstract class Robot {
  protected List<Task> tasks = Collections.synchronizedList(new ArrayList());
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
      new Thread(this::processTasks).start();
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
    return Objects.equals(tasks, robot.tasks) &&
      Objects.equals(name, robot.name) &&
      Objects.equals(alive, robot.alive);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tasks, name, alive);
  }
}
