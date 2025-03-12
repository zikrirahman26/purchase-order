package purchaseorder.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import purchaseorder.userservice.dto.ApiResponse;
import purchaseorder.userservice.dto.UserRequest;
import purchaseorder.userservice.dto.UserResponse;
import purchaseorder.userservice.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody UserRequest userRequest) {
        userService.createUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("User successfully created")
                        .data(null)
                .build());
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<ApiResponse<String>> updateUser(@RequestBody UserRequest userRequest, @PathVariable int userId) {
        userService.updateUser(userRequest, userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("User successfully updated")
                        .data(null)
                .build());
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<String>builder()
                        .status("Success")
                        .message("User successfully deleted")
                        .data(null)
                .build());
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> userResponseList = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<List<UserResponse>>builder()
                        .status("Success")
                        .message("Users successfully retrieved")
                        .data(userResponseList)
                .build());
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUser(@PathVariable int userId) {
        UserResponse userResponse = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.<UserResponse>builder()
                        .status("Success")
                        .message("User successfully retrieved")
                        .data(userResponse)
                .build());
    }
}
