package tamit0.mappingdata;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tamit0.mappingdata.Services.ThreadReadAllDataAPI;

@SpringBootApplication
public class MappingdataApplication {
  public static void main(String[] args) throws IOException {
    
    SpringApplication.run(MappingdataApplication.class, args);
    new ThreadReadAllDataAPI().start();
  }
}