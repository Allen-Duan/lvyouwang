package cn.exrick.xboot.modules.your.serviceimpl;

import cn.exrick.xboot.common.exception.XbootException;
import cn.exrick.xboot.common.utils.ObjectUtil;
import cn.exrick.xboot.common.utils.ReflectUtil;
import cn.exrick.xboot.modules.your.dao.*;
import cn.exrick.xboot.modules.your.entity.Line;
import cn.exrick.xboot.modules.your.entity.LineDailyDetail;
import cn.exrick.xboot.modules.your.service.LineService;
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
import org.springframework.util.StringUtils;

import java.awt.image.ImageProducer;
import java.util.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Field;

/**
 * 旅游线路接口实现
 * @author dsh
 */
@Slf4j
@Service
@Transactional
public class LineServiceImpl implements LineService {

    @Autowired
    private LineDao lineDao;

    @Autowired
    private ReflectUtil reflectUtil;

    @Autowired
    private LineDailyDetailDao dailyDetailDao;

    @Autowired
    private AreasDao areasDao;

    @Autowired
    private CitiesDao citiesDao;

    @Autowired
    private ProvincesDao provincesDao;

    @Override
    public LineDao getRepository() {
        return lineDao;
    }

    @Override
    public Page<Line> findByCondition(Line line, SearchVo searchVo, Pageable pageable) {

        return lineDao.findAll(new Specification<Line>() {
            @Nullable
            @Override
            public Predicate toPredicate(Root<Line> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

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
    public void saveLineAndDetail(Map<String, Object> paramMap) {
        Line line;
        LineDailyDetail lineDailyDetail;
        try {
            line = reflectUtil.mapConvertBean(paramMap, new Line());
            lineDailyDetail = reflectUtil.mapConvertBean(paramMap, new LineDailyDetail());
        } catch (Exception e) {
            throw new XbootException("参数有误 请联系管理员");
        }
        lineDao.save(line);
        dailyDetailDao.save(lineDailyDetail);
    }

    @Override
    public List<Map<String, Object>> getAllbyLikeName(String name) {
        List<Map<String, Object>> retList = new ArrayList<>();
        List<Line> all;
        if (StringUtils.isEmpty(name)) {
             all = lineDao.findAll();
        }else{
            all = lineDao.findAllByLineTitleLike("%"+name+"%");
        }
        for (Line line : all) {
            Map<String, Object> map = ObjectUtil.beanToMapFormatDate(line);
            map.put("areasName",Objects.requireNonNull(areasDao.findByAreaid(line.getAreasId()).orElse(null)).getArea());
            map.put("cityName",Objects.requireNonNull(citiesDao.findByCityid(line.getCitiesId()).orElse(null)).getCity());
            map.put("provinceName",Objects.requireNonNull(provincesDao.findByProvinceid(line.getProvincesId()).orElse(null)).getProvince());
            retList.add(map);
        }
        return retList;
    }

}