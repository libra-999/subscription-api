package org.project.subscriptionservice.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.project.subscriptionservice.dao.Dao;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class DaoImpl<T, M extends BaseMapper<T>> extends ServiceImpl<M, T> implements Dao<T> {

    private boolean checkDeleteAt() {
        Class<T> entityClass = this.getEntityClass();
        try {
            entityClass.getField("deletedAt");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<T> queryAll() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (checkDeleteAt()) {queryWrapper.isNull("deleted_at");}
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public T view(Integer id) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        if (checkDeleteAt()) {
            queryWrapper.isNull("deleted_at");
        }
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int create(T entity) {
        return baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(T newEntity, Integer id) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        if (checkDeleteAt()) {
            queryWrapper.isNull("deleted_at");
        }
        return baseMapper.update(newEntity, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int forceDelete(Integer id) {
        return baseMapper.deleteById(id);
    }
}
