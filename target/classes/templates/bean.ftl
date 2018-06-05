package ${bean.packageName};

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;


@Data
@Entity
@Table(name = "${bean.tableName}")
public class ${bean.className} extends EntityId{

    <#list bean.fieldList as field>
    <#if (field.comment)?? && field.comment != ''>
    /**
    *  ${field.comment}
    */
    </#if>
    @Column(name = "${field.annColumn}")
    <#if (field.type)?? && field.type == 'Date'>
    @Temporal(TemporalType.TIMESTAMP)
    </#if>
    private ${field.type} ${field.name};

    </#list>
}

