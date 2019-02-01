import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class CPMActivity implements Comparable<CPMActivity> {
	private Integer id;
	private String name;
	private Duration time, earlyStart, lateStart, earlyFinish, lateFinish, reserve;
	private boolean crytical;
	private Set<Integer> prevList, nextList;
	
	
	public CPMActivity (Integer id, String name) {
		this.setId(id);
		this.setName(name);
		this.prevList = new TreeSet<Integer>();
		this.nextList = new TreeSet<Integer>();
	}
	
	public void addPrevAction (Integer z) {
		if (z==this.id) {
			System.out.println("[Prev] Nie uda³o siê dodaæ akcji id "+z+" do akcji o id "+this.id);
		}
		else {
			this.prevList.add(z);
			System.out.println("[Prev] Dodano akcjê id "+z+" do akcji o id "+this.id);
		}
	}
	
	public void removePrevAction (Integer z) {
		this.prevList.remove(z);
		System.out.println("[Prev] Usuniêto akcjê o id "+z+" z akcji o id "+this.id);
	}
	
	public static boolean isValidActivity (Integer newId, String prevString) {
		if (!prevString.isEmpty()) {
			List<Integer> list = Az.toIntegerList(prevString);
	
			if (list.contains(newId))
				return false;
			else 
				return true;
		}
		else
			return true;
	}
	
	public void addPrevActionFromString (String prevString) {
		if (!prevString.isEmpty()) {
		
			List<Integer> list = Az.toIntegerList(prevString);
			
			if (list.contains(this.id)) {
				System.out.println("[Prev] Nie uda³o siê dodaæ listy id "+list+" do akcji o id "+this.id);
			}
			else {
				this.prevList.addAll(list);
				System.out.println("[Prev] Dodano listê id "+list+" do akcji o id "+this.id);
			}
		}	
	}
	
	public void addNextActionFromIndex (Integer newId, Set<Integer> potentialNextList) {
		for(Integer x : potentialNextList)
			if (x==id) {
				this.nextList.add(newId);
				System.out.println("[Next] Dodano akcjê o id "+newId+" do akcji o id "+this.id);
				break;
			}
		}
	
	public void calculateEarlyFinish () {
		this.earlyFinish=this.earlyStart.plus(this.time);
	}	
	
	public void calculateLateStart () {
		this.lateStart=this.lateFinish.minus(this.time);
	}
	
	public void calculateReserve () {
		this.reserve=this.lateStart.minus(this.earlyStart);
		if (this.reserve.isZero()) this.crytical=true;
		else this.crytical=false;
	}
	
	public Object[] getArrayRow() {
		Object[] array = {this.getId(),this.getName(),this.getTime(),this.getPrevList(),this.getNextList(),this.getReserve(),this.isCrytical()};
		return array;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public Duration getEarlyStart() {
		return earlyStart;
	}

	public void setEarlyStart(Duration earlyStart) {
		this.earlyStart = earlyStart;
	}

	public Duration getLateStart() {
		return lateStart;
	}

	public Duration getEarlyFinish() {
		return earlyFinish;
	}

	public Duration getLateFinish() {
		return lateFinish;
	}

	public void setLateFinish(Duration lateFinish) {
		this.lateFinish = lateFinish;
	}

	public Duration getReserve() {
		return reserve;
	}

	public boolean isCrytical() {
		return crytical;
	}
	
	public Set<Integer> getPrevList() {
		return prevList;
	}

	public void setPrevList(Set<Integer> prevList) {
		this.prevList = prevList;
	}
	
	public Set<Integer> getNextList() {
		return nextList;
	}

	public void setnextList(Set<Integer> nextList) {
		this.nextList = nextList;
	}
	
	@Override
	public String toString() {
		return("Id: "+id+", "+name+" o czasie "+time);
	}
	
	@Override
	public int hashCode() {
	    int hash = this.id;
	    return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    CPMActivity activity = (CPMActivity) obj;
	    return Objects.equals(id, activity.id);
	}

	@Override
	public int compareTo(CPMActivity other) {
		int value = this.getId() - other.getId();
		return value;
	}
}