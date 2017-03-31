package cz.janhrcek.tifi.util;


import cz.janhrcek.tifi.model.Categories;
import cz.janhrcek.tifi.model.Expense;
import cz.janhrcek.tifi.storage.Storage;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class StorageLoader {

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void fromFileToStorage(Path from, Storage to) {
        try (Scanner sc = new Scanner(from)) {
            while (sc.hasNextLine()) {
                String li = sc.nextLine();
                to.addExpense(buildExpense(li.split("\\|", -1)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Expense buildExpense(String[] ar) {
        if (ar == null || ar.length != 5) {
            throw new IllegalArgumentException("Expecting array of length 5 but was" + Arrays.toString(ar));
        }

        String date = ar[0], amount = ar[1], cat = ar[2], subcat = ar[3], desc = ar[4];
        Expense e = new Expense(dateFromStr(date), Integer.parseInt(amount), cat, subcat, desc);
        subcat = "-".equals(subcat) ? null : subcat;

        if (!Categories.INSTANCE.getCategories().contains(cat)) {
            System.err.println("Unknown cat: " + cat + " for " + e);
        }

        if (subcat != null && !Categories.INSTANCE.getSubcategories(cat).contains(subcat)) {
            System.err.println("Unkwnown subcat: " + subcat + " for " + e);
        }
        return e;
    }

    private LocalDate dateFromStr(String s) {
        return LocalDate.parse(s, dateFormatter);
    }
}
