package com.fzh.reggie.dto;

import com.fzh.reggie.entity.Setmeal;
import com.fzh.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
