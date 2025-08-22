package org.project.subscriptionservice.bean;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.JdbcType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @TableId(type = IdType.AUTO, value = "id")
    private Integer id;

    @CreatedDate
    @TableField(value = "created_at", fill = FieldFill.INSERT, jdbcType = JdbcType.TIMESTAMP)
    private Date createdAt;

    @LastModifiedDate
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.TIMESTAMP)
    private Date updatedAt;

    @TableField(value = "deleted_at", jdbcType = JdbcType.TIMESTAMP)
    private Date deletedAt;

    @CreatedBy
    @TableField("created_by")
    private String createdBy;

    @LastModifiedBy
    @TableField("updated_by")
    private String updatedBy;

    @TableField("deleted_by")
    private String deletedBy;

}
