package template;

import java.util.List;

public class ItemFashion {
	public static List<ItemFashion> ENTRYS;
	public final boolean is_sell;
	public final int price;
	public final byte ID;
	public final short idIcon;
	public final String name;
	public final String info;
	public final short[] mWearing;
	public final List<Option> op;

	public ItemFashion(byte ID, short IDIcon, String name, String info, short[] wear, List<Option> op, byte is_sell,
	      int price) {
		this.ID = ID;
		this.idIcon = IDIcon;
		this.name = name;
		this.info = info;
		this.mWearing = wear;
		this.op = op;
		this.is_sell = (is_sell == 1);
		this.price = price;
	}

	public static ItemFashion get_item(short id) {
		for (int i = 0; i < ItemFashion.ENTRYS.size(); i++) {
			ItemFashion temp = ItemFashion.ENTRYS.get(i);
			if (temp.ID == id) {
				return temp;
			}
		}
		return null;
	}
}
