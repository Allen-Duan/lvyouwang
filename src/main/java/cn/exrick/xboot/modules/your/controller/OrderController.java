package cn.exrick.xboot.modules.your.controller;

import cn.exrick.xboot.base.XbootBaseController;
import cn.exrick.xboot.common.utils.PageUtil;
import cn.exrick.xboot.common.utils.ResultUtil;
import cn.exrick.xboot.common.vo.PageVo;
import cn.exrick.xboot.common.vo.Result;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.exrick.xboot.modules.your.entity.Order;
import cn.exrick.xboot.modules.your.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 段
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
}
