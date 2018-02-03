package com.nik.roboland;

import com.nik.roboland.controllers.RobotsController;
import lombok.SneakyThrows;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RobotsControllerTest extends AbstractControllerTest {

  @Test
  @SneakyThrows
  public void getRobots() {
    // When
    ResultActions resultActions = mockMvc.perform(
      withHeaders(post("http://localhost:8080/getRobots")).content("{}")
    )
      .andDo(print());
    // Then
    resultActions
      .andExpect(status().isOk())
      .andExpect(jsonPath("$").value(IsNull.notNullValue()));
  }

  @Test
  @SneakyThrows
  public void addTaskToAll() {
    RobotsController.RequestContent requestContent = RobotsController.RequestContent.builder()
      .task("TEST ADDING TASK")
      .build();
    // When
    ResultActions resultActions = mockMvc.perform(
      withHeaders(post("http://localhost:8080/addTaskToAll")).content(asJsonString(requestContent))
    )
      .andDo(print());
    // Then
    resultActions
      .andExpect(status().isOk());
  }

}
