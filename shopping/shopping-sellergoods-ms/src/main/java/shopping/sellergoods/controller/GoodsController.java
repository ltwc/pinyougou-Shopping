package shopping.sellergoods.controller;

import com.alibaba.fastjson.JSON;
import com.lt.pojo.*;
import com.lt.pojogroup.Goods;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.web.bind.annotation.*;
import shopping.sellergoods.activeMQ.JmsConfig;
import shopping.sellergoods.service.GoodsService;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private JmsTemplate jmsTemplate;
    @PostMapping("/addGoods")
    public RespBean addGoods(@RequestBody Goods goods){
        try{
            goodsService.addGoods(goods);
            return RespBean.ok("添加商品成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.ok("添加商品失败！！！");
        }

    }
//    @GetMapping("/findByPage")
//    public List<Goods> findByPage(@RequestBody TbGoods tbGoods, @RequestParam(value = "/pageNum",defaultValue = "1",required = false) int pageNum, int pageSize){
//        goodsService.findPage(tbGoods,pageNum,pageSize);
//    }
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids , String status){
        System.out.println("ids:"+ids);
        System.out.println("status"+status);
        try{
            goodsService.updateStatus(ids,status);
            if ("1".equals(status)){
                //导入索引库
                //得到需要导入的SKU列表，根据SKUid查询到对应的SKU的商品
                List<TbItem> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);
                //2.导入solr
               final String jsonString = JSON.toJSONString(itemList);

                System.out.println("Goods Controller"+jsonString);
                //将item数据发送到另一个微服务
                ActiveMQTopic topic = new ActiveMQTopic(JmsConfig.TOPIC_SOLR);
                jmsTemplate.send(topic, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(jsonString);
                    }
                });
                //生成商品详细页freemarker
                for (Long id : ids) {
                    ActiveMQTopic activeMQTopic = new ActiveMQTopic(JmsConfig.TOPIC_HTML);
                    jmsTemplate.send(activeMQTopic, new MessageCreator() {
                        @Override
                        public Message createMessage(Session session) throws JMSException {
                            return session.createTextMessage(id+"");
                        }
                    });
                }

            }
            return new Result(true,"修改状态成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改状态失败");
        }
    }

    @GetMapping("/findAllGoodsByPage")
    public ResultPage findAllGoodsByPage(@RequestParam(defaultValue = "1",value = "pageNum",required = false) int pageNum, int pageSize){
       return goodsService.findAllGoodsByPage(pageNum, pageSize);
    }
}
