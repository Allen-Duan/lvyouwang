package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.LineDailyDetail;
import cn.exrick.xboot.modules.your.entity.Order;
import cn.exrick.xboot.modules.your.entity.Provinces;
import cn.exrick.xboot.modules.your.service.OrderService;
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
@Api(description = "订单接口管理接口")
@RequestMapping("/xboot/order")
@Transactional
public class OrderController extends XbootBaseController<Order, String> {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderService getService() {
        return orderService;
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取")
    public Result<Page<Order>> getByCondition(Order order,
                                                            SearchVo searchVo,
                                                            PageVo pageVo){

        Page<Order> page = orderService.findByCondition(order, searchVo, PageUtil.initPage(pageVo));
        return new ResultUtil<Page<Order>>().setData(page);
    }

    @RequestMapping(value = "/getAllByUserId", method = RequestMethod.GET)
    @ApiOperation(value = "根据用户名查询列表")
    public Result getAllByUserId(@RequestParam String userId , @ModelAttribute PageVo pageVo){

        List<Map<String,Object>> retList  = orderService.getAllByUserId(userId);
        //手动分页
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("totalElements",retList.size());
        retMap.put("content", PageUtil.listToPage(pageVo,retList));
        return new ResultUtil<>().setData(retMap);
    }

    @RequestMapping(value = "/getAllByLikeUserName", method = RequestMethod.GET)
    @ApiOperation(value = "后台使用 根据用户名称模糊查询列表")
    public Result getAllByLikeUserName(@RequestParam(required = false) String userName , @ModelAttribute PageVo pageVo){

        List<Map<String,Object>> retList  = orderService.getAllByLikeUserName(userName);
        //手动分页
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("totalElements",retList.size());
        retMap.put("content", PageUtil.listToPage(pageVo,retList));
        return new ResultUtil<>().setData(retMap);
    }

}
