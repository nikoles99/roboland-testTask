package com.nik.roboland.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Component
@Slf4j
public class ActivityLogger {

  private static SimpMessagingTemplate simpMessagingTemplate;

  @Autowired
  public ActivityLogger(SimpMessagingTemplate simpMessagingTemplate) {
    ActivityLogger.simpMessagingTemplate = simpMessagingTemplate;
  }

  public static void log(String message) {
    if (Objects.nonNull(simpMessagingTemplate)) {
      String logMessage = "[" + getTime() + "]: " + message + ".";
      log.info(logMessage);
      simpMessagingTemplate.convertAndSend("/log", logMessage);
    }
  }

  private static String getTime() {
    return new SimpleDateFormat("HH:mm:ss").format(new Date());
  }
}
