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
    public void testEntityBuilder() {
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

    @Test
    public void testBuilderModel() {
        StringBuilder sql = new StringBuilder("SELECT * FROM \n");
        sql.append("(SELECT lr.CAP_REQUEST_NO as capRequestNo,")
                .append("cb.ORG_CODE as orgCode,")
                .append("cb.AUTHORIZE_NO as authorizeNo,")
                .append("cb.`NAME` as corName,")
                .append("cb.ORG_CUSTOMS_CODE as masterCustoms,")
                .append("(l.DAYS+l.MONTHS*30+l.YEARS*365) as loanDays, ")
                .append("l.AMOUNT as loanAmout,")
                .append("l.TIMESETTLED as loanTimeSettled ,")
                .append("l.METHOD as repayMethod,")
                .append("	l.`STATUS` as loanStatus,")
                .append("lr.DUEDATE as dueDate,")
                .append("lr.REPAYDATE as repayDate,")
                .append("lr.REPAYAMOUNT as repayAmount,")
                .append("lo.ORDERID as loanReceipt,")
                .append("sro.serno as repayReceipt,stm.statementNos as statementNos\n")
                .append("FROM\n")
                .append("	TB_LOAN_REPAYMENT lr\n")
                .append("INNER JOIN \n")
                .append("	TB_LOAN l ON lr.LOAN_ID = l.ID\n")
                .append("LEFT JOIN \n")
                .append("(SELECT GROUP_CONCAT(ro.RTRX_SERNO) serno ,ro.LOANREPAYID FROM TB_REPAY_ORDER ro\n")
                .append(" where ro.STAT = 'P' GROUP BY ro.LOANREPAYID ) sro on sro.LOANREPAYID = lr.ID\n")
                .append("LEFT JOIN\n")
                .append(" TB_LOAN_ORDER lo ON lo.LOANID = l.ID \n")
                .append("LEFT JOIN \n")
                .append("	TB_USER u ON l.USER_ID = u.ID\n")
                .append("LEFT JOIN\n")
                .append("	TB_CORPORATION_BASE cb ON cb.ORG_CODE = u.ORG_CODE\n")
                .append("LEFT JOIN \n")
                .append("(select GROUP_CONCAT(tm.STATEMENT_NO) statementNos,tm.LOANID from TB_TAXPAYMASTER tm GROUP BY tm.LOANID) stm ON stm.LOANID = l.ID ")
                .append(")t Limit 0 ,1");
        entityBuilder.buildModel(sql.toString(), "com.mrb");
    }
}
