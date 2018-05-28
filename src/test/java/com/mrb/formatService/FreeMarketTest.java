package org.dark;

import com.mrb.formatService.FormatApplicationTest;
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

public class FreeMarketTest extends FormatApplicationTest {
	final String TEMP_DIR = "/tmp";
	
	@Resource
    private Configuration cfg;
	
	@Test
	public void testChangePostfix() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		 Template temp = cfg.getTemplate("bean.ftl");
		 Map<String, Object> root = new HashMap<>();
            root.put("packageName", "com.mrb");
            root.put("tableName","test");
            root.put("className", "Test");
            root.put("columns", new ArrayList());
            String fileName = "Test".concat(".java");
            OutputStream fos = new FileOutputStream(new File(TEMP_DIR, fileName));
            Writer out = new OutputStreamWriter(fos);
            temp.process(root, out);
            fos.flush();
            fos.close();
	}
}
