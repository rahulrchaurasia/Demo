package com.example.jetpackdemo.Scanner.ScannerUtil;

public interface ScannerListener {
    void onDetected(String detections);
    void onStateChanged(String state, int i);
}
