package purchaseorder.userservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.userservice.dto.UserRequest;
import purchaseorder.userservice.dto.UserResponse;
import purchaseorder.userservice.entity.UserApp;
import purchaseorder.userservice.repository.UserRepository;
import purchaseorder.userservice.service.impl.UserServiceImpl;
import purchaseorder.userservice.validation.ValidationRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationRequest validationRequest;

    @InjectMocks
    private UserServiceImpl userService;

    private UserApp userApp;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userApp = new UserApp();
        userApp.setId(1);
        userApp.setFirstName("Zikri");
        userApp.setLastName("Rahman");
        userApp.setEmail("zikri.rahman@gmail.com");
        userApp.setPhone("081122334455");

        userRequest = new UserRequest();
        userRequest.setFirstName("Zikri");
        userRequest.setLastName("Rahman");
        userRequest.setEmail("zikri.rahman@gmail.com");
        userRequest.setPhone("081122334455");
    }

    @Test
    void testCreateUser() {
        doNothing().when(validationRequest).validationRequest(userRequest);
        when(userRepository.save(any(UserApp.class))).thenReturn(userApp);

        userService.createUser(userRequest);

        verify(userRepository, times(1)).save(any(UserApp.class));
    }

    @Test
    void testUpdateUser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userApp));
        doNothing().when(validationRequest).validationRequest(userRequest);
        when(userRepository.save(any(UserApp.class))).thenReturn(userApp);

        assertDoesNotThrow(() -> userService.updateUser(userRequest, 1));
        verify(userRepository, times(1)).save(any(UserApp.class));
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.updateUser(userRequest, 1));
        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userApp));
        doNothing().when(userRepository).delete(userApp);

        assertDoesNotThrow(() -> userService.deleteUser(1));
        verify(userRepository, times(1)).delete(userApp);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.deleteUser(1));
        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());
    }

    @Test
    void testGetAllUser() {
        when(userRepository.findAll()).thenReturn(List.of(userApp));
        List<UserResponse> users = userService.getAllUser();

        assertEquals(1, users.size());
        assertEquals("Zikri", users.get(0).getFirstName());
    }

    @Test
    void testGetUser_Success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(userApp));
        UserResponse userResponse = userService.getUser(1);

        assertEquals("Zikri", userResponse.getFirstName());
    }

    @Test
    void testGetUser_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> userService.getUser(1));
        assertEquals("404 NOT_FOUND \"User not found\"", exception.getMessage());
    }
}
