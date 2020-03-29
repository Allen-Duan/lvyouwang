package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Where(clause = "del_flag = 0")
@Table(name = "t_line")
public class Line extends XbootBaseEntity {

    @ApiModelProperty("区id")
    private String areasId;

    @ApiModelProperty("市id")
    private String citiesId;

    @ApiModelProperty("省id")
    private String provincesId;

    @ApiModelProperty("旅游标题")
    private String lineTitle;

    @ApiModelProperty("旅游详情")
    private String lineText;

    @ApiModelProperty("旅游价格")
    private BigDecimal linePrice;

    private String image;

}
