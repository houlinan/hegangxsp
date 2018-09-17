package cn.hgxsp.hegangxsp.service.impl;


import cn.hgxsp.hegangxsp.ObjectVO.ProductListVO;
import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.jpaRepository.ProductRepository;
import cn.hgxsp.hegangxsp.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * DESC：搜索service层实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/2
 * Time : 16:15
 */
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;


    private PageRequest page = null;

    @Override
    public Page<ProductListVO> findAllProduct(Integer index, Integer pageSize) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        page = new PageRequest(index - 1, pageSize, sort);

        Page<Product> result = productRepository.findAll(page);

        return pageProduct2PageProductListVo(result);

    }

    @Override
    public Page<ProductListVO> findAllProduct(Integer index, Integer pageSize, String searchValue) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest page = new PageRequest(index - 1, pageSize, sort);

        Specification<Product> specification = (Specification<Product>) (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.like(root.get("productName").as(String.class), "%" + searchValue + "%"));

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(p));

            return null;
        };
        Page<Product> pageResult = productRepository.findAll(specification, page);

        return pageProduct2PageProductListVo(pageResult);
    }

    @Override
    public Page<ProductListVO> findAllProductInShop(Integer index, Integer pageSize, String shopId) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        page = new PageRequest(index - 1, pageSize, sort);

        Page<Product> pageProduct = productRepository.findByShopId(page, shopId);

        return pageProduct2PageProductListVo(pageProduct);
    }

    @Override
    public Page<ProductListVO> findAllProductInShop(Integer index, Integer pageSize, Shop shop, String searchValue) {

        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        PageRequest page = new PageRequest(index, pageSize, sort);

        Specification<Product> specification = (Specification<Product>) (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.like(root.get("productName").as(String.class), "%" + searchValue + "%"));

            list.add(criteriaBuilder.equal(root.get("shop").as(Shop.class), shop));

            Predicate[] p = new Predicate[list.size()];
            criteriaQuery.where(list.toArray(p));

            return null;
        };

        Page<Product> products = productRepository.findAll(specification, page) ;


        return pageProduct2PageProductListVo(products);
    }


    @Override
    public List<String> getHot() {
        String sql = "Select content from objectsearch group by content order by count(content) desc limit 0, 10";
        Query nativeQuery = entityManager.createNativeQuery(sql);
        List<String> resultList = nativeQuery.getResultList();
        return resultList;
//        return null ;
    }

    /**
     * DESC: 将jpa获取的page<Product> 转化成 Page<ProductListVO> 数据 </>
     *
     * @author hou.linan
     * @date: 2018/9/3 14:03
     * @param: [result]
     * @return: org.springframework.data.domain.Page<cn.hgxsp.hgxsp.ObjectVO.ProductListVO>
     */
    private Page<ProductListVO> pageProduct2PageProductListVo(Page<Product> result) {

        PageRequest page = new PageRequest(result.getPageable().getPageNumber(), result.getPageable().getPageSize());

        List<Product> resultList = result.getContent();
        List<ProductListVO> productListVOS = new ArrayList<>();
        ProductListVO productListVO;
        for (Product product : resultList) {
            productListVO = new ProductListVO();
            BeanUtils.copyProperties(product, productListVO);
            productListVOS.add(productListVO);
        }

        return new PageImpl<>(productListVOS, page, result.getTotalElements());
    }



}
