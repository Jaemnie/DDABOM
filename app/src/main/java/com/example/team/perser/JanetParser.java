package com.example.team.perser;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.team.database.DatabaseHelper;

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
    // API 응답을 저장할 객체
    private LicenseSearchResponse searchResponse;
    // 자격증 데이터를 저장할 리스트
    private List<Object[]> L_Data;
    private List<Object[]> M_Data;
    // 데이터베이스 헬퍼 객체
    private DatabaseHelper dbHelper;

    // 생성자: DatabaseHelper 초기화
    public JanetParser(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // 자넷 API를 통해 자격증 목록을 가져오는 메서드
    public void Janet_list(DataCallback callback) {
        // Retrofit 인스턴스 생성
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Log.d("호출", "Janet_list: ");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.janet.co.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // GraphQL 쿼리 정의
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
        variables.setItemsPerPage(250); // 가져올 자격증 수를 10개로 제한
        variables.setRequest(requestSet);
        LicenseSearchRequest request = new LicenseSearchRequest();
        // 요청 객체 설정
        request.setQuery(query);
        request.setVariables(variables);
        request.setOperationName("JN_LICENSE_SEARCH_LIST");

        // API 요청
        Call<LicenseSearchResponse> call = service.getLicenseSearchList(request);
        call.enqueue(new Callback<LicenseSearchResponse>() {
            @Override
            public void onResponse(Call<LicenseSearchResponse> call, Response<LicenseSearchResponse> response) {
                if (response.isSuccessful()) {
                    // 응답 성공 시 처리
                    searchResponse = response.body();
                    L_Data = new ArrayList<>();
                    for (License license : searchResponse.getData().getJnLicenseSearchList().getData()) {
                        L_Data.add(new Object[]{license.getLdId(), license.getJmfldnm(), license.getRgName(), license.getLicenseType()});
                        insertLicenseData(license);
                    }
                    callback.onDataReceived(L_Data);
                } else {
                    // 응답 실패 시 처리
                    callback.onFailure(new Exception("Response is not successful"));
                }
            }

            @Override
            public void onFailure(Call<LicenseSearchResponse> call, Throwable throwable) {
                // 요청 실패 시 처리
                callback.onFailure(throwable);
            }
        });
    }

    // 자격증 데이터를 데이터베이스에 삽입하는 메서드
    private void insertLicenseData(License license) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LDID, license.getLdId());
        values.put(DatabaseHelper.COLUMN_JMFLDNM, license.getJmfldnm());
        values.put(DatabaseHelper.COLUMN_RGNAME, license.getRgName());
        values.put(DatabaseHelper.COLUMN_LICENSETYPE, license.getLicenseType());
        db.insert(DatabaseHelper.TABLE_LICENSES, null, values);
    }

    public void Janet_Magazine(String url, DataCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    // li 요소를 선택
                    Elements listItems = doc.select("ul.flleft>li");
                    M_Data = new ArrayList<>();
                    // 각 li 요소를 순회하며 정보 추출
                    for (Element listItem : listItems) {
                        String link = listItem.select("a").attr("href");
                        String imgSrc = listItem.select(".imgWrap img").attr("src");
                        String title = listItem.select("strong").text();
                        String description = listItem.select("p").text();

                        // 추출한 정보 출력
                        M_Data.add(new Object[]{link, imgSrc, title, description});
                    }
                    callback.onDataReceived(M_Data);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.onFailure(e);
                }
            }
        }).start();
    }
}
