package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Areas;
import cn.exrick.xboot.modules.your.service.AreasService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author dsh
 */
@Slf4j
@RestController
@Api(description = "地区管理接口")
@RequestMapping("/xboot/areas")
@Transactional
public class AreasController extends XbootBaseController<Areas, String> {

    @Autowired
    private AreasService areasService;

    @Override
    public AreasService getService() {
        return areasService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Areas>> getByCondition(Areas areas,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Areas> page = areasService.findByCondition(areas, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Areas>>().setData(page);
    }

    @RequestMapping(value = "/getAreasByCityId", method = RequestMethod.GET)
    @ApiOperation(value = "根据cityId获取地区")
    public Result getAreasByCityId(@RequestParam Map<String,Object> paramMap){

        List<Areas> retList =  areasService.getAreasByCityid(paramMap.get("cityId").toString());
        return new ResultUtil<List<Areas>>().setData(retList);
    }
}
