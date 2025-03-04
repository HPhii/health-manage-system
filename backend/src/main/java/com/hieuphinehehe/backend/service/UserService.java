package com.hieuphinehehe.backend.service;

import com.hieuphinehehe.backend.dto.request.auth.ChangePasswordRequest;
import com.hieuphinehehe.backend.dto.response.UserResponse;
import org.springframework.data.domain.Page;

import java.security.Principal;

public interface UserService {
    Page<UserResponse> getAllUsers(int page, int size, String keyword);
    UserResponse updateBlockStateUser(Integer id);
    public void changePassword(ChangePasswordRequest request, Principal connectedUser);
    long countUsersCreatedToday();
}
