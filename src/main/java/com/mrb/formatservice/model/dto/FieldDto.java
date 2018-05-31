package com.mrb.formatservice.model.dto;

import lombok.Data;

@Data
public class FieldDto {
        /**
         * 属性类型
         */
	private String type;
	
        /**
         * 属性名
         */
	private String name;
	
        /**
         * 注释
         */
	private String comment;
        
        /**
         * column注解name值
         */
        private String annColumn;
        
        /**
         * JsonProperty注解name值
         */
        private String annJsonProperty;
}
