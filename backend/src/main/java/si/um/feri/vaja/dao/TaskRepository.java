package si.um.feri.vaja.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import si.um.feri.vaja.vao.Task;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEndDate(LocalDate endDate);
    List<Task> findByPriority(Integer priority);
    List<Task> findByEndDateAndPriority(LocalDate endDate, Integer priority);
    List<Task> findByTitleContainingIgnoreCase(String title);
    List<Task> findByTitleContainingIgnoreCaseAndEndDate(String title, LocalDate endDate);
    List<Task> findByTitleContainingIgnoreCaseAndPriority(String title, Integer priority);
    List<Task> findByTitleContainingIgnoreCaseAndEndDateAndPriority(String title, LocalDate endDate, Integer priority);
}

