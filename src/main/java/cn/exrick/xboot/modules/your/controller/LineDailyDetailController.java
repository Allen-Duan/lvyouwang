package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.LineDailyDetail;
import cn.exrick.xboot.modules.your.service.LineDailyDetailService;
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
@Api(description = "旅游线路详情管理接口")
@RequestMapping("/xboot/lineDailyDetail")
@Transactional
public class LineDailyDetailController extends XbootBaseController<LineDailyDetail, String> {

    @Autowired
    private LineDailyDetailService lineDailyDetailService;

    @Override
    public LineDailyDetailService getService() {
        return lineDailyDetailService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<LineDailyDetail>> getByCondition(LineDailyDetail lineDailyDetail,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<LineDailyDetail> page = lineDailyDetailService.findByCondition(lineDailyDetail, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<LineDailyDetail>>().setData(page);
    }
}
