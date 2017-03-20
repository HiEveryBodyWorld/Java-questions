package com.recharge.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.recharge.entity.HttpResult;


//聚通达-已经通了，必须按照模板发
public class HttpClient {

	public  static HttpResult postSendContentType(String url, String encode, String content,int sockeTimeout,int connectTimeout,int connectReqTimeOut,String method,String action,String authorization,String contentType,boolean accept) {
		CloseableHttpClient client = null;
		client = HttpClients.createDefault();
		try {
			HttpResponse response = null;
			HttpEntity entity = null;
			HttpPost httppost = new HttpPost(url); // 引号中的参数是：servlet的地址

			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(sockeTimeout)			// 数据传输时间
					.setConnectTimeout(connectTimeout)		// 连接时间
					.setConnectionRequestTimeout(connectReqTimeOut).build();  
			httppost.setConfig(requestConfig);
			
			// 返回服务器响应
			StringEntity reqEntity = new StringEntity(content, encode);
			reqEntity.setContentType("application/"+contentType+";charset=" + encode);
			reqEntity.setContentEncoding(encode);
			httppost.setEntity(reqEntity);
			if(StringUtils.isNotEmpty(action)){
				httppost.setHeader("Action", action);
			}
			if(StringUtils.isNotEmpty(authorization)){
				httppost.setHeader("Authorization", authorization);
			}
			if(StringUtils.isEmpty(contentType)){
				contentType = "application/"+contentType+";charset="+encode;
			}
			httppost.setHeader("Content-Type", "application/"+contentType+";charset=" + encode);
			if(accept){
				httppost.setHeader("Accept", "application/"+method);
			}
		
			// 将参数传入post方法中
			response = client.execute(httppost); // 执行
			Integer status =response.getStatusLine().getStatusCode();
			if(status==200){
				entity = response.getEntity();
				StringBuffer sb = new StringBuffer();
				InputStreamReader iReader = null;
				InputStream inputStream = entity.getContent();
				iReader = new InputStreamReader(inputStream, encode);
				BufferedReader reader = new BufferedReader(iReader);
				String line = null;
	
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\r\n");
				}
				iReader.close();
				return new HttpResult(status,sb.toString());
			}else{
				return new HttpResult(status,response.getStatusLine().getReasonPhrase());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new HttpResult(-1,e.getMessage());
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
}
