package com.mrb.formatservice.enums;

public enum SqlTypeMapped {
	
	TINYINT("tinyint","Integer"),
	INT("int","Integer"),
	VARCHAR("varchar","String"),
	BIGDECIMAL("decimal","BigDecimal"),
	DATETIME("datetime","Date")
	;
	
	private String key;
	
	private String className;
	
	private SqlTypeMapped(String key,String className) {
		this.key = key;
		this.className = className;
		
	}

	public String getKey() {
		return key;
	}
	
	public static String getClassName(String typeName) {
		SqlTypeMapped[] mappeds = SqlTypeMapped.values();
		for(SqlTypeMapped mapped:mappeds) {
			if(typeName.contains(mapped.key)) {
				return mapped.className;
			}
		}
		return VARCHAR.className;
	}
	
	
}
