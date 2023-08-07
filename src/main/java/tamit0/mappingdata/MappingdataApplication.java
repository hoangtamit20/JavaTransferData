package tamit0.mappingdata;
import java.io.IOException;

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