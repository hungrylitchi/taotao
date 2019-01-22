package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Override
	public CatResult getItemCatList() {
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}

	private List<?> getCatList(long parentId) {
		ArrayList<Object> resultList = new ArrayList<>();

		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		int count = 0;
		for (TbItemCat ic : list) {
			if (ic.getIsParent()) {
				CatNode catNode = new CatNode();
				catNode.setUrl("/products/" + ic.getId() + ".html");
				if (parentId == 0) {
					catNode.setName("<a href='/products/" + ic.getId() + ".html'>" + ic.getName() + "</a>");
				} else {
					catNode.setName(ic.getName());
				}
				catNode.setItem(getCatList(ic.getId()));
				resultList.add(catNode);
				if(parentId==0&&++count==14) {
					break;
				}
			}else {
				resultList.add("/products/"+ic.getId()+".html|"+ic.getName());
			}
		}

		return resultList;
	}

}
