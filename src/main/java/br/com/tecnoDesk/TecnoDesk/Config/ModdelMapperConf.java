package br.com.tecnoDesk.TecnoDesk.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ModdelMapperConf  {
@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
