package purchaseorder.userservice.service;

import purchaseorder.userservice.dto.UserRequest;
import purchaseorder.userservice.dto.UserResponse;

import java.util.List;

public interface UserService {

    void createUser(UserRequest userRequest);

    void updateUser(UserRequest userRequest, int userId);

    void deleteUser(int userId);

    List<UserResponse> getAllUser();

    UserResponse getUser(int userId);
}
