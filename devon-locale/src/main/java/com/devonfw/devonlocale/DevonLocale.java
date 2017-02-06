package com.devonfw.devonlocale;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.devonfw.devonlocale.common.Constant;
import com.devonfw.devonlocale.common.Node;
import com.devonfw.devonlocale.translator.TranslationSource;
import com.devonfw.devonlocale.translator.TranslationSourceImpl;
import com.devonfw.devonlocale.translator.TranslationTarget;

/**
 * This is a main class. Here we will read property file may be from console or input file. We will first convert it to
 * map<String,Node>. Further we will convert map to output target format.
 *
 * @author ssarmoka
 */
public class DevonLocale {

  private TargetAdapterFactory factory;

  private TranslationSource translationSource;

  private TranslationTarget translationTarget;

  /**
   * The constructor.
   */
  public DevonLocale() {
    this.translationSource = new TranslationSourceImpl();
    this.factory = new TargetAdapterFactory();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {

    String outputFormat;
    Map<String, Node> propertyTreeMap;
    System.out.println("Devon Locale");
    DevonLocale locale = new DevonLocale();

    CommandLineParser parser = new BasicParser();
    try {
      CommandLine cmd = parser.parse(locale.getOptions(), args);
      if (cmd.hasOption(Constant.INPUT)) {
        String input = cmd.getOptionValue(Constant.INPUT);
        outputFormat = cmd.getOptionValue(Constant.OUTFORMAT);
        locale.translationTarget = locale.factory.getTranslationTarget(outputFormat);
        if (input.contains(".properties") && new File(input).exists()) {
          propertyTreeMap = locale.translationSource.parseFile(new File(input));

        } else {
          // parse commandline
          InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.ISO_8859_1));
          propertyTreeMap = locale.translationSource.parseStream(stream);
        }

        locale.generateOutput(outputFormat, cmd, propertyTreeMap);
      } else {
        System.out.println("ERROR:: Translation source not present");
      }

    } catch (ParseException e) {

      System.out.println("ERROR:: " + e.getMessage());
    }

  }

  /**
   * @return
   */
  private Options getOptions() {

    Options options = new Options();
    options.addOption("i", Constant.INPUT, true, "File containing source translation");
    options.addOption("f", Constant.INFORMAT, true, "Format of the source translation. Possible values: java ");
    options.addOption("o", Constant.OUTPUT, true, "File with target translation");
    options.addOption("t", Constant.OUTFORMAT, true,
        "Format of the target translation Possible values: extjs or angular(converts to json as of now)");
    return options;
  }

  private void generateOutput(String outputFormat, CommandLine cmd, Map<String, Node> propertyTreeMap) {

    switch (outputFormat.toLowerCase()) {

    case Constant.ANGULAR:
      if (cmd.hasOption(Constant.OUTPUT)) {
        String outputFile = cmd.getOptionValue(Constant.OUTPUT);
        if (!new File(outputFile).exists()) {
          File resultFile = new File(outputFile);
          this.translationTarget.generateFile(propertyTreeMap, resultFile);
        } else {
          this.translationTarget.generateFile(propertyTreeMap, new File(outputFile));
        }

      } else {

        this.translationTarget.generateStream(propertyTreeMap, System.out);
      }
      break;

    case Constant.EXTJS:
      if (cmd.hasOption(Constant.OUTPUT)) {
        String outputFile = cmd.getOptionValue(Constant.OUTPUT);
        if (!new File(outputFile).exists()) {
          File resultFile = new File(outputFile);
          this.translationTarget.generateFile(propertyTreeMap, resultFile);
        } else {
          this.translationTarget.generateFile(propertyTreeMap, new File(outputFile));
        }

      } else {
        System.out.println("propertyTreeMap " + propertyTreeMap);
        this.translationTarget.generateStream(propertyTreeMap, System.out);
      }
      break;

    }
  }

  private void generateExtJsFile(String outputFormat, CommandLine cmd, Map<String, Node> propertyTreeMap) {

    if (cmd.hasOption(Constant.OUTPUT)) {
      String outputFile = cmd.getOptionValue(Constant.OUTPUT);
      if (!new File(outputFile).exists()) {
        File resultFile = new File(outputFile);
        this.translationTarget.generateFile(propertyTreeMap, resultFile);
      } else {
        this.translationTarget.generateFile(propertyTreeMap, new File(outputFile));
      }

    } else {
      System.out.println("propertyTreeMap " + propertyTreeMap);
      this.translationTarget.generateStream(propertyTreeMap, System.out);
    }

  }

}
