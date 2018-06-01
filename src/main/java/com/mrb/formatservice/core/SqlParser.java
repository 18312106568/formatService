package com.mrb.formatservice.core;

import com.mrb.formatservice.enums.SqlTypeMapped;
import com.mrb.formatservice.model.dto.FieldDto;
import com.mrb.formatservice.utils.ConverUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL 建表语句解析器
 *
 * @author MRB
 * @date 2018/5/28 下午16:53
 */
@Component
@Slf4j
public class SqlParser {

    /**
     * 匹配空格缩进换行
     */
    final String CLEARANCE = "(\\s*|\t*)((\r\n)|\n)(\\s*|\t*)";

    /**
     * 匹配属性符号
     */
    final String SYMBOL = "`";

    final String PRIMARY_KEY = "primary";

    final String UNIQUE = "unique";

    final String LBRACK = "(";

    final String RBRACK = ")";

    final String COMMENT = "comment";
    
    final String PREFIX = "TB_";

    /**
     * 空格缩进换行去除
     *
     * @param sql
     * @return
     */
    private String sqlClearance(String sql) {
        sql = sql.trim().toLowerCase()
                .replaceAll(SYMBOL, "")
                .replaceAll(CLEARANCE, "");
        return sql.substring(sql.indexOf(LBRACK) + 1, sql.lastIndexOf(RBRACK));
    }
    
    /**
     * 获取表名
     * @param sql
     * @return 
     */
    public String getTableName(String sql){
        sql = sql.substring(0,sql.indexOf(LBRACK));
        String[] sqlArr = sql.split(" ");
        return sqlArr[2].replaceAll(SYMBOL, "");
    }
    
    public String getClassName(String tableName){
        String className = ConverUtils.upperCase(ConverUtils.changeHungToCame(
                        tableName.replace(PREFIX, "").toLowerCase()));
        return className;
    }

    public List<FieldDto> getSqlFieldDtos(String sql) {
        List<FieldDto> fieldDtoList = new ArrayList();
        sql = sqlClearance(sql);
        String[] sqlArr = sql.split(",");
        for (String column : sqlArr) {
            String[] columnArr = column.split(" ");
            if (columnArr[0] != null && (columnArr[0].
                    equals(PRIMARY_KEY) || columnArr[0].equals(UNIQUE))) {
                continue;
            }
            FieldDto dto = new FieldDto();
            dto.setAnnColumn(columnArr[0].toUpperCase());
            dto.setName(ConverUtils.changeHungToCame(columnArr[0]));
            dto.setType(SqlTypeMapped.getClassName(columnArr[1]));
            if (column.contains(COMMENT)) {
                String comment = column.substring(column.indexOf(COMMENT), column.length() - 1);
                String[] commentEntity = comment.split(" ");
                dto.setComment(commentEntity[1].replaceAll("'", ""));
            }
            fieldDtoList.add(dto);
        }
        return fieldDtoList;
    }

}
