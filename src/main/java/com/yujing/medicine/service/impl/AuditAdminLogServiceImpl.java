package com.yujing.medicine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yujing.medicine.pojo.AuditAdminLog;
import com.yujing.medicine.service.AuditAdminLogService;
import com.yujing.medicine.mapper.AuditAdminLogMapper;
import org.springframework.stereotype.Service;

/**
* @author Acer
* @description 针对表【audit_admin_log(审计日志-后台操作记录)】的数据库操作Service实现
* @createDate 2024-05-14 18:15:15
*/
@Service
public class AuditAdminLogServiceImpl extends ServiceImpl<AuditAdminLogMapper, AuditAdminLog>
    implements AuditAdminLogService{

}




