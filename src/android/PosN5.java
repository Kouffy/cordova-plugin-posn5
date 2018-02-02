package com.example.plugin;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.RemoteException;
import android.util.Log;

import com.example.plugin.util.Order;
import com.google.gson.*;
import com.nexgo.oaf.apiv3.APIProxy;
import com.nexgo.oaf.apiv3.DeviceEngine;
import com.nexgo.oaf.apiv3.SdkResult;
import com.nexgo.oaf.apiv3.device.printer.AlignEnum;
import com.nexgo.oaf.apiv3.device.printer.BarcodeFormatEnum;
import com.nexgo.oaf.apiv3.device.printer.OnPrintListener;
import com.nexgo.oaf.apiv3.device.printer.Printer;
import com.nexgo.oaf.apiv3.device.scanner.OnScannerListener;
import com.nexgo.oaf.apiv3.device.scanner.Scanner;
import com.nexgo.oaf.apiv3.device.scanner.ScannerCfgEntity;
import com.start.smartpos.aidl.device.printer.PrinterFormat;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONException;

import java.io.IOException;

public class PosN5 extends CordovaPlugin {
    private Activity activity;
    private DeviceEngine deviceEngine;
    //打印
    private Printer printer = deviceEngine.getPrinter();
    ;
    //打印相关
    private int FONT_SIZE_SMALL = 20;
    private int FONT_SIZE_NORMAL = 24;
    private AlignEnum FONT_ALIGN = AlignEnum.LEFT;
    private int FONT_SIZE_BIG = 24;

