package org.project.subscriptionservice.share.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Optional;


@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class HttpBodyPagingResponse {

    public static final String SIZE = "size";
    public static final String PAGE = "list";
    public static final String TOTAL = "total";
    public static final String TOTAL_PAGES = "totalPages";

    private int page;
    private int size;
    private long total;
    private int totalPages;

    public static HttpBodyPagingResponse from(Map<String, Object> parameters) {
        if (Optional.ofNullable(parameters).map(Map::isEmpty).orElse(Boolean.TRUE)) return null;

        return HttpBodyPagingResponse.builder()
            .size((Integer) parameters.getOrDefault(HttpBodyPagingResponse.SIZE, 0))
            .page((Integer) parameters.getOrDefault(HttpBodyPagingResponse.PAGE, 0))
            .total((Integer) parameters.getOrDefault(HttpBodyPagingResponse.TOTAL, 0))
            .totalPages((Integer) parameters.getOrDefault(HttpBodyPagingResponse.TOTAL_PAGES, 1))
            .build();
    }

}
