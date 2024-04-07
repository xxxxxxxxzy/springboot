package com.example.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * ID
 * </p>
 *
 * @author 小心卓越
 * @since 2024-04-02
 */
@Getter
@Setter
  @TableName("sys_user")
@ApiModel(value = "User对象", description = "ID")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("用户名")
      private String username;

      @ApiModelProperty("密码")
      private String passward;

      @ApiModelProperty("邮箱")
      private String email;

      @ApiModelProperty("QQ")
      private String qq;

      @ApiModelProperty("微信")
      private String wechat;

      @ApiModelProperty("电话")
      private String phone;

      @ApiModelProperty("创建时间")
      private LocalDateTime createTime;



}
