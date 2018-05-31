/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrb.formatservice.model.dto;

import java.util.List;
import lombok.Data;

/**
 *
 * @author MRB
 */
@Data
public class ModelDto {
     /**
     * 包名
     */
    private String packageName;
    /**
     * 类名
     */
    private String className;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 属性列表
     */
    private List<FieldDto> fieldList;
}
