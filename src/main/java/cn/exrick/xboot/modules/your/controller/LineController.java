package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Line;
import cn.exrick.xboot.modules.your.service.LineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dsh
 */
@Slf4j
@RestController
@Api(description = "旅游线路管理接口")
@RequestMapping("/xboot/line")
@Transactional
public class LineController extends XbootBaseController<Line, String> {

    @Autowired
    private LineService lineService;

    @Override
    public LineService getService() {
        return lineService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Line>> getByCondition(Line line,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Line> page = lineService.findByCondition(line, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Line>>().setData(page);
    }

    @RequestMapping(value = "saveLineAndDetail",method = RequestMethod.POST)
    @ApiOperation(value = "添加旅游线路")
    public Result saveLineAndDetail(@RequestBody Map<String,Object> paramMap){
        try{
            lineService.saveLineAndDetail(paramMap);
        }catch (XbootException e){
            return new ResultUtil<>().setErrorMsg("添加旅游线路有误 "+e.getMsg());
        }
        return new ResultUtil<>().setSuccessMsg("添加旅游线路成功!");
    }

    @RequestMapping(value = "/getAllbyLikeName", method = RequestMethod.GET)
    @ApiOperation(value = "根据名称模糊查询列表")
    public Result getAllbyLikeName(PageVo pageVo,@RequestParam(required = false) String lineTitle){

        List<Map<String,Object>> retList =  lineService.getAllbyLikeName(lineTitle);
        //手动分页
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("totalElements",retList.size());
        retMap.put("content", PageUtil.listToPage(pageVo,retList));
        return new ResultUtil<>().setData(retMap);
    }
}
