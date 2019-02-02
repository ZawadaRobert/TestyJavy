import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Az {
	
	public static List<Integer> toIntegerList(String str) {
		List<Integer> list = Stream.of(str.split("\\s*,\\s*")).map(Integer::parseInt).collect(Collectors.toList());
		return list;
	}
	
	public static Duration toSeconds(String str) {
		Duration duration = Duration.ofSeconds(Integer.parseInt(str));
		return duration;
	}
	
	public static Duration toMinutes(String str) {
		Duration duration = Duration.ofMinutes(Integer.parseInt(str));
		return duration;
	}
	
	public static Duration toHours(String str) {
		Duration duration = Duration.ofHours(Integer.parseInt(str));
		return duration;
	}
	
	public static Duration toDays(String str) {
		Duration duration = Duration.ofDays(Integer.parseInt(str));
		return duration;
	}
	
}