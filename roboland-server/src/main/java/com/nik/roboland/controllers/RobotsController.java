package com.nik.roboland.controllers;

import com.nik.roboland.service.ActivityTracker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class RobotsController {

  @Data
  @NoArgsConstructor
  @Builder
  @AllArgsConstructor
  public static class RequestContent {
    private String task;
    private String robotName;
  }

  @Autowired
  private ActivityTracker activityTracker;

  @PostMapping(value = "/getRobots")
  public List<String> getRobots() {
    return activityTracker.getRobots();
  }

  @PostMapping(value = "/addTaskToAll")
  public void addTaskToAll(@RequestBody RequestContent requestContent) {
    activityTracker.assignTaskToAll(requestContent.getTask());
  }

  @PostMapping(value = "/addTask")
  public void addTask(@RequestBody RequestContent requestContent) {
    activityTracker.assignTask(requestContent.getTask(), requestContent.getRobotName());
  }
}
