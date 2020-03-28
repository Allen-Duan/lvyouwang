 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.Line;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 旅游线路数据处理层
 * @author dsh
 */
public interface LineDao extends XbootBaseDao<Line, String> {
    @Query(value = "select * from t_line where line_title like ?1 and del_flag = 0 ",nativeQuery = true)
    List<Line> findAllByLineTitleLike(String name);
}