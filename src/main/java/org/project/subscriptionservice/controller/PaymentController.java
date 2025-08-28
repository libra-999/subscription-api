package org.project.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.controller.mapper.PaymentControllerMapper;
import org.project.subscriptionservice.controller.response.PaymentResponse;
import org.project.subscriptionservice.domain.service.PaymentService;
import org.project.subscriptionservice.share.entity.HttpBodyResponse;
import org.project.subscriptionservice.share.entity.MetaData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.project.subscriptionservice.share.controller.ControllerHandler.responseSucceed;

/**
 * The type Payment controller.
 * @ libra
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/payments")
public class PaymentController {

    private final PaymentService service;
    private final PaymentControllerMapper mapper;

    /**
     * Check payment response entity.
     *
     * @param code the code
     * @return the response entity
     */
    @PatchMapping("/check")
    public ResponseEntity<HttpBodyResponse<?>> checkPayment(@RequestParam String code) {
        service.verify(code);
        return responseSucceed();
    }

    /**
     * View response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<PaymentResponse>> view(@PathVariable Integer id){
        return responseSucceed(mapper.from(service.view(id, new MetaData())));
    }
}
