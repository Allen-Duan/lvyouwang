package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
@Entity
@Data
@Where(clause = "del_flag = 0")
@Table(name = "t_tourism_customization")
public class TourismCustomization extends XbootBaseEntity {
    private String userId;
    private String departureProvinceId;
    private String departureCityId;
    private String arriveProvinceId;
    private String adultNo;
    private String childrenNo;
    private String arriveCityId;
    private Date departureTime;
    private Date arriveTime;
    private String perCapitaCost;
}
