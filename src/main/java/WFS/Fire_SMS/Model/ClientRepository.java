package WFS.Fire_SMS.Model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<client, Long> {
    List<client> findByLpnumberAndPnumber(String A, String B);

    List<client> findByPnumber(String A);

}
