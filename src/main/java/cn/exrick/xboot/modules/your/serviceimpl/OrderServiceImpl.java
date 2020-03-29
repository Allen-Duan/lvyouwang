package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.utils.ObjectUtil;
import cn.exrick.xboot.config.properties.XbootTokenProperties;
import cn.exrick.xboot.modules.base.dao.UserDao;
import cn.exrick.xboot.modules.base.entity.User;
import cn.exrick.xboot.modules.your.dao.LineDao;
import cn.exrick.xboot.modules.your.dao.OrderDao;
import cn.exrick.xboot.modules.your.entity.Line;
import cn.exrick.xboot.modules.your.entity.Order;
import cn.exrick.xboot.modules.your.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.exrick.xboot.common.vo.SearchVo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 订单接口接口实现
 * @author 段
 */
@Slf4j
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LineDao lineDao;

    @Override
    public OrderDao getRepository() {
        return orderDao;
    }

    @Override
    public Page<Order> findByCondition(Order order, SearchVo searchVo, Pageable pageable) {

        return orderDao.findAll(new Specification<Order>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                // TODO 可添加你的其他搜索过滤条件 默认已有创建时间过滤
                Path<Date> createTimeField = root.get("createTime");

                List<Predicate> list = new ArrayList<Predicate>();

                // 创建时间
                if(StrUtil.isNotBlank(searchVo.getStartDate())&&StrUtil.isNotBlank(searchVo.getEndDate())){
                    Date start = DateUtil.parse(searchVo.getStartDate());
                    Date end = DateUtil.parse(searchVo.getEndDate());
                    list.add(cb.between(createTimeField, start, DateUtil.endOfDay(end)));
                }

                Predicate[] arr = new Predicate[list.size()];
                cq.where(list.toArray(arr));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<Map<String, Object>> getAllByUserId(String userId) {
        List<Order> allByUserId = orderDao.findAllByUserId(userId);
        List<Map<String,Object>> retList = new ArrayList<>();
        for (Order order : allByUserId) {
            Map<String, Object> map = ObjectUtil.beanToMapFormatDate(order);
            Line line = lineDao.findById(order.getLineId()).orElse(null);
            if (line == null) {
                throw new XbootException("线路信息获取失败 请联系管理员!");
            }
            map.putAll(ObjectUtil.beanToMapFormatDate(line));
            retList.add(map);
        }
        return retList;
    }

    @Override
    public List<Map<String, Object>> getAllByLikeUserName(String userName) {
        List<String> byUsernameLike = userDao.findByUsernameLike(userName);
        List<Order> allByUserIdIn = orderDao.findAllByUserIdIn(byUsernameLike);
        List<Map<String,Object>> retList = new ArrayList<>();
        for (Order order : allByUserIdIn) {
            Map<String, Object> map = ObjectUtil.beanToMapFormatDate(order);
            Line line = lineDao.findById(order.getLineId()).orElse(null);
            if (line == null) {
                throw new XbootException("线路信息获取失败 请联系管理员!");
            }
            map.putAll(ObjectUtil.beanToMapFormatDate(line));
            User user = userDao.findById(order.getUserId()).orElse(null);
            if (user == null) {
                throw new XbootException("查询用户失败 请联系管理员");
            }
            map.put("userName",user.getUsername());
            retList.add(map);
        }
        return retList;
    }

}