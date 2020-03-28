 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.Line;

import java.util.List;

/**
 * 旅游线路数据处理层
 * @author dsh
 */
public interface LineDao extends XbootBaseDao<Line, String> {
    List<Line> findAllByLineTitleLike(String name);
}