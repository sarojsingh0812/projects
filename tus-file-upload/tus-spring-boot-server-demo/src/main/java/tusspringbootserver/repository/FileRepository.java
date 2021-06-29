package tusspringbootserver.repository;

import tusspringbootserver.model.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, Long>{

}
