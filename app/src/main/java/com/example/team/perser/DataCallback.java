package com.example.team.perser;

import java.util.List;

public interface DataCallback {
    void onDataReceived(List<Object[]> data);
    void onFailure(Throwable t);
}
