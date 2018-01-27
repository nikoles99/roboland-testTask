package com.nik.roboland.utils;

import com.nik.roboland.domain.Robot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RobotUtils {

  public static final int MAX_ROBOTS_COUNT = 70;

  public static final String[] NAMES = {"Jacob", "Michael", "Joshua", "Matthew", "Ethan", "Andrew", "Daniel",
    "Anthony", "Christopher", "Joseph", "William", "Alexander", "Ryan", "David", "Nicholas", "Tyler", "James",
    "John", "Jonathan", "Nathan"};

  public static List<String> getRobotNames(Map<String, Robot> robots) {
    return robots.values().stream().map(Robot::getName).collect(Collectors.toList());
  }

}
