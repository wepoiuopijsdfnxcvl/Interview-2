package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	private static final Class<Object> TbContent = null;

	@Autowired
	private TbContentMapper tbContentMapper;

	@Autowired
	private JedisClient jedisClient;

	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

	@Override
	public TaotaoResult insertContent(TbContent content) {
		// 补全pojo特性
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		// 向内容表中插入数据
		tbContentMapper.insert(content);

		try {
			// 做缓存同步，清除redis中内容分类id对应的缓存信息
			jedisClient.hdel(CONTENT_KEY, content.getCategoryId().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TaotaoResult.ok();
	}

	@Override
	public List<TbContent> getContentList(long cid) {
		// 查询数据库之前，先查询缓存，并且添加缓存不能影响业务逻辑
		try {
			String json = jedisClient.hget(CONTENT_KEY, cid + "");
			// 判断是否命中缓存，判断json字符串是否为null或""
			if (StringUtils.isNotBlank(json)) {
				// 将json转为list集合
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		// 设置条件查询
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		// 执行查询
		List<TbContent> list = tbContentMapper.selectByExample(example);
		try {
			// 向缓存中保存信息，并且添加缓存不能影响业务逻辑
			jedisClient.hset(CONTENT_KEY, cid + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
