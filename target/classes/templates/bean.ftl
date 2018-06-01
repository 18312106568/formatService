package ${bean.packageName};

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;


@Data
@Entity
@Table(name = "${bean.tableName}")
public class ${bean.className}{

    <#list bean.fieldList as field>
    <#if (field.comment)?? && field.comment != ''>
    /**
    *  ${field.comment}
    */
    </#if>
    @Column(name = "${field.annColumn}")
    private ${field.type} ${field.name};

    </#list>
}

