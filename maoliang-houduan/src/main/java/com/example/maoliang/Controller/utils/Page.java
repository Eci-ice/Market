package com.example.maoliang.Controller.utils;

import lombok.Data;

@Data
public class Page {
    public static final String ORIGINAL=null;//原界面
    public static final String LOGIN_PAGE="/login";
    public static final String SELLER_PAGE="/seller";
    public static final String BUYER_PAGE="/buyer";
    public static final String ERROR_PAGE="/error";
    public static final String SUCCESS_PAGE="/success";
    public static final String RENAME_PAGE="/rename";
    public static final String ANSWER_PAGE="/answer";
    public static final String UPDATEPWD_PAGE="/seller/update-password";
    public static final String SELLER_ALL_GOODS_PAGE="/seller/ShowGoods";
    public static final String SELLER_ALL_HISTROYGOODS_PAGE="/seller/show-historygoods";
    public static final String SELLER_SEARCHLIST_PAGE="/seller/show-searchgoods";
    public static final String SELLER_SEARCHHISTROYLIST_PAGE="/seller/show-searchhistorygoods";
    public static final String SELLER_UPLOAD_GOOD_PAGE="/seller/upload-good";
    public static final String SELLER_UPLOAD_MULTI_GOODS_PAGE="/seller/upload-multiplegoods";
    public static final String BUYER_ALL_GOODS_PAGE="/buyer";
    public static final String BUYER_SEARCHLIST_PAGE="/buyer-search";
    public static final String BUYER_SHOP_PAGE="/buyer-shop";

}
