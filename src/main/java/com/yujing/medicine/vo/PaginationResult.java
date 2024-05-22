package com.yujing.medicine.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/20 16:21
 * 创建分页工具类
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResult<T> {
    private long total; // 总记录数
    private long current; // 当前页码
    private long size; // 每页大小
    private List<T> records; // 记录列表
}
