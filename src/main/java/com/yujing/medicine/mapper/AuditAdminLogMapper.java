package com.yujing.medicine.mapper;

import com.yujing.medicine.pojo.AuditAdminLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Acer
* @description 针对表【audit_admin_log(审计日志-后台操作记录)】的数据库操作Mapper
* @createDate 2024-05-14 18:15:15
* @Entity com.yujing.medicine.pojo.AuditAdminLog
*/
@Mapper
public interface AuditAdminLogMapper extends BaseMapper<AuditAdminLog> {

}




