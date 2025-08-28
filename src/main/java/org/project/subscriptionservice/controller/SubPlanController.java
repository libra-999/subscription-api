package org.project.subscriptionservice.controller;


import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.bean.SubscriptionPlanEntity;
import org.project.subscriptionservice.controller.mapper.SubPlanControllerMapper;
import org.project.subscriptionservice.controller.request.SubPlanCreation;
import org.project.subscriptionservice.controller.request.SubPlanUpdated;
import org.project.subscriptionservice.controller.response.SubPlanDetailResponse;
import org.project.subscriptionservice.controller.response.SubPlanResponse;
import org.project.subscriptionservice.domain.service.SubscriptionPlanService;
import org.project.subscriptionservice.share.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.project.subscriptionservice.share.controller.ControllerHandler.*;

/**
 * The type Sub plan controller.
 * @ libra
 */
@RestController
@RequestMapping("v1/api")
@RequiredArgsConstructor
public class SubPlanController {

    private final SubPlanControllerMapper mapper;
    private final SubscriptionPlanService service;

    /**
     * List response entity.
     *
     * @param page    the page
     * @param size    the size
     * @param keyword the keyword
     * @return the response entity
     */
    @GetMapping("/front/subscription-plans")
    public ResponseEntity<HttpBodyResponse<List<SubPlanResponse>>> list(@RequestParam(required = false) Integer page,
                                                                        @RequestParam(required = false) Integer size,
                                                                        @RequestParam(required = false) String keyword) {


        Paging<SubscriptionPlanEntity> paging = service.list(PaginationQuery.of(page, size, keyword), new MetaData());
        return responsePaging(
            paging.getItems().stream().map(mapper::fromList).toList(),
            HttpBodyPagingResponse.of(
                paging.getPage(),
                paging.getSize(),
                paging.getTotal(),
                paging.getTotalPages()
            )
        );
    }

    /**
     * View response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @GetMapping("/front/subscription-plans/{id}")
    public ResponseEntity<HttpBodyResponse<SubPlanDetailResponse>> view(@PathVariable Integer id) {
        return responseSucceed(mapper.fromDetail(service.view(id, new MetaData())));
    }

    /**
     * Create response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("/subscription-plans")
    public ResponseEntity<HttpBodyResponse<SubPlanDetailResponse>> create(@Validated @RequestBody SubPlanCreation request) {
        return responseCreated(mapper.fromDetail(service.create(request, new MetaData())));
    }

    /**
     * Update response entity.
     *
     * @param id      the id
     * @param request the request
     * @return the response entity
     */
    @PutMapping("/subscription-plans/{id}")
    public ResponseEntity<HttpBodyResponse<SubPlanDetailResponse>> update(@PathVariable Integer id,
                                                                          @Validated @RequestBody SubPlanUpdated request){
        return responseSucceed(mapper.fromDetail(service.update(id, request, new MetaData())));
    }

}
