package si.um.feri.vaja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import si.um.feri.vaja.vao.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
