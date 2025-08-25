package org.project.subscriptionservice.controller;

import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.bean.SubscriptionEntity;
import org.project.subscriptionservice.controller.mapper.SubControllerMapper;
import org.project.subscriptionservice.controller.request.SubCreation;
import org.project.subscriptionservice.controller.response.SubDetailResponse;
import org.project.subscriptionservice.controller.response.SubResponse;
import org.project.subscriptionservice.domain.service.SubService;
import org.project.subscriptionservice.share.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.project.subscriptionservice.share.controller.ControllerHandler.*;

@RestController
@RequestMapping("v1/api/subscribes")
@RequiredArgsConstructor
public class SubController {

    private final SubControllerMapper mapper;
    private final SubService service;

    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<SubResponse>>> list(@RequestParam(required = false) Integer page,
                                                                    @RequestParam(required = false) Integer size,
                                                                    @RequestParam(required = false) String keyword,
                                                                    @RequestParam(required = false) String status,
                                                                    @RequestParam(required = false) String startDate,
                                                                    @RequestParam(required = false) String endDate) {

        Paging<SubscriptionEntity> paging = service.list(PaginationQuery.of(page, size, keyword), status , startDate, endDate, new MetaData());
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
    public ResponseEntity<HttpBodyResponse<SubDetailResponse>> view(@PathVariable Integer id) {
        return responseSucceed(mapper.fromDetail(service.view(id, new MetaData())));
    }


    @PostMapping
    public ResponseEntity<HttpBodyResponse<SubDetailResponse>> create(@RequestBody SubCreation request) {
        return responseCreated(mapper.fromDetail(service.create(request, new MetaData())));
    }

}
