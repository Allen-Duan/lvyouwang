package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Areas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 地区接口
 * @author dsh
 */
public interface AreasService extends XbootBaseService<Areas, String> {

    /**
    * 多条件分页获取
    * @param areas
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Areas> findByCondition(Areas areas, SearchVo searchVo, Pageable pageable);

    List<Areas> getAreasByCityid(String cityId);
}