/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrb.formatservice.model.dto;

import java.io.Serializable;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
@AllArgsConstructor
public class BeanDto implements Serializable{
	/**
     * 类名
     */
    private String clazzName;

    /**
     * 表名
     */
    private String tableName;

    /**
     * 属性列表
     */
    private List<FieldDto> columnList;
}
