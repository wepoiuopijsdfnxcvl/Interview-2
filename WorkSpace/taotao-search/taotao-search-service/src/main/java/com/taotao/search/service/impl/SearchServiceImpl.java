package com.taotao.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private ItemSearchDao itemSearchDao;

	@Override
	public SearchResult search(String queryString, int page, int rows) throws Exception {
		// 1创建一个solrQuery对象
		SolrQuery query = new SolrQuery();
		// 2设置查询条件
		query.setQuery(queryString);
		// 3 设置分页条件
		// page 为当前页
		if (page < 1) {
			page = 1;
		}
		query.setStart((page - 1) * rows);
		if (rows < 1) {
			rows = 10;
		}
		query.setRows(rows);
		// 4、需要指定默认搜索域。由于复制域查询不太准确，因此建议直接使用item_title域
		query.set("df", "item_title");
		// 5、设置高亮
		query.setHighlight(true);
		query.addHighlightField("item_title"); // 设置高亮显示的域
		query.setHighlightSimplePre("<em style=\"color:red\">"); // 设置高亮显示的前缀
		query.setHighlightSimplePost("</em>"); // 设置高亮显示的后缀
		// 6、执行查询，调用SearchDao。得到SearchResult
		SearchResult searchResult = itemSearchDao.search(query);
		// 7、需要计算总页数。
		long totalNumber = searchResult.getTotalNumber();
		long totalPage = totalNumber / rows;
		if (totalNumber % rows > 0) {
			totalPage++;
		}
		searchResult.setTotalPage(totalPage);
		// 8、返回SearchResult
		return searchResult;
	}

}
