package com.eblog.demo.chatgpt.service;

import com.eblog.demo.chatgpt.model.response.PlagReport;
import com.eblog.demo.chatgpt.model.response.TurnitinResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;

@Service
public class Turnitin {

  @SuppressWarnings("deprecation")    //The @SuppressWarnings annotation disables certain compiler warnings. In this case, the warning about deprecated code ("deprecation") and unused local variables or unused private methods ("unused"). 
public TurnitinResponse checkPlagarism(String text){
	  
	  //Factory for calls, which can be used to send HTTP requests and read their responses. Most applications can use a single OkHttpClient for all of their HTTP requests, benefiting from a shared response cache, thread pool, connection re-use, etc.
	 // To create an OkHttpClient with the default settings, use the default constructor. Or create a configured instance with OkHttpClient.Builder. To adjust an existing client before making a request, use newBuilder().
    //OkHttpClient is same as RestTemplate in Springboot
	  
	  OkHttpClient client = new OkHttpClient().newBuilder()
        .readTimeout(50000, TimeUnit.MILLISECONDS)
        .callTimeout(50000, TimeUnit.MILLISECONDS)
        .build();
    MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
    RequestBody body = RequestBody.create(mediaType,
        "is_free=false&plagchecker_locale=en&product_paper_type=1&title=&text="+text);
    Request request = new Request.Builder()
        .url("https://papersowl.com:443/plagiarism-checker-send-data")
        .method("POST", body)
        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:100.0) Gecko/20100101 Firefox/100.0")
        .addHeader("Accept", "*/*")
        .addHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.5,en;q=0.3")
        .addHeader("Accept-Encoding", "gzip, deflate")
        .addHeader("Referer", "https://papersowl.com/free-plagiarism-checker")
        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
        .addHeader("X-Requested-With", "XMLHttpRequest")
        .addHeader("Origin", "https://papersowl.com")
        .addHeader("Dnt", "1")
        .addHeader("Sec-Fetch-Dest", "empty")
        .addHeader("Sec-Fetch-Mode", "no-cors")
        .addHeader("Sec-Fetch-Site", "same-origin")
        .addHeader("Pragma", "no-cache")
        .addHeader("Cache-Control", "no-cache")
        .addHeader("Te", "trailers")
        .addHeader("Connection", "close")
        .addHeader("Cookie",
            "PHPSESSID=qjc72e3vvacbtn4jd1af1k5qn1; first_interaction_user=%7B%22referrer%22%3A%22https%3A%5C%2F%5C%2Fwww.google.com%5C%2F%22%2C%22internal_url%22%3A%22%5C%2Ffree-plagiarism-checker%22%2C%22utm_source%22%3Anull%2C%22utm_medium%22%3Anull%2C%22utm_campaign%22%3Anull%2C%22utm_content%22%3Anull%2C%22utm_term%22%3Anull%2C%22gclid%22%3Anull%2C%22msclkid%22%3Anull%2C%22adgroupid%22%3Anull%2C%22targetid%22%3Anull%2C%22appsflyer_id%22%3Anull%2C%22appsflyer_cuid%22%3Anull%2C%22cta_btn%22%3Anull%7D; first_interaction_order=%7B%22referrer%22%3A%22https%3A%5C%2F%5C%2Fwww.google.com%5C%2F%22%2C%22internal_url%22%3A%22%5C%2Ffree-plagiarism-checker%22%2C%22utm_source%22%3Anull%2C%22utm_medium%22%3Anull%2C%22utm_campaign%22%3Anull%2C%22utm_content%22%3Anull%2C%22utm_term%22%3Anull%2C%22gclid%22%3Anull%2C%22msclkid%22%3Anull%2C%22adgroupid%22%3Anull%2C%22targetid%22%3Anull%2C%22appsflyer_id%22%3Anull%2C%22appsflyer_cuid%22%3Anull%2C%22cta_btn%22%3Anull%7D; affiliate_user=a%3A3%3A%7Bs%3A9%3A%22affiliate%22%3Bs%3A9%3A%22papersowl%22%3Bs%3A6%3A%22medium%22%3Bs%3A9%3A%22papersowl%22%3Bs%3A8%3A%22campaign%22%3Bs%3A9%3A%22papersowl%22%3B%7D; sbjs_migrations=1418474375998%3D1; sbjs_current_add=fd%3D2022-05-24%2019%3A01%3A22%7C%7C%7Cep%3Dhttps%3A%2F%2Fpapersowl.com%2Ffree-plagiarism-checker%7C%7C%7Crf%3Dhttps%3A%2F%2Fwww.google.com%2F; sbjs_first_add=fd%3D2022-05-24%2019%3A01%3A22%7C%7C%7Cep%3Dhttps%3A%2F%2Fpapersowl.com%2Ffree-plagiarism-checker%7C%7C%7Crf%3Dhttps%3A%2F%2Fwww.google.com%2F; sbjs_current=typ%3Dorganic%7C%7C%7Csrc%3Dgoogle%7C%7C%7Cmdm%3Dorganic%7C%7C%7Ccmp%3D%28none%29%7C%7C%7Ccnt%3D%28none%29%7C%7C%7Ctrm%3D%28none%29; sbjs_first=typ%3Dorganic%7C%7C%7Csrc%3Dgoogle%7C%7C%7Cmdm%3Dorganic%7C%7C%7Ccmp%3D%28none%29%7C%7C%7Ccnt%3D%28none%29%7C%7C%7Ctrm%3D%28none%29; sbjs_udata=vst%3D1%7C%7C%7Cuip%3D%28none%29%7C%7C%7Cuag%3DMozilla%2F5.0%20%28Windows%20NT%206.3%3B%20Win64%3B%20x64%3B%20rv%3A100.0%29%20Gecko%2F20100101%20Firefox%2F100.0; sbjs_session=pgs%3D1%7C%7C%7Ccpg%3Dhttps%3A%2F%2Fpapersowl.com%2Ffree-plagiarism-checker; _ga_788D7MTZB4=GS1.1.1653411683.1.0.1653411683.0; _ga=GA1.1.1828699233.1653411683; trustedsite_visit=1; trustedsite_tm_float_seen=1; AppleBannercookie_hide_header_banner=1; COOKIE_PLAGIARISM_CHECKER_TERMS=1; plagiarism_checker_progress_state=1")
        .build();
    try {
      Response response = client.newCall(request).execute();
      if (response.isSuccessful()) {
        String htmlContent = getResponseBodyAsString(response);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
          System.out.println(htmlContent);
          PlagReport plagReport = objectMapper.readValue(htmlContent, PlagReport.class);
          if(plagReport.getError_code() == 0){
            TurnitinResponse turnitinResponse = new TurnitinResponse("success");
            Map<String, Double> urlPercentMap = new HashMap<>();
            double maxPercentage = 0.0;
            for (PlagReport.Match match : plagReport.getMatches()) {
              String url = match.getUrl();
              String percentStr = match.getPercent();
              double percent = Double.parseDouble(percentStr);
              urlPercentMap.put(url, percent);
              if (percent > maxPercentage) {
                maxPercentage = percent;
              }
            }
            if (!plagReport.getMatches().isEmpty()) {
              DecimalFormat decimalFormat = new DecimalFormat("#.##");
              String formattedOverallPercentage = decimalFormat.format(maxPercentage);
              turnitinResponse.setPlagiarismPercentage(formattedOverallPercentage);
            }else{
              turnitinResponse.setPlagiarismPercentage("0.00");
            }
            turnitinResponse.setUrlPercentMap(urlPercentMap);
            return turnitinResponse;
          }
          TurnitinResponse turnitinResponse = new TurnitinResponse("error");
          turnitinResponse.setMessage(plagReport.getMessage());
         return turnitinResponse;
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        System.out.println("Request was not successful: " + response.code());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new TurnitinResponse("error");
  }
  private static String getResponseBodyAsString(Response response) throws IOException {
    ResponseBody responseBody = response.body();
    if (responseBody != null) {
      if (isResponseCompressed(response)) {
        String encoding = response.headers().get("Content-Encoding");
        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
          return decompressGzip(responseBody.byteStream());
        } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
          return decompressDeflate(responseBody.byteStream());
        }
      }
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        PlagReport plagReport = objectMapper.readValue(responseBody.string(), PlagReport.class);
        System.out.println(plagReport);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return responseBody.string();
    }
    return "";
  }

  private static boolean isResponseCompressed(Response response) {
    String encoding = response.headers().get("Content-Encoding");
    return encoding != null && (encoding.equalsIgnoreCase("gzip") || encoding.equalsIgnoreCase("deflate"));
  }

  private static String decompressGzip(InputStream inputStream) throws IOException {
    GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
    return readInputStreamAsString(gzipInputStream);
  }

  private static String decompressDeflate(InputStream inputStream) throws IOException {
    InflaterInputStream inflaterInputStream = new InflaterInputStream(inputStream);
    return readInputStreamAsString(inflaterInputStream);
  }

  private static String readInputStreamAsString(InputStream inputStream) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      stringBuilder.append(new String(buffer, 0, bytesRead));
    }
    return stringBuilder.toString();
  }
}
