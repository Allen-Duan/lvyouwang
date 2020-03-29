package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Line;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;
import java.util.Map;

/**
 * 旅游线路接口
 * @author dsh
 */
public interface LineService extends XbootBaseService<Line, String> {

    /**
    * 多条件分页获取
    * @param line
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Line> findByCondition(Line line, SearchVo searchVo, Pageable pageable);

    void saveLineAndDetail(Map<String, Object> paramMap);

    List<Map<String, Object>> getAllbyLikeName(String lineTitle);
}