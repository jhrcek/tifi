package cz.janhrcek.tifi.model;


import java.io.InputStream;
import java.util.*;

public class Categories {
    public static final Categories INSTANCE = new Categories();
    private final Map<String, List<String>> cat2subcats = new HashMap<>();
    private final ArrayList<String> sortedCats;

    private Categories() {
        InputStream is = getClass().getResourceAsStream("Categories");
        Scanner scan = new Scanner(is);
        String cat = null;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            if (line.charAt(0) == '\t') {
                cat2subcats.get(cat).add(line.trim());
            } else {
                cat = line;
                cat2subcats.put(cat, new ArrayList<>());
            }
        }

        // Ensure all cats and subcats are sorted.
        sortedCats = new ArrayList<>(cat2subcats.keySet());
        Collections.sort(sortedCats);
        cat2subcats.forEach((c, subcats) -> Collections.sort(subcats));
    }

    public List<String> getCategories() {
        return sortedCats;
    }

    public List<String> getSubcategories(String category) {
        return cat2subcats.get(category);
    }

    @Override
    public String toString() {
        return cat2subcats.toString();
    }
}
