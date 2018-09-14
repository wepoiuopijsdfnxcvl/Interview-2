package com.taotao.item.listener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class HtmlGenListener implements MessageListener {

	/**
	 * itemService服务
	 */
	@Autowired
	private ItemService itemService;

	/**
	 * freemarker
	 */
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	/**
	 * .html文件输出路径
	 */
	@Value("${HTML_OUT_PATH}")
	private String HTML_OUT_PATH;

	@Override
	public void onMessage(Message message) {
		try {
			// 从message中取出商品id
			TextMessage textMessage = (TextMessage) message;
			String strItemId = textMessage.getText();
			System.out.println("taotao-item-web收到商品id From MQ ：Id "+strItemId);
			Long itemId = new Long(strItemId);
			//根据商品id查询商品基本信息、商品描述
			  /*
             * 等待事务的提交，采用三次尝试的机会
             * 
             * 根据商品id查询商品基本信息，这里需要注意的是消息发送方法
             * 有可能还没有提交事务，因此这里是有可能取不到商品信息的，
             * 为了避免这种情况出现，我们最好等待事务提交，这里我采用3次
             * 尝试的方法，每尝试一次休眠一秒   
             */
            TbItem tbItem = null;
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000); // 休眠一秒
                tbItem = itemService.getItemById(itemId);
                // 如果获取到了商品基本信息，那就退出循环
                if (tbItem != null) {
                    break;
                }
            }
			Item item = new Item(tbItem);
			TbItemDesc tbItemDesc = itemService.getItemDesc(itemId);
			//创建数据集
			Map data = new HashMap<>();
			data.put("item", item);
			data.put("itemDesc",tbItemDesc);
			//创建商品详情页模板、指定静态文件输出路径
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate("item.htm");
			FileWriter out = new FileWriter(new File(HTML_OUT_PATH+itemId+".html"));
			//生成静态文件
			template.process(data, out);
			//关闭流
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
