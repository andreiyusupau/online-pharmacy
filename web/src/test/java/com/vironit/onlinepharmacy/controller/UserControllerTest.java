package com.vironit.onlinepharmacy.controller;


import com.vironit.onlinepharmacy.model.Role;
import com.vironit.onlinepharmacy.service.user.UserService;
import com.vironit.onlinepharmacy.vo.UserPublicVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(classes = ApplicationConfigurationTest.class)
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    private MockMvc mockMvc;

    @BeforeEach
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
    }

    @Test
    void addShouldReturnFirstUserId() throws Exception {
//TODO:validation
        when(userService.add(any())).thenReturn(1L);
        String userData = "{\"firstName\":\"testf\",\n" +
                "    \"middleName\":\"testm\",\n" +
                "    \"lastName\":\"testl\",\n" +
                "    \"dateOfBirth\":\"2020-12-12\",\n" +
                "    \"email\":\"email@test.com\",\n" +
                "    \"password\":\"mytestpass\",\n" +
                "    \"confirmPassword\":\"mytestpass\"\n" +
                "    }";

        this.mockMvc
                .perform(post("/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userData))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").value("1"));
    }

    @Test
    void addShouldReturnBadRequest() throws Exception {
//TODO:validation
        when(userService.add(any())).thenReturn(1L);
        String userData = "{\"firstName\":\"testf\",\n" +
                "    \"middleName\":\"testm\",\n" +
                "    \"lastName\":\"testl\",\n" +
                "    \"dateOfBirth\":\"2060-12-12\",\n" +
                "    \"email\":\"emailtest.com\",\n" +
                "    \"password\":\"mytestpass9\",\n" +
                "    \"confirmPassword\":\"mytestpass\"\n" +
                "    }";

        this.mockMvc
                .perform(post("/users")
                        .contentType("application/json;charset=UTF-8")
                        .content(userData))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void getShouldReturnFirstUser() throws Exception {
        when(userService.get(1)).thenReturn(new UserPublicVo(1, "testfname", "testmname", "testlname", LocalDate.of(2000, 1, 1), "email@test.com", Role.CONSUMER));
        this.mockMvc
                .perform(get("/users/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("testfname"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void getAllShouldReturnAllUsers() throws Exception {
        when(userService.getAll()).thenReturn(List.of(new UserPublicVo(1, "testfname", "testmname", "testlname", LocalDate.of(2000, 1, 1), "email@test.com", Role.CONSUMER)));
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[*].password").doesNotExist());
    }

//    @Test
//    void updateShouldReturnUser(){
//        this.mockMvc
//                .perform(get("/users"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json"))
//                .andExpect(jsonPath("$.*").isArray())
//                .andExpect(jsonPath("$.length()").value(1))
//                .andExpect(jsonPath("$[*].password").doesNotExist());
//    }
//
//    @Test
//    void removeShouldRemoveUser(){
//
//    }
}