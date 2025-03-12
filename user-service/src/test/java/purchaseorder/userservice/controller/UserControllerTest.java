package purchaseorder.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import purchaseorder.userservice.dto.UserRequest;
import purchaseorder.userservice.dto.UserResponse;
import purchaseorder.userservice.service.UserService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createUser_ShouldReturn201() throws Exception {
        UserRequest userRequest = new UserRequest("Zikri", "Rahman", "zikri.rahman@gmail.com", "081122334455");

        mockMvc.perform(post("/api/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("User successfully created"));

        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    void updateUser_ShouldReturn200() throws Exception {
        UserRequest userRequest = new UserRequest("Zikri", "Rahman", "zikri.rahman@gmail.com", "081122334455");

        mockMvc.perform(patch("/api/users/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("User successfully updated"));

        verify(userService, times(1)).updateUser(any(UserRequest.class), eq(1));
    }

    @Test
    void deleteUser_ShouldReturn200() throws Exception {
        mockMvc.perform(delete("/api/users/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("User successfully deleted"));

        verify(userService, times(1)).deleteUser(1);
    }

    @Test
    void getAllUsers_ShouldReturnList() throws Exception {
        List<UserResponse> userResponses = Collections.singletonList(new UserResponse(1, "Zikri", "Rahman", "zikri.rahman@gmail.com", "081122334455"));
        when(userService.getAllUser()).thenReturn(userResponses);

        mockMvc.perform(get("/api/users/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("Users successfully retrieved"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].firstName").value("Zikri"));

        verify(userService, times(1)).getAllUser();
    }

    @Test
    void getUser_ShouldReturnUser() throws Exception {
        UserResponse userResponse = new UserResponse(1, "Zikri", "Rahman", "zikri.rahman@gmail.com", "081122334455");
        when(userService.getUser(1)).thenReturn(userResponse);

        mockMvc.perform(get("/api/users/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.message").value("User successfully retrieved"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.firstName").value("Zikri"));

        verify(userService, times(1)).getUser(1);
    }
}
