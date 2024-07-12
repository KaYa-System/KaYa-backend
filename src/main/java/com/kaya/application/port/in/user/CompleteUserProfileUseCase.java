package com.kaya.application.port.in.user;

import com.kaya.application.dto.UserProfileDTO;
import com.kaya.domain.model.User;
import io.smallrye.mutiny.Uni;

import java.util.UUID;

public interface CompleteUserProfileUseCase {
    Uni<User> completeProfile(UUID userId, UserProfileDTO profileData);
}