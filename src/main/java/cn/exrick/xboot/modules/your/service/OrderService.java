package cn.exrick.xboot.modules.your.service;

import cn.exrick.xboot.base.XbootBaseService;
import cn.exrick.xboot.modules.your.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import cn.exrick.xboot.common.vo.SearchVo;

import java.util.List;
import java.util.Map;

/**
 * 订单接口接口
 * @author 段
 */
public interface OrderService extends XbootBaseService<Order, String> {

    /**
    * 多条件分页获取
    * @param order
    * @param searchVo
    * @param pageable
    * @return
    */
    Page<Order> findByCondition(Order order, SearchVo searchVo, Pageable pageable);

    List<Map<String, Object>> getAllByUserId(String userId);

    List<Map<String, Object>> getAllByLikeUserName(String userName);
}