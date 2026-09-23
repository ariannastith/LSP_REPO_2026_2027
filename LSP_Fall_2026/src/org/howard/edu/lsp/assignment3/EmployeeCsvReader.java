package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Extract step: reads the employee CSV file and hands back its data rows.
 *
 * <p>The reader knows that the first line of the file is a header and drops it,
 * so callers only see rows that are meant to be transformed. It does not
 * interpret the rows themselves; that is {@link EmployeeRowParser}'s job.
 *
 * @author Arianna Stith
 */
public class EmployeeCsvReader {

    /** File this reader reads from. */
    private final Path source;

    /**
     * @param source path of the input CSV file
     */
    public EmployeeCsvReader(Path source) {
        this.source = source;
    }

    /**
     * Reads every data row of the input file.
     *
     * @return the lines after the header, in file order
     * @throws IOException if the file is missing or cannot be read
     */
    public List<String> readDataRows() throws IOException {
        List<String> rows = new ArrayList<>();
        try (BufferedReader reader =
                     Files.newBufferedReader(source, StandardCharsets.UTF_8)) {
            boolean headerSeen = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (headerSeen) {
                    rows.add(line);
                } else {
                    headerSeen = true;
                }
            }
        }
        return rows;
    }

    /**
     * @return the path this reader reads from
     */
    public Path getSource() {
        return source;
    }
}
