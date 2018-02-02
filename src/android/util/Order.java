package com.example.plugin.util;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-11-07.
 */

//要打印的整体信息
public class Order {
    //基本信息
    private String type; //类型  0采购   1销售   2零售  3 库存
    private String orderName; //单据类型  采购 收货 退货 付款 ,出货 收款 客户退货 销售,零售,损益 盘点 调拨
    private String orderNo;//订单号
    private String placeDate;//下单日期
    private String date;//订单交期
    private String typeName;//支出类型
    private String accountName;//结算账户 收款账户
    private String advanceMoney;//预付金额 付款金额
    private String amount;//采购总数
    private String money;//采购总额 销售金额 应收金额
    private String sponsorName;//经手人
    private ArrayList<Detail> detail=new ArrayList<Detail>();//单据信息
    private ArrayList<Account> account=new ArrayList<Account>();//账户信息
    private ArrayList<Goods> goods=new ArrayList<Goods>();//货品
    private String printTime;//打印时间

    private String supplierName;//供应商
    private String customerName;//客户

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPlaceDate() {
        return placeDate;
    }

    public void setPlaceDate(String placeDate) {
        this.placeDate = placeDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAdvanceMoney() {
        return advanceMoney;
    }

    public void setAdvanceMoney(String advanceMoney) {
        this.advanceMoney = advanceMoney;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public ArrayList<Detail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<Detail> detail) {
        this.detail = detail;
    }

    public ArrayList<Account> getAccount() {
        return account;
    }

    public void setAccount(ArrayList<Account> account) {
        this.account = account;
    }

    public ArrayList<Goods> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<Goods> goods) {
        this.goods = goods;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMemberCard_MemberName() {
        return memberCard_MemberName;
    }

    public void setMemberCard_MemberName(String memberCard_MemberName) {
        this.memberCard_MemberName = memberCard_MemberName;
    }

    public String getPerformanceMoney() {
        return performanceMoney;
    }

    public void setPerformanceMoney(String performanceMoney) {
        this.performanceMoney = performanceMoney;
    }

    public String getAccountScoreMoney() {
        return accountScoreMoney;
    }

    public void setAccountScoreMoney(String accountScoreMoney) {
        this.accountScoreMoney = accountScoreMoney;
    }

    public String getAccountTicketMoney() {
        return accountTicketMoney;
    }

    public void setAccountTicketMoney(String accountTicketMoney) {
        this.accountTicketMoney = accountTicketMoney;
    }

    public String getActualMoney() {
        return actualMoney;
    }

    public void setActualMoney(String actualMoney) {
        this.actualMoney = actualMoney;
    }

    public String getSourceOrderNo() {
        return sourceOrderNo;
    }

    public void setSourceOrderNo(String sourceOrderNo) {
        this.sourceOrderNo = sourceOrderNo;
    }

    public String getCheckClass() {
        return checkClass;
    }

    public void setCheckClass(String checkClass) {
        this.checkClass = checkClass;
    }

    public String getFromWarehouseName() {
        return fromWarehouseName;
    }

    public void setFromWarehouseName(String fromWarehouseName) {
        this.fromWarehouseName = fromWarehouseName;
    }

    public String getToWarehouseName() {
        return toWarehouseName;
    }

    public void setToWarehouseName(String toWarehouseName) {
        this.toWarehouseName = toWarehouseName;
    }

    //零售独有
    private String memberCard_MemberName;//会员名
    private String performanceMoney;//促销金额
    private String accountScoreMoney;//积分抵扣
    private String accountTicketMoney;//代金券
    private String actualMoney;//实收金额

    //损益单独有
    private String sourceOrderNo;//原始单号

    //盘点单独有
    private String checkClass;//盘点级别

    //调拨单独有
    private String fromWarehouseName;//调出仓库
    private String toWarehouseName;//调入仓库


    //货品信息
    public class Goods{
        private String kindName;//名称
        private String colorName;//颜色
        private String sizeText;//尺码
        private String price;//采购价
        private String amount;//数量
        private String retailPrice;//成本
        private String taxRate; //税率
        private String taxMoney;//税额
        private String discountRate;//折扣
        private String inventory;//库存
        private String Zmoney;//总金额
        private String checkAmount;//盘点数量
        private String profitLossAmount;//盈亏数量

        public String getKindName() {
            return kindName;
        }

        public void setKindName(String kindName) {
            this.kindName = kindName;
        }

        public String getColorName() {
            return colorName;
        }

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public String getSizeText() {
            return sizeText;
        }

        public void setSizeText(String sizeText) {
            this.sizeText = sizeText;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(String retailPrice) {
            this.retailPrice = retailPrice;
        }

        public String getTaxRate() {
            return taxRate;
        }

        public void setTaxRate(String taxRate) {
            this.taxRate = taxRate;
        }

        public String getTaxMoney() {
            return taxMoney;
        }

        public void setTaxMoney(String taxMoney) {
            this.taxMoney = taxMoney;
        }

        public String getDiscountRate() {
            return discountRate;
        }

        public void setDiscountRate(String discountRate) {
            this.discountRate = discountRate;
        }

        public String getInventory() {
            return inventory;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public String getZmoney() {
            return Zmoney;
        }

        public void setZmoney(String zmoney) {
            Zmoney = zmoney;
        }

        public String getCheckAmount() {
            return checkAmount;
        }

        public void setCheckAmount(String checkAmount) {
            this.checkAmount = checkAmount;
        }

        public String getProfitLossAmount() {
            return profitLossAmount;
        }

        public void setProfitLossAmount(String profitLossAmount) {
            this.profitLossAmount = profitLossAmount;
        }
    }

    //单据信息
    public class Detail {//单据信息
        private String no;//单号
        private String date;//日期
        private String typeName;//单据类型
        private String sumMoney;//单据金额
        private String money;//本次付款额

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getSumMoney() {
            return sumMoney;
        }

        public void setSumMoney(String sumMoney) {
            this.sumMoney = sumMoney;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }
    }

    //账户信息
    public class Account{
        private String accountName;//账户名称
        private String accountCode;//账户代码
        private String money;//付款金额
        private String feeMoney;//手续费
        private String accountingMoney;//支出金额
        private String description;//备注

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getAccountCode() {
            return accountCode;
        }

        public void setAccountCode(String accountCode) {
            this.accountCode = accountCode;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getFeeMoney() {
            return feeMoney;
        }

        public void setFeeMoney(String feeMoney) {
            this.feeMoney = feeMoney;
        }

        public String getAccountingMoney() {
            return accountingMoney;
        }

        public void setAccountingMoney(String accountingMoney) {
            this.accountingMoney = accountingMoney;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}

