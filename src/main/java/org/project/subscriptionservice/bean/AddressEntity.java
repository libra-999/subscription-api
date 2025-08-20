package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressEntity {

    @TableField(value = "street")
    private String street;

    @TableField(value = "city")
    private String city;

    @TableField(value = "state")
    private String state;

    @TableField(value = "zip_code")
    private Integer zipCode;

    @TableField("country")
    private String country;

}
