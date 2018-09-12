package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	// 根据商品id查询商品信息
	TbItem getItemById(long itemId);

	EasyUIDataGridResult getItemList(int page, int rows);

	TaotaoResult addItem(TbItem item, String desc);

	// 根据商品id查询商品描述
	TbItemDesc getItemDesc(long itemId);
}
