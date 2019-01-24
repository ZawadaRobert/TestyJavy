import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Az {
	
	public static List<Integer> toIntegerList(String str) {
		List<Integer> list = Stream.of(str.split("\\s*,\\s*")).map(Integer::parseInt).collect(Collectors.toList());
		return list;
	}
	
}