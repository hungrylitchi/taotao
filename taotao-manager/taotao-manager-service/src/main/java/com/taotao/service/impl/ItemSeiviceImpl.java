package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemSeiviceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper descMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	// 根据Id返回单个产品
	@Override
	public TbItem getItemById(long itemId) {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 商品列表查询
	 * 
	 */
	@Override
	public EUDataGridResult getItemList(int page, int rows) {
		// 查询商品列表
		TbItemExample example = new TbItemExample();
		// 分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建返回值对象
		EUDataGridResult result = new EUDataGridResult();
		result.setRows(list);
		PageInfo<TbItem> info = new PageInfo<>(list);
		result.setTotal(info.getTotal());
		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc,String itemParam) {
		// 生成商品id
		item.setId(IDUtils.genItemId());
		// 设置状态
		item.setStatus((byte) 1);
		Date date = new Date();
		item.setUpdated(date);
		item.setCreated(date);
		// 插入数据库
		itemMapper.insert(item);
		insertItemDesc(item.getId(),desc,date);
		insertItemParam(item.getId(),itemParam,date);
		return TaotaoResult.ok();
	}
	

	private int insertItemParam(Long itemId,String itemParam,Date date) {
		
		TbItemParamItem record = new TbItemParamItem();
		record.setItemId(itemId);
		record.setCreated(date);
		record.setUpdated(date);
		record.setParamData(itemParam);
		return itemParamItemMapper.insert(record);
	}

	private int insertItemDesc(Long itemId, String desc,Date date) {
		TbItemDesc record = new TbItemDesc();
		record.setItemDesc(desc);
		record.setItemId(itemId);
		record.setCreated(date);
		record.setUpdated(date);
		return descMapper.insert(record);
	}
}
