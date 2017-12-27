package com.nti56.scadashow.scadashow.utils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Map;

/**
 * Created by chencheng on 2017/8/10.
 */

public class LinkWebService {

    // **********【使用jar包的解析方法soap】**********************

    /**
     * soap 解析
     *
     * @see 1.导包 ksoap2-android-assembly-3.0.0-jar-with-dependencies.jar
     * @see 2.定义网址、方法、命名空间
     * @see 3.HttpTransportSE soap请求对象
     * @see 4.SoapSerializationEnvelope soap序列化后的封装对象： soap信封
     * @see 5.SoapObject 参数对象 并设置 addProperty
     * @see 6.htse 调用 call方法 client.call(namespace + method, envelope);
     * @see 7.信封中 查看响应 envelope.getResponse，返回bodyIn信息：字符串替换查找
     */
    public String LinkSoapWebservice(String wsdl, String method, Map<String, Object> params) throws Exception {

        // 响应数据的namespace
        String namespace = "http://tempuri.org/";

        // soap客户端 http传输协议对象 send envelope
        HttpTransportSE client = new HttpTransportSE(wsdl);
        // soap序列化后的封装对象： soap信封
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 参数对象 目标
        SoapObject rpc = new SoapObject(namespace, method);
        // 传入调用参数
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                rpc.addProperty(key, params.get(key));
            }
        }

        // 设置参数对象 放入信封
        envelope.bodyOut = rpc;
        // 设置支持dotNet 服务器
        envelope.dotNet = true;


        // 访问webService 1.设置所需的SOAPAction头字段 2.包含soap调用信息的信封
        client.call(namespace + method, envelope);
        // 有响应结果 ； api 从包装对象中拉取一个对象，并返回，不为空表示得到了对象
        if (envelope.getResponse() != null) {
            // 返回响应对象 接收信封中的内容
            SoapObject response = (SoapObject) envelope.bodyIn;
            // 处理响应数据 ： 返回指定位置的特定属性 0；得到body中的内容
            String obj = response.getProperty(0).toString();

            obj = obj.replace("<?xml version='1.0' encoding='UTF-8' ?><string xmlns='http://tempuri.org/'>", "");
            obj = obj.replace("</string>", "");
            //Log.d("jochen", "" + obj);
            return obj;
        } else {
            return null;
        }
    }
}

