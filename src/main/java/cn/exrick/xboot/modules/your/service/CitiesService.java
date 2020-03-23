package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Cities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 地区接口
 * @author dsh
 */
public interface CitiesService extends XbootBaseService<Cities, String> {

    /**
    * 多条件分页获取
    * @param cities
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Cities> findByCondition(Cities cities, SearchVo searchVo, Pageable pageable);

}