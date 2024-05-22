package com.yujing.medicine.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/16 13:51
 * 添加更新时间自动填充
*/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("created_at", LocalDateTime.now(), metaObject);

    }
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName( "updated_at", LocalDateTime.now(), metaObject);
    }
}
