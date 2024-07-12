package com.kaya.infrastructure.external;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ApplicationScoped
public class OTPService {

    private final Map<String, String> otpStorage = new HashMap<>();
    private final Random random = new Random();

    public Uni<Boolean> generateAndSendOTP(String phoneNumber) {
        String otp = generateOTP();
        otpStorage.put(phoneNumber, otp);
        return sendOTP(phoneNumber, otp);
    }

    public Uni<Boolean> verifyOTP(String phoneNumber, String otp) {
        return Uni.createFrom().item(() -> {
            String storedOTP = otpStorage.get(phoneNumber);
            if (storedOTP != null && storedOTP.equals(otp)) {
                otpStorage.remove(phoneNumber);  // Remove OTP after successful verification
                return true;
            }
            return false;
        });
    }

    private String generateOTP() {
        // Generate a 6-digit OTP
        return String.format("%06d", random.nextInt(1000000));
    }

    private Uni<Boolean> sendOTP(String phoneNumber, String otp) {
        // In a real implementation, this method would send an SMS with the OTP
        // For this example, we'll just simulate sending and always return success
        System.out.println("Sending OTP " + otp + " to " + phoneNumber);
        return Uni.createFrom().item(true);
    }
}