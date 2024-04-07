package com.example.back.controller;


import cn.hutool.core.annotation.Alias;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.back.controller.dto.UserDTO;
import com.example.back.entity.User;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.beans.Encoder;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.example.back.service.IUserService;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.yaml.snakeyaml.events.Event.ID.Alias;

/**
 * <p>
 * ID 前端控制器
 * </p>
 *
 * @author 小心卓越
 * @since 2024-04-02
 */
@RestController
@RequestMapping("/user")
public class UserController {
@Resource
private IUserService userService;

@PostMapping("/login")
public Boolean login(@RequestBody UserDTO userDTO){
    String username = userDTO.getUsername();
    String passward = userDTO.getPassward();
    if (StrUtil.isBlank(username) || StrUtil.isBlank(passward)){
        return false;
    }
    return userService.login(userDTO);
}
//新增和修改
@PostMapping
public Boolean save(@RequestBody User user){   //前台传入JSON数据，可以映射成user对象
    return userService.saveOrUpdate(user); //新增或者更新
}
//刪除
@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id) {
    return userService.removeById(id);
}
//查詢所有
@GetMapping
public List<User> findAll() {
    return userService.list();
}
//根据id查询
@GetMapping("/{id}")
public User findOne(@PathVariable Integer id) {
    return userService.getById(id);
}
//分页查询
@GetMapping("/page")
public Page<User> findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam (defaultValue = "") String username,
                           @RequestParam (defaultValue = "") String phone) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
    if (!"".equals(username)) {
        queryWrapper.like("username", username);
    }
    if (!"".equals(phone)) {
        queryWrapper.like("phone", phone);
    }
    queryWrapper.orderByDesc("id");
    return userService.page(new Page<>(pageNum, pageSize),queryWrapper);
}

//批量删除
@PostMapping("/del/batch")
public Boolean deleteBatch(@RequestBody List<Integer> ids){
    return userService.removeBatchByIds(ids);

}

/*excel导出*/
@GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
    //从数据库查询所有数据
    List<User> list = userService.list();
    //通过工具类创建writer写出到磁盘路径
    //在内存操作，写出到浏览器
    ExcelWriter writer = ExcelUtil.getWriter(true);
    writer.addHeaderAlias("id","ID");
    writer.addHeaderAlias("username","用户名");
    writer.addHeaderAlias("passward","密码");
    writer.addHeaderAlias("email","邮箱");
    writer.addHeaderAlias("qq","QQ号");
    writer.addHeaderAlias("wechat","微信");
    writer.addHeaderAlias("phone","电话号码");
    writer.addHeaderAlias("creatTime","创建时间");
    //一次性写出list里的对象到excel
    writer.write(list,true);

    //设置浏览器响应的格式
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
    String fileName = URLEncoder.encode("用户信息","UTF-8");
    response.setHeader("Content-Disposition","attachment;filename=" + fileName + ".xlsx");

    ServletOutputStream out = response.getOutputStream();
    writer.flush(out,true);
    out.close();
    writer.close();

}
/*导入接口*/
    @PostMapping("/import")
    public Boolean imp(MultipartFile file)throws Exception{
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
//        //        List<User> list = reader.readAll(User.class);
//        List<User> list = reader.read(0,1,User.class);
        List<List<java.lang.Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> row:list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassward(row.get(1).toString());
            user.setEmail(row.get(2).toString());
            user.setQq(row.get(3).toString());
            user.setWechat(row.get(4).toString());
            user.setPhone(row.get(5).toString());
            users.add(user);
        }
        userService.saveBatch(users);
        return true;
    }
}


