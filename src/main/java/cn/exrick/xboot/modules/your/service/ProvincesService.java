package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Provinces;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 地区接口
 * @author dsh
 */
public interface ProvincesService extends XbootBaseService<Provinces, String> {

    /**
    * 多条件分页获取
    * @param provinces
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Provinces> findByCondition(Provinces provinces, SearchVo searchVo, Pageable pageable);

}