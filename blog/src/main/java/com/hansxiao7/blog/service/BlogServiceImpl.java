package com.hansxiao7.blog.service;

import com.hansxiao7.blog.NotFoundException;
import com.hansxiao7.blog.dao.BlogRepository;
import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.po.Tag;
import com.hansxiao7.blog.po.Type;
import com.hansxiao7.blog.util.MarkdownUtils;
import com.hansxiao7.blog.vo.BlogQuery;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TypeService typeService;

    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }

                if (blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }

                if (blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }

                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null){
            blog.setCreateTime(new Date());
            blog.setViews(0);
        }
        blog.setUpdateTime(new Date());
        return blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null){
            throw new NotFoundException("Blog not exists");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtml(content));
        return b;
    }

    @Transactional
    @Override
    public void updateViews(Long id) {
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null){
            throw new NotFoundException();
        }
        blog.setViews(blog.getViews() + 1);
        blogRepository.save(blog);
    }

    @Override
    public Page<Blog> listBlogByType(Pageable pageable, Long typeId) {
        return blogRepository.findAllByTypeId(pageable, typeId);
    }

    @Override
    public Page<Blog> findBlogByTag(Long tagId, Pageable pageable) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Join join = root.join("tags");
                return cb.equal(join.get("id"), tagId);
            }
        }, pageable);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogRepository.findGroupByYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years){
            map.put(year, blogRepository.findByYear(year));
        }

        return map;
    }

    @Override
    public List<Blog> listBlog() {
        return blogRepository.findAll();
    }

    @Override
    public List<String> listFlags() {
        return blogRepository.findFlags();
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).orElse(null);
        if (b == null){
            throw new NotFoundException("No specified blog exists");
        }

        BeanUtils.copyProperties(blog, b);
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        Blog b = blogRepository.findById(id).orElse(null);
        blogRepository.delete(b);
    }
}
