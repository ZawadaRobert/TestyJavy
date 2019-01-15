import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CPMActivity {
	private Integer id;
	private String name;
	private Duration time, earlyStart, lateStart, earlyFinish, lateFinish, reserve;
	private boolean crytical;
	private Set<Integer> prevList, nextList;
	
	
	public CPMActivity (Integer id, String name) {
		this.setId(id);
		this.setName(name);
		this.prevList = new HashSet<Integer>();
		this.nextList = new HashSet<Integer>();
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
	
	public void addPrevActionFromString (String prevString) {
		List<Integer> list = Stream.of(prevString.split("\\s*,\\s*")).map(Integer::parseInt).collect(Collectors.toList());
		
		if (list.contains(this.id)) {
			System.out.println("[Prev] Nie uda³o siê dodaæ listy id "+list+" do akcji o id "+this.id);
		}
		else {
			this.prevList.addAll(list);
			System.out.println("[Prev] Dodano listê id "+list+" do akcji o id "+this.id);
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
	
	public void calculateStart () {
		this.lateStart=this.earlyStart.plus(this.time);
	}
	
	public void calculateFinish () {
		this.earlyFinish=this.lateFinish.minus(this.time);
	}
	
	public void calculateReserve () {
		this.reserve=this.lateStart.minus(this.earlyStart);
	}
	
	public void calculateCrytical () {
		if (this.reserve.isZero()) this.crytical=true;
		else this.crytical=false;
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
	
	public String toString() {
		return("Id: "+id+", "+name+" o czasie "+time);
	}
}