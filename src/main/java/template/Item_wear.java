package template;

import java.util.ArrayList;
import java.util.List;
import core.Util;

public class Item_wear {
	public short id;
	public String name;
	public byte clazz;
	public byte typeEquip;
	public short icon;
	public short level;
	public byte levelup;
	public byte color;
	public byte isTrade;
	public byte typelock;
	public byte numHoleDaDuc;
	public int timeUse;
	public short valueChetac;
	public byte isHoanMy;
	public byte valueKichAn;
	public List<Option> option_item;
	public List<Option> option_item_2;
        public List<Option> option_item_Goc;
	public byte numLoKham;
	public short[] mdakham;
	public byte index;

	public void setup_template_by_id(int id) {
		ItemTemplate3 it_temp = ItemTemplate3.get_it_by_id(id);
		if (it_temp != null) {
			this.id = (short) id;
			this.name = it_temp.name;
			this.clazz = it_temp.clazz;
			this.typeEquip = it_temp.typeEquip;
			this.icon = it_temp.icon;
			this.level = it_temp.level;
			this.levelup = 0;
			this.color = it_temp.color;
			this.isTrade = 0;
			this.typelock = it_temp.typelock;
			this.numHoleDaDuc = it_temp.numHoleDaDuc;
			this.timeUse = 0;
			this.valueChetac = (short) Util.random(it_temp.valueChetac);
			this.isHoanMy = it_temp.isHoanMy;
			this.valueKichAn = it_temp.valueKichAn;
			this.option_item = new ArrayList<>();
			this.option_item_2 = new ArrayList<>();
			this.numLoKham = it_temp.numLoKham;
			this.mdakham = new short[it_temp.mdakham.length];
			for(int i = 0; i < it_temp.mdakham.length; i++) {
				this.mdakham[i] = it_temp.mdakham[i];
			}
			this.index = 0;
                            switch(this.typeEquip) {
                                            case 0:
                                                this.option_item.add(randomPercentAtk(this.level, 1));
                                                this.option_item.add(randomPercentAtk(this.level,2));
                                                Option[] z = (random4PercentAtk(this.level,-1)); // -1 la random 4 loai
                                                this.option_item.add(z[0]);
                                                if(z[1] != null){
                                                    this.option_item.add(z[1]);
                                                }
                                                this.option_item.add(randomRecoverHp_Mp());
                                                break;
                                            case 1:
                                                this.option_item.add(random_5_Point());
                                                this.option_item.add(random_5_Point());
                                                this.option_item.add(random_5_Point());
                                                this.option_item.add(random_5_Point());
                                                break;
                                            case 2:
                                                this.option_item.add(random_4_ATK_byList(new short[] {10, 13}));
                                                this.option_item.add(random_4_ATK_byList(new short[] {10, 13}));
                                                break;
                                            case 3:
                                                this.option_item.add(random_Hp_Mp(15));
                                                this.option_item.add(random_Hp_Mp(16));
                                                this.option_item.add(random_Hp_Mp(17));
                                                this.option_item.add(random_Hp_Mp(18));
                                                break;
                                            case 4:
                                                this.option_item.add(random_4_ATK_byList(new short[] {12, 14}));
                                                this.option_item.add(random_4_ATK_byList(new short[] {12, 14}));
                                                break;
                                            case 5: 
                                                this.option_item.add(random_Hp_Mp(15));
                                                this.option_item.add(random_Hp_Mp(16));
                                                this.option_item.add(random_Hp_Mp(17));
                                                this.option_item.add(random_Hp_Mp(18));
                                                this.option_item.add(random_4_ATK_byList(new short[] {12, 14, 10, 13}));
                                                break;
                                            case 7:
                                                this.option_item.add(randomPercentAtk(this.level, 1));
                                                this.option_item.add(randomPercentAtk(this.level,2));
                                                this.option_item.add(random_Hp_Mp(15));
                                                this.option_item.add(random_Hp_Mp(16));
                                                this.option_item.add(random_Hp_Mp(17));
                                                this.option_item.add(random_Hp_Mp(18));
                                                this.option_item.add(random_4_ATK_byList(new short[] {12, 14, 10, 13}));
                                                break;

                                    }
		} else {
			this.id = -1;
                        System.out.println("Item_Wear: loi load template item");
		}
	}

        public Option randomPercentAtk(int levelItem, int type){
            if(type == 11)
            {
                return new Option(type,((Util.random(0, 10) + (this.level * 15) + (this.color * 20) + 500) * 15) / 10);
            }
            return new Option(type,Util.random(0, 10) + (this.level * 15) + (this.color * 20) + 500); // (random) 0.90  (lv 10) 15.0 + (color 3) 6.0 + (bonus) 50.0
        }
        
