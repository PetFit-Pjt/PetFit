package com.port.petfit.user.member.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.port.petfit.user.member.account.User;
import com.port.petfit.user.member.account.UserRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

@RestController
@RequestMapping("/verify-payment")
public class PaymentController {

    private static final Logger LOGGER = Logger.getLogger(PaymentController.class.getName());
    private final IamportClient iamportClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private MembershipRepository membershipRepository;

    public PaymentController() {
        iamportClient = new IamportClient("4722527667201751", "Km59aVj3vC3XGecMkiPgOF16B61MzLUKPjOyDfEweIzEIOwkBJDtoPGeaWFzyTN2kZhA1XwEzROrsvIQ");
    }

    @PostMapping
    public ResponseEntity<VerificationResponse> verifyPayment(
        @RequestParam("imp_uid") String impUid,
        @RequestParam("paid_amount") double paidAmount,
        @RequestParam("durationMonths") int durationMonths) {
        try {
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(impUid);
            if (paymentResponse.getResponse() != null) {
                Payment apiPayment = paymentResponse.getResponse();

                BigDecimal amountToBePaid = BigDecimal.valueOf(paidAmount);
                if (apiPayment.getAmount().compareTo(amountToBePaid) == 0) {
                    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                    String userId = authentication.getName();
                    User user = userRepository.findByUserId(userId);

                    com.port.petfit.user.member.payment.Payment newPayment = new com.port.petfit.user.member.payment.Payment();
                    newPayment.setUser(user);
                    newPayment.setAmount(apiPayment.getAmount().doubleValue());
                    newPayment.setImpUid(apiPayment.getImpUid());
                    newPayment.setPaymentMethod("Credit Card");
                    paymentRepository.save(newPayment);

                    Membership membership = new Membership();
                    membership.setUser(user);
                    membership.setStartDate(new Date());
                    membership.setDurationMonths(durationMonths);
                    membership.setMembershipstatus("Active");
                    membership.setPayment(newPayment); // Connect the payment to the membership
                    membershipRepository.save(membership);

                    return new ResponseEntity<>(new VerificationResponse(true, "Payment verified successfully."), HttpStatus.OK);
                }
            }

            LOGGER.warning("Payment verification failed: ImpUid mismatch or payment amount mismatch.");
            return new ResponseEntity<>(new VerificationResponse(false, "Payment verification failed."), HttpStatus.BAD_REQUEST);
        } catch (IamportResponseException e) {
            LOGGER.severe("Failed to verify payment: " + e.getMessage());
            return new ResponseEntity<>(new VerificationResponse(false, "Failed to verify payment."), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            LOGGER.severe("Internal server error: " + e.getMessage());
            return new ResponseEntity<>(new VerificationResponse(false, "Internal server error."), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}