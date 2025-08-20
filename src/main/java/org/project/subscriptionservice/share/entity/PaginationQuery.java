package org.project.subscriptionservice.share.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Builder
public class PaginationQuery {

    private Integer page;
    private Integer size;
    private String keyword;

    public Integer getPage() {
        return Objects.nonNull(page) ? page : 1;
    }

    public Integer getSize() {
        return Objects.nonNull(size) ? size : 20;
    }

}
