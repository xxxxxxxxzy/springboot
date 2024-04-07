package com.example.back.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back.entity.Food;
import com.example.back.service.IFoodService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小心卓越
 * @since 2024-04-04
 */
@RestController
@RequestMapping("/food")
public class FoodController {
@Resource
private IFoodService foodService;

//新增和修改
@PostMapping
public Boolean save(@RequestBody Food food){   //前台传入JSON数据，可以映射成user对象
    return foodService.saveOrUpdate(food); //新增或者更新
}
//刪除
@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id) {
    return foodService.removeById(id);
}
//查詢所有
@GetMapping
public List<Food> findAll() {
    return foodService.list();
}
//分页查询
@GetMapping("/page")
public Page<Food> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
    QueryWrapper<Food> queryWrapper = new QueryWrapper<>();
    queryWrapper.orderByDesc("id");
    return foodService.page(new Page<>(pageNum, pageSize),queryWrapper);
}
    @PostMapping("/del/batch")
    public Boolean deleteBatch(@RequestBody List<Integer> ids){
        return foodService.removeBatchByIds(ids);

    }

}


