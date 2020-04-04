package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.TourismCustomization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;

/**
 * 定制接口接口
 * @author dsh
 */
public interface TourismCustomizationService extends XbootBaseService<TourismCustomization, String> {

    /**
    * 多条件分页获取
    * @param tourismCustomization
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<TourismCustomization> findByCondition(TourismCustomization tourismCustomization, SearchVo searchVo, Pageable pageable);

}