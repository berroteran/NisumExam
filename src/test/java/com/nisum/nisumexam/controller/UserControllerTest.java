package com.nisum.nisumexam.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nisum.nisumexam.dto.UserRequestDTO;
import com.nisum.nisumexam.persistence.Phone;
import com.nisum.nisumexam.support.utils.StringUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  
  @Autowired
  private MockMvc mockMvc;
  
  @BeforeEach
  void setUp() {
  }
  
  @Test
  void createUser_returnDTO_and_statusOK() throws Exception {
    UserRequestDTO validUser = getValidUserRequestDummy();
    
    var executionResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(StringUtils.entityToString(validUser))).andDo(print())
        .andExpect(status().isCreated()).andReturn();
    
    var executionResultContent = executionResult.getResponse().getContentAsString();
    assertThat(executionResultContent).isNotNull();
  }
  
  @Test
  void createUser_fail_and_messageError() throws Exception {
    UserRequestDTO invalidUser = getInvalidUserRequestDummy();
    
    var executionFailResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/signup").contentType(MediaType.APPLICATION_JSON).content(StringUtils.entityToString(invalidUser))).andDo(print())
        .andExpect(status().isBadRequest()).andReturn();
    
    var executionResultContent = executionFailResult.getResponse().getContentAsString();
    assertThat(executionResultContent).isNotNull();
  }
  
   private UserRequestDTO getInvalidUserRequestDummy() {
    UserRequestDTO ur = new UserRequestDTO();
    ur.setEmail("newEmail@servercom");
    ur.setName("usuario NO valido");
    ur.setPassword("$2a$12$9XM0Uw168DMGdVGOK5LF7u927gYi9v8QCGoAvM.9xXA4DDktKLKgm'"); //pass2
    ur.setPhones(List.of(new Phone("123123123", "1", "57")));
    return ur;
  }
  private UserRequestDTO getValidUserRequestDummy() {
    UserRequestDTO ur = new UserRequestDTO();
    ur.setEmail("newEmail@serverchileno.cl");
    ur.setName("usuario nuevo valido");
    ur.setPassword("$2a$12$9XM0Uw168DMGdVGOK5LF7u927gYi9v8QCGoAvM.9xXA4DDktKLKgm'"); //pass2
    ur.setPhones(List.of(new Phone("123123123", "1", "57")));
    return ur;
  }
  
}