package com.lacunaexpanse.LacunaAndroidClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Library {
	public static String parseParams(String[] target) {
		StringBuilder sbOne = new StringBuilder();
		sbOne.append("[");
		
		for (int i = 0; i < target.length; i++) {
			sbOne.append("\"" + target[i] + "\",");
		}
		
		String resultOne = sbOne.toString();
		resultOne = resultOne.substring(0, resultOne.length()-1);
		
		StringBuilder sbTwo = new StringBuilder(resultOne);
		sbTwo.append("]");
		String result = sbTwo.toString();
		
		return result;
	}
	
	public static String assembleGetUrl(String selectedServer,String module,String method,String params) {
		String encodedParams = null;
		try {
			encodedParams = URLEncoder.encode(params,"utf-8");
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		
		sb.append("http://" + selectedServer + ".lacunaexpanse.com/" + module + "?jsonrpc=2.0&id=1&method=" + method + "&params=" + encodedParams);
		
		String finalURL = sb.toString();
		
		return finalURL;
	}
	
	public static String sendServerRequest(String serverUrl) {
		URI uri = null;
		String receivedData = null;
		String line = null;

		try {
			HttpClient client = new DefaultHttpClient();
			uri = new URI(serverUrl);
			HttpGet request = new HttpGet();
			request.setURI(uri);
			HttpResponse response = client.execute(request);
			BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder sb = new StringBuilder();

			while ((line = in.readLine()) != null) {
				sb.append(line + "\n");
			}

			receivedData = sb.toString();
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return receivedData;
	}
}