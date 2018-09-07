package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Autowired
	private TbItemDescMapper tbItemDescMapper;

	@Override
	public TbItem getItemById(long itemId) {
		TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = tbItemMapper.selectByExample(example);
		// 创建返回结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 取总记录数
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult addItem(TbItem item, String desc) {
		//先生成商品id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态：1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		//设置创建及更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//出入到商品列表
		tbItemMapper.insert(item);
		//商品描述添加
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		//插入商品描述
		tbItemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}

}
