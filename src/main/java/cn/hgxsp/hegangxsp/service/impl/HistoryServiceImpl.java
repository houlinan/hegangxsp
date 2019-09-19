package cn.hgxsp.hegangxsp.service.impl;

import cn.hgxsp.hegangxsp.entity.History;
import cn.hgxsp.hegangxsp.entity.jpaRepository.HistoryRepository;
import cn.hgxsp.hegangxsp.service.HistoryService;
import cn.hgxsp.hegangxsp.utils.ObjectConvertVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * DESC：搜索历史实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 17:26
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public void addHistory(History history) {

        history.setCreateTime(new Date());

        historyRepository.save(history);
    }

    @Override
    public Page findAllByUserId(String userId, Integer index, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest page = new PageRequest(index - 1, pageSize, sort);
        Page allByUserId = historyRepository.findAllByUserId(userId, page);
        List content = allByUserId.getContent();
        List historyVO = ObjectConvertVO.history2HistoryVO(content);

        return new PageImpl<>(historyVO, page, allByUserId.getTotalElements());
    }

    @Override
    public void deleteAllHistory(String userId) {
        historyRepository.deleteByUserId(userId);
    }

    @Override
    public void updateHistroy(History history) {
        if (StringUtils.isEmpty(history.getType()) &&
                StringUtils.isEmpty(history.getUserId()) &&
                (ObjectUtils.isEmpty(history.getProduct()) && ObjectUtils.isEmpty(history.getShop()))) return;


        if(!StringUtils.isEmpty(history.getCreateTime())) history.setCreateTime(null);

        Example<History> example = Example.of(history) ;
        Optional one = historyRepository.findOne(example);
        if(one.isPresent()){
            History result = (History)one.get();
            addHistory(result);
            return ;
        }
        addHistory(history);
    }
}
