package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			// 创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");

			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {

		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// '状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		// 添加记录
		contentCategoryMapper.insert(contentCategory);
		// 查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			// 更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		// 返回结果
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCategory(long id) {
		// 递归删除以该节点为父节点的所有子节点
		deleteContentCategoryByParentId(id);
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(contentCategoryMapper.selectByPrimaryKey(id).getParentId());
		// 删除该节点
		contentCategoryMapper.deleteByPrimaryKey(id);
		// 判断父节点是否还为parent
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentCat.getId());
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		//仍然是父节点
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(parentCat);
		}
		//不是父节点
		parentCat.setIsParent(false);
		contentCategoryMapper.updateByPrimaryKeySelective(parentCat);
		
		return TaotaoResult.ok(parentCat);
	}

	private void deleteContentCategoryByParentId(long parentId) {
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent())
			return;

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		for (TbContentCategory tbContentCategory : list) {
			deleteContentCategoryByParentId(tbContentCategory.getId());
			contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
		}
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory record = contentCategoryMapper.selectByPrimaryKey(id);
		record.setName(name);
		contentCategoryMapper.updateByPrimaryKeySelective(record );
		return TaotaoResult.ok(record);
	}
}
