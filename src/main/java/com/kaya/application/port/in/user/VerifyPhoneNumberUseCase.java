package com.kaya.application.port.in.user;

import io.smallrye.mutiny.Uni;

public interface VerifyPhoneNumberUseCase {
    Uni<Boolean> sendOTP(String phoneNumber);
    Uni<Boolean> verifyOTP(String phoneNumber, String otp);
}