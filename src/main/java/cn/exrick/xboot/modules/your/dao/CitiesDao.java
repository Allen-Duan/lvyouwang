 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.Cities;

import java.util.List;

/**
 * 地区数据处理层
 * @author dsh
 */
public interface CitiesDao extends XbootBaseDao<Cities, String> {
    List<Cities> findByProvinceid(String provinceId);
}