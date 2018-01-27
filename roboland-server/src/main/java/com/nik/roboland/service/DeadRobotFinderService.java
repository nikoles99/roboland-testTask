package com.nik.roboland.service;

import com.nik.roboland.domain.Robot;
import com.nik.roboland.utils.ActivityLogger;
import com.nik.roboland.utils.RobotUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class DeadRobotFinderService {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @Async
  public Comparable<?> process(Map<String, Robot> robots) {
    while (true) {
      Iterator<Robot> iterator = robots.values().iterator();
      while (iterator.hasNext()) {
        Robot robot = iterator.next();
        if (robot.isDied()) {
          ActivityLogger.log("Robot " + robot.getName() + " found dead");
          iterator.remove();
          simpMessagingTemplate.convertAndSend("/robots", RobotUtils.getRobotNames(robots));
        }
      }
    }
  }
}
