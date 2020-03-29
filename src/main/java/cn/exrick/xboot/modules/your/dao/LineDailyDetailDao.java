 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.LineDailyDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 旅游线路详情数据处理层
 * @author dsh
 */
public interface LineDailyDetailDao extends XbootBaseDao<LineDailyDetail, String> {

    @Query(value = "select * from t_line_daily_detail where line_id = ?1 and del_flag = 0 order by day ",nativeQuery = true)
    List<LineDailyDetail> getAllBylineId(String lineId);
}