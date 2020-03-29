package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Where(clause = "del_flag = 0")
@Table(name = "t_order")
public class Order extends XbootBaseEntity {

    private String userId;

    private Integer status = 0;

    private BigDecimal orderPrice = new BigDecimal("0.00");

    private String lineId;

}
