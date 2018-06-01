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

        String sql = "CREATE TABLE `TB_YHX_DECLARATION` (\n"
                + "  `ID` varchar(50) NOT NULL,\n"
                + "  `IMPORT_EXPORT_FLAG` varchar(2) DEFAULT NULL COMMENT '进出口标示 I-进口，E-出口',\n"
                + "  `TYPE` varchar(20) DEFAULT NULL COMMENT '业务类型',\n"
                + "  `DECLEAR_LOCATION` varchar(255) DEFAULT NULL COMMENT '申报地海关',\n"
                + "  `CUSTOM` varchar(255) DEFAULT NULL COMMENT '出口口岸',\n"
                + "  `COMPANY_SOCIAL_CODE` varchar(50) DEFAULT NULL COMMENT '收发货人编码',\n"
                + "  `COMPANY_NAME` varchar(255) DEFAULT NULL COMMENT '收发货人名称',\n"
                + "  `ENTERPRISE` varchar(20) DEFAULT NULL COMMENT '企业性质',\n"
                + "  `COMPANY2_SOCIAL_CODE` varchar(50) DEFAULT NULL COMMENT '生产销售单位编号',\n"
                + "  `COMPANY2_NAME` varchar(255) DEFAULT NULL COMMENT '生产销售单位名称',\n"
                + "  `REPORT_COMPANY_SOCIAL_CODE` varchar(50) DEFAULT NULL COMMENT '申报单位编码',\n"
                + "  `REPORT_COMPANY_NAME` varchar(255) DEFAULT NULL COMMENT '申报单位名称',\n"
                + "  `TRANSPORT_TYPE` varchar(50) DEFAULT NULL COMMENT '运输方式',\n"
                + "  `TRANSPORT_TOOL` varchar(255) DEFAULT NULL COMMENT '运输工具名称',\n"
                + "  `TRANSPORT_NUM` varchar(50) DEFAULT NULL COMMENT '航次号',\n"
                + "  `BLNO` varchar(50) DEFAULT NULL COMMENT '提运单号',\n"
                + "  `JIANGUAN` varchar(50) DEFAULT NULL COMMENT '监管方式',\n"
                + "  `TAX` varchar(50) DEFAULT NULL COMMENT '征免性质',\n"
                + "  `PAYMENT` decimal(15,2) DEFAULT NULL COMMENT '征税比例',\n"
                + "  `NSDW` varchar(50) DEFAULT NULL COMMENT '纳税单位',\n"
                + "  `COUNTRY` varchar(50) DEFAULT NULL COMMENT '运抵国（地区）',\n"
                + "  `HARBOUR` varchar(50) DEFAULT NULL COMMENT '指运港',\n"
                + "  `CHURCHYARD` varchar(50) DEFAULT NULL COMMENT '境内货源地',\n"
                + "  `DEAL_MODE` varchar(50) DEFAULT NULL COMMENT '成交方式',\n"
                + "  `CARRIAGE_TYPE` varchar(50) DEFAULT NULL COMMENT '运费类型',\n"
                + "  `CARRIAGE_PRICE` decimal(15,2) DEFAULT NULL COMMENT '运费金额',\n"
                + "  `CARRIAGE_CURRENCY` varchar(20) DEFAULT NULL COMMENT '运费币制',\n"
                + "  `PREMIUM_TYPE` varchar(50) DEFAULT NULL COMMENT '保费类型',\n"
                + "  `PREMIUM_PRICE` varchar(50) DEFAULT NULL COMMENT '保费金额',\n"
                + "  `PREMIUM_CURRENCY` varchar(20) DEFAULT NULL COMMENT '保费币制',\n"
                + "  `BOX_NUM` int(4) DEFAULT NULL COMMENT '件数',\n"
                + "  `BOX_TYPE` varchar(50) DEFAULT NULL COMMENT '包装种类',\n"
                + "  `GROSS_WEIGHT` int(4) DEFAULT NULL COMMENT '毛重(KG)',\n"
                + "  `NET_WEIGHT` int(4) DEFAULT NULL COMMENT '净重(KG)',\n"
                + "  `TRADING_COUNTRY` varchar(50) DEFAULT NULL COMMENT '贸易国(地区)',\n"
                + "  `RELATIVE_ENTRY_NO` varchar(50) DEFAULT NULL COMMENT '关联报关单号',\n"
                + "  `RELATIVE_ENROL_NO` varchar(50) DEFAULT NULL COMMENT '关联备案号',\n"
                + "  `BSJG_PLACE` varchar(50) DEFAULT NULL COMMENT '保税/监管场所',\n"
                + "  `YARD_CODE` varchar(50) DEFAULT NULL COMMENT '货场代码',\n"
                + "  `PRERECORD_SN` varchar(50) DEFAULT NULL COMMENT '报关单号',\n"
                + "  `PRERECORD_SN_TIME` datetime DEFAULT NULL COMMENT '报关单申报时间',\n"
                + "  PRIMARY KEY (`ID`)\n"
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
        String sql = "CREATE TABLE TB_YHX_DECLARATION_RECORD(\n"
                + "	ID varchar(50),\n"
                + "	ORG_CUSTOMS_CODE varchar(10) COMMENT '海关十位编码',\n"
                + "	CREATE_TIME datetime COMMENT '易航线注册时间',\n"
                + "	EDIT_TIME datetime COMMENT '报关单最后同步时间',\n"
                + "	PRIMARY KEY (`ID`)\n"
                + ");";
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
