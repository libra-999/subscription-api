package org.project.subscriptionservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.controller.mapper.AuthControllerMapper;
import org.project.subscriptionservice.controller.request.UserCreation;
import org.project.subscriptionservice.controller.response.UserDetailResponse;
import org.project.subscriptionservice.controller.response.UserListResponse;
import org.project.subscriptionservice.domain.service.UserService;
import org.project.subscriptionservice.share.entity.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.project.subscriptionservice.share.controller.ControllerHandler.*;

@RequestMapping("/v1/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final AuthControllerMapper mapper;

    @GetMapping
    public ResponseEntity<HttpBodyResponse<List<UserListResponse>>> list(@RequestParam(required = false) Integer page,
                                                                         @RequestParam(required = false) Integer size,
                                                                         @RequestParam(required = false) String keyword) {

        Paging<UserEntity> paging = service.list(PaginationQuery.of(page, size, keyword), new MetaData());
        return responsePaging(
            paging.getItems().stream().map(mapper::toList).toList(),
            HttpBodyPagingResponse.of(
                paging.getPage(),
                paging.getSize(),
                paging.getTotal(),
                paging.getTotalPages()
            )
        );
    }

    @PostMapping
    public ResponseEntity<HttpBodyResponse<UserListResponse>> register(@Valid @RequestBody UserCreation request) {
        return responseCreated(mapper.toList(service.create(request,new MetaData())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpBodyResponse<UserDetailResponse>> view(@PathVariable Integer id){
        return responseSucceed(mapper.toDetail(service.view(id, new MetaData())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id){
        service.delete(id, new MetaData());
        return responseDeleted();
    }


}
