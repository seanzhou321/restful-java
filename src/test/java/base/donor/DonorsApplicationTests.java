package base.donor;

import static org.springframework.test.util.AssertionErrors.assertNotNull;

import base.donor.controller.DonorController;
import base.donor.controller.HateoasDonorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DonorsApplicationTests {

	@Autowired
	private DonorController donorController;

	@Autowired
	private HateoasDonorController hateoasDonorController;

	@Test
	void contextLoads() {
		assertNotNull("donorController should hbe in context.", donorController);
		assertNotNull("hateoasDonorController should hbe in context.", hateoasDonorController);
	}

}
