package com.nik.roboland.service;

import com.nik.roboland.domain.Robot;
import com.nik.roboland.domain.Task;
import com.nik.roboland.domain.UniversalRobot;
import com.nik.roboland.utils.ActivityLogger;
import com.nik.roboland.utils.RobotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ProgressTaskEstimatorService {


  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Async
  public Comparable<?> process(List<Task> tasks, Map<String, Robot> robots) {
    while (true) {
      synchronized (tasks) {
        Iterator<Task> iterator = tasks.iterator();
        while (iterator.hasNext()) {
          Task task = iterator.next();

          long remainingTime = task.getRemainingTime();
          long timeForTask = task.getTimeForTask();
          long spendTime = timeForTask - remainingTime;
          if (spendTime != 0) {
            addRobotForHelp(robots, task, remainingTime, spendTime);
          }
        }
      }
    }
  }

  private void addRobotForHelp(Map<String, Robot> robots, Task task, long remainingTime, long spendTime) {
    long executionProcess = task.getExecutionProcess();
    if (executionProcess == 0) {
      addRobot(robots, task);
    } else {
      long expectedFinishTaskTime = (100 - executionProcess) * spendTime / executionProcess;
      if (expectedFinishTaskTime <= remainingTime) {
        addRobot(robots, task);
      }
    }
  }

  private void addRobot(Map<String, Robot> robots, Task task) {
    Robot robot = new UniversalRobot(RobotUtils.NAMES[new Random().nextInt(19)]);
    ActivityLogger.log("Robot " + robot.getName() + " create to help with task: " + task.getName());
    robot.addTaskInQueue(task);
    robots.put(robot.getName(), robot);
    simpMessagingTemplate.convertAndSend("/robots", RobotUtils.getRobotNames(robots));
  }
}
