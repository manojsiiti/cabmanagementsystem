package librarysystem.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Library {
    private Map<String , BookStatus> bookStatusMap;

    public Library() {
        bookStatusMap = new HashMap<>();
    }

    public Map<String, BookStatus> getBookStatusMap() {
        return bookStatusMap;
    }
}
