package com.bank.abc.simdata.repositories;

import com.bank.abc.simdata.models.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    @Query("SELECT v FROM Voucher v INNER JOIN v.user u WHERE u.phoneNumber = :phoneNumber")
    List<Voucher> getAllByUserPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
