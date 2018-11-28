package cn.collabtech.utils;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 签名工具类
 * @author: 研发部-黄泽建
 * @since: 2018年8月13日下午4:00:34 
 * @version: 1.0.4
 * 假设请求参数如下
a: 20
b: 10
c: 30

公共参数:
nonceStr： 430f08b340be43e5b87e9061ff960e78
timestamp： 1542868554259
apiKey： 123456

按请求参数和公共参数按首字母升序排序：
a=20&apiKey=123456&b=10&c=30&nonceStr=430f08b340be43e5b87e9061ff960e78&timestamp=1542868554259

签名密钥
密钥为：654321

sign的计算方式为：
md5(“a=20&apiKey=123456&b=10&c=30&nonceStr=430f08b340be43e5b87e9061ff960e78&timestamp=1542868554259”+”&key=654321”)
得到的sign值应为：4F497DB40FAC2C10E4D19956F6422338

 */
public class SignUtil {
	
	public static void main(String[] args) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("name", "A");
		paramMap.put("password", "123456");
		String sign = SignUtil.getSign(paramMap,"key65423");
		System.out.println(sign);
	}
	

    /**
     * 获取随机字符串
     * @author: 研发部-黄泽建
     * @since: 2018年8月13日下午4:03:33 
     * @version: 1.0.4
     * @return
     */
    public static String getNonceStr(){
        return DigestUtils.md5Hex(UUID.randomUUID().toString());
    }

    /**
     * 获取当前时间戳
     * @author: 研发部-黄泽建
     * @since: 2018年8月13日下午4:03:46 
     * @version: 1.0.4
     * @return
     */
    public static long getTimeStamp(){
        return System.currentTimeMillis();
    }
    
    /**
     * 校验签名
     * @author: 研发部-黄泽建
     * @since: 2018年8月13日下午4:03:58 
     * @version: 1.0.4
     * @param paramMap 参数集合
     * @param apiSecret 接口密钥
     * @return
     */
    public static final boolean checkSign(Map<String, String> paramMap, String apiSecret){
        //需要验证的签名
        String validateSign = paramMap.get("sign");
        Map<String, String> map = new HashMap<>(paramMap);
        map.remove("sign");
        return getSign(paramMap, apiSecret).equals(validateSign);
    }
	
	/**
     * 排序比较器
     * @author: 研发部-黄泽建
     * @since: 2018年6月26日下午2:33:29 
     * @version: 1.0.2
     */
    static class SpellComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2){
            // 按字符串自然序(升序)排序
            // 按照参数名ASCII码从小到大排序（字典序）
            return o1.compareTo(o2);
        }
    }

    /**
     * 获取签名字符串
     * @author: 研发部-黄泽建
     * @since: 2018年8月13日下午4:06:15 
     * @version: 1.0.4
     * @param paramMap 参数集合
     * @param apiSecret 接口密钥
     * @return
     */
    public static String getSign(Map<String, String> paramMap, String apiSecret){
        Map<String, String> sortMap = new TreeMap<String, String>(new SpellComparator());
        sortMap.putAll(paramMap);
        StringBuffer stringA = new StringBuffer();
        int index = 0;
        for(Map.Entry<String, String> entry : sortMap.entrySet()){
            if (null == entry.getKey() || entry.getKey().length() == 0){
                continue;
            }
            if (null == entry.getValue() || entry.getValue().length() == 0){
                continue;
            }
            if(index == 0){
                stringA.append(entry.getKey()).append("=").append(entry.getValue());
            }else{
                stringA.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
            index++;
        }

        String stringSignTemp = stringA.append("&key=").append(apiSecret).toString();
        String mySignature = DigestUtils.md5Hex(stringSignTemp);
        String sign = mySignature.toUpperCase();
        return sign;
    }
}
