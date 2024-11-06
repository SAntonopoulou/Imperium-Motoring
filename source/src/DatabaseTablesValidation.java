import java.util.Set;
import java.util.HashSet;

public class DatabaseTablesValidation {
	private static final Set<String> allowedTables = new HashSet<>();

	static {
		allowedTables.add("users");
		allowedTables.add("saltpw");
		allowedTables.add("salts");
		allowedTables.add("admin");
		allowedTables.add("vehicles");
	}

	public static boolean isValidTable(String tableName) {
		return allowedTables.contains(tableName.toLowerCase());
	}
	
}	
