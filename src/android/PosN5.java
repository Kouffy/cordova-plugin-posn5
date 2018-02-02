package com.example.plugin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;
import com.google.gson.*;
import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.BarcodeFormatEnum;
import com.nexgo.oaf.apiv3.device.printer.DotMatrixFontEnum;
import com.nexgo.oaf.apiv3.device.printer.FontEntity;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener;
import com.nexgo.oaf.apiv3.device.scanner.Scanner;
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;

import io.cordova.hellocordova.R;

public class PosN5 extends CordovaPlugin {
    private Activity activity;
    private DeviceEngine deviceEngine;
    private CallbackContext callbackContext = null;//返回的回调

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        activity = cordova.getActivity();
        deviceEngine =  APIProxy.getDeviceEngine();
    }

    @Override
    public boolean execute(String action, CordovaArgs args, CallbackContext callback) throws JSONException {
        callbackContext = callback;
        String param = args.getString(0);
        if ("test".equals(action)) {
            callbackContext.success("test");
            return true;
        } else if ("scanner".equals(action)) {
            scanner();
            return true;
        }else if ("printer".equals(action)) {
            printer(param);
            return true;
        }
        return false;
    }

    //调用打印
    private void printer(String str) {
        Gson gson = new Gson();
        Order data=gson.fromJson(str,Order.class);

        //打印相关
        int FONT_SIZE_SMALL = 20;
        int FONT_SIZE_NORMAL = 24;
        int FONT_SIZE_BIG = 24;

        Bitmap bitmap;
        Printer printer=deviceEngine.getPrinter();;
        printer.initPrinter();
        printer.setLetterSpacing(5);
        //bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
       // printer.appendImage(bitmap, AlignEnum.CENTER);
        printer.appendPrnStr("商户名称:应用测试", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("商户号:123456789012345", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("终端号:12345678", "操作员号:01", FONT_SIZE_NORMAL, false);
        printer.appendPrnStr("发卡行:工商银行", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("收单行:银联商务", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("有效期:26/06", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("卡号:", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("621226*********1197/C", FONT_SIZE_BIG, AlignEnum.LEFT, false);
        printer.appendPrnStr("交易类型:", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("消费撤销", FONT_SIZE_BIG, AlignEnum.LEFT, false);
        printer.appendPrnStr("批次号:000938", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("凭证号:000146", "授权码:012345", FONT_SIZE_NORMAL, false);
        printer.appendPrnStr("参考号:150303296481", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("交易日期:2016/09/21 15:03:03", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("金额:", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("    RMB:123456789.00", FONT_SIZE_BIG, AlignEnum.LEFT, false);
        printer.appendPrnStr("备注:", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("原凭证号:000145", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("附加信息(Host):", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendBarcode("1234567890", 50, 0, 2, BarcodeFormatEnum.CODE_128, AlignEnum.CENTER);
        printer.appendQRcode("测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码测试二维码", 200, AlignEnum.CENTER);
        printer.appendPrnStr("---------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("持卡人签名:", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("\n", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("---------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("本人确认以上交易,同意将其计入本卡账户", FONT_SIZE_SMALL, AlignEnum.LEFT, false);
        printer.appendPrnStr("I ACKNOWLEDGE SATISFACTORY RECEIPT OF RELATIVE GOODS/SERVICES", FONT_SIZE_SMALL, AlignEnum.LEFT, false);
        printer.appendPrnStr("---------------------------", FONT_SIZE_NORMAL, AlignEnum.LEFT, false);
        printer.appendPrnStr("商户存根", FONT_SIZE_NORMAL, AlignEnum.RIGHT, false);
        printer.startPrint(true, new OnPrintListener() {
            @Override
            public void onPrintResult(final int retCode) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callbackContext.success(retCode);
                        //Toast.makeText(activity, retCode + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //调用扫码
    private void scanner() {
        final Scanner scanner = deviceEngine.getScanner(activity);
        ScannerCfgEntity cfgEntity = new ScannerCfgEntity();
        cfgEntity.setAutoFocus(false);
        cfgEntity.setUsedFrontCcd(false);
        scanner.initScanner(cfgEntity, new OnScannerListener() {
            @Override
            public void onInitResult(int retCode) {
                if (retCode == SdkResult.Success) {
                    int result = scanner.startScan(60, new OnScannerListener() {
                        @Override
                        public void onInitResult(int retCode) {}
                        @Override
                        public void onScannerResult(int retCode, final String data) {
                            switch (retCode) {
                                case SdkResult.Success:
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callbackContext.success(data);
                                            //Toast.makeText(activity, "扫码内容：" + " " + data, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    break;
                                case SdkResult.TimeOut:
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callbackContext.error("扫码超时");
                                            //Toast.makeText(activity, "扫码超时", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                case SdkResult.Scanner_Customer_Exit:
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callbackContext.error("用户退出");
                                            //Toast.makeText(activity,"用户退出", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                                default:
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            callbackContext.error("扫码失败");
                                            //Toast.makeText(activity, "扫码失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    break;
                            }
                        }
                    });
                    if (result != SdkResult.Success) {
                        callbackContext.error("启动扫码失败");
                        //Toast.makeText(activity, "启动扫码失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    callbackContext.error("扫码初始化失败");
                    //Toast.makeText(activity,"扫码初始化失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScannerResult(int retCode, String data) {}
        });
    }

}
