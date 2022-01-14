package com.fzh.reggie.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fzh.reggie.entity.AddressBook;
import com.fzh.reggie.mapper.AddressBookMapper;
import com.fzh.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author 房忠瀚
 * @version 1
 * @date 2022/1/14 0:17
 * @description 1
 */

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
