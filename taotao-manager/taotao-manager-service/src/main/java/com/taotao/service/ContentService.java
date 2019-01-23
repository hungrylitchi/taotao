package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	public TaotaoResult insertContent(TbContent content) ;

	public EUDataGridResult getContentList(Long categoryId, Integer page, Integer rows);
}
