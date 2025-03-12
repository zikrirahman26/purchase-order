package purchaseorder.userservice.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import purchaseorder.userservice.dto.UserRequest;
import purchaseorder.userservice.dto.UserResponse;
import purchaseorder.userservice.entity.UserApp;
import purchaseorder.userservice.repository.UserRepository;
import purchaseorder.userservice.service.UserService;
import purchaseorder.userservice.validation.ValidationRequest;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationRequest validationRequest;

    @Transactional
    @Override
    public void createUser(UserRequest userRequest) {

        validationRequest.validationRequest(userRequest);

        UserApp userApp = new UserApp();
        userApp.setFirstName(userRequest.getFirstName());
        userApp.setLastName(userRequest.getLastName());
        userApp.setEmail(userRequest.getEmail());
        userApp.setPhone(userRequest.getPhone());
        userRepository.save(userApp);
    }

    @Transactional
    @Override
    public void updateUser(UserRequest userRequest, int userId) {

        UserApp userApp = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        validationRequest.validationRequest(userRequest);

        userApp.setFirstName(userRequest.getFirstName());
        userApp.setLastName(userRequest.getLastName());
        userApp.setEmail(userRequest.getEmail());
        userApp.setPhone(userRequest.getPhone());
        userRepository.save(userApp);
    }

    @Transactional
    @Override
    public void deleteUser(int userId) {
        UserApp userApp = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.delete(userApp);
    }

    @Transactional
    @Override
    public List<UserResponse> getAllUser() {

        return userRepository.findAll().stream()
                .map(this::userResponse)
                .toList();
    }

    @Transactional
    @Override
    public UserResponse getUser(int userId) {
        UserApp userApp = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userResponse(userApp);
    }

    private UserResponse userResponse(UserApp userApp) {
        return UserResponse.builder()
                .id(userApp.getId())
                .firstName(userApp.getFirstName())
                .lastName(userApp.getLastName())
                .email(userApp.getEmail())
                .phone(userApp.getPhone())
                .build();
    }
}
