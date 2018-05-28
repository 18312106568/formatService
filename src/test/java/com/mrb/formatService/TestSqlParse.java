package com.mrb.formatService;


import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.mrb.formatservice.core.SqlParser;
import com.mrb.formatservice.model.dto.FieldDto;

public class TestSqlParse extends FormatApplicationTest {
	
	@Resource
	SqlParser sqlParse;
	
	@Test
	public void testField() {
		String sql = "CREATE TABLE `TB_TAXPAYMASTER_RESET_LOG` (\r\n" + 
				"  `ID` varchar(50) NOT NULL COMMENT '唯一标识',\r\n" + 
				"  `TAXPAYMASTER_ID` varchar(50) NOT NULL COMMENT '税单ID',\r\n" + 
				"  `ORIGIN_STATUS` varchar(15) NOT NULL COMMENT '原状态',\r\n" + 
				"  `LOAN_ID` varchar(50) DEFAULT NULL COMMENT '原借款ID',\r\n" + 
				"  `RESULT_CODE` varchar(20) DEFAULT NULL COMMENT '原结果码',\r\n" + 
				"  `REASON` varchar(100) DEFAULT NULL COMMENT '原理由',\r\n" + 
				"  `OPERATOR_NAME` varchar(20) NOT NULL COMMENT '操作人',\r\n" + 
				"  `GEN_DATE` datetime NOT NULL COMMENT '操作时间',\r\n" + 
				"  PRIMARY KEY (`ID`)\r\n" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		List<FieldDto> fieldDtoList=sqlParse.getSqlFieldDtos(sql);
                System.out.println(fieldDtoList.size());
		for(FieldDto dto : fieldDtoList) {
			System.out.println(dto);
		}
		
	}
}
