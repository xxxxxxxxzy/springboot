package com.example.back.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author 小心卓越
 * @since 2024-04-04
 */
@Getter
@Setter
  @TableName("sys_food")
@ApiModel(value = "Food对象", description = "ID")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("ID")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("美食名称")
      private String foodname;

      @ApiModelProperty("美食简介")
      private String foodintroduce;

      @ApiModelProperty("美食做法")
      private String foodmethod;

      @ApiModelProperty("美食图片")
      private String foodpicture;


}
