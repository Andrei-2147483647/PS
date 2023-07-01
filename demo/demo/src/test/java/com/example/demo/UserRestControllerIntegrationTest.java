package com.example.demo;

import com.example.demo.domain.User;
import com.example.demo.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserRestControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void givenUsers_whenGetUsers_thenReturnJsonArray()
            throws Exception {

        User alex = new User("alex","a","b","c");

        List<User> allUsers = Arrays.asList(alex);

        given(service.getAllUsers()).willReturn((ArrayList<User>) allUsers);

        mvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$", hasSize(1)))
                .andExpect((ResultMatcher) jsonPath("$[0].name", is(alex.getName())));
    }

    @Test
    public void testGetUsers() throws Exception {
        User user1 = new User("John", "john123","j","a");
        User user2 = new User("Jane", "jane456","f","f");
        List<User> users = Arrays.asList(user1, user2);

        given(service.getAllUsers()).willReturn((ArrayList<User>) users);

        mvc.perform(get("/api/user")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect((ResultMatcher) jsonPath("$.length()").value(users.size()))
                .andExpect((ResultMatcher) jsonPath("$[0].name").value(user1.getName()))
                .andExpect((ResultMatcher) jsonPath("$[0].username").value(user1.getUsername()))
                .andExpect((ResultMatcher) jsonPath("$[1].name").value(user2.getName()))
                .andExpect((ResultMatcher) jsonPath("$[1].username").value(user2.getUsername()));
    }
}
