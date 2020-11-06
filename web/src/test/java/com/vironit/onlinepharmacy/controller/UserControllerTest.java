package com.vironit.onlinepharmacy.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfigurationTest.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@WebAppConfiguration
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void set() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertTrue(servletContext instanceof MockServletContext);
        assertNotNull(webApplicationContext.getBean("userController"));
    }

    @Test
    void addShouldReturnFirstUserId() throws Exception {

        String userData="{\"firstName\":\"testf\",\n" +
                "    \"middleName\":\"testm\",\n" +
                "    \"lastName\":\"testl\",\n" +
                "    \"dateOfBirth\":\"2000-12-12\",\n" +
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
    void getShouldReturnFirstUser() throws Exception {
        this.mockMvc
                .perform(get("/users/{id}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("testfname"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    @Test
    void getAllShouldReturnAllUsers() throws Exception {
        this.mockMvc
                .perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.*").isArray())
                .andExpect(jsonPath("$.*.length()").value(1))
        .andExpect(jsonPath("$.*.password").doesNotExist());
    }
}