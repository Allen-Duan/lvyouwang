package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.LineDailyDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 旅游线路详情接口
 * @author dsh
 */
public interface LineDailyDetailService extends XbootBaseService<LineDailyDetail, String> {

    /**
    * 多条件分页获取
    * @param lineDailyDetail
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<LineDailyDetail> findByCondition(LineDailyDetail lineDailyDetail, SearchVo searchVo, Pageable pageable);

    List<LineDailyDetail> getAllBylineId(String lineId);
}