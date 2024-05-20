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
    private LicenseSearchResponse searchResponse;
    private List<Object[]> L_Data;
    private DatabaseHelper dbHelper;

    public JanetParser(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void Janet_list(DataCallback callback) {
        // Retrofit instance creation
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

        // Service interface creation
        useAPI service = retrofit.create(useAPI.class);
        // Request object creation
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
        // Set request object...
        request.setQuery(query);
        request.setVariables(variables);
        request.setOperationName("JN_LICENSE_SEARCH_LIST");

        // API request
        Call<LicenseSearchResponse> call = service.getLicenseSearchList(request);
        call.enqueue(new Callback<LicenseSearchResponse>() {
            @Override
            public void onResponse(Call<LicenseSearchResponse> call, Response<LicenseSearchResponse> response) {
                if (response.isSuccessful()) {
                    // On successful response...
                    searchResponse = response.body();
                    L_Data = new ArrayList<>();
                    for (License license : searchResponse.getData().getJnLicenseSearchList().getData()) {
                        L_Data.add(new Object[]{license.getLdId(), license.getJmfldnm(), license.getRgName(), license.getLicenseType()});
                        insertLicenseData(license);
                    }
                    callback.onDataReceived(L_Data);
                } else {
                    callback.onFailure(new Exception("Response is not successful"));
                    // On failed response...
                }
            }

            @Override
            public void onFailure(Call<LicenseSearchResponse> call, Throwable throwable) {
                callback.onFailure(throwable);
            }
        });
    }

    private void insertLicenseData(License license) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_LDID, license.getLdId());
        values.put(DatabaseHelper.COLUMN_JMFLDNM, license.getJmfldnm());
        values.put(DatabaseHelper.COLUMN_RGNAME, license.getRgName());
        values.put(DatabaseHelper.COLUMN_LICENSETYPE, license.getLicenseType());
        db.insert(DatabaseHelper.TABLE_LICENSES, null, values);
    }

    public void Janet_page(String url) {
        new Thread(() -> {
            try {
                Document doc = Jsoup.connect(url).get();
                // Select the second 'li' tag
                Element secondLi = doc.select("ul.ul_list > li:eq(1)").first();

                if (secondLi != null) {
                    // Select and print the h3 tag
                    Element h3Tag = secondLi.selectFirst("div.q>div.q_base>h3");
                    if (h3Tag != null) {
                        Log.v("H3 text: ", h3Tag.text());
                    }
                    String tb = "";
                    // Select and process the table tag
                    Element table = secondLi.selectFirst("div.a>article.conts>table");
                    if (table != null) {
                        Elements rows = table.select("tr");
                        for (Element row : rows) {
                            // Loop through all cells (<td>) in the row
                            Elements cells = row.select("td");
                            for (Element cell : cells) {
                                // Print cell data
                                tb += (cell.text() + "\t");
                            }
                            tb += "\n";
                        }
                    }
                    Log.v("table : ", tb);
                } else {
                    Log.v("오류", "데이터없음");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
