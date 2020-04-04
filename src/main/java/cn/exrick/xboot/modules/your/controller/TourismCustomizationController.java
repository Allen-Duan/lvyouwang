package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.TourismCustomization;
import cn.exrick.xboot.modules.your.service.TourismCustomizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dsh
 */
@Slf4j
@RestController
@Api(description = "定制接口管理接口")
@RequestMapping("/xboot/tourismCustomization")
@Transactional
public class TourismCustomizationController extends XbootBaseController<TourismCustomization, String> {

    @Autowired
    private TourismCustomizationService tourismCustomizationService;

    @Override
    public TourismCustomizationService getService() {
        return tourismCustomizationService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<TourismCustomization>> getByCondition(TourismCustomization tourismCustomization,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<TourismCustomization> page = tourismCustomizationService.findByCondition(tourismCustomization, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<TourismCustomization>>().setData(page);
    }
}
