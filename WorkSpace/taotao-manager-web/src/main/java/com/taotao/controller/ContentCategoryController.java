package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

/**
 * 内容分类管理
 * 
 * @author Darrick
 *
 */
@Controller
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(defaultValue = "0") Long id) {
		return contentCategoryService.getContentCatList(id);
	}

	@RequestMapping("/content/category/create")
	@ResponseBody
	public TaotaoResult insertContentCat(long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCat(parentId, name);
		return result;
	}

}
