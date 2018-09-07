package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper tbItemCatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		// 根据父节点id查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		// 查询条件： 父节点id=parentId
		criteria.andParentIdEqualTo(parentId);
		// 执行查询 ，无需分页
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		// 转换成EasyUITreeNode列表
		List<EasyUITreeNode> easyUITreeNodes = new ArrayList<>();

		for (TbItemCat tbItemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			/**
			 * 判断下面是否有子节点，如果有子节点，它应该是“closed” 如果没有子节点 “open”
			 */
			node.setState(tbItemCat.getIsParent() ? "closed" : "open");
			easyUITreeNodes.add(node);
		}

		return easyUITreeNodes;
	}

}
