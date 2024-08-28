package com.kaya.application.port.in.user;

import com.kaya.application.dto.AbstractCreateUserDTO;
import com.kaya.domain.model.User;
import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;



public interface UserUseCases extends CreateUserUseCase, VerifyPhoneNumberUseCase, SetUserPinUseCase, CompleteUserProfileUseCase {

    Uni<User> getUserById(UUID userId);
    Uni<User> getUserByEmail(String email);

    Uni<User> getUserByPhoneNumber(String phoneNumber);

    Uni<List<User>> getUsers(int page, int size);

}
