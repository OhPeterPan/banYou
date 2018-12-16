package com.banyou.app.net;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Hello world!
 */
public class App {
    public static void demo() {
        // 加参数
        Map<String, String> params = new HashMap<>();
        params.put("usernumber", "17601417313");
        params.put("userpwd", "12345");

        // 创建soapXML格式文本
        String soapText = createNetSoapText("http://webService.youzi_ws.jetinfor.com/",
                "admin", "admin",
                "userlogin", params);
        String urlAddress = "https://bmws.banutech.com/cxf/receivablesWebService?wsdl";
        // 发送请求，获取结果
        String result;
        try {
            result = testUrlAccess(urlAddress, soapText);
            System.out.println(result);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String createNetSoapText(String namespace, String username,
                                           String password, String methodName,
                                           Map<String, String> params) {

        StringBuffer buf = new StringBuffer();
        Set<String> tags = params.keySet();

        buf.append("<?xml version=\"1.0\" ?>");
        buf.append("<S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\">");
        buf.append("<S:Header>");
        buf.append("<SllmWebService xmlns=\"" + namespace + "\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" SOAP-ENV:actor=\"http://www.w3.org/2003/05/soap-envelope/role/next\">");
        buf.append(username + "&amp;" + password);
        buf.append("</SllmWebService>");
        buf.append("</S:Header>");
        buf.append("<S:Body>");
        buf.append("<ns2:" + methodName + " xmlns:ns2=\"" + namespace + "\">");

        for (String tag : tags) {
            buf.append("<" + tag + ">");
            buf.append(params.get(tag));
            buf.append("</" + tag + ">");
        }

        buf.append("</ns2:" + methodName + ">");
        buf.append("</S:Body>");
        buf.append("</S:Envelope>");

        return buf.toString();
    }

    //该方法是用URL访问webService
    public static String testUrlAccess(String urlAddress, String soapText) throws Exception {


        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                long startTime = System.currentTimeMillis();
                Request request = chain.request();
                Response response = chain.proceed(request);
                long endTime = System.currentTimeMillis();
                System.out.println("时间：" + (endTime - startTime));
                return response;
            }
        };
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml; charset=utf-8"), soapText);
        Request request = new Request.Builder().url(urlAddress).post(requestBody).build();

        Response response = client.newCall(request).execute();
        Log.i("wak", response.body().string());

        //创建url请求
        //String urlAddress="http://192.168.1.102/WebServiceDemo/PersonActionImpPort?wsdl";
  /*      URL url = new URL(urlAddress);
        //获取请求的连接
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        //用输出流发送数据
        OutputStream out = con.getOutputStream();
        //请求体
        //String soapText = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://web/\"><soapenv:Header/><soapenv:Body><web:login><name>test1</name></web:login></soapenv:Body></soapenv:Envelope>";

        //用流发送送请求
        out.write(soapText.getBytes("utf-8"));

        //用输入流来获取请求返回的数据
        InputStream in = con.getInputStream();

        //读取返回的数据
        byte[] buf = new byte[2024];
        int i = 0;
        String s = "";
        while ((i = in.read(buf)) != -1) {
            String ss = new String(buf, 0, i, "utf-8");
            s += ss;
        }
        in.close();
        out.close();
        con.disconnect();*/
        return response.body().string();
    }
}
