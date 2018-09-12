package com.klst.client.Thread;

import com.alibaba.fastjson.JSONObject;
import com.klst.client.service.McuScanService;
import com.klst.client.util.Constants;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;

public class EventMessageReceiveTask extends Thread{

    public static Boolean isStop = false;

    private String mcuIp;

    private int port;

    private SocketChannel socketChannel;

    private Selector selector;

    private  BlockingQueue eventMessageQueue;

    public EventMessageReceiveTask(String mcuIp, int port, BlockingQueue eventMessageQueue){

        this.mcuIp = mcuIp;

        this.port = port;

        this.eventMessageQueue = eventMessageQueue;

        this.isStop = false;

        init();

    }

    private void init(){
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(mcuIp, port));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (!isStop){
            try{
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    SocketChannel clientchannel = (SocketChannel) key.channel();
                    if (clientchannel.isConnectionPending()) {
                        clientchannel.finishConnect();
                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                    } else if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int count = -1;
                        StringBuffer eventBuffer = new StringBuffer();
                        while((count = clientchannel.read(buffer))>0){
                            eventBuffer.append(new String(buffer.array(), 0, count));
                            buffer.flip();
                        }
                        String eventMessages = eventBuffer.toString();
                        if(!"".equals(eventMessages)){
                            String[] eventMessageArray = eventMessages.split("\n");
                            for(String eventMessage : eventMessageArray) {
                                JSONObject jsonObj = JSONObject.parseObject(eventMessage.trim());
                                String eventType = jsonObj.getString("eventType");
                                String eventAction = jsonObj.getString("eventAction");
                                if(Constants.EVENT_MEETINGCHANGED.equals(eventType)){
                                    if(Constants.EVENT_POLLINGPART.equals(eventAction)){
                                        eventMessageQueue.add(jsonObj.toString());
                                        ConcurrentHashMap<String, BlockingQueue> eventQueueMap = McuScanService.getInstance().getEventQueueMap();
                                        BlockingQueue<String> queue = eventQueueMap.get(mcuIp);
                                        if(queue == null){
                                            queue = new LinkedBlockingDeque<>();
                                            eventQueueMap.put(mcuIp, queue);
                                        }
                                        queue.add(jsonObj.getString("detail"));
                                    }else if(Constants.EVENT_PARTCOUNTCHANGED.equals(eventAction)
                                            || Constants.EVENT_MEETING_UPDATED.equals(eventAction)
                                            || Constants.EVENT_MEETING_DELETED.equals(eventAction)){
                                        eventMessageQueue.add(jsonObj.toString());
                                    }
                                }else if(Constants.EVENT_MEETINGPARTICIPANTCHANGED.equals(eventType)){
                                    if(Constants.EVENT_PARTSTATUSCHANGED.equals(eventAction)
                                            || Constants.EVENT_PART_DELETED.equals(eventAction)
                                            || Constants.EVENT_PART_CREATED.equals(eventAction)){
                                        eventMessageQueue.add(jsonObj.toString());
//                                        System.out.println(jsonObj.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }catch (Exception e){
//                e.printStackTrace();
            }
        }
    }
}
