package org.hillel.service;

import org.hillel.persistence.entity.VehicleEntity;
import org.hillel.persistence.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.Optional;

@Service
public class TransactionalVehicleService extends AbstractTransactionalService<VehicleEntity, Long>{

    private final VehicleRepository vehicleRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

//    @PersistenceContext
//    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public TransactionalVehicleService(VehicleRepository vehicleRepository) {
        super(vehicleRepository);
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public VehicleEntity createOrUpdate(VehicleEntity vehicle){
        return vehicleRepository.createOrUpdate(vehicle);
//        EntityManager em = entityManagerFactory.createEntityManager();
//        em.getTransaction().begin();
//        vehicleRepository.createOrUpdate(vehicle);
//        em.getTransaction().commit();
//        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
//        final TransactionStatus transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);
//        VehicleEntity vehicleEntity = vehicleRepository.createOrUpdate(vehicle);
//        platformTransactionManager.commit(transactionStatus);
//        return vehicleEntity;
    }

    @Transactional
    public void remove(VehicleEntity vehicle){
        vehicleRepository.remove(vehicle);
    }

    @Transactional
    public void removeById(Long id){
        vehicleRepository.removeById(id);
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findByIds(Long... ids){
        return vehicleRepository.findByIds(ids);
    }

    @Transactional(readOnly = true)
    public Optional<VehicleEntity> findById(Long id, boolean withDep){
        final Optional<VehicleEntity> vehicle = vehicleRepository.findById(id);
        if (!vehicle.isPresent()) return vehicle;
        if (!withDep) return vehicle;
        vehicle.get().getJourneys().size();
        vehicle.get().getSeatInfos().size();
        return vehicle;
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findAllByName(String name){
        return vehicleRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findWithMinFreeSeats(){
        return vehicleRepository.findWithMinFreeSeats();
    }

    @Transactional(readOnly = true)
    public Collection<VehicleEntity> findWithMaxFreeSeats(){
        return vehicleRepository.findWithMaxFreeSeats();
    }
}
