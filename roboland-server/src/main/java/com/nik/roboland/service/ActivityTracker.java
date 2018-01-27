package com.nik.roboland.service;

import com.nik.roboland.domain.Robot;
import com.nik.roboland.domain.Task;
import com.nik.roboland.utils.TaskUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ActivityTracker {

  private List<Task> tasks = Collections.synchronizedList(new LinkedList<>());
  private Map<String, Robot> robots = new ConcurrentHashMap();

  @Autowired
  private DeadRobotFinderService deadRobotFinderService;

  @Autowired
  private InitService initService;

  @Autowired
  private ProgressTaskEstimatorService progressTaskEstimatorService;

  @PostConstruct
  public void run() {
    initService.process(tasks, robots);
    progressTaskEstimatorService.process(tasks, robots);
    deadRobotFinderService.process(robots);
  }

  public List<String> getRobots() {
    return robots.values().stream().map(Robot::getName).collect(Collectors.toList());
  }

  public void assignTaskToAll(String taskName) {
    Task task = TaskUtils.createTask(taskName);
    robots.values().forEach(robot -> robot.addTaskInQueue(task));
  }

  public void assignTask(String taskName, String robotName) {
    Task task = TaskUtils.createTask(taskName);
    robots.get(robotName).addTaskInQueue(task);
  }
}