    private CallbackContext callbackContext = null;//返回的回调

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        activity = cordova.getActivity();
        deviceEngine = APIProxy.getDeviceEngine();
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
        } else if ("printer".equals(action)) {
            printer(param);
            return true;
        }
        return false;
    }

    //调用打印
    private void printer(String str) {
        Gson gson = new Gson();
        Order data = new Order();
        try {
            data = gson.fromJson(str, Order.class);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(activity.getAssets().open("print.bmp"), null, opts);
            } catch (IOException e) {
                e.printStackTrace();
            }
            printer.initPrinter();
            printer.setLetterSpacing(5);
            printer.appendImage(bitmap, AlignEnum.CENTER);

            //基本信息
            printText("订单号:" + data.getOrderNo(), data.getOrderNo());
            if ("0".equals(data.getType())) {//采购
                if ("付款".equals(data.getOrderName())) {
                    printText("收款单位:" + data.getSupplierName(), data.getSupplierName());
                } else {
                    printText("供应商:" + data.getSupplierName(), data.getSupplierName());
                }
                printText("日期:" + data.getDate(), data.getDate());
                if (data.getPlaceDate() != null) {
                    printText("订单交期:" + data.getPlaceDate(), data.getPlaceDate());
                }
                if ("采购".equals(data.getOrderName())) {
                    printText("预付金额:" + data.getAdvanceMoney(), data.getAdvanceMoney());
                    printText("采购总数:" + data.getAmount(), data.getAmount());
                    printText("采购总额:" + data.getMoney(), data.getMoney());
                } else if ("收货".equals(data.getOrderName())) {
                    printText("付款金额:" + data.getAdvanceMoney(), data.getAdvanceMoney());
                } else if ("退货".equals(data.getOrderName())) {
                    if (data.getAccountName() != null) {
                        printText("结算账户:" + data.getAccountName(), data.getAccountName());
                        printText("本次退款:" + data.getAdvanceMoney(), data.getAdvanceMoney());
                    }
                } else if ("付款".equals(data.getOrderName())) {
                    printText("支出类型:" + data.getTypeName(), data.getTypeName());
                }
            }
            if ("1".equals(data.getType())) {//销售
                printText(data.getOrderName() + "客户:" + data.getCustomerName(), data.getCustomerName());
                printText(data.getOrderName() + "日期:" + data.getDate(), data.getDate());
                if (data.getPlaceDate() != null) {
                    printText("订单交期:" + data.getPlaceDate(), data.getPlaceDate());
                }
                if ("出货".equals(data.getOrderName())) {
                    if (data.getAccountName() != null) {
                        printText("收款账户:" + data.getAccountName(), data.getAccountName());
                    }
                    printText("出货总量:" + data.getAmount(), data.getAmount());
                    printText("出货总额:" + data.getMoney(), data.getMoney());
                } else if ("收款".equals(data.getOrderName())) {
                    printText("收款类型:" + data.getTypeName(), data.getTypeName());
                } else if ("客户退货".equals(data.getOrderName())) {
                    printText("退货总量:" + data.getAmount(), data.getAmount());
                    printText("退货总额:" + data.getMoney(), data.getMoney());
                    if (data.getAccountName() != null) {
                        printText("退款账户:" + data.getAccountName(), data.getAccountName());
                        printText("本次退款:" + data.getAdvanceMoney(), data.getAdvanceMoney());
                    }
                } else if ("销售".equals(data.getOrderName())) {
                    printText("定金金额:" + data.getAdvanceMoney(), data.getAdvanceMoney());
                    printText("销售数量:" + data.getAmount(), data.getAmount());
                    printText("销售金额:" + data.getMoney(), data.getMoney());
                }
            }
            if ("2".equals(data.getType()) && "零售".equals(data.getOrderName())) {//零售
                printText(data.getOrderName() + "日期:" + data.getDate(), data.getDate());
                if (data.getMemberCard_MemberName() != null) {
                    printText("会员信息:" + data.getMemberCard_MemberName(), data.getMemberCard_MemberName());
                }
                printText("销售金额:" + data.getMoney(), data.getMoney());
                printText("促销金额:" + data.getPerformanceMoney(), data.getPerformanceMoney());
                printText("应收金额 :" + data.getMoney(), data.getMoney());
                printText("积分抵扣:" + data.getAccountScoreMoney(), data.getAccountScoreMoney());
                printText("代金券:" + data.getAccountTicketMoney(), data.getAccountTicketMoney());
                printText("实收金额:" + data.getActualMoney(), data.getActualMoney());
            }
            if ("3".equals(data.getType())) {//库存
                printText("日期:" + data.getDate(), data.getDate());
                if ("损益".equals(data.getOrderName())) {
                    printText("原始单号:" + data.getSourceOrderNo(), data.getSourceOrderNo());
                    printText("益损总数:" + data.getAmount(), data.getAmount());
                    printText("益损总额:" + data.getMoney(), data.getMoney());
                } else if ("盘点".equals(data.getOrderName())) {
                    printText("盘点级别:" + data.getCheckClass(), data.getCheckClass());
                } else if ("调拨".equals(data.getOrderName())) {
                    printText("调出仓库:" + data.getFromWarehouseName(), data.getFromWarehouseName());
                    printText("调入仓库:" + data.getToWarehouseName(), data.getToWarehouseName());
                }
            }
            printText("经手人:" + data.getSponsorName(), data.getSponsorName());

            //付款-收款单信息
            if (data.getDetail() != null && data.getDetail().size() > 0) {
                printText(" ", "");
                printText("-----------单据信息-----------", "");
                for (int i = 0; i < data.getDetail().size(); i++) {
                    com.example.plugin.util.Order.Detail detail = data.getDetail().get(i);
                    printText(data.getOrderName() + "单号:" + detail.getNo(), detail.getNo());
                    printText("日期:" + detail.getDate(), detail.getDate());
                    printText("单据类型:" + detail.getTypeName(), detail.getTypeName());
                    if (detail.getSumMoney() != null && detail.getMoney() != null) {
                        printText(data.getOrderName() + "金额:" + detail.getSumMoney() + "  本次" + data.getOrderName() + "额" + detail.getMoney(), "");
                    }
                    printText("------------------------------", "");
                }
            }

            //付款-收款账户信息
            if (data.getAccount() != null && data.getAccount().size() > 0) {
                printText(" ", "");
                printText("-----------账户信息-----------", "");
                for (int i = 0; i < data.getAccount().size(); i++) {
                    com.example.plugin.util.Order.Account account = data.getAccount().get(i);
                    printText("账户名称:" + account.getAccountName(), account.getAccountName());
                    printText("账户代码:" + account.getAccountCode(), account.getAccountCode());
                    if (account.getFeeMoney() != null) {
                        printText(data.getOrderName() + "金额:" + account.getMoney() + "  手续费" + account.getFeeMoney(), account.getMoney());
                    } else {
                        printText(data.getOrderName() + "金额:" + account.getMoney(), account.getMoney());
                    }
                    if (account.getAccountingMoney() != null && account.getDescription() != null) {
                        printText("支出金额:" + account.getAccountingMoney() + "  备注" + account.getDescription(), "");
                    }
                    if ("付款".equals(data.getOrderName())) {
                        printText("支出金额:" + account.getAccountingMoney(), account.getAccountingMoney());
                    } else {
                        printText("收款金额:" + account.getAccountingMoney(), account.getAccountingMoney());
                    }

                    printText("------------------------------", "");
                }
            }

            //货品信息
            if (data.getGoods() != null && data.getGoods().size() > 0) {
                printText(" ", "");
                printText("-----------货品信息-----------", "");
                for (int i = 0; i < data.getGoods().size(); i++) {
                    com.example.plugin.util.Order.Goods good = data.getGoods().get(i);
                    printText("名称:" + good.getKindName(), good.getKindName());
                    if (good.getColorName() != null && good.getSizeText() != null) {
                        printText("颜色:" + good.getColorName() + "  尺码:" + good.getSizeText(), "");
                    }
                    if ("0".equals(data.getType())) {
                        if (good.getZmoney() != null && good.getAmount() != null) {
                            printText(data.getOrderName() + "价:" + good.getZmoney() + "  数量:" + good.getAmount(), "");
                        }
                    } else if ("1".equals(data.getType()) || "2".equals(data.getType())) {
                        if (good.getZmoney() != null && good.getAmount() != null) {
                            printText("批发价:" + good.getZmoney() + "  数量:" + good.getAmount(), "");
                        }
                    } else if ("3".equals(data.getType()) && "损益".equals(data.getOrderName())) {
                        if (good.getZmoney() != null && good.getAmount() != null) {
                            printText("成本价:" + good.getZmoney() + "  数量:" + good.getAmount(), "");
                        }
                    }
                    if (good.getTaxRate() != null && good.getTaxMoney() != null) {
                        printText("税率:" + good.getTaxRate() + "  税额:" + good.getTaxMoney(), "");
                    }
                    if ("3".equals(data.getType())) {
                        if ("损益".equals(data.getOrderName())) {
                            if (good.getZmoney() != null && good.getInventory() != null) {
                                printText("总额:" + good.getZmoney() + "   库存:" + good.getInventory(), "");
                            }
                        } else if ("盘点".equals(data.getOrderName())) {
                            printText("账面数量:" + good.getAmount(), good.getAmount());
                            printText("盘点数量:" + good.getCheckAmount(), good.getCheckAmount());
                            printText("盈亏数量:" + good.getProfitLossAmount(), good.getProfitLossAmount());
                        } else if ("调拨".equals(data.getOrderName())) {
                            printText("调拨数量:" + good.getAmount(), good.getAmount());
                        }
                    } else if (good.getDiscountRate() != null && good.getZmoney() != null) {
                        printText("折扣:" + good.getDiscountRate() + "  总金额:" + good.getZmoney(), "");
                    }
                    printText("------------------------------", "");
                }
            }
            printText("打印时间:" + data.getPrintTime(), data.getPrintTime());
            printer.startPrint(true, new OnPrintListener() {
                @Override
                public void onPrintResult(final int retCode) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callbackContext.success(retCode);
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.i("rain", e.getMessage());
        }
    }

    //打印相关
    private boolean printText(String text, String old) throws RemoteException {
        if (old != null && !"0".equals(old) && !"0.00".equals(old)) {
            printer.appendPrnStr("商户名称:应用测试", FONT_SIZE_NORMAL, FONT_ALIGN, false);
        }
        return false;
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
                        public void onInitResult(int retCode) {
                        }

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
            public void onScannerResult(int retCode, String data) {
            }
        });
    }

}
