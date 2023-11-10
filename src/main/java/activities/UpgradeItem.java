package activities;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import client.Player;
import core.Log;
import core.Service;
import core.Util;
import io.Message;
import template.DataUpgrade;
import template.ItemTemplate7;
import template.Item_wear;
import template.UpgradeMaterialTemplate;

public class UpgradeItem {

    public static List<DataUpgrade> DATA = new ArrayList<>();

    static {
        try ( ByteArrayInputStream bais = new ByteArrayInputStream(Util.loadfile("data/msg/login/request/msg-7_12"));  DataInputStream dis = new DataInputStream(bais)) {
            int n = dis.readByte();
            for (int i = 0; i < n; i++) {
                DataUpgrade temp = new DataUpgrade();
                temp.level = dis.readByte();
                temp.per = dis.readShort();
                temp.prelevel = dis.readByte();
                temp.beri = dis.readInt();
                temp.beri_white = dis.readInt();
                temp.ruby = dis.readShort();
                temp.att = dis.readShort();
                int n2 = dis.readByte();
                temp.material = new UpgradeMaterialTemplate[n2];
                for (int j = 0; j < n2; j++) {
                    temp.material[j] = new UpgradeMaterialTemplate(dis.readByte(), dis.readByte(), dis.readShort());
                }
                UpgradeItem.DATA.add(temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void show_table_upgrade(Player p) throws IOException {
        Message m = new Message(-48);
        m.writer().writeByte(7);
        p.conn.addmsg(m);
        m.cleanup();
    }

    public static void show_table_super_upgrade(Player p) throws IOException {
        Message m = new Message(66);
        m.writer().writeByte(7);
        p.conn.addmsg(m);
        m.cleanup();
    }

    public static int[] beri = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
    public static int[] ruby = new int[]{80, 80, 80, 100, 100, 100, 120, 120};

    public static void processSuperUpgrade(Player p, Message m2) throws IOException {
        byte type = m2.reader().readByte();
        int id = m2.reader().readShort();
        int gem = m2.reader().readByte();
        short numIt2 = m2.reader().readByte();
        //System.out.println(type + " " + id + " " + gem + " " + numIt2);
        switch (type) {
            case 1:
                Item_wear itW = p.item.bag3[id];
                if (itW != null && itW.levelup >= 10 && itW.levelup < 15 && itW.level >= 30) {
                    int botNguyenLieu = p.tool_upgrade[1];
                    int botCuongHoa = p.tool_upgrade[0];
                    if (p.item.total_item_bag_by_id(7, 1) < 1) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + botCuongHoa + " Bột cường hóa");
                        return;
                    }
                    if (p.item.total_item_bag_by_id(7, 4) < 1) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + botNguyenLieu + " Bột vàng");
                        return;
                    }
                    int numThienThach = p.tool_upgrade[2];
                    int numMaiRua = p.tool_upgrade[3];
                    int numKhien = p.tool_upgrade[4];
                    if (p.item.total_item_bag_by_id(7, 11) < numThienThach || p.item.total_item_bag_by_id(7, 6) < numMaiRua || p.item.total_item_bag_by_id(7, 10) < numKhien) {
                        return;
                    }
                    int beriNeed = beri[(int) (itW.level / 10) - 3];
                    int rubyNeed = ruby[(int) (itW.level / 10) - 3];
                    Message m = new Message(66);
                    m.writer().writeByte(1);
                    m.writer().writeUTF("Bạn có muốn cường hóa vật phẩm " + itW.name + " lên cấp " + (itW.levelup + 1) + " ?");
                    m.writer().writeInt(beriNeed);
                    m.writer().writeShort(rubyNeed);
                    m.writer().writeShort(itW.id);
                    p.conn.addmsg(m);
                    m.cleanup();
                    p.tool_upgrade[5] = id;
                }
                break;
            case 2:
                itW = p.item.bag3[p.tool_upgrade[5]];
                if (itW != null && itW.levelup >= 10 && itW.levelup < 15 && itW.level >= 30) {
                    int botNguyenLieu = p.tool_upgrade[1];
                    int botCuongHoa = p.tool_upgrade[0];
                    if (p.item.total_item_bag_by_id(7, 1) < 1) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + botCuongHoa + " Bột cường hóa!");
                        return;
                    }
                    if (p.item.total_item_bag_by_id(7, 4) < 1) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + botNguyenLieu + " Bột vàng!");
                        return;
                    }
                    int numThienThach = p.tool_upgrade[2];
                    int numMaiRua = p.tool_upgrade[3];
                    int numKhien = p.tool_upgrade[4];
                    if (p.item.total_item_bag_by_id(7, 11) < numThienThach || p.item.total_item_bag_by_id(7, 6) < numMaiRua || p.item.total_item_bag_by_id(7, 10) < numKhien) {
                        return;
                    }
                    p.item.remove_item47(7, 1, botCuongHoa);
                    p.item.remove_item47(7, 4, botNguyenLieu);
                    p.item.remove_item47(7, 11, numThienThach);
                    p.item.remove_item47(7, 6, numMaiRua);
                    p.item.remove_item47(7, 10, numKhien);
                    int beriNeed = beri[(int) (itW.level / 10) - 3];
                    int rubyNeed = ruby[(int) (itW.level / 10) - 3];
                    if (gem == 1) {
                        if (p.get_vang() < beriNeed) {
                            Service.send_box_ThongBao_OK(p, "Không đủ " + Util.number_format(beriNeed) + " beri");
                            return;
                        }
                        p.update_vang(-beriNeed);
                        p.update_money();
                    } else if (gem == 2) {
                        if (p.get_ngoc() < rubyNeed) {
                            Service.send_box_ThongBao_OK(p, "Không đủ " + Util.number_format(rubyNeed) + " ruby");
                            return;
                        }
                        p.update_ngoc(-rubyNeed);
                        p.update_money();
                    }
                    int tiLeRotCap = 80 - (numMaiRua * (15 / 10)) - (numKhien * (20 / 10));
                    if (true) {
                        itW.levelup++;
                        if(itW.levelup > 15){
                            itW.levelup = 15;
                        }
                        Message m = new Message(66);
                        m.writer().writeByte(2);
                        m.writer().writeUTF("Cường hóa vật phẩm thành công");
                        p.conn.addmsg(m);
                        m.cleanup();
                    } else {
                        if(itW.levelup > 10 && numMaiRua <= 0 && numKhien <= 0){
                            itW.levelup--;
                        }
                        Message m = new Message(66);
                        m.writer().writeByte(3);
                        m.writer().writeUTF("Cường hóa vật phẩm thất bại");
                        p.conn.addmsg(m);
                        m.cleanup();
                    }
                    p.item.update_Inventory(4, false);
                    p.item.update_Inventory(7, false);
                    p.item.update_Inventory(3, false);
                }
                break;
            case 4:
                itW = p.item.bag3[id];
                if (itW != null && itW.levelup >= 10 && itW.levelup < 15 && itW.level >= 30) {
                    int botNguyenLieu = itW.level / 10 * 5;
                    int botCuongHoa = 100;
                    if (itW.level >= 30 && itW.level < 40) {
                        botCuongHoa += botCuongHoa * 20 / 100;
                        botNguyenLieu += botNguyenLieu * 20 / 100;
                    } else if (itW.level >= 40 && itW.level < 50) {
                        botCuongHoa += botCuongHoa * 10 / 100;
                        botNguyenLieu += botNguyenLieu * 10 / 100;
                    }
                    if (p.item.total_item_bag_by_id(7, 1) < 100) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + botCuongHoa + " bột cường hóa!");
                        return;
                    }
                    if (p.item.total_item_bag_by_id(7, 4) < botNguyenLieu) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + botNguyenLieu + " bột vàng!");
                        return;
                    }
                    Message m = new Message(66);
                    m.writer().writeByte(4);
                    m.writer().writeShort(id);
                    m.writer().writeShort(4);
                    m.writer().writeShort(botNguyenLieu);
                    m.writer().writeShort(1);
                    m.writer().writeShort(botCuongHoa);
                    p.conn.addmsg(m);
                    m.cleanup();
                    p.tool_upgrade = new int[]{botCuongHoa, botNguyenLieu, 0, 0, 0, 0};
                }
                break;
            case 5:
                if (gem == 1 && numIt2 == 1 && id == 11) {
                    if (p.item.total_item_bag_by_id(7, id) < 1) {
                        return;
                    }
                    Message m = new Message(66);
                    m.writer().writeByte(5);
                    m.writer().writeByte(p.item.total_item_bag_by_id(7, id));
                    m.writer().writeShort(id);
                    p.conn.addmsg(m);
                    m.cleanup();
                    p.tool_upgrade[2] = numIt2;
                }
                break;
            case 6:
                if (gem == 1 && id == 6) {
                    if (numIt2 > 5) {
                        Service.send_box_ThongBao_OK(p, "Tối đa 5 mai rùa");
                        return;
                    }
                    if (p.item.total_item_bag_by_id(7, id) < numIt2) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + numIt2 + "");
                        return;
                    }
                    Message m = new Message(66);
                    m.writer().writeByte(6);
                    m.writer().writeByte(p.item.total_item_bag_by_id(7, id));
                    m.writer().writeShort(id);
                    m.writer().writeByte(numIt2);
                    p.conn.addmsg(m);
                    m.cleanup();
                    p.tool_upgrade[3] = numIt2;
                }
                break;
            case 14:
                if (gem == 1 && numIt2 == 1 && id == 10) {
                    if (p.item.total_item_bag_by_id(7, id) < 1) {
                        return;
                    }
                    Message m = new Message(66);
                    m.writer().writeByte(14);
                    m.writer().writeByte(p.item.total_item_bag_by_id(7, id));
                    m.writer().writeShort(id);
                    p.conn.addmsg(m);
                    m.cleanup();
                    p.tool_upgrade[4] = numIt2;
                }
                break;
        }
    }

    public static void process(Player p, Message m2) throws IOException {
        byte type = m2.reader().readByte();
        short id = m2.reader().readShort();
        byte bery_gem = m2.reader().readByte();
        // System.out.println(type);
        // System.out.println(id);
        // System.out.println(bery_gem);
        if(type == 15 && id == -1 && bery_gem == 0){
            Item_wear p_Heart = p.item.it_body[6];
            if(p_Heart == null){
                Service.send_box_ThongBao_OK(p, "Không tìm thấy quả tim");
                return;
            }
            if(p_Heart.levelup >= 99){
                Service.send_box_ThongBao_OK(p, "Quả tim đã đạt cấp tối đa");
                return;
            }
            if(p_Heart.levelup >= 99){
                Service.send_box_ThongBao_OK(p, "Quả tim đã đạt cấp tối đa");
                return;
            }
            if(p.get_Vnd() < 100) {
                Service.send_box_ThongBao_OK(p, "Bạn không đủ Extol");
                return;
            }
            Service.send_box_yesno(p, 9000, "Bạn muốn nâng cấp quả tim lên +" + (p_Heart.levelup + 1) + " với giá 100 Extol ?. "
                    + "Tỉ lệ " + (100 - p_Heart.levelup) + "%");
        }
        else if (type == 8 && id == 0 && bery_gem == 0) {
            Service.Send_UI_Shop(p, 21);
        } else if (type == 10 && id == 0 && bery_gem == 0) {
            Kham_Ngoc.show_table(p, 0);
        } else if (type == 12 && id == 0 && bery_gem == 0) {
            Kham_Ngoc.show_table(p, 1);
        } else if (type == 9 && id == 0 && bery_gem == 0) {
            Kham_Ngoc.show_table(p, 2);
        } else if (type == 11 && id == 0 && bery_gem == 0) {
            Service.Send_UI_Shop(p, 111);
        } else if (bery_gem == 0) {
            switch (type) {
                case 4: { // bo do vao
                    Item_wear it = p.item.bag3[id];
                    if (it != null) {
                        if (it.levelup > 9) {
                            Service.send_box_ThongBao_OK(p, "Vật phẩm đã đạt cấp tối đa");
                            return;
                        }
                        Message m = new Message(-48);
                        m.writer().writeByte(4);
                        m.writer().writeShort(id);
                        p.conn.addmsg(m);
                        m.cleanup();
                        //
                        p.tool_upgrade = new int[]{-1, -1};
                    }
                    break;
                }
                case 1: {
                    Item_wear it = p.item.bag3[id];
                    if (it != null) {
                        if (it.levelup > 9) {
                            Service.send_box_ThongBao_OK(p, "Vật phẩm đã đạt cấp tối đa");
                            return;
                        }
                        Message m = new Message(-48);
                        m.writer().writeByte(1);
                        m.writer().writeUTF("Xác nhận muốn cường hóa vật phẩm lên +" + (it.levelup + 1) + " ?");
                        m.writer().writeInt(UpgradeItem.DATA.get(it.levelup).beri);
                        m.writer().writeShort(UpgradeItem.DATA.get(it.levelup).ruby);
                        m.writer().writeShort(id);
                        p.conn.addmsg(m);
                        m.cleanup();
                    }
                    break;
                }
            }
        } else if (type == 2) {
            Item_wear it = p.item.bag3[id];
            if (it != null) {
                if (it.levelup > 9) {
                    Service.send_box_ThongBao_OK(p, "Vật phẩm đã đạt cấp tối đa");
                    return;
                }
                short[] material_req = get_material(it.levelup, it.color);
                if (material_req[0] > -1) {
                    if (p.item.total_item_bag_by_id(7, material_req[0]) < material_req[1]) {
                        Service.send_box_ThongBao_OK(p, "Không đủ bột cường hóa");
                        return;
                    }
                    if (p.item.total_item_bag_by_id(7, material_req[2]) < material_req[3]) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + ItemTemplate7.get_it_by_id(material_req[2]).name);
                        return;
                    }
                } else {
                    Service.send_box_ThongBao_OK(p, "Đã có lỗi xảy ra");
                    return;
                }
                if (bery_gem == 1) {
                    if (p.get_vang() < UpgradeItem.DATA.get(it.levelup).beri) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + UpgradeItem.DATA.get(it.levelup).beri + " beri");
                        return;
                    }
                    p.update_vang(-UpgradeItem.DATA.get(it.levelup).beri);
                    p.update_money();
                    Log.gI().add_log(p,
                            "Cường hóa item " + it.name + " -" + UpgradeItem.DATA.get(it.levelup).beri + " beri");
                } else {
                    if (p.get_ngoc() < UpgradeItem.DATA.get(it.levelup).ruby) {
                        Service.send_box_ThongBao_OK(p, "Không đủ " + UpgradeItem.DATA.get(it.levelup).ruby + " ruby");
                        return;
                    }
                    p.update_ngoc(-UpgradeItem.DATA.get(it.levelup).ruby);
                    p.update_money();
                    Log.gI().add_log(p,
                            "Cường hóa item " + it.name + " -" + UpgradeItem.DATA.get(it.levelup).ruby + " ruby");
                }
                p.item.remove_item47(7, material_req[0], material_req[1]);
                p.item.remove_item47(7, material_req[2], material_req[3]);
                boolean suc = true;
                if (suc) {
                    it.levelup++;
                    if (it.levelup == 10) {
                        UpgradeItem.show_table_upgrade(p);
                    }
                    notice_upgrade(p, 2, "Cường hóa vật phẩm thành công");
                } else {
                    notice_upgrade(p, 3, "Cường hóa vật phẩm thất bại");
                    if (p.tool_upgrade[1] == -1) {
                        it.levelup = UpgradeItem.DATA.get(it.levelup).prelevel;
                    }
                }
                //
                if (p.tool_upgrade[0] != -1) {
                    p.item.remove_item47(7, p.tool_upgrade[0], 1);
                }
                if (p.tool_upgrade[1] != -1) {
                    p.item.remove_item47(7, p.tool_upgrade[1], 1);
                }
                if (p.item.total_item_bag_by_id(7, p.tool_upgrade[0]) < 1) {
                    p.tool_upgrade[0] = -1;
                }
                if (p.item.total_item_bag_by_id(7, p.tool_upgrade[1]) < 1) {
                    p.tool_upgrade[1] = -1;
                }
                //
                p.item.update_Inventory(4, false);
                p.item.update_Inventory(7, false);
                p.item.update_Inventory(3, false);
            }
        } else if (bery_gem == 1) {
            if (type == 5) {
                if (p.item.total_item_bag_by_id(7, id) > 0) {
                    Message m5 = new Message(-48);
                    m5.writer().writeByte(5);
                    m5.writer().writeByte(1); // is use
                    m5.writer().writeShort(id);
                    p.conn.addmsg(m5);
                    m5.cleanup();
                    p.tool_upgrade[0] = id;
                }
            } else if (type == 6) {
                if (p.item.total_item_bag_by_id(7, id) > 0) {
                    Message m5 = new Message(-48);
                    m5.writer().writeByte(6);
                    m5.writer().writeByte(1); // is use
                    m5.writer().writeShort(id);
                    p.conn.addmsg(m5);
                    m5.cleanup();
                    p.tool_upgrade[1] = id;
                }
            }
        }
    }

    private static short[] get_material(int level, int color) {
        short[] result = new short[]{-1, -1, -1, -1};
        DataUpgrade temp = UpgradeItem.DATA.get(level);
        for (int i = 0; i < temp.material.length; i++) {
            if (temp.material[i].type == -1) {
                result[0] = temp.material[i].id;
                result[1] = temp.material[i].quant;
            }
            if (temp.material[i].type == color) {
                result[2] = temp.material[i].id;
                result[3] = temp.material[i].quant;
            }
        }
        return result;
    }

    private static void notice_upgrade(Player p, int type, String s) throws IOException {
        Message m = new Message(-48);
        m.writer().writeByte(type);
        m.writer().writeUTF(s);
        p.conn.addmsg(m);
        m.cleanup();
    }
    
    public static void show_Heart_Table(Player p) throws IOException {
        Message m = new Message(-48);
        m.writer().writeByte(15);
        p.conn.addmsg(m);
        m.cleanup();
    }
    
    public static void send_Eff_Heart(Player p) throws IOException{
        Message m = new Message(-15);
        m.writer().writeByte(25);
        m.writer().writeShort(p.id);
        m.writer().writeByte(0);
        m.writer().writeShort(0);
        p.conn.addmsg(m);
        m.cleanup();
    }
}
