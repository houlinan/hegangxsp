package cn.hgxsp.hegangxsp.service.impl;

import cn.hgxsp.hegangxsp.entity.History;
import cn.hgxsp.hegangxsp.entity.jpaRepository.HistoryRepository;
import cn.hgxsp.hegangxsp.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * DESC：搜索历史实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 17:26
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository ;

    @Override
    public void addHistory(History history) {

        if(StringUtils.isEmpty(history.getObjectId()) && StringUtils.isEmpty(history.getType()) &&
                StringUtils.isEmpty(history.getUserId())) return ;

        history.setCreateTime(new Date());

        historyRepository.save(history);
    }

    @Override
    public Page findAllByUserId(String userId ,Integer index, Integer pageSize) {
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest page = new PageRequest( index -1 ,  pageSize ,sort) ;
        Page allByUserId = historyRepository.findAllByUserId(userId, page);

//        return new PageImpl<>();
        //TODO ！！
        return null ;
    }

    @Override
    public void deleteAllHistory(String userId) {

    }
}
