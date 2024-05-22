package com.yujing.medicine.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 审计日志-后台操作记录
 * @TableName audit_admin_log
 */
@TableName(value ="audit_admin_log")
@Data
public class AuditAdminLog implements Serializable {
    /**
     * 日志id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 请求URL
     */
    private String uri;

    /**
     * 请求类型（GET,POST）
     */
    private String methodType;

    /**
     * 目标方法名
     */
    private String methodName;

    /**
     * 接口介绍
     */
    private String methodDesc;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 请求状态
     */
    private String status;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 操作者id
     */
    private Long userId;

    /**
     * 方法耗时（ms）
     */
    private Long executionTime;

    /**
     * 操作时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AuditAdminLog other = (AuditAdminLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getUri() == null ? other.getUri() == null : this.getUri().equals(other.getUri()))
            && (this.getMethodType() == null ? other.getMethodType() == null : this.getMethodType().equals(other.getMethodType()))
            && (this.getMethodName() == null ? other.getMethodName() == null : this.getMethodName().equals(other.getMethodName()))
            && (this.getMethodDesc() == null ? other.getMethodDesc() == null : this.getMethodDesc().equals(other.getMethodDesc()))
            && (this.getRequestParam() == null ? other.getRequestParam() == null : this.getRequestParam().equals(other.getRequestParam()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getResult() == null ? other.getResult() == null : this.getResult().equals(other.getResult()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getExecutionTime() == null ? other.getExecutionTime() == null : this.getExecutionTime().equals(other.getExecutionTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getUri() == null) ? 0 : getUri().hashCode());
        result = prime * result + ((getMethodType() == null) ? 0 : getMethodType().hashCode());
        result = prime * result + ((getMethodName() == null) ? 0 : getMethodName().hashCode());
        result = prime * result + ((getMethodDesc() == null) ? 0 : getMethodDesc().hashCode());
        result = prime * result + ((getRequestParam() == null) ? 0 : getRequestParam().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getResult() == null) ? 0 : getResult().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getExecutionTime() == null) ? 0 : getExecutionTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", ip=").append(ip);
        sb.append(", uri=").append(uri);
        sb.append(", methodType=").append(methodType);
        sb.append(", methodName=").append(methodName);
        sb.append(", methodDesc=").append(methodDesc);
        sb.append(", requestParam=").append(requestParam);
        sb.append(", status=").append(status);
        sb.append(", result=").append(result);
        sb.append(", userId=").append(userId);
        sb.append(", executionTime=").append(executionTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}