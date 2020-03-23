package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Where(clause = "del_flag = 0")
@Table(name = "areas")
public class Areas extends XbootBaseEntity {
    private String areaid;
    private String area;
    private String cityid;


}