        public Option[] random4PercentAtk(int levelItem, int type){
            if(type == -1) {
                short[] id_add = new short[] {10, 12, 13, 14};
                int valueRD = id_add[Util.random(id_add.length)];
                type = valueRD;
            }
            Option listOP[] = new Option[2]; 
            listOP[0] = new Option(type,Util.random(1, 10) + (levelItem * 2) + 10 + this.color * 10); // lv1 9 + 2 + 10 = 21%, lv10 9 + 20 + 10 = 39, lv20 9 + 40 + 10 = 59, lv70 = 9 + 140 + 10
            if(type == 10){
                listOP[1] = randomPercentAtk(levelItem, 11);
            }
            else{
                listOP[1] = null;
            }
            return listOP;
        }
        
        public Option random_4_ATK_byList(short[] list) {
            short[] id_add = list;
            int valueRD = id_add[Util.random(id_add.length)];
            return new Option(valueRD,Util.random(1, 10) + (this.level * 2) + 10 + this.color * 10);
        }
        
        public Option randomRecoverHp_Mp() {
            short[] id_add = new short[] {19, 20};
            int valueRD = id_add[Util.random(id_add.length)];
            return new Option(valueRD,(Util.random(1, 10)) * 10 + this.level * 2 + this.color * 10);
        }
        
        public Option random_5_Point() {
            short[] id_add = new short[] {5, 6, 7, 8, 9};
            int valueRD = id_add[Util.random(id_add.length)];
            return new Option(valueRD,Util.random(1, 10 + this.level / 10) + this.level / 10);
        }
        
        public Option random_Hp_Mp_Steal() {
            short[] id_add = new short[] {21, 22};
            int valueRD = id_add[Util.random(id_add.length)];
            return new Option(valueRD,Util.random(0, 99) + this.level * 10);
        }
        
        public Option random_Hp_Mp(int type){
            return new Option(type,Util.random(0, 99) + this.level * 10);
        }
        
        public Option random_SpecialOp_PerLevel(short[] list){
            int valueRD = list[Util.random(list.length)];
            return new Option(valueRD,Util.random(0, 9) + this.level * 10);
        }
        
	public void clone_obj(Item_wear it_temp) {
		if (it_temp != null) {
			this.id = it_temp.id;
			this.name = it_temp.name;
			this.clazz = it_temp.clazz;
			this.typeEquip = it_temp.typeEquip;
			this.icon = it_temp.icon;
			this.level = it_temp.level;
			this.levelup = it_temp.levelup;
			this.color = it_temp.color;
			this.isTrade = it_temp.isTrade;
			this.typelock = it_temp.typelock;
			this.numHoleDaDuc = it_temp.numHoleDaDuc;
			this.timeUse = it_temp.timeUse;
			this.valueChetac = it_temp.valueChetac;
			this.isHoanMy = it_temp.isHoanMy;
			this.valueKichAn = it_temp.valueKichAn;
			this.option_item = new ArrayList<>();
			for (int i = 0; i < it_temp.option_item.size(); i++) {
				this.option_item.add(new Option(it_temp.option_item.get(i).id, it_temp.option_item.get(i).getParam(0)));
			}
			this.option_item_2 = new ArrayList<>();
			for (int i = 0; i < it_temp.option_item_2.size(); i++) {
				this.option_item_2
				      .add(new Option(it_temp.option_item_2.get(i).id, it_temp.option_item_2.get(i).getParam(0)));
			}
			this.numLoKham = it_temp.numLoKham;
			this.mdakham = new short[it_temp.mdakham.length];
			for (int i = 0; i < it_temp.mdakham.length; i++) {
				this.mdakham[i] = it_temp.mdakham[i];
			}
			this.index = it_temp.index;
		}
	}
        
        public int get_AllCs_byID(int IdtypeCS) {
            int result = 0;
            for (int i = 0; i < option_item.size(); i++) {
                if (option_item.get(i).id == IdtypeCS) {
                    result += option_item.get(i).getParamGoc();
                }
            }
            if(result != 0) {
                result += ((result * (isHoanMy * 10)) / 100) + ((result * (levelup * 10)) / 100);
            }
            return result;
        }
        
        public void setup_Heart() {
            this.id = (short) 11536;
            this.name = "Quả tim";
            this.clazz = 0;
            this.typeEquip = 6;
            this.icon = 242;
            this.level = 30;
            this.levelup = 0;
            this.color = (byte)(levelup / 10);
            this.isTrade = 0;
            this.typelock = -1;
            this.numHoleDaDuc = -1;
            this.timeUse = 0;
            this.valueChetac = (short) 100;
            this.isHoanMy = 0;
            this.valueKichAn = -1;
            this.option_item = new ArrayList<>();
            this.option_item.add(random_Hp_Mp(17));
            this.option_item.add(random_Hp_Mp(18));
            this.option_item_2 = new ArrayList<>();
            this.numLoKham = 0;
            this.mdakham = new short[0];
            this.index = 0;
        }
}
