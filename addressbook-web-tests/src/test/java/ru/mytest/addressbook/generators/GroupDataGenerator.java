package ru.mytest.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.mytest.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")
  public int count;

  @Parameter(names = "-f", description = "Target file")
  public String file;

  @Parameter(names = "-d", description = "Data format")
  public String format;

  public static void main(String[] args) throws IOException {
    //we get two parameters - count of group and file path to which data to generate
    GroupDataGenerator generator = new GroupDataGenerator();
    JCommander jCommander = new JCommander(generator); //jcommander is library for work with command line
    try {
      jCommander.parse(args);  // to get some args from command line and try to parse
    } catch (ParameterException ex) {
      jCommander.usage(); //if parameters are incorrect, jcommander sends how to use this GroupDataGenerator program
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<GroupData> groups = generateGroups(count);
    if (format.equals("csv")) {
      saveAsCsv(groups, new File(file));
    } else if (format.equals("xml")) {
      saveAsXml(groups, new File(file));
    } else if (format.equals("json")) {
      saveAsJson(groups, new File(file));
      {
        System.out.println("Unrecognized format " + format);
      }
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    //Gson gson = new Gson(); //create gson, but it will have not very good format, not convenient for reading
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    //GsonBuilder will create gson in good format
    String json = gson.toJson(groups);
    try (Writer writer = new FileWriter(file)) { //init writer
      writer.write(json); //using of writer, writer will close automatically after try-block is finished
    }
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xStream = new XStream();
    xStream.processAnnotations(GroupData.class); //to read annotations in GroupData.class
    String xml = xStream.toXML(groups); //to convert object to XML format
    try (Writer writer = new FileWriter(file)) { //init of writer
      writer.write(xml);  //using of writer, writer will close automatically after try-block is finished
    }
  }

  private void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    System.out.println(new File(".").getAbsolutePath());
    try (Writer writer = new FileWriter(file)) { //init writer
      for (GroupData group : groups) {
        //to write in CSV format (comma-separated-values)
        writer.write(String.format("%s;%s;%s\n", group.getGrname(), group.getGrheader(), group.getGrfooter()));
      }
    }
  }

  private List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("group %s", i))
              .withHeader(String.format("header %s", i)).withFooter(String.format("footer %s", i)));
    }
    return groups;
  }
}
