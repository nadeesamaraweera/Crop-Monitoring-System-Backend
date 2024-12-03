package lk.ijse.crop_monitoring_systembackend;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CropMonitoringSystemBackend {

	public static void main(String[] args) {
		SpringApplication.run(CropMonitoringSystemBackend.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
