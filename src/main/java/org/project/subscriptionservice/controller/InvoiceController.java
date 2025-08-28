package org.project.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.controller.mapper.InvoiceControllerMapper;
import org.project.subscriptionservice.controller.response.InvoiceDetailResponse;
import org.project.subscriptionservice.domain.service.InvoiceService;
import org.project.subscriptionservice.share.entity.HttpBodyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.project.subscriptionservice.share.controller.ControllerHandler.responseSucceed;

/**
 * The type Invoice controller.
 * @ libra
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/invoices")
public class InvoiceController {

    private final InvoiceService service;
    private final InvoiceControllerMapper mapper;

    /**
     * View response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<InvoiceDetailResponse>> view(@PathVariable Integer id){
        return responseSucceed(mapper.from(service.view(id)));
    }

}
