 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.Areas;

import java.util.List;
import java.util.Optional;

 /**
 * 地区数据处理层
 * @author dsh
 */
public interface AreasDao extends XbootBaseDao<Areas, String> {
    List<Areas> findByCityid(String cityId);

    Optional<Areas> findByAreaid(String areasId);
}