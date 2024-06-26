package com.ase.notification;

import com.ase.notification.integration.rabbitMQConfiguration.RabbitMQBookmarkedConfiguration;
import com.ase.notification.integration.rabbitMQConfiguration.RabbitMQEventConfiguration;
import com.ase.notification.integration.rabbitMQConfiguration.RabbitMQRegisteredConfiguration;
import com.ase.notification.integration.rabbitMQConfiguration.RabbitMQSendNotificationConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class NotificationApplicationTests {

	// mock the rabbitmq configurations to not get warnings about them not being able to connect
	@MockBean
	RabbitMQEventConfiguration eventConfiguration;

	@MockBean
	RabbitMQBookmarkedConfiguration bookmarkedConfiguration;

	@MockBean
	RabbitMQRegisteredConfiguration registeredConfiguration;

	@MockBean
	RabbitMQSendNotificationConfiguration sendNotificationConfiguration;

	@Test
	void contextLoads() {
	}

}
