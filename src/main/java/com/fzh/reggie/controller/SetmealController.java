package com.fzh.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fzh.reggie.common.R;
import com.fzh.reggie.dto.SetmealDto;
import com.fzh.reggie.entity.Category;
import com.fzh.reggie.entity.Setmeal;
import com.fzh.reggie.entity.SetmealDish;
import com.fzh.reggie.service.CategoryService;
import com.fzh.reggie.service.SetmealDishService;
import com.fzh.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/12 10:20
 * @description 管理套餐
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增
     *
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> setmealSave(@RequestBody SetmealDto setmealDto) {
        log.info("套餐信息{}", setmealDto);
        setmealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }

    /**
     * 分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> DtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        //排序条件
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getCreateTime);

        //执行分页查询
        setmealService.page(pageInfo, setmealLambdaQueryWrapper);

        //对象拷贝 排除records
        BeanUtils.copyProperties(pageInfo, DtoPage, "records");

        List<Setmeal> records = pageInfo.getRecords(); // 获取records
/*

        //处理records
        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId = item.getCategoryId();//获取分类id
            Category category = categoryService.getById(categoryId);//根据分类id 查询数据

            if(category!=null){
                String name1 = category.getName();
                setmealDto.setCategoryName(name1);
            }

            return setmealDto;
        }).collect(Collectors.toList());
*/

        //使用for遍历的方法 遍历处理
        List<SetmealDto> list = new ArrayList<>();
        for (Setmeal record : records) {
            SetmealDto setmealDto = new SetmealDto();

            //拷贝的两个参数要类型相同,字段名相同,变量名形同  不然考不过去,,,还不报错
            BeanUtils.copyProperties(record, setmealDto);

            //获取分类id
            Long categoryId = record.getCategoryId();
            //通过id获取套餐种类
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                //获取分类实体类表中的 套餐名字
                String categoryName = category.getName();
                //把套餐名字 写进 setmealDto中
                setmealDto.setCategoryName(categoryName);
            }
            list.add(setmealDto);
        }

        //处理过后的records 写进DtoPage中
        DtoPage.setRecords(list);
        return R.success(DtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids ) {
        log.info("ids,{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list (Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        //查询条件  根据分类ID 比对一下 当前分类 是什么套餐 或者 什么菜品
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId())
                .eq(setmeal.getStatus()!=null ,Setmeal::getStatus, setmeal.getStatus())
                .orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);
        return R.success(list);
    }

    /**
     * 修改状态
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public  R<String> updateStatus (@PathVariable int status , Long[] ids){
        Setmeal setmeal = new Setmeal();
        setmeal.setStatus(status);

        for (Long id : ids) {
            LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
            setmealLambdaQueryWrapper.eq(Setmeal::getId, id);
            // 根据 setmealLambdaQueryWrapper 条件，更新记录
            setmealService.update(setmeal,setmealLambdaQueryWrapper);
        }

        return R.success("状态修改成功");
    }


    /**
     * 根据id查询数据进行数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){

        //先查询出套餐基本信息
        Setmeal setmeal = setmealService.getById(id);

        //再根据id查询出套餐对应的菜品信息
        LambdaQueryWrapper<SetmealDish> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> dishList = setmealDishService.list(wrapper);

        //创建SetmealDto对象，封装套餐和对饮的菜品信息
        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(dishList);
        return R.success(setmealDto);
    }


    /**
     * 更新套餐信息
     * @param setmealDto
     * @return
     */
    @PutMapping
//    @CacheEvict(value = "setmealCache" ,allEntries = true)
//    @ApiOperation(value = "更新套餐信息")
    public R<String> update(@RequestBody SetmealDto setmealDto){

        setmealService.updateWithDish(setmealDto);

        return R.success("修改成功");
    }
}
