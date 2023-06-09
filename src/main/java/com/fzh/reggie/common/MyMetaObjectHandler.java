package com.fzh.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/7 22:03
 * @description 自定义元数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入操作自动填充
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        log.info(metaObject.toString());

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        //在这个类中是不能直接获得HttpSession的,所以我们需要通过其他的方法来获取登录用户的ID
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * 修改自动填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id:{}", id);

        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
