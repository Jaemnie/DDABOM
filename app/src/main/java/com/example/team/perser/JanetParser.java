package com.example.team.perser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JanetParser {
    LicenseSearchResponse searchResponse;
    List<Object[]> L_Data;
    public void Janet_list(DataCallback callback){
// Retrofit 인스턴스 생성
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Log.d("호출", "Janet_list: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.janet.co.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String query = "query JN_LICENSE_SEARCH_LIST($request: LicenseSearchInput, $page: Int, $itemsPerPage: Int) {\n" +
                "  jnLicenseSearchList(request: $request, page: $page, itemsPerPage: $itemsPerPage) {\n" +
                "    page {\n" +
                "      totalPage\n" +
                "      totalItems\n" +
                "      itemsPerPage\n" +
                "      page\n" +
                "      __typename\n" +
                "    }\n" +
                "    data {\n" +
                "      ldId\n" +
                "      jmfldnm\n" +
                "      rgName\n" +
                "      rgImg\n" +
                "      licenseType\n" +
                "      licenseTypeColor\n" +
                "      always\n" +
                "      receiptUrl\n" +
                "      description\n" +
                "      examRegStart\n" +
                "      examRegEnd\n" +
                "      examStart\n" +
                "      examEnd\n" +
                "      passStart\n" +
                "      passEnd\n" +
                "      bigData\n" +
                "      hopeLics\n" +
                "      licenseTags {\n" +
                "        text\n" +
                "        color\n" +
                "        __typename\n" +
                "      }\n" +
                "      __typename\n" +
                "    }\n" +
                "    __typename\n" +
                "  }\n" +
                "}\n";

// 서비스 인터페이스 생성
        useAPI service = retrofit.create(useAPI.class);
// 요청 객체 생성
        Request requestSet = new Request();
        requestSet.setField("main");
        requestSet.setKeyword("");
        requestSet.setNcsCd(Arrays.asList("20"));
        requestSet.setType(Arrays.asList());
        requestSet.setScheduleStatus(Arrays.asList());
        Variables variables = new Variables();
        variables.setPage(1);
        variables.setItemsPerPage(256);
        variables.setRequest(requestSet);
        LicenseSearchRequest request = new LicenseSearchRequest();
// request 객체 설정...
        request.setQuery(query);
        request.setVariables(variables);
        request.setOperationName("JN_LICENSE_SEARCH_LIST");
// API 요청
        Call<LicenseSearchResponse> call = service.getLicenseSearchList(request);
        call.enqueue(new Callback<LicenseSearchResponse>() {
            @Override
            public void onResponse(Call<LicenseSearchResponse> call, Response<LicenseSearchResponse> response) {
                if (response.isSuccessful()) {
// 응답 성공 시 처리...
                    searchResponse = response.body();
                    L_Data = new ArrayList<>();
                    for (License license : searchResponse.getData().getJnLicenseSearchList().getData()) {
                        L_Data.add(new Object[]{license.getLdId(), license.getJmfldnm(), license.getRgName(), license.getLicenseType()});
                    }
                    callback.onDataReceived(L_Data);
                } else {
                    callback.onFailure(new Exception("Response is not successful"));
// 응답 실패 시 처리...
                }
            }
            @Override
            public void onFailure(Call<LicenseSearchResponse> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    public void Janet_page(String url){
        new Thread(() -> {
            try {
                Document doc = Jsoup.connect(url).get();
                // 두 번째 'li' 태그 선택
                Element secondLi = doc.select("ul.ul_list > li:eq(1)").first();

                if (secondLi != null) {
                    // h3 태그 선택 및 출력
                    Element h3Tag = secondLi.selectFirst("div.q>div.q_base>h3");
                    if (h3Tag != null) {
                        Log.v("H3 text: ", h3Tag.text());
                    }
                    String tb = "";
                    // table 태그 선택 및 처리
                    Element table = secondLi.selectFirst("div.a>article.conts>table");
                    if (table != null) {
                        Elements rows = table.select("tr");
                        for (Element row : rows) {
                            // 해당 행의 모든 셀(<td>)을 반복
                            Elements cells = row.select("td");
                            for (Element cell : cells) {
                                // 셀 데이터 출력
                                tb += (cell.text() + "\t");
                            }
                            tb += "\n";
                        }
                    }
                    Log.v("table : ", tb);
                }else{
                    Log.v("오류","데이터없음");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}