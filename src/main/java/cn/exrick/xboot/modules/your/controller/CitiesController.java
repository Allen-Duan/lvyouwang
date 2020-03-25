package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Cities;
import cn.exrick.xboot.modules.your.service.CitiesService;
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
@Api(description = "地区管理接口")
@RequestMapping("/xboot/cities")
@Transactional
public class CitiesController extends XbootBaseController<Cities, String> {

    @Autowired
    private CitiesService citiesService;

    @Override
    public CitiesService getService() {
        return citiesService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Cities>> getByCondition(Cities cities,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Cities> page = citiesService.findByCondition(cities, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Cities>>().setData(page);
    }
}
