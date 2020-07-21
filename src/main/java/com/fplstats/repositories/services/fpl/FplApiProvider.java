package com.fplstats.repositories.services.fpl;

import com.fplstats.common.dto.fplstats.Result;
import com.fplstats.repositories.services.HttpClient;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class FplApiProvider {

    private static String BASE_URL = "https://fantasy.premierleague.com/api/bootstrap-static/";

    public Result<String> getFplPlayerData() throws IOException {

        Result<String> result = new Result<>();
        result.Success = false;

        HttpClient client = new HttpClient();

        try {
            //Create connection
            result.Data = client.sendGet(BASE_URL);


        } catch (Exception e) {
            e.printStackTrace();
            return result;
        } finally {
            client.close();
        }

        result.Success = true;
        return result;
    }
}

