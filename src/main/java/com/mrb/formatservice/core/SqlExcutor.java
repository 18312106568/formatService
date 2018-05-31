/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrb.formatservice.core;

import com.mrb.formatservice.model.dto.FieldDto;
import com.mrb.formatservice.utils.ConverUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

/**
 *
 * @author MRB
 */
@Component
@Slf4j
public class SqlExcutor {

    /**
     * 匹配属性符号
     */
    final String SYMBOL = "`";
    
    final String TBNAME_INDEX = "FROM TB_";
    
    final String PREFIX = "TB_";    
    
    @PersistenceContext
    private EntityManager em;

    /**
     * 获取表名
     *
     * @param sql
     * @return
     */
    public String getTableName(String sql) {
        sql = sql.substring(sql.indexOf(TBNAME_INDEX));
        String[] sqlArr = sql.split(" ");
        return sqlArr[1].replaceAll(SYMBOL, "");
    }

    /**
     * 获取类名
     *
     * @param tableName
     * @return
     */
    public String getClassName(String tableName) {
        String className = ConverUtils.upperCase(ConverUtils.changeHungToCame(
                tableName.replace(PREFIX, "").toLowerCase()));
        return className;
    }
    
    public List<FieldDto> getSqlFieldDtos(String sql) {
        List<FieldDto> fieldDtoList = new ArrayList();
        Query query = em.createNativeQuery(sql);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);        
        List<Map> mapsList = query.getResultList();
        for (Map map : mapsList) {
            for (Object key : map.keySet()) {
                FieldDto dto = new FieldDto();
                dto.setName((String) key);
                if (map.get(key) != null) {
                    String className = map.get(key).getClass().getName();
                    dto.setType(className.substring(className.lastIndexOf(".") + 1));
                } else {
                    dto.setType("String");
                }
                fieldDtoList.add(dto);
            }
            break;
        }
        return fieldDtoList;
    }
    
}
