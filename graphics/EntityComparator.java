package graphics;

import java.util.Comparator;

public class EntityComparator implements Comparator<Entity> {
	@Override
	public int compare(Entity o1, Entity o2) {
		int l1=o1.getLayer();
		int l2=o2.getLayer();
		if(l1>l1) return 1;
		if(l1<l2) return -1;
		return 0;
	}

}
