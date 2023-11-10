package activities;

import java.io.IOException;
import client.Player;
import core.Log;
import core.Manager;
import core.Service;
import core.Util;
import io.Message;
import template.ItemBag47;
import template.ItemTemplate4;
import template.Item_wear;
import template.Option;

public class Kham_Ngoc {

    public static short[] ID_SELL = new short[]{74, 68, 62, 56, 50, 44, 323, 339, 241, 242, 243, 244, 245, 246, 247, 248, 249,
         250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270};
    public static int[] ID_SELL_PRICE = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};

    public static void show_table(Player p, int type) throws IOException {
        Message m = new Message(-67);
        m.writer().writeByte(0);
        switch (type) {
            case 0: {
                m.writer().writeByte(2);
                break;
            }
            case 1: {
                m.writer().writeByte(4);
                break;
            }
            case 2: {
                m.writer().writeByte(1);
                break;
            }
            case 3: {
                m.writer().writeByte(3);
                break;
            }
            case 4: {
                m.writer().writeByte(10);
            }
            case 5: {
                m.writer().writeByte(11);
            }
        }
        p.conn.addmsg(m);
        m.cleanup();
        p.item_to_kham_ngoc = null;
        p.item_to_kham_ngoc_id_ngoc = -1;
    }

    public static String[] mTiLeKichAn = new String[]{
        "22->30",
        "20->28",
        "18->26",
        "16->24",
        "14->22",
        "1->5",
        "0->0"
    };
    
    public static void process(Player p, Message m2) throws IOException {
        byte type = m2.reader().readByte();
        byte action = m2.reader().readByte();
        short idItem = m2.reader().readShort();
        byte cat = m2.reader().readByte();
        short num = m2.reader().readShort();
        m2.reader().readShort();
        // System.out.println(type);
        // System.out.println(action);
        // System.out.println(idItem);
        // System.out.println(cat);
        // System.out.println(num);
        if (cat == 3 && num == 1 && type == 2 && action == 1) { // bo item duc lo
            Item_wear it_select = p.item.bag3[idItem];
            if (it_select != null) {
                if (it_select.numLoKham >= 7) {
                    Service.send_box_ThongBao_OK(p, "Vật phẩm chỉ được tối đa 7 lỗ khảm");
                    return;
                }
                Message m = new Message(-67);
                m.writer().writeByte(1);
                m.writer().writeShort(idItem);
                m.writer().writeByte(3);
                m.writer().writeShort(1);
                p.conn.addmsg(m);
                m.cleanup();
            }
        } 
        else if (cat == 3 && num == 1 && type == 2 && action == 7) { // duc lo
            Item_wear it_select = p.item.bag3[idItem];
            if (it_select != null) {
                if (it_select.valueChetac <= 0) {
                    // Service.send_box_ThongBao_OK(p, "Vật phẩm không đủ phẩm chất để thực hiện!");
                    // return;
                }
                if (it_select.numLoKham >= 7) {
                    Service.send_box_ThongBao_OK(p, "Vật phẩm chỉ được tối đa 7 lỗ khảm");
                    return;
                }
                int vang_req = 50 * (it_select.numLoKham + 1);
                
                if (p.get_ngoc() < vang_req) {
                    Service.send_box_ThongBao_OK(p, "Không đủ " + vang_req + " ruby!");
                    return;
                }
                if (p.item.total_item_bag_by_id(4, 323) > 0) {
                    p.item.remove_item47(4, 323, 1);
                } 
                else if (p.item.total_item_bag_by_id(4, 339) > 0) {
                    p.item.remove_item47(4, 339, 1);
                } 
                else {
                    Service.send_box_ThongBao_OK(p, "Không đủ búa đục trong hành trang!");
                    return;
                }
                p.update_ngoc(-vang_req);
                p.update_money();
                Log.gI().add_log(p, "Đục lỗ " + it_select.name + " -" + vang_req + " ruby");
                boolean suc = true;
                it_select.numLoKham += 1;
                Kham_Ngoc.show_table(p, 0);
                p.item.update_Inventory(4, false);
                p.item.update_Inventory(7, false);
                p.item.update_Inventory(3, false);
                Kham_Ngoc.show_table(p, 0);
                if (suc) {
                    Service.send_box_ThongBao_OK(p, "Đục lỗ thành công");
                } else {
                    Service.send_box_ThongBao_OK(p, "Đục lỗ thất bại!");
                }
            } 
            else
            {
                Kham_Ngoc.show_table(p, 0);
                Service.send_box_ThongBao_OK(p, "Bạn chưa bỏ vật phẩm vào");
            }
        } 
        else if (cat == 4 && num > 0 && type == 4 && action == 1) { // bo da kham vao de hop
            if(!checkIsDaKham(idItem)){
                Service.send_box_ThongBao_OK(p, "Vật phẩm không phải đá khảm");
                return;
            }
            if (p.item.total_item_bag_by_id(4, idItem) < num){
                Service.send_box_ThongBao_OK(p, "Không đủ vật phẩm trong hành trang");
                return;
            }
            Message m = new Message(-67);
            m.writer().writeByte(1);
            m.writer().writeShort(idItem);
            m.writer().writeByte(4);
            m.writer().writeShort(num);
            p.conn.addmsg(m);
            m.cleanup();
        } else if (cat == 4 && num > 0 && type == 4 && action == 5) { // hop da kham
            if (idItem >= 44 && idItem <= 78 && idItem != 73 && idItem != 67 && idItem != 61 && idItem != 55
                    && idItem != 49) {
                if (p.item.total_item_bag_by_id(4, idItem) < num) {
                    Service.send_box_ThongBao_OK(p, "Không đủ vật phẩm trong hành trang!");
                    return;
                }
                int vang_req = 50;
                if (p.get_ngoc() < vang_req) {
                    Service.send_box_ThongBao_OK(p, "Không đủ " + vang_req + " ruby!");
                    return;
                }
                int it_quan_can_process = (num - (num % 3)) / 3;
                //
                ItemBag47 it = new ItemBag47();
                it.id = (short) (idItem + 1);
                it.category = 4;
                it.quant = (short) it_quan_can_process;
                if (p.item.able_bag() > 0 && (p.item.total_item_bag_by_id(4, it.id) + it.quant) < 32_000) {
                    p.update_ngoc(-vang_req);
                    p.update_money();
                    Log.gI().add_log(p,
                            "Hợp đá khảm -> " + ItemTemplate4.get_it_by_id(it.id).name + " -" + vang_req + " ruby");
                    //
                    p.item.remove_item47(4, idItem, (it_quan_can_process * 3));
                    p.item.add_item_bag47(4, it);
                    p.item.update_Inventory(4, false);
                    p.item.update_Inventory(7, false);
                    p.item.update_Inventory(3, false);
                    Kham_Ngoc.show_table(p, 1);
                    Service.send_box_ThongBao_OK(p,
                            "Nhận được " + it_quan_can_process + " " + ItemTemplate4.get_item_name(it.id));
                } 
                else {
                    Kham_Ngoc.show_table(p, 1);
                    Service.send_box_ThongBao_OK(p, "Hành trang không đủ chỗ trống!");
                }
            
            }
            else {
                Kham_Ngoc.show_table(p, 1);
                Service.send_box_ThongBao_OK(p, "Vật phẩm không hợp lệ!");
            }
        } 
        else if (cat == 3 && num == 1 && type == 1 && action == 1) { // bo item kham ngoc vao
            Item_wear it_select = p.item.bag3[idItem];
            if (it_select != null) {
                if(it_select.mdakham.length >= 7) {
                    Service.send_box_ThongBao_OK(p, "Số lượng ngọc khảm của trang bị đã đạt tối đa");
                    return;
                }
                if(it_select.numLoKham <= 0) {
                    Service.send_box_ThongBao_OK(p, "Vật phẩm không có lỗ khảm");
                    return;
                }
                if(it_select.numLoKham <= it_select.mdakham.length) {
                    Service.send_box_ThongBao_OK(p, "Vật phẩm không còn lỗ khảm trống");
                    return;
                }
                Message m = new Message(-67);
                m.writer().writeByte(1);
                m.writer().writeShort(idItem);
                m.writer().writeByte(3);
                m.writer().writeShort(1);
                p.conn.addmsg(m);
                m.cleanup();
                p.item_to_kham_ngoc = it_select;
            }
        }
        else if (cat == 4 && num == 1 && type == 1 && action == 1) { // bo ngoc kham vao
            if (p.item.total_item_bag_by_id(4, idItem) < num) {
                Service.send_box_ThongBao_OK(p, "Không đủ vật phẩm trong hành trang");
                return;
            }
            if(p.item_to_kham_ngoc == null)
            {
                Service.send_box_ThongBao_OK(p, "Hãy bỏ vật phẩm muốn khảm vào trước");
                return;
            }
            if(idItem == 325) {
                int count = 0;
                for(int i = 0; i < p.item_to_kham_ngoc.mdakham.length; i++) {
                    if(p.item_to_kham_ngoc.mdakham[i] == idItem) {
                        count++;
                    }
                }
                if(count >= 3) {
                    Service.send_box_ThongBao_OK(p, "Loại đá này chỉ được phép khảm 3 viên trên mỗi vật phẩm");
                    return;
                }
            }
            Message m = new Message(-67);
            m.writer().writeByte(1);
            m.writer().writeShort(idItem);
            m.writer().writeByte(4);
            m.writer().writeShort(1);
            p.conn.addmsg(m);
            m.cleanup();
            p.item_to_kham_ngoc_id_ngoc = idItem;
        } 
        else if (cat == 0 && num == 0 && type == 1 && action == 4) { // bat dau kham ngoc len item
            Item_wear it_select = p.item_to_kham_ngoc;
            if (it_select != null && p.item_to_kham_ngoc_id_ngoc != -1) {
                if (p.item.total_item_bag_by_id(4, p.item_to_kham_ngoc_id_ngoc) < 1) {
                    Service.send_box_ThongBao_OK(p, "Không đủ vật phẩm trong hành trang");
                    return;
                }
                if(!checkIsDaKham(p.item_to_kham_ngoc_id_ngoc) || !check_can_kham_len_item(it_select, p.item_to_kham_ngoc_id_ngoc))
                {
                    Service.send_box_ThongBao_OK(p, "Đá khảm không phù hợp");
                    return;
                }
                if (p.item.total_item_bag_by_id(4, p.item_to_kham_ngoc_id_ngoc) < num) {
                    Service.send_box_ThongBao_OK(p, "Không đủ vật phẩm trong hành trang");
                    return;
                }   
                int result = Kham_Da(it_select, p.item_to_kham_ngoc_id_ngoc, p);
                if(result == 2){
                    Service.send_box_ThongBao_OK(p, "Tối đa 7 đá khảm");
                    return;
                }
                if(result == 0){
                    Service.send_box_ThongBao_OK(p, "Có lỗi xảy ra, hãy thử lại[0]");
                    return;
                }
                if(result == 3) {
                    Service.send_box_ThongBao_OK(p, "Loại đá này chỉ được phép khảm 3 viên trên mỗi vật phẩm");
                    return;
                }
                int vang_req = 50 * (it_select.numLoKham + 1);
                if (p.get_ngoc() < vang_req) {
                    Service.send_box_ThongBao_OK(p, "Không đủ " + vang_req + " ruby");
                    return;
                }
                if(result == 4) {
                    Service.send_box_ThongBao_OK(p, "Khảm ngọc thất bại. Tỉ lệ 1%");
                    p.update_ngoc(-vang_req);
                    p.update_money();
                    p.item.remove_item47(4, p.item_to_kham_ngoc_id_ngoc, 1);
                    p.item.update_Inventory(4, false);
                    p.item.update_Inventory(7, false);
                    p.item.update_Inventory(3, false);
                    return;
                }
                p.update_ngoc(-vang_req);
                p.update_money();
                Log.gI().add_log(p, "Khảm ngọc lên -> " + it_select.name + " -" + vang_req + " ruby");
                p.item.remove_item47(4, p.item_to_kham_ngoc_id_ngoc, 1);
                Kham_Ngoc.show_table(p, 2);
                Service.send_box_ThongBao_OK(p, "Khảm ngọc thành công");
                p.item.update_Inventory(4, false);
                p.item.update_Inventory(7, false);
                p.item.update_Inventory(3, false);
            } 
            else {
                Kham_Ngoc.show_table(p, 2);
                Service.send_box_ThongBao_OK(p, "Có lỗi xảy ra, hãy thử lại[1]");
            }
        } else if (cat == 3 && num == 1 && type == 3 && action == 1) { // bo item thao ngoc kham
            Item_wear it_select = p.item.bag3[idItem];
            if (it_select != null) {
                if (it_select.mdakham.length > 0) {
                    Message m = new Message(-67);
                    m.writer().writeByte(1);
                    m.writer().writeShort(idItem);
                    m.writer().writeByte(3);
                    m.writer().writeShort(1);
                    p.conn.addmsg(m);
                    m.cleanup();
                    p.item_to_kham_ngoc = it_select;
                } else {
                    Service.send_box_ThongBao_OK(p, "Vật phẩm chưa có đá khảm!");
                }
            }
        } else if (cat == 3 && num == 1 && type == 3 && action == 6) { // bat dau thao ngoc kham
            if (p.item_to_kham_ngoc != null) {
                int vang_req = p.item_to_kham_ngoc.mdakham.length * 250;
                Service.send_box_yesno(p, 1, "Xác nhận tháo tất cả ngọc khảm với giá " + vang_req + " ruby?");
            }
        }
        switch (type) {
            case 10:
                if (action == 20) {
                    Service.send_box_yesno(p, 3, "Để hoàn mỹ bạn sẽ mất phí 5 ruby");
                } else if (cat == 3 && num == 1) {
                    if (action == 1) {
                        Item_wear it_select = p.item.bag3[idItem];
                        if(it_select != null && it_select.isHoanMy >= 10)
                        {
                            Service.send_box_ThongBao_OK(p, "Vật phẩm " + p.item_to_kham_ngoc.name + " đã hoàn mĩ 10 lần");
                            return;
                        }
                        if (it_select != null && it_select.color >= 2) {
                            Message m = new Message(-67);
                            m.writer().writeByte(1);
                            m.writer().writeShort(idItem);
                            m.writer().writeByte(3);
                            m.writer().writeShort(1);
                            p.conn.addmsg(m);
                            m.cleanup();
                            p.item_to_kham_ngoc = it_select;
                        } else {
                            Service.send_box_ThongBao_OK(p, "Vật phẩm không hợp lệ!");
                        }
                    }
                } else if (cat == 4 && num == 1) {
                    if (action == 1 && idItem >= 221 && idItem <= 226) {
                        Message m = new Message(-67);
                        m.writer().writeByte(1);
                        m.writer().writeShort(idItem);
                        m.writer().writeByte(4);
                        m.writer().writeShort(num);
                        p.conn.addmsg(m);
                        m.cleanup();
                        p.item_to_kham_ngoc_id_ngoc = idItem;
                    } else {
                        Service.send_box_ThongBao_OK(p, "Vật phẩm không hợp lệ!");
                    }
                }
                break;
            case 11:
                if (action == 22) {
                    Service.send_box_yesno(p, 4, "Để kích ẩn trang bị bạn sẽ mất phí 5 ruby");
                } else if (cat == 3 && num == 1) {
                    if (action == 1) {
                        Item_wear it_select = p.item.bag3[idItem];
                        if (it_select != null && it_select.color >= 2) {
                            Message m = new Message(-67);
                            m.writer().writeByte(1);
                            m.writer().writeShort(idItem);
                            m.writer().writeByte(3);
                            m.writer().writeShort(1);
                            p.conn.addmsg(m);
                            m.cleanup();
                            p.item_to_kham_ngoc = it_select;
                        } else {
                            Service.send_box_ThongBao_OK(p, "Vật phẩm không hợp lệ!");
                        }
                    }
                } else if (cat == 4 && num == 1) {
                    if (action == 1 && idItem >= 221 && idItem <= 226) {
                        Message m = new Message(-67);
                        m.writer().writeByte(1);
                        m.writer().writeShort(idItem);
                        m.writer().writeByte(4);
                        m.writer().writeShort(num);
                        p.conn.addmsg(m);
                        m.cleanup();
                        p.item_to_kham_ngoc_id_ngoc = idItem;
                    } else {
                        Service.send_box_ThongBao_OK(p, "Vật phẩm không hợp lệ!");
                    }
                }
                break;
        }
    }

    private static void add_op_ngoc_kham_new(Item_wear it_select, short id) {
        if (it_select.mdakham.length > 0) {
            short[] temp = new short[it_select.mdakham.length + 1];
            for (int i = 0; i < it_select.mdakham.length; i++) {
                temp[i] = it_select.mdakham[i];
            }
            temp[temp.length - 1] = id;
            it_select.mdakham = temp;
        } else {
            it_select.mdakham = new short[]{id};
        }
        byte[] id_add = null;
        int[] par_add = null;
        switch (id) {
            case 44: {
                id_add = new byte[]{4};
                par_add = new int[]{20};
                break;
            }
            case 45: {
                id_add = new byte[]{4};
                par_add = new int[]{30};
                break;
            }
            case 46: {
                id_add = new byte[]{4};
                par_add = new int[]{40};
                break;
            }
            case 47: {
                id_add = new byte[]{4};
                par_add = new int[]{60};
                break;
            }
            case 48: {
                id_add = new byte[]{4};
                par_add = new int[]{90};
                break;
            }
            case 49: {
                id_add = new byte[]{4};
                par_add = new int[]{140};
                break;
            }
            case 50: {
                id_add = new byte[]{1};
                par_add = new int[]{50};
                break;
            }
            case 51: {
                id_add = new byte[]{1};
                par_add = new int[]{70};
                break;
            }
            case 52: {
                id_add = new byte[]{1};
                par_add = new int[]{90};
                break;
            }
            case 53: {
                id_add = new byte[]{1};
                par_add = new int[]{120};
                break;
            }
            case 54: {
                id_add = new byte[]{1};
                par_add = new int[]{160};
                break;
            }
            case 55: {
                id_add = new byte[]{1};
                par_add = new int[]{220};
                break;
            }
            case 56: {
                id_add = new byte[]{10};
                par_add = new int[]{10};
                break;
            }
            case 57: {
                id_add = new byte[]{10};
                par_add = new int[]{20};
                break;
            }
            case 58: {
                id_add = new byte[]{10};
                par_add = new int[]{30};
                break;
            }
            case 59: {
                id_add = new byte[]{10};
                par_add = new int[]{40};
                break;
            }
            case 60: {
                id_add = new byte[]{10};
                par_add = new int[]{50};
                break;
            }
            case 61: {
                id_add = new byte[]{10};
                par_add = new int[]{60};
                break;
            }
            case 62: {
                id_add = new byte[]{13};
                par_add = new int[]{10};
                break;
            }
            case 63: {
                id_add = new byte[]{13};
                par_add = new int[]{20};
                break;
            }
            case 64: {
                id_add = new byte[]{13};
                par_add = new int[]{30};
                break;
            }
            case 65: {
                id_add = new byte[]{13};
                par_add = new int[]{40};
                break;
            }
            case 66: {
                id_add = new byte[]{13};
                par_add = new int[]{60};
                break;
            }
            case 67: {
                id_add = new byte[]{13};
                par_add = new int[]{90};
                break;
            }
            case 68: {
                id_add = new byte[]{26, 27};
                par_add = new int[]{10, 10};
                break;
            }
            case 69: {
                id_add = new byte[]{26, 27};
                par_add = new int[]{20, 20};
                break;
            }
            case 70: {
                id_add = new byte[]{26, 27};
                par_add = new int[]{30, 30};
                break;
            }
            case 71: {
                id_add = new byte[]{26, 27};
                par_add = new int[]{40, 40};
                break;
            }
            case 72: {
                id_add = new byte[]{26, 27};
                par_add = new int[]{60, 60};
                break;
            }
            case 73: {
                id_add = new byte[]{26, 27};
                par_add = new int[]{90, 90};
                break;
            }
            case 74: {
                id_add = new byte[]{14};
                par_add = new int[]{10};
                break;
            }
            case 75: {
                id_add = new byte[]{14};
                par_add = new int[]{20};
                break;
            }
            case 76: {
                id_add = new byte[]{14};
                par_add = new int[]{30};
                break;
            }
            case 77: {
                id_add = new byte[]{14};
                par_add = new int[]{40};
                break;
            }
            case 78: {
                id_add = new byte[]{14};
                par_add = new int[]{60};
                break;
            }
            case 79: {
                id_add = new byte[]{14};
                par_add = new int[]{90};
                break;
            }
            case 324: {
                id_add = new byte[]{1, 4, 10, 13, 14, 26, 27};
                par_add = new int[]{70, 110, 30, 50, 50, 50, 50};
                break;
            }
            case 325: {
                id_add = new byte[]{49, 50, 52, 51, 47, 48};
                par_add = new int[]{40, 40, 40, 40, 40, 20};
                break;
            }
            case 326: {
                id_add = new byte[]{1, 4, 10, 13, 14, 26, 27};
                par_add = new int[]{140, 220, 60, 90, 90, 90, 90};
                break;
            }
        }
        if (id_add != null && par_add != null) {
            for (int i = 0; i < id_add.length; i++) {
                Option op_new = null;
                for (int j = 0; j < it_select.option_item_2.size(); j++) {
                    if (it_select.option_item_2.get(j).id == id_add[i]) {
                        op_new = it_select.option_item_2.get(j);
                        break;
                    }
                }
                if (op_new != null) {
                    int par_old = op_new.getParam(0);
                    op_new.setParam(par_old + par_add[i]);
                } else {
                    op_new = new Option(id_add[i], par_add[i]);
                    it_select.option_item_2.add(op_new);
                }
            }
        }
    }

    private static boolean check_can_kham_len_item(Item_wear it_select, short id) {
        
        boolean result = false;
        switch(it_select.typeEquip) 
        {
            case 0:{
                        if ((id >= 246 && id <= 250) || id == 325){
                            result = true;
                        }
                        break;
            }
            case 1:
            case 3:
            case 5: {
                        if (id >= 241 && id <= 245) {
                            result = true;
                        }
                        if (id >= 261 && id <= 265) {
                            result = true;
                        }
                        break;
            }
            case 4:
            case 2:{
                    if (id >= 251 && id <= 255) { // CM
                        result = true;
                    }
                    if (id >= 256 && id <= 260) {// XG
                        result = true;
                    }
                    if (id >= 266 && id <= 270) {// XG
                        result = true;
                    }
            }
                break;
            
        }
        return result;
    }
    
    public static boolean checkIsDaKham(short id)
    {
        return !(id < 241 || id > 270) || !(id != 325);
    }
    
    public static int Kham_Da(Item_wear it_select, short idDaKham, Player p){
        if(it_select.mdakham.length >= 7)
        {
            return 2;
        }
        if(idDaKham == 325) {
            int count = 0;
            for(int i = 0; i < it_select.mdakham.length; i++) {
                if(it_select.mdakham[i] == idDaKham) {
                    count++;
                }
            }
            if(count >= 3) {
                return 3;
            }
            boolean suc = Util.random(100) < 1;
            if(!suc) {
                return 4;
            }
            else {
                try {
                    Manager.gI().sendChatKTG(p.name + " vừa khảm đá siêu cấp S vào vũ khí thành công với tỉ lệ 1%");
                }
                catch (IOException e) {
                    
                }
            }
        }
        Option[] opDA = getOptionDa(idDaKham);
        // Add option vao
        for(int mDa = 0; mDa < opDA.length; mDa++) {
            if(opDA[mDa] != null) {
                Option op_new = null;
                for (int j = 0; j < it_select.option_item_2.size(); j++) {
                    if (it_select.option_item_2.get(j).id == opDA[mDa].id) {
                        op_new = it_select.option_item_2.get(j);
                        break;
                    }
                }
                if (op_new != null) {
                    op_new.setParam(op_new.getParamGoc() + opDA[mDa].getParamGoc());
                } 
                else {
                    it_select.option_item_2.add(opDA[mDa]);
                }
            }
        }
        // Add da vao
        if(it_select.mdakham.length > 0) {
            short[] mdakhamNew = new short[it_select.mdakham.length + 1];
            for(int i = 0; i < it_select.mdakham.length; i++) {
                mdakhamNew[i] = it_select.mdakham[i];
            }
            mdakhamNew[mdakhamNew.length - 1] = idDaKham;
            it_select.mdakham = mdakhamNew;
        }
        else{
                it_select.mdakham = new short[]{idDaKham};
            }
        return 1;
    }
    
    public static Option[] getOptionDa(int idDa) {
        // 47: ghu
        // 48: ptm
        //49: gcm
        //50: gxg;
        //51: gn
        //52: gpd
        //Da PT
        Option OptionDa[] = new Option[2];
        if(idDa == 325) {
            Option OptionDa2[] = new Option[6];
            OptionDa2[0] = new Option(49, 40);
            OptionDa2[1] = new Option(50, 40);
            OptionDa2[2] = new Option(52, 40);
            OptionDa2[3] = new Option(51, 40);
            OptionDa2[4] = new Option(47, 40);
            OptionDa2[5] = new Option(48, 20);
            return OptionDa2;
        }
        if(idDa == 241){
            OptionDa[0] = new Option(4, 140);
            OptionDa[1] = new Option(48, 20);
        }
        if(idDa == 242){
            OptionDa[0] = new Option(4, 140);
            OptionDa[1] = new Option(49, 40);
        }
        if(idDa == 243){
            OptionDa[0] = new Option(4, 140);
            OptionDa[1] = new Option(50, 40);
        }
        if(idDa == 244){
            OptionDa[0] = new Option(4, 140);
            OptionDa[1] = new Option(51, 40);
        }
        if(idDa == 245){
            OptionDa[0] = new Option(4, 140);
            OptionDa[1] = new Option(52, 40);
        }
        //Da TC
        if(idDa == 246){ //tcghu
            OptionDa[0] = new Option(1, 220);
            OptionDa[1] = new Option(47, 40);
        }
        if(idDa == 247){ //tcgcm
            OptionDa[0] = new Option(1, 220);
            OptionDa[1] = new Option(49, 40);
        }
        if(idDa == 248){ //tcgxg
            OptionDa[0] = new Option(1, 220);
            OptionDa[1] = new Option(50, 40);
        }
        if(idDa == 249){ //tcgn
            OptionDa[0] = new Option(1, 220);
            OptionDa[1] = new Option(51, 40);
        }
        if(idDa == 250){ //tcgpd
            OptionDa[0] = new Option(1, 220);
            OptionDa[1] = new Option(52, 40);
        }
        //DA CM
        if(idDa == 251){ //cmghu
            OptionDa[0] = new Option(10, 60);  // cm 6%
            OptionDa[1] = new Option(47, 40); // ghu 4%
        }
        if(idDa == 252){ //cmgptm
            OptionDa[0] = new Option(10, 60);  // cm 6%
            OptionDa[1] = new Option(48, 20); // ptm 2%
        }
        if(idDa == 253){ //cmgxg
            OptionDa[0] = new Option(10, 60);  // cm 6%
            OptionDa[1] = new Option(50, 40); // gxg 4%
        }
        if(idDa == 254){ //cmgn
            OptionDa[0] = new Option(10, 60);  // cm 6%
            OptionDa[1] = new Option(51, 40); // gn 4%
        }
        if(idDa == 255){ //cmgpd
            OptionDa[0] = new Option(10, 60);  // cm 6%
            OptionDa[1] = new Option(52, 40); // gpd 4%
        }
        //DA XG
        if(idDa == 256){ //xgghu
            OptionDa[0] = new Option(13, 90);  // xg 9%
            OptionDa[1] = new Option(47, 40); // ghu 4%
        }
        if(idDa == 257){ //xgptm
            OptionDa[0] = new Option(13, 90);  // xg 9%
            OptionDa[1] = new Option(48, 20); // ptm 2%
        }
        if(idDa == 258){ //xggcm
            OptionDa[0] = new Option(13, 90);  // xg 9%
            OptionDa[1] = new Option(49, 40); // gcm 4%
        }
        if(idDa == 259){ //xggn
            OptionDa[0] = new Option(13, 90);  // xg 9%
            OptionDa[1] = new Option(51, 40); // gn 4%
        }
        if(idDa == 260){ //xggpd
            OptionDa[0] = new Option(13, 90);  // xg 9%
            OptionDa[1] = new Option(52, 40); // gpd 4%
        }
        //DA KVl
        if(idDa == 261){ //kvlghu
            OptionDa[0] = new Option(26, 90);  // kvl 9%
            OptionDa[1] = new Option(47, 40); // ghu 4%
        }
        if(idDa == 262){ //kvlptm
            OptionDa[0] = new Option(26, 90);  // kvl 9%
            OptionDa[1] = new Option(48, 20); // ptm 2%
        }
        if(idDa == 263){ //kvlgcm
            OptionDa[0] = new Option(26, 90);  // kvl 9%
            OptionDa[1] = new Option(49, 40); // gcm 4%
        }
        if(idDa == 264){ //kvlgxg
            OptionDa[0] = new Option(26, 90);  // kvl 9%
            OptionDa[1] = new Option(50, 40); // gxg 4%
        }
        if(idDa == 265){ //kvlgpd
            OptionDa[0] = new Option(26, 90);  // kvl 9%
            OptionDa[1] = new Option(52, 40); // gpd 4%
        }
        //DA PD
        if(idDa == 266){ //pdghu
            OptionDa[0] = new Option(14, 90);  // pd 9%
            OptionDa[1] = new Option(47, 40); // ghu 4%
        }
        if(idDa == 267){ //pdptm
            OptionDa[0] = new Option(14, 90);  // pd 9%
            OptionDa[1] = new Option(48, 20); // ptm 2%
        }
        if(idDa == 268){ //pdgcm
            OptionDa[0] = new Option(14, 90);  // pd 9%
            OptionDa[1] = new Option(49, 40); // ghu 4%
        }
        if(idDa == 269){ //pdgxg
            OptionDa[0] = new Option(14, 90);  // pd 9%
            OptionDa[1] = new Option(50, 40); // gxg 4%
        }
        if(idDa == 270){ //pdgn
            OptionDa[0] = new Option(14, 90);  // pd 9%
            OptionDa[1] = new Option(51, 40); // ghu 4%
        }
        return OptionDa;
    }
    
    public static boolean isDaKhamRate(int id) {
        return id == 325;
    }
}
