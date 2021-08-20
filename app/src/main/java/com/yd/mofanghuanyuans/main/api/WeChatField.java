package com.yd.mofanghuanyuans.main.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class WeChatField {
    /**
     * 解析返回的XML为键值对
     * @param content
     * @return
     */
    public static Map<String, String> decodeXml(String content) {

        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {

                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {

        }
        return null;

    }


    /***
     * 将键值对xml化
     * @param map
     * @param sign
     * @return
     */
    public static String parseString2Xml(SortedMap<String, String> map, String sign) {
        StringBuffer sb = new StringBuffer();
        map.put("sign",sign);
        sb.append("<xml>");
        Set es = map.entrySet();
        Iterator iterator = es.iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry)iterator.next();
            String k = (String)entry.getKey();
            String v = (String)entry.getValue();
            sb.append("<"+k+">"+v+"</"+k+">");
        }
        sb.append("</xml>");
        return sb.toString();
    }


    /**
     * 签名获得sign字段
     * @param params
     * @return
     */
    public static String getSign(SortedMap<String, String> params) {
        StringBuffer sb = new StringBuffer();
        Set es = params.entrySet();
        Iterator iterator = es.iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (null != value && !TextUtils.isEmpty(value) && !key.equals("key")){
                sb.append(key + "=" + value + "&");
            }
        }
        sb.append("key=wst12016wst12016wst12016wst12016");//商品平台API密钥,32位的字母数字，找申请支付功能的人要，就在商户平台那里
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

//    /**
//     * MD5加密
//     * @return
//     */
//    public static String getRandomString() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    /**
//     * 要求外部订单号必须唯一。
//     * @return
//     */
//    public static String getOutTradeNo() {
//        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//        Date date = new Date();
//        String key = format.format(date);
//
//        Random r = new Random();
//        key = key + r.nextInt();
//        key = key.substring(0, 15);
//        return key;
//    }

    /**获取设备ip*/
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }



    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }
}
