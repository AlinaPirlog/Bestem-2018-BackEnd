package training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import training.demo.model.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    void deleteReviewByReviewId(int id);

    Review findByReviewId(int id);

    List<Review> findAll();
}