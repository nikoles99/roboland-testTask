package com.nik.roboland;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
public abstract class AbstractControllerTest {
  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  protected MockHttpServletRequestBuilder withHeaders(MockHttpServletRequestBuilder request) {
    return request
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON);
  }

  @SneakyThrows
  protected String asJsonString(Object requestContent) {
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    return objectMapper.writeValueAsString(requestContent);
  }
}
