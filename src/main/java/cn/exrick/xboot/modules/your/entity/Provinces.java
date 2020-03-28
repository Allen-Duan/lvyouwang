package cn.exrick.xboot.modules.your.entity;

import cn.exrick.xboot.base.XbootBaseEntity;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Where(clause = "del_flag = 0")
@Table(name = "provinces")
public class Provinces extends XbootBaseEntity {
    private String provinceid;
    private String province;


}
