 package cn.exrick.xboot.modules.your.dao;

import cn.exrick.xboot.base.XbootBaseDao;
import cn.exrick.xboot.modules.your.entity.Order;

import java.util.List;

/**
 * 订单接口数据处理层
 * @author 段
 */
public interface OrderDao extends XbootBaseDao<Order, String> {
    List<Order> findAllByUserId(String userId);

    List<Order> findAllByUserIdIn(List<String> userid);
}