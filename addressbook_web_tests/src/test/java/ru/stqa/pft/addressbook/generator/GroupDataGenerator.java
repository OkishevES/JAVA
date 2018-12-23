package ru.stqa.pft.addressbook.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.thoughtworks.xstream.XStream;
import ru.stqa.pft.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

    @Parameter(names = "-c", description = "Group count")
    public static int count;

    @Parameter(names = "-f", description = "Target file")
    public static String file;

    @Parameter(names = "-d", description = "Data format")
    public static String format;

    public static void main(String[] args) throws IOException {
        GroupDataGenerator generator = new GroupDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            jCommander.usage();
            return;
        }

        generator.run();
    }

    public void run() throws IOException {
        List<GroupData> groups = generateGroups(count);

    }

    private List<GroupData> generateGroups(int count) throws IOException {
        List<GroupData> groups = new ArrayList<GroupData>();
        for (int i = 0; i < count; i++) {
            groups.add(new GroupData()
                    .withName(String.format("group%s", i))
                    .withHeader(String.format("header%s", i))
                    .withFooter(String.format("footer%s", i)));
        }
        if (format.equals("csv")) {
            saveAsCsv(groups, new File(file));
        } else if (format.equals("xml")) {
            saveAsXml(groups, new File(file));
        } else {
            System.out.println("Unrecognized format");
        }
        return groups;
    }

    private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
        try (Writer writer = new FileWriter(file)) {
            for (GroupData group : groups) {
                writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
            }
        }
    }

    private void saveAsXml(List<GroupData> groups, File file) throws IOException {
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        String xml = xstream.toXML(groups);
        try (Writer writer = new FileWriter(file)) {
            writer.write(xml);
        }
    }
}
