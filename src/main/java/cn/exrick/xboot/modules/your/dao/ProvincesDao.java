 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.Provinces;

import java.util.List;
import java.util.Optional;

 /**
 * 地区数据处理层
 * @author dsh
 */
public interface ProvincesDao extends XbootBaseDao<Provinces, String> {
    Optional<Provinces> findByProvinceid(String provincesId);
}