package ${model.packageName};

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import lombok.Data;

/**
 *
 * @author ${model.authorName}
 */
@Data
@NoArgsConstructor
public class ${model.className} implements Serializable {

    <#list model.fieldList as field>
    <#if (field.comment)?? && field.comment != ''>
    /**
    *  ${field.comment}
    */
    </#if>
    <#if (field.annJsonProperty)?? && field.annJsonProperty != ''>
        @JsonProperty(name = "${field.annJsonProperty}")
    </#if>
    private ${field.type} ${field.name};

    </#list>
}

