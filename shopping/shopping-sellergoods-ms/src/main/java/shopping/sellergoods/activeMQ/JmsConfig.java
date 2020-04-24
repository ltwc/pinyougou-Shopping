package shopping.sellergoods.activeMQ;



public class JmsConfig {

    public final static String TOPIC_HTML = "pingyougou.topic.createhtml"; 
    public final static String TOPIC_HTML_DELETE = "pingyougou.topic.deletehtml";
    
    public final static String QUEUE = "pingyougou.queue.solr";   
    public final static String QUEUE_DELETE = "pingyougou.queue.solr.delete";
    
    public final static String TOPIC_SOLR = "pingyougou.topic.solr";
}
