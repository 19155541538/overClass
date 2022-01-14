package com.fzh.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fzh.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 0:14
 * @description 地址管理的 持久层
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
