package mate.academy.winetaster.repository.wine;

import java.util.List;
import mate.academy.winetaster.model.Wine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface WineRepository extends JpaRepository<Wine, Long>, JpaSpecificationExecutor<Wine> {
    @Query("SELECT b FROM Wine b JOIN b.categories c WHERE c.id = :categoryId")
    List<Wine> findAllByCategoryId(Long categoryId);

    List<Wine> findAll();

    boolean existsByRegion(String region);
}
