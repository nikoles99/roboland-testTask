package com.nik.roboland.service;

import com.nik.roboland.domain.Robot;
import com.nik.roboland.domain.Task;
import com.nik.roboland.domain.UniversalRobot;
import com.nik.roboland.utils.ActivityLogger;
import com.nik.roboland.utils.RobotUtils;
import com.nik.roboland.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InitService {

  private static int i = 0;
  private List<Task> tasks = new ArrayList<>();
  private Map<String, Robot> robots;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Async
  public Comparable<?> process(List<Task> tasks, Map<String, Robot> robots) {
    this.tasks = tasks;
    this.robots = robots;
    createRobots();
    while (true) {
      if (tasks.isEmpty()) {
        createTasks();
        distributeTasks();
      }
    }
  }

  private void createTasks() {
    for (int i = 0; i < 50; i++) {
      Task task = TaskUtils.createTask("RandomTask");
      tasks.add(task);
    }
  }

  private void createRobots() {
    for (String name : RobotUtils.NAMES) {
      name = name + ":" + getRobotNumber();
      Robot robot = createRobot(name);
      robots.put(name, robot);
      simpMessagingTemplate.convertAndSend("/robots", RobotUtils.getRobotNames(robots));
    }
  }

  private Robot createRobot(String name) {
    if (robots.size() < RobotUtils.MAX_ROBOTS_COUNT) {
      return new UniversalRobot(name);
    } else {
      ActivityLogger.log("REACHED THE LIMIT OF ROBOTS COUNT!!!!!!");
      return null;
    }
  }

  private int getRobotNumber() {
    if (i == Integer.MAX_VALUE) {
      i = 0;
    } else {
      i++;
    }
    return i;
  }

  private void distributeTasks() {
    int robotIndex = 0;
    Iterator<Task> iterator = tasks.iterator();
    while (iterator.hasNext()) {
      Task task = iterator.next();
      if (robotIndex == robots.size() - 1) {
        robotIndex = 0;
      }
      List<String> robotNames = new ArrayList<>(robots.keySet());
      Robot robot = robots.get(robotNames.get(robotIndex));
      if (Objects.nonNull(robot)) {
        robot.addTaskInQueue(task);
      }
      robotIndex++;
    }
  }
}
