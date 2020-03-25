package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Where(clause = "del_flag = 0")
@Table(name = "t_line_daily_detail")
public class LineDailyDetail extends XbootBaseEntity {
    @ApiModelProperty("线路id")
    private String lineId;

    @ApiModelProperty("天")
    private String day;

    @ApiModelProperty("当天标题")
    private String dayTitle;

    @ApiModelProperty("具体行程")
    private String specificJourney;

    @ApiModelProperty("早餐")
    private String breakfast;

    @ApiModelProperty("午餐")
    private String lunch;

    @ApiModelProperty("晚餐")
    private String dinner;

    @ApiModelProperty("交通")
    private String traffic;

    @ApiModelProperty("导游")
    private String guide;

    @ApiModelProperty("酒店")
    private String hotel;
}
