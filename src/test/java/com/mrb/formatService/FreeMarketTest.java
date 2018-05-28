package com.mrb.formatService;

import com.mrb.formatService.FormatApplicationTest;
import com.mrb.formatservice.core.EntityBuilder;
import com.mrb.formatservice.core.SqlParser;
import com.mrb.formatservice.model.dto.BeanDto;
import com.mrb.formatservice.model.dto.FieldDto;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.junit.Test;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class FreeMarketTest extends FormatApplicationTest {

    final String TEMP_DIR = "/tmp";

    @Autowired
    private Configuration cfg;

    @Autowired
    SqlParser sqlParse;
    
    @Autowired
    EntityBuilder entityBuilder;

    @Test
    public void testChangePostfix() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {

        String sql = "CREATE TABLE `TB_TAXPAYMASTER_RESET_LOG` (\r\n"
                + "  `ID` varchar(50) NOT NULL COMMENT '唯一标识',\r\n"
                + "  `TAXPAYMASTER_ID` varchar(50) NOT NULL COMMENT '税单ID',\r\n"
                + "  `ORIGIN_STATUS` varchar(15) NOT NULL COMMENT '原状态',\r\n"
                + "  `LOAN_ID` varchar(50) DEFAULT NULL COMMENT '原借款ID',\r\n"
                + "  `RESULT_CODE` varchar(20) DEFAULT NULL COMMENT '原结果码',\r\n"
                + "  `REASON` varchar(100) DEFAULT NULL COMMENT '原理由',\r\n"
                + "  `OPERATOR_NAME` varchar(20) NOT NULL COMMENT '操作人',\r\n"
                + "  `GEN_DATE` datetime NOT NULL COMMENT '操作时间',\r\n"
                + "  PRIMARY KEY (`ID`)\r\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        List<FieldDto> fieldDtoList = sqlParse.getSqlFieldDtos(sql);
//        System.out.println(fieldDtoList.size());
//        for (FieldDto dto : fieldDtoList) {
//            System.out.println(dto);
//        }
        String tableName = sqlParse.getTableName(sql);
        String className = sqlParse.getClassName(tableName);
        BeanDto bean = new BeanDto();
        bean.setPackageName("com.mrb");
        bean.setClassName(className);
        bean.setTableName(tableName);
        bean.setFieldList(fieldDtoList);
        Template temp = cfg.getTemplate("bean.ftl");
        Map<String, Object> root = new HashMap<>();
        root.put("bean", bean);
        String fileName = className.concat(".java");
        OutputStream fos = new FileOutputStream(new File(TEMP_DIR, fileName));
        Writer out = new OutputStreamWriter(fos);
        temp.process(root, out);
        fos.flush();
        fos.close();
    }
    
    @Test
    public void testEntityBuilder(){
        String sql = "CREATE TABLE `TB_TAXPAYMASTER_RESET_LOG` (\r\n"
                + "  `ID` varchar(50) NOT NULL COMMENT '唯一标识',\r\n"
                + "  `TAXPAYMASTER_ID` varchar(50) NOT NULL COMMENT '税单ID',\r\n"
                + "  `ORIGIN_STATUS` varchar(15) NOT NULL COMMENT '原状态',\r\n"
                + "  `LOAN_ID` varchar(50) DEFAULT NULL COMMENT '原借款ID',\r\n"
                + "  `RESULT_CODE` varchar(20) DEFAULT NULL COMMENT '原结果码',\r\n"
                + "  `REASON` varchar(100) DEFAULT NULL COMMENT '原理由',\r\n"
                + "  `OPERATOR_NAME` varchar(20) NOT NULL COMMENT '操作人',\r\n"
                + "  `GEN_DATE` datetime NOT NULL COMMENT '操作时间',\r\n"
                + "  PRIMARY KEY (`ID`)\r\n"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        entityBuilder.buildEntity(sql);
    }
}
