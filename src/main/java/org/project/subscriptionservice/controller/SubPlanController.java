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

@RestController
@RequestMapping("v1/api/public/subscription-plans")
@RequiredArgsConstructor
public class SubPlanController {

    private final SubPlanControllerMapper mapper;
    private final SubscriptionPlanService service;


    @GetMapping
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

    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<SubPlanDetailResponse>> view(@PathVariable Integer id) {
        return responseSucceed(mapper.fromDetail(service.view(id, new MetaData())));
    }

    @PostMapping
    public ResponseEntity<HttpBodyResponse<SubPlanDetailResponse>> create(@Validated @RequestBody SubPlanCreation request) {
        return responseCreated(mapper.fromDetail(service.create(request, new MetaData())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<SubPlanDetailResponse>> update(@PathVariable Integer id,
                                                                          @Validated @RequestBody SubPlanUpdated request){
        return responseSucceed(mapper.fromDetail(service.update(id, request, new MetaData())));
    }

}
