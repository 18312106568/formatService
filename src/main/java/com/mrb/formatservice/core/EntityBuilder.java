/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrb.formatservice.core;

import com.mrb.formatservice.model.dto.BeanDto;
import com.mrb.formatservice.model.dto.FieldDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author MRB
 */
@Slf4j
@Component
public class EntityBuilder {
    
    @Autowired
    private Configuration cfg;

    @Autowired
    SqlParser sqlParse;
    
    final String BEAN_FTL = "bean.ftl";
    
    final String JAVA_FIX = ".java";
    
    final String TEMP_DIR = "/tmp";
    
    final String PACKAGE = "com.mrb";
    
    public void buildEntity(String sql){
        buildEntity(sql,PACKAGE);
    }
    
    public void buildEntity(String sql,String packageName){
        List<FieldDto> fieldDtoList = sqlParse.getSqlFieldDtos(sql);
        String tableName = sqlParse.getTableName(sql);
        String className = sqlParse.getClassName(tableName);
        BeanDto bean = new BeanDto();
        bean.setPackageName(packageName);
        bean.setClassName(className);
        bean.setTableName(tableName);
        bean.setFieldList(fieldDtoList);
        try{
            Template temp = cfg.getTemplate(BEAN_FTL);
            Map<String, Object> root = new HashMap<>();
            root.put("bean", bean);
            String fileName = className.concat(JAVA_FIX);
            OutputStream fos = new FileOutputStream(new File(TEMP_DIR, fileName));
            Writer out = new OutputStreamWriter(fos);
            temp.process(root, out);
            fos.flush();
            fos.close();
        }catch(Exception e){
            log.error("create entity error :{}",e.toString());
        }finally{
            
        }
        
    }
    
}
