/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.util.Dictionary;
import java.util.HashMap;

/**
 *
 * @author baodubai
 */
public class Data {
    public static Data instance;
    public static Data gI(){
     if (instance == null) instance = new Data();
     return instance;
    }
    HashMap<String, String[]> CuaHangBieuTuong = new HashMap<String, String[]>();
    public void InitData(){
        CuaHangBieuTuong.put("0", new String[]{"Huy Hiệu Đá Xanh","Được đúc từ phiến đá Xanh ở Làng FooSha"});
        CuaHangBieuTuong.put("1", new String[]{"Huy Hiệu Đá Đỏ","Được đúc từ phiến đá Đỏ ở Làng FooSha"});
        CuaHangBieuTuong.put("2", new String[]{"Huy Hiệu Đá Tím","Được đúc từ phiến đá Tím ở Làng FooSha"});
        CuaHangBieuTuong.put("3", new String[]{"Huy Hiệu Đá Vàng","Được đúc từ phiến đá Vàng ở Làng FooSha"});
        CuaHangBieuTuong.put("4", new String[]{"Huy Hiệu Đá Xanh","Được đúc từ phiến đá Xanh ở Làng FooSha"});
        CuaHangBieuTuong.put("5", new String[]{"Huy Hiệu Sao Thủy",  "Được làm từ tinh thể Sao Biển ở Đảo Vỏ Sò"});
        CuaHangBieuTuong.put("6", new String[]{"Huy Hiệu Sao Kim","Được làm từ tinh thể Sao Biển ở Đảo Vỏ Sò"});
        CuaHangBieuTuong.put("7", new String[]{"Huy Hiệu Sao Thổ","Được làm từ tinh thể Sao Biển ở Đảo Vỏ Sò"});
        CuaHangBieuTuong.put("8", new String[]{"Huy Hiệu Sao Mộc","Được làm từ tinh thể Sao Biển ở Đảo Vỏ Sò"});
        CuaHangBieuTuong.put("9", new String[]{"Huy Hiệu Sao Hỏa","Được làm từ tinh thể Sao Biển ở Đảo Vỏ Sò"});
        //System.out.println("Load Cua Hang Bieu Tuong: " + CuaHangBieuTuong.size() + " SUCCESS !!!");
        for (int i = 0; i < Data.gI().CuaHangBieuTuong.size();i++){
                    var data = Data.gI().CuaHangBieuTuong.get(""+i);
                                        //System.out.print("data: " + data[0] + " | " + data[1]);
}  
}
}
