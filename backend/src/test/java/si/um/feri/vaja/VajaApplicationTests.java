/*package si.um.feri.vaja;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import si.um.feri.vaja.dao.TaskRepository;
import si.um.feri.vaja.rest.VajaController;
import si.um.feri.vaja.vao.Task;

import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VajaApplicationTests {

	@Autowired
	private VajaController controller;

	@Autowired
	private TaskRepository taskRepository;

	@Test
	@BeforeEach
	void cleanUp() {

		taskRepository.deleteAll();
	}

	@Test
	void testGetTaskById() {
		Task task = new Task("Test Task", LocalDate.now(), "Sample task", "In Progress", 3);
		Task savedTask = controller.createTask(task);

		assertTrue(controller.getTaskById(savedTask.getId()).isPresent());
	}

	@RepeatedTest(5)
	void testCreateTask() {
		Task task = new Task("New Task", LocalDate.now().plusDays(3), "Another task", "Not Started", 1);
		Task createdTask = controller.createTask(task);

		assertNotNull(createdTask.getId());
	}



	@Test
	void testDeleteTask() {
		Task task = new Task("Task to delete", LocalDate.now().plusDays(2), "Sample task", "In Progress", 3);
		Task savedTask = controller.createTask(task);
		controller.deleteTask(savedTask.getId());

		assertFalse(controller.getTaskById(savedTask.getId()).isPresent());
	}


	@Test
	void testFilterTasksNoMatch() {
		List<Task> noTasks = controller.getFilteredTasks(null, null, null);
		assertTrue(noTasks.isEmpty());
	}


	@Test
	void testGetAllTasksWhenEmpty() {
		taskRepository.deleteAll();
		Assertions.assertEquals(0,taskRepository.count());

	}
	@Test
	void testGetAllTasksWhenEmpty2() {
		taskRepository.deleteAll();
		Assertions.assertEquals(1,taskRepository.count());
	}
}
