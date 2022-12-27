import manager.AppManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;

import java.io.File;
import java.io.IOException;

public final class Main {

    private Main() { }

    /**
     * Main function
     * @param args
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        File inputFile = new File(args[0]);

        File outputFile = new File(args[1]);
        outputFile.createNewFile();

        ObjectMapper objectMapper = new ObjectMapper();
        Input input = objectMapper.readValue(inputFile, Input.class);
        ArrayNode output = objectMapper.createArrayNode();

        AppManager.initiateApp(input, output);

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(outputFile, output);
    }
}
