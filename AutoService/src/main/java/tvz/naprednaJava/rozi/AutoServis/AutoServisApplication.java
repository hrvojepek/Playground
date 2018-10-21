package tvz.naprednaJava.rozi.AutoServis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class AutoServisApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(AutoServisApplication.class, args);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/templates/css/");
        registry.addResourceHandler("/icons/**").addResourceLocations("classpath:/templates/icons/");
        //registry.addResourceHandler("/dist/**").addResourceLocations("classpath:/templates/dist/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/templates/img/");
        //registry.addResourceHandler("/bower_components/**").addResourceLocations("classpath:/templates/bower_components/");
        //registry.addResourceHandler("/js/**").addResourceLocations("classpath:/templates/js/");
    }
}
