package com.ase.attendance.network;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Value("${attendance.exchange}")
    private String attendanceExchange;
    private final RabbitMQConfigurationHelper configHelper = new RabbitMQConfigurationHelper();

    @Bean(name = "attendance.exchange")
    public FanoutExchange attendanceExchange() {
        return configHelper.getExchange(attendanceExchange);
    }
}
