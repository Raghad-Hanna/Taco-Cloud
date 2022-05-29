package com.raghad.Taco.Cloud;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

// guides JUnit to run the tests with custom testing behavior
// creates the application context to let JUnit tests be run against it
@RunWith(SpringRunner.class)
// tells JUnit to bootstrap the tests with spring boot capabilities
@SpringBootTest
class TacoCloudApplicationTests {
	// makes sure the context can load correctly
	@Test
	void contextLoads() {
	}

}
