package com.gladunalexander.clientservice.repository;

import com.gladunalexander.clientservice.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author Alexander Gladun
 */
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query(value = "SELECT client FROM Client client " +
                   "WHERE (:firstName   IS NULL OR client.firstName =:firstName)" +
                   "AND   (:lastName    IS NULL OR client.lastName =:lastName) " +
                   "AND   (:middleName  IS NULL OR client.middleName =:middleName) " +
                   "AND   (:email       IS NULL OR client.email =:email) " +
                   "AND   (:phoneNumber IS NULL OR client.phoneNumber =:phoneNumber)")
    List<Client> findClientsByFilter(@Param("firstName") String firstName,
                                     @Param("lastName") String lastName,
                                     @Param("middleName") String middleName,
                                     @Param("email") String email,
                                     @Param("phoneNumber") String phoneNumber);

    List<Client> findByEmailOrPhoneNumber(String email, String phoneNumber);
}
